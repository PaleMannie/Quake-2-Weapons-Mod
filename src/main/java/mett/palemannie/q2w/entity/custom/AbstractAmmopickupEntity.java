package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.Q2WConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractAmmopickupEntity extends Entity {

    protected AbstractAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public static int durationOnPickup = 0;

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void tick() {
        super.tick();

        /// Extract entity lifetime from config
        if(!level().isClientSide) { durationOnPickup = Q2WConfig.SERVER.powerupEffectDuration.get(); }

        /// Lifetime check
        if (this.tickCount > Q2WConfig.SERVER.powerupLifetime.get()) {
            discard();
            return;
        }

        /// Entity Hitbox
        if (!level().isClientSide) {
            for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(0.5))) {
                onPickup(player);
                discard();
                break;
            }
        }
    }

    protected abstract void onPickup(Player player);

    @Override
    public boolean isPickable() {
        return true;
    }
}