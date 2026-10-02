package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.util.PowerupOverlay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ClientModidEvents {

    @SubscribeEvent
    public static void onRenderStage(CustomizeGuiOverlayEvent.Chat event) {

        PowerupOverlay.render(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(false),
                event.getWindow().getGuiScaledWidth(),
                event.getWindow().getGuiScaledHeight());
    }
}
