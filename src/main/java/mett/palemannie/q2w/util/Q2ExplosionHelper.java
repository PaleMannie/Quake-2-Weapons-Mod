package mett.palemannie.q2w.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class Q2ExplosionHelper {

    /// Custom made explosion handler since vanillas damage falloff is not ideal

    private Q2ExplosionHelper() {}

    public static final float HANDGRENADE_DAMAGE = Q2WConfigStats.HandGrenadeDamage;
    public static final double HANDGRENADE_RADIUS = Q2WConfigStats.HandGrenadeRadius;

    public static final float GRENADELAUNCHER_DAMAGE = Q2WConfigStats.GrenadelauncherDamage;
    public static final double GRENADELAUNCHER_RADIUS = Q2WConfigStats.GrenadelauncherRadius;

    public static final float ROCKET_DAMAGE = Q2WConfigStats.RocketlauncherDamage;
    public static final double ROCKET_RADIUS = Q2WConfigStats.RocketlauncherRadius;

    public static void handgrenadeExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center, Entity quadapply, boolean isOvercook) {

        DamageSource source = level.damageSources().source(ModDamageTypes.HANDGRENADE_DAMAGE, inflictor, attacker);
        DamageSource source2 = level.damageSources().source(ModDamageTypes.HANDGRENADE_OVERCOOK_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, Q2WConfigStats.applyQuadDamage(HANDGRENADE_DAMAGE, quadapply), HANDGRENADE_RADIUS, isOvercook ? source2 : source, quadapply);
    }

    public static void grenadelauncherExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center, Entity quadapply) {

        DamageSource source = level.damageSources().source(ModDamageTypes.GRENADELAUNCHER_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, Q2WConfigStats.applyQuadDamage(GRENADELAUNCHER_DAMAGE, quadapply), GRENADELAUNCHER_RADIUS, source, quadapply);
    }

    public static void rocketExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center, Entity quadapply) {

        DamageSource source = level.damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, Q2WConfigStats.applyQuadDamage(ROCKET_DAMAGE, quadapply), ROCKET_RADIUS, source, quadapply);
    }

    public static void q2RadiusDamage(ServerLevel level, Entity inflictor, Vec3 center, float maxDamage, double radius, DamageSource damageSource) {
        q2RadiusDamage(level, inflictor, center, maxDamage, radius, damageSource, null);
    }

    private static void q2RadiusDamage(ServerLevel level, Entity inflictor, Vec3 center, float maxDamage, double radius, DamageSource damageSource, @Nullable Entity owner) {

        AABB area = new AABB(center, center).inflate(radius);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
            if (target == inflictor) {
                continue;
            }

            Vec3 closestPoint = closestPointOnBox(target.getBoundingBox(), center);
            double distance = closestPoint.distanceTo(center);

            if (distance > radius) {
                continue;
            }

            double falloff = 1d - distance / radius;
            falloff = Mth.clamp(falloff, 0d, 1d);

            float damage = (float) (maxDamage * falloff);

            if (damage <= 0f) {
                continue;
            }

            if (!hasLooseLineOfSight(level, center, target)) {
                damage *= 0.35f;
            }

            if (damage <= 0f) {
                continue;
            }

            target.hurtServer(level, damageSource, damage);
            applyQ2Knockback(target, center, falloff, target == owner && target instanceof Player);
        }
    }

    private static Vec3 closestPointOnBox(AABB box, Vec3 point) {

        return new Vec3(Mth.clamp(point.x, box.minX, box.maxX), Mth.clamp(point.y, box.minY, box.maxY), Mth.clamp(point.z, box.minZ, box.maxZ));
    }

    private static boolean hasLooseLineOfSight(ServerLevel level, Vec3 center, LivingEntity target) {

        Vec3 body = target.getBoundingBox().getCenter();
        Vec3 eyes = target.getEyePosition();
        Vec3 feet = target.position().add(0d, 0.15d, 0d);

        return clearPath(level, center, body, target) || clearPath(level, center, eyes, target) || clearPath(level, center, feet, target);
    }

    private static boolean clearPath(ServerLevel level, Vec3 from, Vec3 to, Entity entity) {

        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));

        return hit.getType() == HitResult.Type.MISS;
    }

    public static void applyQ2Knockback(LivingEntity target, Vec3 center, double falloff) {
        applyQ2Knockback(target, center, falloff, false);
    }

    private static void applyQ2Knockback(LivingEntity target, Vec3 center, double falloff, boolean selfBlast) {

        Vec3 targetCenter = target.getBoundingBox().getCenter();
        Vec3 dir = targetCenter.subtract(center);

        if (dir.lengthSqr() < 1e-7d) {
            dir = new Vec3(0d, 1d, 0d);
        } else {
            dir = dir.normalize();
        }

        double strength = (selfBlast ? 1.35d : 0.85d) * falloff;
        double lift = selfBlast
                ? Math.max(0.35d * falloff, dir.y * strength + 0.3d * falloff)
                : Math.max(0.18d, dir.y * strength + 0.15d);

        // Preserve air speed so successive blasts can build momentum.
        target.push(dir.x * strength, lift, dir.z * strength);
        if (selfBlast) target.setOnGround(false);

        target.hurtMarked = true;
    }
}
