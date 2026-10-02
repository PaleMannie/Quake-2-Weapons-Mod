package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class SilencerHudOverlay {

    public static final GuiLayer HUD = (guiGraphics, deltaTracker) -> {
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        int shotsLeft = ClientSilencerData.getSilencedShotsLeft();

        if (shotsLeft <= 0) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.gui.hud.isHidden() || minecraft.player == null) {
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

        guiGraphics.item(silencerStack, iconX, iconY);
        drawOutlinedString(guiGraphics, font, numberText, textX, textY, 0xFFFFFFFF, 0xFF000000);
    };

    private static void drawOutlinedString(GuiGraphicsExtractor guiGraphics, Font font, String text, int x, int y, int color, int outlineColor) {

        guiGraphics.text(font, text, x - 1, y, outlineColor, false);
        guiGraphics.text(font, text, x + 1, y, outlineColor, false);
        guiGraphics.text(font, text, x, y - 1, outlineColor, false);
        guiGraphics.text(font, text, x, y + 1, outlineColor, false);

        guiGraphics.text(font, text, x, y, color, false);
    }
}
