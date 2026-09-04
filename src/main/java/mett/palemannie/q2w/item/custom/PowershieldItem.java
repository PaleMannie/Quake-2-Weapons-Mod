package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class PowershieldItem extends Item {

    private static final String ACTIVE_TAG = "Q2WPowerShieldActive";

    public PowershieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (level.isClientSide) {
            return InteractionResultHolder.consume(stack);
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.consume(stack);
        }

        if (isActive(stack)) {
            setActive(stack, false);
            playOffSound(serverPlayer);
            return InteractionResultHolder.consume(stack);
        }

        if (!hasCells(serverPlayer)) {
            playOffSound(serverPlayer);
            return InteractionResultHolder.fail(stack);
        }

        /// Only one Powershield can be active

        deactivateOtherPowerShields(serverPlayer, stack);

        setActive(stack, true);
        playOnSound(serverPlayer);

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isActive(stack) || super.isFoil(stack);
    }

    public static boolean isActive(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(ACTIVE_TAG);
    }

    public static void setActive(ItemStack stack, boolean active) {

        if (active) {
            stack.getOrCreateTag().putBoolean(ACTIVE_TAG, true);
            return;
        }

        if (!stack.hasTag()) {
            return;
        }

        stack.getTag().remove(ACTIVE_TAG);

        if (stack.getTag().isEmpty()) {
            stack.setTag(null);
        }
    }

    public static Optional<ItemStack> findActiveShield(Player player) {

        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof PowershieldItem && isActive(stack)) {
                return Optional.of(stack);
            }
        }

        ItemStack offhand = player.getOffhandItem();

        if (offhand.getItem() instanceof PowershieldItem && isActive(offhand)) {
            return Optional.of(offhand);
        }

        return Optional.empty();
    }

    public static boolean hasCells(Player player) {

        if (player.isCreative()) {
            return true;
        }

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.is(ModItems.CELL.get()) && inventoryStack.getCount() > 0) {
                return true;
            }
        }

        return false;
    }

    public static int countCells(Player player) {

        if (player.isCreative()) {
            return Integer.MAX_VALUE;
        }

        int count = 0;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.is(ModItems.CELL.get())) {
                count += inventoryStack.getCount();
            }
        }

        return count;
    }

    public static boolean consumeCells(Player player, int amount) {

        if (amount <= 0) {
            return true;
        }

        if (player.isCreative()) {
            return true;
        }

        if (countCells(player) < amount) {
            return false;
        }

        int remaining = amount;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (!inventoryStack.is(ModItems.CELL.get())) {
                continue;
            }

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

    public static void deactivateAll(Player player) {

        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof PowershieldItem) {
                setActive(stack, false);
            }
        }

        ItemStack offhand = player.getOffhandItem();

        if (offhand.getItem() instanceof PowershieldItem) {
            setActive(offhand, false);
        }
    }

    private static void deactivateOtherPowerShields(Player player, ItemStack exceptStack) {

        for (ItemStack stack : player.getInventory().items) {
            if (stack == exceptStack) {
                continue;
            }

            if (stack.getItem() instanceof PowershieldItem) {
                setActive(stack, false);
            }
        }

        ItemStack offhand = player.getOffhandItem();

        if (offhand != exceptStack && offhand.getItem() instanceof PowershieldItem) {
            setActive(offhand, false);
        }
    }

    private static void playOnSound(ServerPlayer player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.POWERSHIELD_ENABLE.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    private static void playOffSound(ServerPlayer player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.POWERSHIELD_DISABLE.get(), SoundSource.PLAYERS, 1f, 1f);
    }
}