package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.util.PowerupOverlay;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ClientModidEvents {

    @SubscribeEvent
    public static void registerPowerupOverlay(RegisterGuiLayersEvent event) {
        // The chat customization event is skipped while the chat screen is open.
        event.registerBelow(VanillaGuiLayers.CHAT,
                Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "powerup_overlay"),
                (graphics, deltaTracker) -> PowerupOverlay.render(graphics,
                        deltaTracker.getGameTimeDeltaPartialTick(false),
                        graphics.guiWidth(), graphics.guiHeight()));
    }
}
