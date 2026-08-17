package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class HyperblasterSoundHandler {

    private static HyperblasterFireLoopSoundInstance hyperblasterLoop;

    private static boolean wasFiring = false;

    private static int fireTicks = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            stopHyperblasterLoop();
            wasFiring = false;
            return;
        }

        boolean firing = isFiringHyperblaster(player);

        if (!firing && wasFiring) {

            playSpindown(player);
        }

        if (firing) {

            fireTicks++;
            startHyperblasterLoop(player);
        } else {

            fireTicks = 0;
            stopHyperblasterLoop();
        }

        wasFiring = firing;
    }

    private static boolean isFiringHyperblaster(LocalPlayer player) {
        ItemStack mainHand = player.getMainHandItem();

        boolean usingHyperblaster =
                mainHand.getItem() instanceof HyperblasterItem
                        && player.isUsingItem()
                        && player.getUseItem() == mainHand;

        return usingHyperblaster && hasBulletAmmo(player);
    }

    private static void playSpindown(LocalPlayer player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.HYPERBLASTER_SPINDOWN.get(),
                        0.85f,
                        1f
                )
        );
    }

    private static void startHyperblasterLoop(LocalPlayer player) {
        if (hyperblasterLoop != null && !hyperblasterLoop.isStopped()) {
            return;
        }

        hyperblasterLoop = new HyperblasterFireLoopSoundInstance(
                player,
                ModSounds.HYPERBLASTER_LOOP.get()
        );

        Minecraft.getInstance().getSoundManager().play(hyperblasterLoop);
    }

    private static void stopHyperblasterLoop() {
        if (hyperblasterLoop != null) {
            hyperblasterLoop.stop();
            hyperblasterLoop = null;
        }
    }

    private static boolean hasBulletAmmo(LocalPlayer player) {
        if (player.isCreative()) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                return true;
            }
        }

        return false;
    }
}