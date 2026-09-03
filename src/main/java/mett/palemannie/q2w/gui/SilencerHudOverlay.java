package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SilencerHudOverlay {

    public static final IGuiOverlay HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int shotsLeft = ClientSilencerData.getSilencedShotsLeft();

        if (shotsLeft <= 0) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.options.hideGui || minecraft.player == null) {
            return;
        }

        if (!(minecraft.player.getMainHandItem().getItem() instanceof mett.palemannie.q2w.item.custom.AbstractWeapon)) {
            return;
        }

        Font font = minecraft.font;

        ItemStack silencerStack = new ItemStack(ModItems.SILENCER_ITEM.get());

        String numberText = String.valueOf(shotsLeft);

        int iconX = screenWidth / 2 + 10;
        int iconY = minecraft.player.isCreative() ? screenHeight - 40 :screenHeight - 58 ;

        int textX = iconX + 15;
        int textY = iconY + 4;

        guiGraphics.renderItem(silencerStack, iconX, iconY);
        drawOutlinedString(guiGraphics, font, numberText, textX, textY, 0xFFFFFF, 0x000000);
    };

    private static void drawOutlinedString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, int outlineColor) {

        guiGraphics.drawString(font, text, x - 1, y, outlineColor, false);
        guiGraphics.drawString(font, text, x + 1, y, outlineColor, false);
        guiGraphics.drawString(font, text, x, y - 1, outlineColor, false);
        guiGraphics.drawString(font, text, x, y + 1, outlineColor, false);

        guiGraphics.drawString(font, text, x, y, color, false);
    }
}