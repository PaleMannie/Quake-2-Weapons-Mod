package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.Q2WConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class AbstractPowerupEntity extends Entity {

    protected AbstractPowerupEntity(EntityType<?> type, Level level) {
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

    protected int getPowerupDuration() {
        return Q2WConfig.SERVER.powerupEffectDuration.get();
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

    protected abstract Item getPowerupItem();

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        /// Rightclicking the entity with totem of undying will drop it
        if (!level().isClientSide && stack.is(Items.TOTEM_OF_UNDYING)) {

            this.spawnAtLocation(getPowerupItem());
            level().playSound(null, blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 2.0F);

            if(!player.isCreative()){

                stack.shrink(1);
            }
            this.discard();

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}