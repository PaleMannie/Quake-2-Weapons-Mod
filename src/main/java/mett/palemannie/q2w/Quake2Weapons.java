package mett.palemannie.q2w;

import com.mojang.logging.LogUtils;
import mett.palemannie.q2w.block.ModBlocks;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.entity.client.*;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ModCreativeModeTabs;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(Quake2Weapons.MODID)
public class Quake2Weapons {

    public static final String MODID = "q2w";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Quake2Weapons(FMLJavaModLoadingContext context){

        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        GeckoLib.initialize();
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);

        Q2WConfig.registerConfigs();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){

            EntityRenderers.register(ModEntities.MUZZLE_FLASH.get(), MuzzleflashRenderer::new);
            EntityRenderers.register(ModEntities.LASER_PROJECTILE.get(), LaserProjectileRenderer::new);
            EntityRenderers.register(ModEntities.GRENADELAUNCHER_PROJECTILE.get(), GrenadelauncherProjectileRenderer::new);
            EntityRenderers.register(ModEntities.ROCKETLAUNCHER_PROJECTILE.get(), RocketlauncherProjectileRenderer::new);
            EntityRenderers.register(ModEntities.HANDGRENADE_PROJECTILE.get(), HandgrenadeProjectileRenderer::new);
            EntityRenderers.register(ModEntities.BFG10K_PROJECTILE.get(), Bfg10kProjectileRenderer::new);
            EntityRenderers.register(ModEntities.QUAD_DAMAGE_POWERUP.get(), QuaddamagePowerupRenderer::new);
            EntityRenderers.register(ModEntities.INVULN_POWERUP.get(), InvulnerabilityPowerupRenderer::new);
            EntityRenderers.register(ModEntities.ENVIROSUIT_POWERUP.get(), EnvirosuitPowerupRenderer::new);
            EntityRenderers.register(ModEntities.SILENCER_POWERUP.get(), SilencerPowerupRenderer::new);
            EntityRenderers.register(ModEntities.BULLETS_AMMOPICKUP.get(), BulletsAmmopickupRenderer::new);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent e) {
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(ModMessages::register);
    }
}
