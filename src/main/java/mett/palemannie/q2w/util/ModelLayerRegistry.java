package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelLayerRegistry {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(MuzzleflashModel.FLASH_LAYER, MuzzleflashModel::createBodyLayer);
        event.registerLayerDefinition(LaserProjectileModel.LASER_LAYER, LaserProjectileModel::createBodyLayer);
        event.registerLayerDefinition(GrenadelauncherProjectileModel.GRENADE_LAYER, GrenadelauncherProjectileModel::createBodyLayer);
        event.registerLayerDefinition(RocketlauncherProjectileModel.ROCKET_LAYER, RocketlauncherProjectileModel::createBodyLayer);
        event.registerLayerDefinition(HandgrenadeProjectileModel.HANDGRENADE_LAYER, HandgrenadeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(Bfg10kProjectileModel.BALL_LAYER, Bfg10kProjectileModel::createBodyLayer);
        event.registerLayerDefinition(QuaddamagePowerupModel.QUAD_LAYER, QuaddamagePowerupModel::createBodyLayer);
        event.registerLayerDefinition(InvulnerabilityPowerupModel.INVULN_LAYER, InvulnerabilityPowerupModel::createBodyLayer);
        event.registerLayerDefinition(EnvirosuitPowerupModel.ENVIROSUIT_LAYER, EnvirosuitPowerupModel::createBodyLayer);
        event.registerLayerDefinition(SilencerPowerupModel.SILENCER_LAYER, SilencerPowerupModel::createBodyLayer);
        event.registerLayerDefinition(BulletsAmmopickupModel.BULLETSPICKUP_LAYER, BulletsAmmopickupModel::createBodyLayer);
        event.registerLayerDefinition(ShellsAmmopickupModel.SHELLSPICKUP_LAYER, ShellsAmmopickupModel::createBodyLayer);
        event.registerLayerDefinition(GrenadesAmmopickupModel.GRENADESPICKUP_LAYER, GrenadesAmmopickupModel::createBodyLayer);
        event.registerLayerDefinition(RocketsAmmopickupModel.ROCKETSPICKUP_LAYER, RocketsAmmopickupModel::createBodyLayer);
        event.registerLayerDefinition(CellsAmmopickupModel.CELLSPICKUP_LAYER, CellsAmmopickupModel::createBodyLayer);
        event.registerLayerDefinition(SlugsAmmopickupModel.SLUGSPICKUP_LAYER, SlugsAmmopickupModel::createBodyLayer);
    }
}