package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.util.ServerPlayHandler;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public abstract class AbstractQ2Weapon extends AbstractWeapon {

    private final int fireIntervalTicks;
    private final int releaseCooldownTicks;

    protected AbstractQ2Weapon(Properties properties, int fireIntervalTicks, int releaseCooldownTicks) {
        super(properties);
        this.fireIntervalTicks = fireIntervalTicks;
        this.releaseCooldownTicks = releaseCooldownTicks;
    }

    protected int fireIntervalTicks() {
        return this.fireIntervalTicks;
    }

    @Override
    protected int getReleaseCooldownTicks() {
        return this.releaseCooldownTicks;
    }

    @Nullable
    protected Item ammoItem() {
        return null;
    }

    protected int ammoCostPerShot() {
        return 0;
    }

    protected int shotsPerTrigger(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        return 1;
    }

    protected void heldTick(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
    }

    protected boolean shouldAttemptFire(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        return useTicks % fireIntervalTicks() == 0;
    }

    protected abstract void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks);

    @Override
    protected final void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(user instanceof ServerPlayer serverPlayer)) return;

        int useTicks = getUseDuration(stack) - remainingUseDuration;

        heldTick(serverLevel, serverPlayer, stack, useTicks);

        if (!shouldAttemptFire(serverLevel, serverPlayer, stack, useTicks)) {
            return;
        }

        int shots = Math.max(1, shotsPerTrigger(serverLevel, serverPlayer, stack, useTicks));
        int totalAmmoCost = ammoCostPerShot() * shots;

        if (!consumeAmmo(serverPlayer, ammoItem(), totalAmmoCost)) {
            onAmmoEmpty(serverLevel, serverPlayer, stack);
            return;
        }

        onSuccessfulFire(serverLevel, serverPlayer, stack);

        for (int i = 0; i < shots; i++) {
            fireWeapon(serverLevel, serverPlayer, stack, useTicks);
            WeaponAggroHandler.onWeaponShot(serverPlayer);
        }
    }

    protected void onSuccessfulFire(ServerLevel level, ServerPlayer player, ItemStack stack) {
        triggerShootingAnimation(player, level, stack);
    }

    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        ServerPlayHandler.playAmmoEmptySound(player);
        triggerAmmoEmptyAnimation(player, level, stack);
    }

    protected boolean consumeAmmo(Player player, @Nullable Item ammoItem, int amount) {
        if (amount <= 0 || ammoItem == null) {
            return true;
        }

        if (player.isCreative()) {
            return true;
        }

        int available = 0;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.is(ammoItem)) {
                available += inventoryStack.getCount();
            }
        }

        if (available < amount) {
            return false;
        }

        int remaining = amount;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (!inventoryStack.is(ammoItem)) continue;

            int removed = Math.min(remaining, inventoryStack.getCount());
            inventoryStack.shrink(removed);
            remaining -= removed;

            if (remaining <= 0) {
                player.getInventory().setChanged();
                return true;
            }
        }

        player.getInventory().setChanged();
        return true;
    }
}