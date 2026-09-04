package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.Quake2Weapons;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Q2WConfigEvents {

    /// A way to set config values after they've been (re)loaded upon game start
    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {

            Quake2Weapons.LOGGER.trace("[Q2W] SERVER config loaded — reloading powerup spawner values");
            PowerupSpawner.reloadConfigValues();

            Quake2Weapons.LOGGER.trace("[Q2W] SERVER config loaded — reloading power shield values");
            PowerShieldEventHandler.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == Q2WConfig.COMMON_SPEC){

            //Quake2Weapons.LOGGER.trace("[Q2W] COMMON config loaded — loading animation switches");
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {

            Quake2Weapons.LOGGER.trace("[Q2W] SERVER config reloaded — reloading powerup spawner values");
            PowerupSpawner.reloadConfigValues();

            Quake2Weapons.LOGGER.trace("[Q2W] SERVER config reloaded — reloading power shield values");
            PowerShieldEventHandler.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == Q2WConfig.COMMON_SPEC){

            //Quake2Weapons.LOGGER.trace("[Q2W] COMMON config loaded — loading animation switches");
        }
    }
}
