package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.ExplosionImpulseS2CPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * QuakeWorld splash damage and impulse adapted to Minecraft.
 * Based on Quake's T_RadiusDamage behavior (120 - distance / 2).
 */
public final class QWExplosionHelper {

    private static final double QUAKE_SPLASH_DAMAGE = 120.0D;
    private static final double QUAKE_SPLASH_RADIUS = QUAKE_SPLASH_DAMAGE + 40.0D;
    private static final double IMPULSE_PER_POINT = 8.0D / (32.0D * 20.0D);
    private static final double TRACE_OFFSET = 15.0D / 32.0D;

    private QWExplosionHelper() {}

    public static void handGrenadeExplosion(ServerLevel level, @Nullable Entity projectile,
                                            @Nullable Entity owner, Vec3 center,
                                            @Nullable Entity quadApplier, boolean overcooked) {
        DamageSource source = level.damageSources().source(
                overcooked ? ModDamageTypes.HANDGRENADE_OVERCOOK_DAMAGE : ModDamageTypes.HANDGRENADE_DAMAGE,
                projectile, owner);
        radiusDamage(level, projectile, owner, center, Q2WConfigStats.HandGrenadeDamage,
                Q2WConfigStats.HandGrenadeRadius, source, quadApplier);
    }

    public static void grenadeLauncherExplosion(ServerLevel level, @Nullable Entity projectile,
                                                @Nullable Entity owner, Vec3 center,
                                                @Nullable Entity quadApplier) {
        DamageSource source = level.damageSources().source(
                ModDamageTypes.GRENADELAUNCHER_DAMAGE, projectile, owner);
        radiusDamage(level, projectile, owner, center, Q2WConfigStats.GrenadelauncherDamage,
                Q2WConfigStats.GrenadelauncherRadius, source, quadApplier);
    }

    public static void rocketExplosion(ServerLevel level, @Nullable Entity projectile,
                                       @Nullable Entity owner, Vec3 center,
                                       @Nullable Entity quadApplier) {
        DamageSource source = level.damageSources().source(
                ModDamageTypes.ROCKETLAUNCHER_DAMAGE, projectile, owner);
        radiusDamage(level, projectile, owner, center, Q2WConfigStats.RocketlauncherDamage,
                Q2WConfigStats.RocketlauncherRadius, source, quadApplier);
    }

    public static void radiusDamage(ServerLevel level, @Nullable Entity inflictor,
                                    @Nullable Entity owner, Vec3 center, float maxDamage,
                                    double radius, DamageSource source,
                                    @Nullable Entity quadApplier) {
        if (!Double.isFinite(radius) || radius <= 0.0D || !Float.isFinite(maxDamage)) return;

        float quad = Q2WConfigStats.applyQuadDamage(1.0F, quadApplier);
        boolean quadHandledByEvent = source.getEntity() instanceof LivingEntity attacker
                && attacker.hasEffect(ModEffects.QUAD_DAMAGE.getHolder().get());
        float damageScale = Math.max(0.0F, maxDamage) * (quadHandledByEvent ? 1.0F : quad);
        double selfDamageMultiplier = Q2WConfig.COMMON.explosionSelfDamageMultiplier.get();
        AABB area = new AABB(center, center).inflate(radius);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
            if (target == inflictor || target.isSpectator()) continue;
            if (target instanceof Player player && player.getAbilities().flying) continue;

            double distance = target.getBoundingBox().getCenter().distanceTo(center);
            if (distance > radius || !canDamage(level, center, target)) continue;

            double falloff = quakeFalloff(distance, radius);
            double damageMultiplier = target == owner ? selfDamageMultiplier : 1.0D;
            if (damageScale > 0.0F && damageMultiplier > 0.0D) {
                target.hurtServer(level, source,
                        (float) (damageScale * falloff * damageMultiplier));
            }

            applyImpulse(target, center, falloff, target == owner, quad);
        }
    }

    /** QW movement for explosions such as the BFG that keep a custom damage model. */
    public static void applyExplosionImpulse(ServerLevel level, LivingEntity target, Vec3 center, double radius,
                                             @Nullable Entity owner, @Nullable Entity quadApplier) {
        if (!Double.isFinite(radius) || radius <= 0.0D) return;
        double distance = target.getBoundingBox().getCenter().distanceTo(center);
        if (distance > radius || !canDamage(level, center, target)) return;

        applyImpulse(target, center, quakeFalloff(distance, radius), target == owner,
                Q2WConfigStats.applyQuadDamage(1.0F, quadApplier));
    }

    private static double quakeFalloff(double distance, double radius) {
        return 1.0D - 0.5D * QUAKE_SPLASH_RADIUS * (distance / radius) / QUAKE_SPLASH_DAMAGE;
    }

    private static void applyImpulse(LivingEntity target, Vec3 center, double falloff,
                                     boolean selfBlast, float quad) {
        Vec3 velocity = target.getDeltaMovement();
        double impulsePoints = QUAKE_SPLASH_DAMAGE * falloff * (selfBlast ? 0.5D : 1.0D);
        double knockbackMultiplier = target instanceof Mob ? 0.1D : 1.0D;
        Vec3 impulse = quakeOrigin(target).subtract(center).normalize()
                .scale(impulsePoints * quad * IMPULSE_PER_POINT * knockbackMultiplier);

        target.setDeltaMovement(velocity.add(impulse));
        if (impulse.y > 0.0D) target.setOnGround(false);
        if (target instanceof ServerPlayer player) {
            ModMessages.sendToPlayer(new ExplosionImpulseS2CPacket(impulse), player);
        } else {
            target.hurtMarked = true;
        }
    }

    private static Vec3 quakeOrigin(LivingEntity target) {
        return target.position().add(0.0D, target.getBbHeight() * (24.0D / 56.0D), 0.0D);
    }

    private static boolean canDamage(ServerLevel level, Vec3 center, LivingEntity target) {
        Vec3 origin = quakeOrigin(target);
        if (clearPath(level, center, origin, target)) return true;
        for (int x = -1; x <= 1; x += 2) {
            for (int z = -1; z <= 1; z += 2) {
                if (clearPath(level, center,
                        origin.add(x * TRACE_OFFSET, 0.0D, z * TRACE_OFFSET), target)) return true;
            }
        }
        return false;
    }

    private static boolean clearPath(ServerLevel level, Vec3 from, Vec3 to, LivingEntity target) {
        return level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, target)).getType() == HitResult.Type.MISS;
    }
}
