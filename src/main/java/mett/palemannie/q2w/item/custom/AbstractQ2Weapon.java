package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.util.ServerPlayHandler;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import java.util.Map;
import java.util.WeakHashMap;
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

    private record ActiveUse(AbstractQ2Weapon weapon, ItemStack stack, int slot) {}
    private static final Map<Player, ActiveUse> ACTIVE_USES = new WeakHashMap<>();

    /** Runs on the server, including before a new use packet can start another weapon. */
    public static void checkInterruptedUse(Player player) {
        ActiveUse active = ACTIVE_USES.get(player);
        if (active == null) return;
        if (!player.isUsingItem() || player.getInventory().selected != active.slot()
                || player.getMainHandItem().getItem() != active.weapon()) {
            ACTIVE_USES.remove(player);
            active.weapon().applySharedCooldown(player);
            active.weapon().afterShooting(active.stack(), player.level(), player, 0);
            if (player.getUseItem() == active.stack()) player.stopUsingItem();
        }
    }

    private void applySharedCooldown(Player player) {
        int ticks = getReleaseCooldownTicks();
        if (ticks <= 0) return;
        for (var entry : ModItems.ITEMS.getEntries()) {
            if (entry.get() instanceof AbstractQ2Weapon weapon) {
                player.getCooldowns().addCooldown(weapon, ticks);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) checkInterruptedUse(player);
        if (hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        var result = super.use(level, player, hand);
        if (!level.isClientSide && player.isUsingItem() && player.getUseItem() == player.getItemInHand(hand)) {
            ACTIVE_USES.put(player, new ActiveUse(this, player.getItemInHand(hand), player.getInventory().selected));
        }
        return result;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        if (user instanceof Player player) {
            ACTIVE_USES.remove(player);
            applySharedCooldown(player);
        }
        super.releaseUsing(stack, level, user, timeCharged);
    }

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

    @Override
    @Nullable
    public Item getAmmoItem() {
        return ammoItem();
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

        checkInterruptedUse(serverPlayer);
        if (serverPlayer.getCooldowns().isOnCooldown(this)
                || serverPlayer.getMainHandItem() != stack || !serverPlayer.isUsingItem()) return;

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
