package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.Bfg10kItem;
import mett.palemannie.q2w.item.custom.RailgunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WeaponAggroTickHandler {

    /// Monsters will be aggro'd by holding Railgun & BFG10k

    private static final int HELD_AGGRO_INTERVAL_TICKS = 20;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent.Post event) {



        if (!(event.player() instanceof ServerPlayer player)) {
            return;
        }

        if (player.tickCount % HELD_AGGRO_INTERVAL_TICKS != 0) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.getItem() instanceof Bfg10kItem || mainHand.getItem() instanceof RailgunItem) {
            WeaponAggroHandler.onLoudWeaponHeld(player);
        }
    }
}
