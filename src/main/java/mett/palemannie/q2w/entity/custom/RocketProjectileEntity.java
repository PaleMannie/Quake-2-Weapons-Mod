package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.block.ModBlocks;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ModDamageTypes;
import mett.palemannie.q2w.util.Q2WConfigStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;

public class RocketProjectileEntity extends Projectile {

    public RocketProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public static float computeRadiusFromDamage(float configDamage, Player player) {

        if(player == null){ return ((configDamage - 1) / 7.0F)/2;} else{
            return player.hasEffect(ModEffects.QUAD_DAMAGE.get()) ? (((configDamage * 4) - 1) / 7.0F)/2 : ((configDamage - 1) / 7.0F)/2;}
    }

    private void quakeExplosion(Level level) {
        if (this.level().isClientSide) return;

        Vec3 center = this.position();

        DamageSource source = level.damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, null, null);

        AABB area = new AABB(this.blockPosition()).inflate(computeRadiusFromDamage(Q2WConfigStats.RocketlauncherDamage, (Player)this.getOwner()));
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != this.getOwner()) {
                entity.hurt(this.damageSources().source(DamageTypes.PLAYER_ATTACK, this, this.getOwner()), Float.MIN_VALUE);
            }
        }

        level().explode(null, source, null, center.x, center.y, center.z, computeRadiusFromDamage(Q2WConfigStats.RocketlauncherDamage, (Player)this.getOwner()), false, Level.ExplosionInteraction.NONE, false);

        ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME,
                center.x, center.y, center.z,
                40,
                0.0, 0.0, 0.0,
                0.2);

        ((ServerLevel) this.level()).sendParticles(ParticleTypes.LARGE_SMOKE,
                center.x, center.y, center.z,
                20,
                0.0, 0.0, 0.0,
                0.1);

        this.level().playSound(null, center.x, center.y, center.z,
                ModSounds.EXPLOSION.get(), SoundSource.PLAYERS,
                2f, 1f);

        this.cleanupLight();
        this.discard();
    }

    private BlockPos lightPos;

    private void tryPlaceLight() {
        BlockPos origin = this.blockPosition();
        Level level = this.level();

        int[] dyOrder = {0, -1, 1};
        for (int dy : dyOrder) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos candidate = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(candidate);

                    if (state.isAir()) {
                        level.setBlock(candidate, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15), 3);
                        this.lightPos = candidate;
                        return;
                    } else if (state.getBlock() == Blocks.WATER) {
                        level.setBlock(candidate, ModBlocks.LIGHT_WATER.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    }
                }
            }
        }
    }

    private void cleanupLight() {
        if (this.lightPos != null) {
            BlockState state = this.level().getBlockState(this.lightPos);
            if (state.getBlock() == Blocks.LIGHT) {
                this.level().removeBlock(this.lightPos, false);
            } else if (state.getBlock() == ModBlocks.LIGHT_WATER.get()) {
                this.level().setBlock(this.lightPos, Blocks.WATER.defaultBlockState(), 3);
            }
        }
    }

    void hitResultHandler(){

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
    }

    void projectileFlyStraight(){

        if (this.level().isClientSide) {
            Vec3 motion = this.getDeltaMovement().normalize().scale(-0.25);
            double px = this.getX() + motion.x;
            double py = this.getY() + motion.y;
            double pz = this.getZ() + motion.z;

            this.level().addParticle(ParticleTypes.SMOKE, px, py, pz, 0, 0, 0);
            this.level().addParticle(ParticleTypes.FLAME, px, py, pz, 0, 0, 0);
        }

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.scale(1f));
        this.setDeltaMovement(this.getDeltaMovement().add(0f, 0f, 0f));

        this.setPos(d0, d1, d2);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && Q2WConfig.COMMON.enableProjectileTrailLight.get()) {
            if (this.tickCount % 2 == 0) {
                this.cleanupLight();
                this.tryPlaceLight();
            }
        }

        hitResultHandler();
        projectileFlyStraight();

        if(this.tickCount > 400) {

            quakeExplosion(this.level());
            cleanupLight();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

        quakeExplosion(this.level());

        cleanupLight();
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        Player player = (Player) this.getOwner();

        pResult.getEntity().hurt(level().damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner()), Float.MIN_VALUE);
        pResult.getEntity().hurt(level().damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, null, null),
                player.hasEffect(ModEffects.QUAD_DAMAGE.get()) ? (Q2WConfigStats.RocketlauncherDamage * RandomSource.create().nextFloat()/4) * 4 : (Q2WConfigStats.RocketlauncherDamage * RandomSource.create().nextFloat()/4));

        quakeExplosion(this.level());

        cleanupLight();
        this.discard();
    }
}