package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.event.EffectOverlayRenderClientEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ClientHudRegistry {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("q2w", "powerup_effects"), EffectOverlayRenderClientEvent::render);
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("q2w", "silencer_shots"), SilencerHudOverlay.HUD);
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("q2w", "weapon_ammo"), AmmoHudOverlay.HUD);
    }
}
