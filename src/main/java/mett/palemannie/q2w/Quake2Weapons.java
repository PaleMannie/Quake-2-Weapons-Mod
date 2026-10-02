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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.slf4j.Logger;

@Mod(Quake2Weapons.MODID)
public class Quake2Weapons {

    public static final String MODID = "q2w";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Quake2Weapons(IEventBus modBusGroup, ModContainer container){


        modBusGroup.addListener(ModMessages::register);

        ModItems.register(modBusGroup);
        ModEffects.register(modBusGroup);
        ModEntities.register(modBusGroup);
        ModSounds.register(modBusGroup);
        ModBlocks.register(modBusGroup);
        ModParticles.PARTICLES.register(modBusGroup);
        ModCreativeModeTabs.register(modBusGroup);

        Q2WConfig.registerConfigs(container);
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){

            event.registerEntityRenderer(ModEntities.MUZZLE_FLASH.get(), MuzzleflashRenderer::new);
            event.registerEntityRenderer(ModEntities.LASER_PROJECTILE.get(), LaserProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.GRENADELAUNCHER_PROJECTILE.get(), GrenadelauncherProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.ROCKETLAUNCHER_PROJECTILE.get(), RocketlauncherProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.HANDGRENADE_PROJECTILE.get(), HandgrenadeProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.BFG10K_PROJECTILE.get(), Bfg10kProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.QUAD_DAMAGE_POWERUP.get(), QuaddamagePowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.INVULN_POWERUP.get(), InvulnerabilityPowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.ENVIROSUIT_POWERUP.get(), EnvirosuitPowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.SILENCER_POWERUP.get(), SilencerPowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.BULLETS_AMMOPICKUP.get(), BulletsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.SHELLS_AMMOPICKUP.get(), ShellsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.GRENADES_AMMOPICKUP.get(), GrenadesAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.ROCKETS_AMMOPICKUP.get(), RocketsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.CELLS_AMMOPICKUP.get(), CellsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.SLUGS_AMMOPICKUP.get(), SlugsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.ADRENALINE_PICKUP.get(), AdrenalinePickupRenderer::new);
            event.registerEntityRenderer(ModEntities.MEGAHEALTH_PICKUP.get(), MegahealthPickupRenderer::new);
            event.registerEntityRenderer(ModEntities.REBREATHER_PICKUP.get(), RebreatherPickupRenderer::new);
            event.registerEntityRenderer(ModEntities.POWERSHIELD_PICKUP.get(), PowershieldPickupRenderer::new);
        }
    }
}
