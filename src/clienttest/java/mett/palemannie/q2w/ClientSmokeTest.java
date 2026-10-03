package mett.palemannie.q2w;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/** Opt-in smoke test: loads every weapon model and animation, then closes the client. */
@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public final class ClientSmokeTest {
    private static int readyTicks;

    @SubscribeEvent
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void tick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!(minecraft.screen instanceof TitleScreen) || minecraft.getOverlay() != null || ++readyTicks != 40) return;
        int count = 0;
        for (var holder : ModItems.ITEMS.getEntries()) {
            if (!(holder.get() instanceof AbstractWeapon weapon)) continue;
            var bakedItem = minecraft.getItemRenderer().getModel(weapon.getDefaultInstance(), null, null, 0);
            if (bakedItem == minecraft.getModelManager().getMissingModel()) {
                throw new AssertionError("Missing baked item model: " + holder.getId());
            }
            var renderer = IClientItemExtensions.of(weapon).getCustomRenderer();
            if (!(renderer instanceof GeoItemRenderer geo)) throw new AssertionError("Missing renderer: " + holder.getId());
            var model = geo.getGeoModel();
            model.getBakedModel(model.getModelResource(weapon));
            if (!GeckoLibCache.getBakedAnimations().containsKey(model.getAnimationResource(weapon))) {
                throw new AssertionError("Missing animations: " + holder.getId());
            }
            if (minecraft.getResourceManager().getResource(model.getTextureResource(weapon)).isEmpty()) {
                throw new AssertionError("Missing texture: " + holder.getId());
            }
            count++;
        }
        if (count != 11) throw new AssertionError("Expected 11 weapons, got " + count);
        Quake2Weapons.LOGGER.info("Q2W_CLIENT_SMOKE_PASSED: {} weapon renderers, models, textures and animations", count);
        minecraft.stop();
    }
}
