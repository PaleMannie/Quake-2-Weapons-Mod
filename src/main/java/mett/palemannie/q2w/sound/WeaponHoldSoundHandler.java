package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.Bfg10kItem;
import mett.palemannie.q2w.item.custom.RailgunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class WeaponHoldSoundHandler {

    private static WeaponHoldLoopSoundInstance railgunLoop;
    private static WeaponHoldLoopSoundInstance bfg10kLoop;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) { return; }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {

            stopRailgunLoop();
            stopBfg10kLoop();
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        Item heldItem = mainHand.getItem();

        if (heldItem instanceof RailgunItem) {

            startRailgunLoop(player, heldItem);
        } else {

            stopRailgunLoop();
        }

        if (heldItem instanceof Bfg10kItem) {

            startBfg10kLoop(player, heldItem);
        } else {

            stopBfg10kLoop();
        }
    }

    private static void startRailgunLoop(LocalPlayer player, Item heldItem) {

        if (railgunLoop != null && !railgunLoop.isStopped()) { return; }

        railgunLoop = new WeaponHoldLoopSoundInstance(player, heldItem, ModSounds.RAILGUN_HUM.get(), 0.55f, 1f);

        Minecraft.getInstance().getSoundManager().play(railgunLoop);
    }

    private static void stopRailgunLoop() {

        if (railgunLoop != null) {

            railgunLoop.stop();
            railgunLoop = null;
        }
    }

    private static void startBfg10kLoop(LocalPlayer player, Item heldItem) {

        if (bfg10kLoop != null && !bfg10kLoop.isStopped()) { return; }

        bfg10kLoop = new WeaponHoldLoopSoundInstance(player, heldItem, ModSounds.BFG10K_HUM.get(), 0.65f, 1f);

        Minecraft.getInstance().getSoundManager().play(bfg10kLoop);
    }

    private static void stopBfg10kLoop() {

        if (bfg10kLoop != null) {

            bfg10kLoop.stop();
            bfg10kLoop = null;
        }
    }
}