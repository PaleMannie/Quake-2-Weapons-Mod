package mett.palemannie.q2w.util;

import mett.palemannie.q2w.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class PowerupOverlay {

    /// RGBA
    private static final int[] QUAD   = {61, 102, 204, 100};
    private static final int[] SUIT   = {0, 255, 128, 100};
    private static final int[] PENT   = {255, 214, 0, 100};

    public static void render(GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int maxAlpha = 0;

        /// Quad
        MobEffectInstance quadInst = player.getEffect(ModEffects.QUAD_DAMAGE.getHolder().get());
        if (quadInst != null) {
            float mult = getPulseMultiplier(quadInst, partialTick);
            int currA = (int) (QUAD[3] * mult);
            totalR += QUAD[0] * currA / 255;
            totalG += QUAD[1] * currA / 255;
            totalB += QUAD[2] * currA / 255;
            maxAlpha = Math.max(maxAlpha, currA);
        }

        /// Envirosuit
        MobEffectInstance suitInst = player.getEffect(ModEffects.ENVIROSUIT.getHolder().get());
        if (suitInst != null) {
            float mult = getPulseMultiplier(suitInst, partialTick);
            int currA = (int) (SUIT[3] * mult);
            totalR += SUIT[0] * currA / 255;
            totalG += SUIT[1] * currA / 255;
            totalB += SUIT[2] * currA / 255;
            maxAlpha = Math.max(maxAlpha, currA);
        }

        /// Pent
        MobEffectInstance pentInst = player.getEffect(ModEffects.INVULNERABILITY.getHolder().get());
        if (pentInst != null) {
            float mult = getPulseMultiplier(pentInst, partialTick);
            int currA = (int) (PENT[3] * mult);
            totalR += PENT[0] * currA / 255;
            totalG += PENT[1] * currA / 255;
            totalB += PENT[2] * currA / 255;
            maxAlpha = Math.max(maxAlpha, currA);
        }

        if (maxAlpha == 0) return;

        totalR = Math.min(255, totalR);
        totalG = Math.min(255, totalG);
        totalB = Math.min(255, totalB);

        int color = (maxAlpha << 24) | (totalR << 16) | (totalG << 8) | totalB;
        graphics.fill(0, 0, screenWidth, screenHeight, color);
    }

    ///pulsate 1Hz
    private static float getPulseMultiplier(MobEffectInstance effect, float partialTick) {

        float remaining = effect.getDuration() + partialTick;
        if (remaining > 60.0f) return 1.0f;

        float progress = 1.0f - (remaining / 60.0f);
        float angle = progress * (float) Math.PI * 6.0f;
        float normalized = (float) ((Math.sin(angle) + 1.0) / 2.0);
        return 0.3f + 0.7f * normalized;
    }
}
