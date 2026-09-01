package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractItempickupEntity extends Entity {

    protected AbstractItempickupEntity(EntityType<?> type, Level level) {
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

    protected abstract SoundEvent getPickupSound();

    @Override
    public boolean isPickable() {
        return true;
    }

    protected void giveAmmoOrDrop(Player player, Item ammoItem, int amount) {

        if (amount <= 0) {
            return;
        }

        ItemStack remainingStack = new ItemStack(ammoItem, amount);

        player.getInventory().add(remainingStack);

        if (!remainingStack.isEmpty()) {

            ItemEntity droppedAmmo = new ItemEntity(level(), this.getX(), this.getY() + 1d, this.getZ(), remainingStack.copy());

            droppedAmmo.setDefaultPickUpDelay();
            level().addFreshEntity(droppedAmmo);
        }

        level().playSound(null, this.getX(), this.getY(), this.getZ(), getPickupSound(), SoundSource.PLAYERS, 1f, 1f);
    }
}