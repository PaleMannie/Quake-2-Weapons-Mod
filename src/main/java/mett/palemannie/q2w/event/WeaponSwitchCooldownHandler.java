package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.AbstractQ2Weapon;
import mett.palemannie.q2w.item.custom.HandgrenadeItem;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID)
public final class WeaponSwitchCooldownHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AbstractQ2Weapon.checkSpinningWeaponSwitch(player);
            ((HandgrenadeItem) ModItems.GRENADE.get()).tickActiveGrenade(player);
        }
    }
}
