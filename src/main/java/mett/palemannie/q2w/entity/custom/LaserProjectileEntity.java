package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ModDamageTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class LaserProjectileEntity extends Projectile {

    public LaserProjectileEntity(EntityType<? extends Projectile> entityType, Level level, double damageValue, boolean isBlaster) {
        super(entityType, level);
        this.damage = damageValue;
        this.isBlasterKey = isBlaster;
    }

    boolean isBlasterKey;
    double damage = 0;

    public LaserProjectileEntity(EntityType<LaserProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public boolean isNoGravity() {
        return true;
    }

    void hitResultHandler(){

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
    }

    void projectileFlyStraight(){

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.scale(1f));
        this.setDeltaMovement(this.getDeltaMovement().add(0f, 0f, 0f));

        this.setPos(d0, d1, d2);
    }

    void handleDamage(EntityHitResult pResult, Level level, ResourceKey<DamageType> damageType, Player player){

        DamageSource source = level.damageSources().source(damageType, null, null);
        DamageSource source2 = level.damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());
        if(pResult.getEntity() instanceof LivingEntity entity){

            entity.hurt(source2, Float.MIN_VALUE);
            entity.hurt(source, (float) (player.hasEffect(ModEffects.QUAD_DAMAGE.get()) ? damage * 4 : damage));
        }
    }

    void handleProjectileBlockHitEffects(){

        if(level() instanceof ServerLevel)
            ((ServerLevel) level()).sendParticles((ServerPlayer) this.getOwner(), ParticleTypes.SMOKE, true, this.getX(), this.getY(), this.getZ(), 1, 0f, 0f, 0f, 0f);
    }

    void handleGore(Level level){

        if(level instanceof ServerLevel)
            ((ServerLevel) level()).sendParticles((ServerPlayer) this.getOwner(), ParticleTypes.LANDING_LAVA, true, this.getX(), this.getY(), this.getZ(), 1, 0f, 0f, 0f, 0f);
    }

    void handleHitSound(@Nullable EntityHitResult entityHitResult, @Nullable BlockHitResult blockHitResult, Level level, SoundEvent soundEvent, float volume, float pitch){

        if(entityHitResult != null && blockHitResult == null) level.playSound(null, entityHitResult.getEntity().blockPosition(), soundEvent, SoundSource.NEUTRAL,volume, pitch);
        if(blockHitResult != null && entityHitResult == null) level.playSound(null, blockHitResult.getBlockPos(), soundEvent, SoundSource.NEUTRAL, volume, pitch);
    }

    @Override
    public void tick() {
        super.tick();

        hitResultHandler();
        projectileFlyStraight();

        if(this.tickCount > 100) this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        var soundEvent = ModSounds.BLASTER_HIT.get();

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

        handleHitSound(null, pResult, level(), soundEvent, 1f, 1f);
        handleProjectileBlockHitEffects();

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        //var damageType = ModDamageTypes.BLASTER_DAMAGE;
        var soundEvent = ModSounds.BLASTER_HIT.get();

        handleDamage(pResult, level(), isBlasterKey ? ModDamageTypes.BLASTER_DAMAGE : ModDamageTypes.HYPERBLASTER_DAMAGE, (Player) this.getOwner());
        handleHitSound(pResult, null, level(), soundEvent, 1f, 1f);

        if(Q2WConfig.COMMON.enableGore.get()){
            handleGore(level()); }

        this.discard();
    }
}