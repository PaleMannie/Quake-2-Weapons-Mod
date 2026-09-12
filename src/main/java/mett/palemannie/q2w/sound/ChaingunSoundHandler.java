package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.ChaingunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import mett.palemannie.q2w.item.client.WeaponPresentation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ChaingunSoundHandler {

    private ChaingunFireLoopSoundInstance chaingunLoop;

    private boolean wasFiring = false;

    private int fireTicks = 0;
    private static final int LOOP_START_DELAY_TICKS = 17;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {



        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            states.values().forEach(state -> state.stopChaingunLoop());
            states.clear();
            return;
        }
        states.entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            if (player.isRemoved() || !player.isAlive() || player.level() != minecraft.level) {
                entry.getValue().stopChaingunLoop();
                return true;
            }
            return false;
        });
        for (Player player : minecraft.level.players()) {
            if (player.isAlive()) states.computeIfAbsent(player, ignored -> new ChaingunSoundHandler()).tick(player);
        }
    }

    private static final java.util.Map<Player, ChaingunSoundHandler> states = new java.util.HashMap<>();

    private void tick(Player player) {
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

    private boolean isFiringChaingun(Player player) {
        ItemStack mainHand = player.getMainHandItem();

        boolean usingChaingun =
                mainHand.getItem() instanceof ChaingunItem
                        && player.isUsingItem()
                        && player.getUseItem() == mainHand;

        return usingChaingun && hasBulletAmmo(player);
    }

    private void playSpinup(Player player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.CHAINGUN_SPINUP.get(),
                        0.85f,
                        1f
                )
        );
    }

    private void playSpindown(Player player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.CHAINGUN_SPINDOWN.get(),
                        0.85f,
                        1f
                )
        );
    }

    private void startChaingunLoop(Player player) {
        if (chaingunLoop != null && !chaingunLoop.isStopped()) {
            return;
        }

        chaingunLoop = new ChaingunFireLoopSoundInstance(
                player,
                ModSounds.CHAINGUN_LOOP.get()
        );

        Minecraft.getInstance().getSoundManager().play(chaingunLoop);
    }

    private void stopChaingunLoop() {
        if (chaingunLoop != null) {
            chaingunLoop.stop();
            chaingunLoop = null;
        }
    }

    private boolean hasBulletAmmo(Player player) {
        return WeaponPresentation.hasAmmo(player, ModItems.BULLET.get());
    }
}
