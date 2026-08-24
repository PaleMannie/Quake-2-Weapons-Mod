package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ModDamageTypes;
import mett.palemannie.q2w.util.Q2WConfigStats;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Bfg10kProjectileEntity extends Projectile {

    private static final EntityDataAccessor<Boolean> EXPLODING =
            SynchedEntityData.defineId(Bfg10kProjectileEntity.class, EntityDataSerializers.BOOLEAN);


    public static final float SPEED_BLOCKS_PER_TICK = 0.625f;
    private static final double LASER_SEARCH_RADIUS = 8d;
    private static final double LASER_TRACE_RANGE = 64d;
    private static final double BLAST_RADIUS = 3.125d;
    private static final double EFFECT_RADIUS = 31.25d;

    private static final float DIRECT_BLAST_DAMAGE = Q2WConfigStats.Bfg10kDamage;
    private static final float RADIUS_BLAST_DAMAGE = 4f;
    private static final float LASER_DAMAGE = Q2WConfigStats.Bfg10kLaserDamage;
    private static final float EFFECT_DAMAGE_MAX = Q2WConfigStats.Bfg10kFlashDamage;

    private static final int BFG_THINK_INTERVAL_TICKS = 2;

    private static final int EXPLOSION_FRAME_TICKS = 2;
    private static final int EXPLOSION_FRAMES = 5;

    private static final int MAX_LIFETIME_TICKS = 400;

    private int explosionFrame = 0;
    private int explosionFrameTimer = 0;
    private boolean effectApplied = false;

    public Bfg10kProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = false;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(EXPLODING, false);
    }

    public boolean isExploding() {
        return this.entityData.get(EXPLODING);
    }

    private void setExploding(boolean exploding) {
        this.entityData.set(EXPLODING, exploding);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            return;
        }

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (isExploding()) {
            tickExplosion(serverLevel);
            return;
        }

        if (this.tickCount > MAX_LIFETIME_TICKS) {
            this.discard();
            return;
        }

        if (this.tickCount % BFG_THINK_INTERVAL_TICKS == 0) {
            bfgLaserThink(serverLevel);
        }

        Vec3 motion = this.getDeltaMovement();

        if (motion.lengthSqr() <= 1e-7d) {
            this.discard();
            return;
        }

        Vec3 start = this.position();
        Vec3 end = start.add(motion);

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        Vec3 realEnd = end;

        if (blockHit.getType() != HitResult.Type.MISS) {
            realEnd = blockHit.getLocation();
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(this, start, realEnd, this.getBoundingBox().expandTowards(motion).inflate(0.5d), this::canHitEntity, motion.lengthSqr());

        if (entityHit != null) {

            this.setPos(entityHit.getLocation());
            impact(serverLevel, entityHit.getEntity());
            return;
        }

        if (blockHit.getType() != HitResult.Type.MISS) {

            this.setPos(blockHit.getLocation());
            impact(serverLevel, null);
            return;
        }

        this.setPos(end.x, end.y, end.z);
        updateRotationFromMotion(motion);
    }

    private void updateRotationFromMotion(Vec3 motion) {

        Vec3 dir = motion.normalize();

        float yaw = (float) (Mth.atan2(dir.x, dir.z) * Mth.RAD_TO_DEG);
        float pitch = (float) (-(Mth.atan2(dir.y, Math.sqrt(dir.x * dir.x + dir.z * dir.z)) * Mth.RAD_TO_DEG));

        this.setYRot(yaw);
        this.setXRot(pitch);
        this.yRotO = yaw;
        this.xRotO = pitch;
    }

    private void bfgLaserThink(ServerLevel level) {

        Vec3 origin = this.position();

        AABB area = this.getBoundingBox().inflate(LASER_SEARCH_RADIUS);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, this::isValidLaserTarget);

        for (LivingEntity target : targets) {

            Vec3 targetPoint = target.getBoundingBox().getCenter();
            Vec3 dir = targetPoint.subtract(origin);

            if (dir.lengthSqr() <= 1e-7d) {
                continue;
            }

            dir = dir.normalize();

            Vec3 traceEnd = origin.add(dir.scale(LASER_TRACE_RANGE));

            BlockHitResult blockHit = level.clip(new ClipContext(origin, traceEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

            Vec3 beamEnd = blockHit.getType() == HitResult.Type.MISS ? traceEnd : blockHit.getLocation();

            damageEntitiesAlongBfgLaser(level, origin, beamEnd, dir);
            spawnBfgLaserParticles(level, origin, beamEnd);

            if (blockHit.getType() != HitResult.Type.MISS) {
            }
        }
    }

    private boolean isValidLaserTarget(LivingEntity entity) {
        Entity owner = this.getOwner();

        return entity.isAlive()
                && entity != owner
                && !entity.isSpectator()
                && entity.isPickable();
    }

    private void damageEntitiesAlongBfgLaser(ServerLevel level, Vec3 start, Vec3 end, Vec3 direction) {

        AABB lineBox = new AABB(start, end).inflate(0.35D);

        List<LivingEntity> hits = new ArrayList<>();

        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, lineBox, this::isValidLaserTarget)) {

            Optional<Vec3> hitPos = living.getBoundingBox()
                    .inflate(0.2d)
                    .clip(start, end);

            if (hitPos.isPresent()) {
                hits.add(living);
            }
        }

        hits.sort(Comparator.comparingDouble(e -> e.distanceToSqr(this)));

        DamageSource source = level.damageSources().source(ModDamageTypes.BFG10K_LASER_DAMAGE, this, this.getOwner());

        for (LivingEntity living : hits) {
            hurtWithScaledKnockback(living ,source, LASER_DAMAGE, 0.1f);
        }
    }

    private static boolean hurtWithScaledKnockback(LivingEntity target, DamageSource damageSource, float damage, double knockbackScale) {

        Vec3 motionBefore = target.getDeltaMovement();

        boolean hurt = target.hurt(damageSource, damage);

        if (hurt) {

            Vec3 motionAfter = target.getDeltaMovement();
            Vec3 addedKnockback = motionAfter.subtract(motionBefore);
            Vec3 scaledMotion = motionBefore.add(addedKnockback.scale(knockbackScale));

            target.setDeltaMovement(scaledMotion);
            target.hurtMarked = true;
        }

        return hurt;
    }

    private void impact(ServerLevel level, Entity directTarget) {

        if (isExploding()) {
            return;
        }

        if (directTarget instanceof LivingEntity livingTarget) {

            livingTarget.hurt(level.damageSources().source(ModDamageTypes.BFG10K_DAMAGE, this, this.getOwner()), DIRECT_BLAST_DAMAGE);
        }

        doBlastRadiusDamage(level, directTarget);

        level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.BFG10K_PROJECTILE_FLASH.get(), SoundSource.PLAYERS, 3f, 1f);

        Vec3 motion = this.getDeltaMovement();

        if (motion.lengthSqr() > 1e-7d) {
            Vec3 back = motion.normalize().scale(0.35d);
            this.setPos(this.getX() - back.x, this.getY() - back.y, this.getZ() - back.z);
        }

        this.setDeltaMovement(Vec3.ZERO);
        this.setExploding(true);
        this.noPhysics = true;

        this.explosionFrame = 0;
        this.explosionFrameTimer = 0;
        this.effectApplied = false;
    }

    private void doBlastRadiusDamage(ServerLevel level, Entity directTarget) {

        Vec3 center = this.position();

        AABB area = new AABB(
                center.x - BLAST_RADIUS,
                center.y - BLAST_RADIUS,
                center.z - BLAST_RADIUS,
                center.x + BLAST_RADIUS,
                center.y + BLAST_RADIUS,
                center.z + BLAST_RADIUS
        );

        DamageSource source = level.damageSources().source(
                ModDamageTypes.BFG10K_DAMAGE,
                this,
                this.getOwner()
        );

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
            if (entity == directTarget) {
                continue;
            }

            double distance = entity.getBoundingBox().getCenter().distanceTo(center);

            if (distance > BLAST_RADIUS) {
                continue;
            }

            float scale = (float) (1d - distance / BLAST_RADIUS);
            float damage = RADIUS_BLAST_DAMAGE * scale;

            if (damage > 0f) {
                entity.hurt(source, damage);
            }
        }
    }

    private void tickExplosion(ServerLevel level) {

        if (!effectApplied) {
            effectApplied = true;
            doBfgEffect(level);
        }

        explosionFrameTimer++;

        if (explosionFrameTimer >= EXPLOSION_FRAME_TICKS) {
            explosionFrameTimer = 0;
            explosionFrame++;

            spawnExplosionFrameParticles(level);
        }

        if (explosionFrame >= EXPLOSION_FRAMES) {
            this.discard();
        }
    }

    private void doBfgEffect(ServerLevel level) {

        Vec3 center = this.position();

        AABB area = new AABB(
                center.x - EFFECT_RADIUS,
                center.y - EFFECT_RADIUS,
                center.z - EFFECT_RADIUS,
                center.x + EFFECT_RADIUS,
                center.y + EFFECT_RADIUS,
                center.z + EFFECT_RADIUS
        );

        DamageSource source = level.damageSources().source(ModDamageTypes.BFG10K_FLASH_DAMAGE, this, this.getOwner());

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {

            if (entity == this.getOwner()) {
                continue;
            }

            Vec3 targetPoint = entity.getBoundingBox().getCenter();
            double distance = targetPoint.distanceTo(center);

            if (distance > EFFECT_RADIUS) {
                continue;
            }

            if (!hasBlockLineOfSight(level, center, targetPoint)) {
                continue;
            }

            Entity owner = this.getOwner();

            if (owner != null && !hasBlockLineOfSight(level, owner.getEyePosition(), targetPoint)) {
                continue;
            }

            float damage = (float) (EFFECT_DAMAGE_MAX * (1d - Math.sqrt(distance / EFFECT_RADIUS)));

            if (damage <= 0f) {
                continue;
            }

            entity.hurt(source, damage);
            spawnBfgEffectHitParticles(level, entity);
        }
    }

    private boolean hasBlockLineOfSight(ServerLevel level, Vec3 from, Vec3 to) {

        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        return hit.getType() == HitResult.Type.MISS;
    }

    private void spawnBfgLaserParticles(ServerLevel level, Vec3 start, Vec3 end) {

        Vec3 axis = end.subtract(start);
        double length = axis.length();

        if (length <= 0.01d) {
            return;
        }

        Vec3 dir = axis.normalize();

        final double STEP = 0.45d;

        for (double d = 0d; d <= length; d += STEP) {
            Vec3 pos = start.add(dir.scale(d));

            level.sendParticles((ServerPlayer) this.getOwner(), ModParticles.BFG_LASER_PARTICLE.get(),true, pos.x, pos.y, pos.z,
                    1, 0d, 0d, 0d, 0d);
        }
    }

    private void spawnExplosionFrameParticles(ServerLevel level) {
        Vec3 pos = this.position();

        level.sendParticles((ServerPlayer) this.getOwner(), ModParticles.BFG_EXPLOSION_PARTICLE.get(), true, pos.x, pos.y, pos.z,
                1, 0d, 0d, 0d, 0d);
    }

    private void spawnBfgEffectHitParticles(ServerLevel level, LivingEntity entity) {

        Vec3 pos = entity.getBoundingBox().getCenter();

        level.sendParticles((ServerPlayer) this.getOwner(), ModParticles.BFG_FLASH_PARTICLE.get(), true, pos.x, pos.y, pos.z,
                1, 0d, 0d, 0d, 0d);
    }
}