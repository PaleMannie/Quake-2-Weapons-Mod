package mett.palemannie.q2w.event;

import mett.palemannie.q2w.effect.ModEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;

/** Full-screen powerup tint, drawn once as the top HUD layer. */
public final class EffectOverlayRenderClientEvent {
    private EffectOverlayRenderClientEvent() {}

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null || minecraft.options.hideGui) return;

        float time = (minecraft.level.getGameTime() % 20)
                + deltaTracker.getGameTimeDeltaPartialTick(true);
        drawEffect(graphics, minecraft.player.getEffect(ModEffects.QUAD_DAMAGE), time, 0x3D70F2);
        drawEffect(graphics, minecraft.player.getEffect(ModEffects.INVULNERABILITY), time, 0xFFD600);
        drawEffect(graphics, minecraft.player.getEffect(ModEffects.ENVIROSUIT), time, 0x00FF7F);
    }

    private static void drawEffect(GuiGraphics graphics, MobEffectInstance effect, float time, int rgb) {
        if (effect == null) return;

        // The old Forge callback blended a 1% tint repeatedly, once per HUD element.
        // A single NeoForge layer needs a visible opacity of its own.
        float alpha = 0.15f;
        if (!effect.isInfiniteDuration() && effect.getDuration() <= 60) {
            alpha += 0.10f * (0.5f + 0.5f * Mth.sin(time / 20.0f * Mth.TWO_PI));
        }
        int color = (Math.round(alpha * 255) << 24) | rgb;
        // Avoid depth occlusion by HUD elements already drawn in earlier layers.
        graphics.fill(RenderType.guiOverlay(), 0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
    }
}
