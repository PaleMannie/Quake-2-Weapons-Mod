package mett.palemannie.q2w.gui;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ClientHudRegistry {
    @SubscribeEvent
    public static void registerGuiOverlays(AddGuiOverlayLayersEvent event) {
        event.getLayeredDraw().addAbove(ForgeLayeredDraw.PRE_SLEEP_STACK,
                Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "silencer_shots"),
                ForgeLayeredDraw.HOTBAR_AND_DECOS, SilencerHudOverlay.HUD);
        event.getLayeredDraw().addAbove(ForgeLayeredDraw.PRE_SLEEP_STACK,
                Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "weapon_ammo"),
                ForgeLayeredDraw.HOTBAR_AND_DECOS, AmmoHudOverlay.HUD);
    }
}
