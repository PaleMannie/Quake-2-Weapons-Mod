package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import mett.palemannie.q2w.item.client.WeaponPresentation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class HyperblasterSoundHandler {

    private HyperblasterFireLoopSoundInstance hyperblasterLoop;

    private boolean wasFiring = false;

    private int fireTicks = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {



        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            states.values().forEach(state -> state.stopHyperblasterLoop());
            states.clear();
            return;
        }
        states.entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            if (player.isRemoved() || !player.isAlive() || player.level() != minecraft.level) {
                entry.getValue().stopHyperblasterLoop();
                return true;
            }
            return false;
        });
        for (Player player : minecraft.level.players()) {
            if (player.isAlive()) states.computeIfAbsent(player, ignored -> new HyperblasterSoundHandler()).tick(player);
        }
    }

    private static final java.util.Map<Player, HyperblasterSoundHandler> states = new java.util.HashMap<>();

    private void tick(Player player) {
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

    private boolean isFiringHyperblaster(Player player) {
        ItemStack mainHand = player.getMainHandItem();

        boolean usingHyperblaster =
                mainHand.getItem() instanceof HyperblasterItem
                        && player.isUsingItem()
                        && player.getUseItem() == mainHand;

        return usingHyperblaster && hasBulletAmmo(player);
    }

    private void playSpindown(Player player) {
        Minecraft.getInstance().getSoundManager().play(
                new FollowPlayerOneShotSoundInstance(
                        player,
                        ModSounds.HYPERBLASTER_SPINDOWN.get(),
                        0.85f,
                        1f,
                        WeaponSoundRange.BLOCKS
                )
        );
    }

    private void startHyperblasterLoop(Player player) {
        if (hyperblasterLoop != null && !hyperblasterLoop.isStopped()) {
            return;
        }

        hyperblasterLoop = new HyperblasterFireLoopSoundInstance(
                player,
                ModSounds.HYPERBLASTER_LOOP.get()
        );

        Minecraft.getInstance().getSoundManager().play(hyperblasterLoop);
    }

    private void stopHyperblasterLoop() {
        if (hyperblasterLoop != null) {
            hyperblasterLoop.stop();
            hyperblasterLoop = null;
        }
    }

    private boolean hasBulletAmmo(Player player) {
        return WeaponPresentation.hasAmmo(player, ModItems.CELL.get());
    }
}
