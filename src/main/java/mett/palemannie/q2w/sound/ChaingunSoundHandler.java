package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.ChaingunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ChaingunSoundHandler {

    private static ChaingunFireLoopSoundInstance chaingunLoop;

    private static boolean wasFiring = false;

    private static int fireTicks = 0;
    private static final int LOOP_START_DELAY_TICKS = 17;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            stopChaingunLoop();
            wasFiring = false;
            return;
        }

        boolean firing = isFiringChaingun(player);

        if (firing && !wasFiring) {
            playSpinup(player);
        }

        if (!firing && wasFiring) {
            playSpindown(player);
        }

        if (firing) {

            fireTicks++;

            if (fireTicks >= LOOP_START_DELAY_TICKS) {

                startChaingunLoop(player);
            }
        } else {

            fireTicks = 0;
            stopChaingunLoop();
        }

        wasFiring = firing;
    }

    private static boolean isFiringChaingun(LocalPlayer player) {
        ItemStack mainHand = player.getMainHandItem();

        boolean usingChaingun =
                mainHand.getItem() instanceof ChaingunItem
                        && player.isUsingItem()
                        && player.getUseItem() == mainHand;

        return usingChaingun && hasBulletAmmo(player);
    }

    private static void playSpinup(LocalPlayer player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.CHAINGUN_SPINUP.get(),
                        0.85F,
                        1.0F
                )
        );
    }

    private static void playSpindown(LocalPlayer player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.CHAINGUN_SPINDOWN.get(),
                        0.85F,
                        1.0F
                )
        );
    }

    private static void startChaingunLoop(LocalPlayer player) {
        if (chaingunLoop != null && !chaingunLoop.isStopped()) {
            return;
        }

        chaingunLoop = new ChaingunFireLoopSoundInstance(
                player,
                ModSounds.CHAINGUN_LOOP.get()
        );

        Minecraft.getInstance().getSoundManager().play(chaingunLoop);
    }

    private static void stopChaingunLoop() {
        if (chaingunLoop != null) {
            chaingunLoop.stop();
            chaingunLoop = null;
        }
    }

    private static boolean hasBulletAmmo(LocalPlayer player) {
        if (player.isCreative()) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.BULLET.get())) {
                return true;
            }
        }

        return false;
    }
}