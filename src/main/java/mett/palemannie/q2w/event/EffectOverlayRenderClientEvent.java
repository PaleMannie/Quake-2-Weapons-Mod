package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class EffectOverlayRenderClientEvent {
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {

        /// Color added to GUI while on Quake effects

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        /// Quad Damage
        if (player != null && player.hasEffect(ModEffects.QUAD_DAMAGE.get())) {

            MobEffectInstance inst = player.getEffect(ModEffects.QUAD_DAMAGE.get());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 0.24f;
            g = 0.44f;
            b = 0.95f;

            if (remaining > 60) {
                alpha = 0.01f;

            } else {
                alpha = 0.01f + 0.025f * (0.25f * (1.0f + Mth.sin((gameTime % 20) / 20.0f * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            event.getGuiGraphics().fill(0, 0, screenW, screenH, color);
        }

        /// Pentagram of Protection
        if (player != null && player.hasEffect(ModEffects.INVULNERABILITY.get())) {

            MobEffectInstance inst = player.getEffect(ModEffects.INVULNERABILITY.get());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 1f;
            g = 0.84f;
            b = 0f;

            if (remaining > 60) {

                alpha = 0.01f;
            } else {

                alpha = 0.01f + 0.025f * (0.25f * (1.0f + Mth.sin((gameTime % 20) / 20.0f * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            event.getGuiGraphics().fill(0, 0, screenW, screenH, color);
        }

        ///Envirosuit
        if (player != null && player.hasEffect(ModEffects.ENVIROSUIT.get())) {

            MobEffectInstance inst = player.getEffect(ModEffects.ENVIROSUIT.get());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 0f;
            g = 1f;
            b = 0.5f;

            if (remaining > 60) {

                alpha = 0.01f;
            } else {

                alpha = 0.01f + 0.025f * (0.25f * (1.0f + Mth.sin((gameTime % 20) / 20.0f * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            event.getGuiGraphics().fill(0, 0, screenW, screenH, color);
        }
    }
}