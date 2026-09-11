package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.AbstractQ2Weapon;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID)
public final class WeaponSwitchCooldownHandler {

    /// TODO: player da reinkriegen

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (/*event.phase == TickEvent.Phase.END &&*/ !event.side().isClient()) {
            AbstractQ2Weapon.checkSpinningWeaponSwitch();
        }
    }
}
