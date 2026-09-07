package mett.palemannie.q2w.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TestKitCommands {
    private TestKitCommands() {}

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {

        event.getDispatcher().register(Commands.literal("q2w")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("testkit").executes(context -> supply(context.getSource(), true)))
                .then(Commands.literal("refill").executes(context -> supply(context.getSource(), false))));
    }

    private static int supply(CommandSourceStack source, boolean includeWeapons) throws CommandSyntaxException {

        ServerPlayer player = source.getPlayerOrException();
        Inventory inventory = player.getInventory();
        int missing = 0;

        if (includeWeapons) {

            for (Item weapon : List.of(ModItems.BLASTER.get(), ModItems.SHOTGUN.get(),
                    ModItems.SUPER_SHOTGUN.get(), ModItems.MACHINEGUN.get(), ModItems.CHAINGUN.get(),
                    ModItems.GRENADELAUNCHER.get(), ModItems.ROCKETLAUNCHER.get(),
                    ModItems.HYPERBLASTER.get(), ModItems.RAILGUN.get(), ModItems.BFG10K.get())) {
                if (!player.getOffhandItem().is(weapon)) {
                    missing += topUp(inventory, weapon, 1);
                }
            }
        }

        missing += topUp(inventory, ModItems.BULLET.get(), 256);
        missing += topUp(inventory, ModItems.CELL.get(), 256);
        missing += topUp(inventory, ModItems.SHELL.get(), 128);
        missing += topUp(inventory, ModItems.GRENADE.get(), 64);
        missing += topUp(inventory, ModItems.ROCKET.get(), 64);
        missing += topUp(inventory, ModItems.SLUG.get(), 64);

        inventory.setChanged();
        player.containerMenu.broadcastChanges();
        if (missing > 0) {
            source.sendFailure(Component.translatable("command.q2w.supply.full", missing));
            return 0;
        }
        source.sendSuccess(() -> Component.translatable(includeWeapons
                ? "command.q2w.testkit.success" : "command.q2w.refill.success"), false);
        return 1;
    }

    private static int topUp(Inventory inventory, Item item, int target) {

        int present = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.is(item)) present += stack.getCount();
        }

        int remaining = Math.max(0, target - present);
        ItemStack template = item.getDefaultInstance();

        for (ItemStack stack : inventory.items) {
            if (remaining == 0) break;
            if (!stack.isEmpty() && ItemStack.isSameItemSameTags(stack, template)) {
                int added = Math.min(remaining, Math.max(0, stack.getMaxStackSize() - stack.getCount()));
                stack.grow(added);
                remaining -= added;
            }
        }

        for (int slot = 0; slot < inventory.items.size() && remaining > 0; slot++) {
            if (inventory.items.get(slot).isEmpty()) {
                int added = Math.min(remaining, template.getMaxStackSize());
                inventory.items.set(slot, new ItemStack(item, added));
                remaining -= added;
            }
        }

        return remaining;
    }
}
