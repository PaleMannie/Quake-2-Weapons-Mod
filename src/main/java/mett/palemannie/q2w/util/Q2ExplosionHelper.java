package mett.palemannie.q2w.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

public final class Q2ExplosionHelper {

    private Q2ExplosionHelper() {}

    public static final float HANDGRENADE_DAMAGE = Q2WConfigStats.HandGrenadeDamage;
    public static final double HANDGRENADE_RADIUS = Q2WConfigStats.HandGrenadeRadius;

    public static final float GRENADELAUNCHER_DAMAGE = Q2WConfigStats.GrenadelauncherDamage;
    public static final double GRENADELAUNCHER_RADIUS = Q2WConfigStats.GrenadelauncherRadius;

    public static final float ROCKET_DAMAGE = Q2WConfigStats.RocketlauncherDamage;
    public static final double ROCKET_RADIUS = Q2WConfigStats.RocketlauncherRadius;

    public static void handgrenadeExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center) {

        DamageSource source = level.damageSources().source(ModDamageTypes.HANDGRENADE_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, HANDGRENADE_DAMAGE, HANDGRENADE_RADIUS, source);
    }

    public static void grenadelauncherExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center) {

        DamageSource source = level.damageSources().source(ModDamageTypes.GRENADELAUNCHER_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, GRENADELAUNCHER_DAMAGE, GRENADELAUNCHER_RADIUS, source);
    }

    public static void rocketExplosion(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity attacker, Vec3 center) {

        DamageSource source = level.damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, inflictor, attacker);
        q2RadiusDamage(level, inflictor, center, ROCKET_DAMAGE, ROCKET_RADIUS, source);
    }

    public static void q2RadiusDamage(ServerLevel level, Entity inflictor, Vec3 center, float maxDamage, double radius, DamageSource damageSource) {

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

            target.hurt(damageSource, damage);
            applyQ2Knockback(target, center, falloff);
        }
    }

    private static Vec3 closestPointOnBox(AABB box, Vec3 point) {

        return new Vec3(Mth.clamp(point.x, box.minX, box.maxX), Mth.clamp(point.y, box.minY, box.maxY), Mth.clamp(point.z, box.minZ, box.maxZ));
    }

    private static boolean hasLooseLineOfSight(ServerLevel level, Vec3 center, LivingEntity target) {

        Vec3 body = target.getBoundingBox().getCenter();
        Vec3 eyes = target.getEyePosition();
        Vec3 feet = target.position().add(0d, 0.15d, 0d);

        return clearPath(level, center, body) || clearPath(level, center, eyes) || clearPath(level, center, feet);
    }

    private static boolean clearPath(ServerLevel level, Vec3 from, Vec3 to) {

        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));

        return hit.getType() == HitResult.Type.MISS;
    }

    private static void applyQ2Knockback(LivingEntity target, Vec3 center, double falloff) {

        Vec3 targetCenter = target.getBoundingBox().getCenter();
        Vec3 dir = targetCenter.subtract(center);

        if (dir.lengthSqr() < 1e-7d) {
            dir = new Vec3(0d, 1d, 0d);
        } else {
            dir = dir.normalize();
        }

        double strength = 0.85d * falloff;

        target.push(dir.x * strength, Math.max(0.18d, dir.y * strength + 0.15d), dir.z * strength);

        target.hurtMarked = true;
    }
}