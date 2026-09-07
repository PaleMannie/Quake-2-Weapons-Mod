package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public final class AmmoHudOverlay {
    private AmmoHudOverlay() {
    }

    public static final IGuiOverlay HUD = (gui, graphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.player == null || minecraft.player.isSpectator()) {
            return;
        }
        if (!(minecraft.player.getMainHandItem().getItem() instanceof AbstractWeapon weapon)) {
            return;
        }
        Item ammo = weapon.getAmmoItem();
        if (ammo == null) {
            return;
        }

        int total = 0;
        for (int slot = 0; slot < minecraft.player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = minecraft.player.getInventory().getItem(slot);
            if (stack.is(ammo)) {
                total += stack.getCount();
            }
        }

        int x = screenWidth / 2 - 91;
        int y = minecraft.player.isCreative() ? screenHeight - gui.leftHeight : hasArmor(minecraft.player.getArmorValue()) ? screenHeight - gui.leftHeight - 18 : screenHeight - gui.leftHeight - 8;
        graphics.renderItem(ammo.getDefaultInstance(), x, y);
        drawOutlinedString(graphics, minecraft.font, Integer.toString(total), x + 20, y + 4, 0xFFFFFF, 0x000000);
    };

    private static void drawOutlinedString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, int outlineColor) {

        guiGraphics.drawString(font, text, x - 1, y, outlineColor, false);
        guiGraphics.drawString(font, text, x + 1, y, outlineColor, false);
        guiGraphics.drawString(font, text, x, y - 1, outlineColor, false);
        guiGraphics.drawString(font, text, x, y + 1, outlineColor, false);

        guiGraphics.drawString(font, text, x, y, color, false);
    }

    private static boolean hasArmor(int armorValue){

        return armorValue > 0;
    }
}
