package mett.palemannie.q2w.item.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Presentation data on held stacks is synchronized by vanilla equipment updates. */
public final class WeaponPresentation {
    private WeaponPresentation() {}

    public static Player holder(ItemStack stack) {
        var level = Minecraft.getInstance().level;
        if (level == null || stack == null) return null;
        for (Player player : level.players()) {
            if (player.getMainHandItem() == stack) return player;
        }
        return null;
    }

    public static boolean hasAmmo(Player player, Item ammo) {
        if (player != Minecraft.getInstance().player) {
            return player.getMainHandItem().hasTag()
                    && player.getMainHandItem().getTag().getBoolean("Q2WHasAmmo");
        }
        if (player.isCreative()) return true;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ammo) && !stack.isEmpty()) return true;
        }
        return false;
    }
}
