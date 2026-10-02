package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ClientHudRegistry {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "silencer_shots"),
                SilencerHudOverlay.HUD);
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "weapon_ammo"),
                AmmoHudOverlay.HUD);
    }
}
