package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.Bfg10kItem;
import mett.palemannie.q2w.item.custom.RailgunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class WeaponHoldSoundHandler {

    private static final Map<Player, WeaponHoldLoopSoundInstance> loops = new HashMap<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {



        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            loops.values().forEach(WeaponHoldLoopSoundInstance::stop);
            loops.clear();
            return;
        }

        // Remove old entities as well as old weapons, including across respawns/world changes.
        loops.entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            WeaponHoldLoopSoundInstance sound = entry.getValue();
            if (player.level() != minecraft.level || !minecraft.level.players().contains(player)
                    || !sound.isValid() || sound.isStopped()) {
                sound.stop();
                return true;
            }
            return false;
        });

        for (Player player : minecraft.level.players()) {
            if (!player.isAlive() || player.isRemoved() || player.isSpectator() || loops.containsKey(player)) continue;

            Item heldItem = player.getMainHandItem().getItem();
            WeaponHoldLoopSoundInstance sound;
            if (heldItem instanceof RailgunItem) {
                sound = new WeaponHoldLoopSoundInstance(player, heldItem, ModSounds.RAILGUN_HUM.get(), 0.55f, 1f);
            } else if (heldItem instanceof Bfg10kItem) {
                sound = new WeaponHoldLoopSoundInstance(player, heldItem, ModSounds.BFG10K_HUM.get(), 0.65f, 1f);
            } else {
                continue;
            }

            loops.put(player, sound);
            minecraft.getSoundManager().play(sound);
        }
    }
}
