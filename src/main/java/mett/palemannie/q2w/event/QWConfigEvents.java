package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.Quake2Weapons;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QWConfigEvents {

    /// A way to set config values after they've been (re)loaded upon game start
    //TODO: System.out zu LOGGER wechseln
    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config loaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
            //AbstractWeaponItemDroppedAnimationFixer.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == Q2WConfig.COMMON_SPEC){

            Quake2Weapons.LOGGER.trace("[QW] COMMON config loaded — loading animation switches");

            //AbstractWeapon.reloadAltModelConfig();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config reloaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == Q2WConfig.COMMON_SPEC){

            Quake2Weapons.LOGGER.trace("[QW] COMMON config reloaded — reloading animation switches");

        }
    }
}
