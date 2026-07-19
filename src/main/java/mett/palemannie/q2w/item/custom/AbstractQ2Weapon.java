package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/*
 * Base class for regular Quake 2-style weapons.
 *
 * Handles:
 * - automatic fire intervals
 * - release cooldowns
 * - ammo checks
 * - ammo consumption across multiple stacks
 * - ammoempty animation/sound
 * - hooks for special weapons like Chaingun and BFG10K
 *
 * Do NOT use this for Hand Grenade.
 * Hand Grenade should extend AbstractWeapon directly because it uses cook/release/fuse logic.
 */
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

    /**
     * null means this weapon does not need ammo.
     * Example: Blaster.
     */
    @Nullable
    protected Item ammoItem() {
        return null;
    }

    /**
     * Cost per logical shot.
     *
     * Examples:
     * Blaster: 0
     * Machinegun: 1
     * Chaingun: 1, but shotsPerTrigger() can return 1-3
     * BFG10K: 50
     */
    protected int ammoCostPerShot() {
        return 0;
    }

    /**
     * Most weapons fire once per trigger.
     * Chaingun can override this for spin-up.
     */
    protected int shotsPerTrigger(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        return 1;
    }

    /**
     * Called every server tick while the weapon is held down.
     * Useful for spin-up sounds, wind-up animations, charging logic, etc.
     */
    protected void heldTick(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
    }

    /**
     * Standard auto-fire logic.
     *
     * useTicks starts at 0 when right click begins.
     */
    protected boolean shouldAttemptFire(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        return useTicks % fireIntervalTicks() == 0;
    }

    /**
     * Actual weapon behavior.
     *
     * Examples:
     * ServerPlayHandler.handleBlasterShoot(player);
     * ServerPlayHandler.handleRocketLauncherShoot(player);
     */
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
        }
    }

    protected void onSuccessfulFire(ServerLevel level, ServerPlayer player, ItemStack stack) {
        stopAmmoEmptyAnimation(player, level, stack);
        stopIdleAnimation(player, level, stack);
        startShootingAnimation(player, level, stack);
    }

    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        ServerPlayHandler.playAmmoEmptySound(player);

        stopShootingAnimation(player, level, stack);
        stopIdleAnimation(player, level, stack);
        startAmmoEmptyAnimation(player, level, stack);
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