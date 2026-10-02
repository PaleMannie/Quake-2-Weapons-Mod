package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;

public final class AmmoHudOverlay {
    private AmmoHudOverlay() {
    }

    public static final GuiLayer HUD = (graphics, deltaTracker) -> {
        int screenWidth = graphics.guiWidth();
        int screenHeight = graphics.guiHeight();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui.hud.isHidden() || minecraft.player == null || minecraft.player.isSpectator()) {
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
        for (ItemStack stack : minecraft.player.getInventory().items) {
            if (weapon.acceptsAmmo(stack)) {
                total += stack.getCount();
            }
        }

        int x = screenWidth / 2 - 91;
        int healthRows = (int) Math.ceil((Math.max(minecraft.player.getMaxHealth(), minecraft.player.getHealth())
                + minecraft.player.getAbsorptionAmount()) / 20.0);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        int hudHeight = 39 + Math.max(0, healthRows - 1) * rowHeight;
        int y = minecraft.player.isCreative() ? screenHeight - 40
                : screenHeight - hudHeight - (hasArmor(minecraft.player.getArmorValue()) ? 28 : 18);
        graphics.item(ammo.getDefaultInstance(), x, y);
        drawOutlinedString(graphics, minecraft.font, Integer.toString(total), x + 20, y + 4, 0xFFFFFFFF, 0xFF000000);
    };

    private static void drawOutlinedString(GuiGraphicsExtractor guiGraphics, Font font, String text, int x, int y, int color, int outlineColor) {

        guiGraphics.text(font, text, x - 1, y, outlineColor, false);
        guiGraphics.text(font, text, x + 1, y, outlineColor, false);
        guiGraphics.text(font, text, x, y - 1, outlineColor, false);
        guiGraphics.text(font, text, x, y + 1, outlineColor, false);

        guiGraphics.text(font, text, x, y, color, false);
    }

    private static boolean hasArmor(int armorValue){

        return armorValue > 0;
    }
}
