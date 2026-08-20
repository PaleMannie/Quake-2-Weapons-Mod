package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.HandgrenadeProjectileEntity;
import mett.palemannie.q2w.item.custom.HandgrenadeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(
        modid = Quake2Weapons.MODID,
        value = Dist.CLIENT
)
public class HandgrenadeCookSoundHandler {

    private static final Map<UUID, HandgrenadePlayerCookLoopSoundInstance> PLAYER_COOK_LOOPS = new HashMap<>();
    private static final Map<Integer, HandgrenadeEntityCookLoopSoundInstance> GRENADE_COOK_LOOPS = new HashMap<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level == null) {
            stopAll();
            return;
        }

        tickPlayerCookLoops(minecraft, level);
        tickGrenadeCookLoops(minecraft, level);
        cleanupStoppedLoops();
    }

    private static void tickPlayerCookLoops(Minecraft minecraft, ClientLevel level) {

        for (Player player : level.players()) {
            UUID uuid = player.getUUID();

            if (isCookingHandgrenade(player)) {
                HandgrenadePlayerCookLoopSoundInstance existing = PLAYER_COOK_LOOPS.get(uuid);

                if (existing == null || existing.isStopped()) {
                    HandgrenadePlayerCookLoopSoundInstance sound =
                            new HandgrenadePlayerCookLoopSoundInstance(
                                    player,
                                    ModSounds.HANDGRENADE_COOK.get()
                            );

                    PLAYER_COOK_LOOPS.put(uuid, sound);
                    minecraft.getSoundManager().play(sound);
                }
            }
        }
    }

    private static void tickGrenadeCookLoops(Minecraft minecraft, ClientLevel level) {

        for (Entity entity : level.entitiesForRendering()) {
            if (!(entity instanceof HandgrenadeProjectileEntity grenade)) {
                continue;
            }

            int id = grenade.getId();

            HandgrenadeEntityCookLoopSoundInstance existing = GRENADE_COOK_LOOPS.get(id);

            if (existing == null || existing.isStopped()) {
                HandgrenadeEntityCookLoopSoundInstance sound =
                        new HandgrenadeEntityCookLoopSoundInstance(
                                grenade,
                                ModSounds.HANDGRENADE_COOK.get()
                        );

                GRENADE_COOK_LOOPS.put(id, sound);
                minecraft.getSoundManager().play(sound);
            }
        }
    }

    private static boolean isCookingHandgrenade(Player player) {

        if (!player.isUsingItem()) {
            return false;
        }

        ItemStack useStack = player.getUseItem();

        if (!(useStack.getItem() instanceof HandgrenadeItem)) {
            return false;
        }

        int useTicks = useStack.getUseDuration() - player.getUseItemRemainingTicks();

        return useTicks >= HandgrenadeItem.COOK_START_TICK;
    }

    private static void cleanupStoppedLoops() {

        Iterator<Map.Entry<UUID, HandgrenadePlayerCookLoopSoundInstance>> playerIterator =
                PLAYER_COOK_LOOPS.entrySet().iterator();

        while (playerIterator.hasNext()) {
            Map.Entry<UUID, HandgrenadePlayerCookLoopSoundInstance> entry = playerIterator.next();

            if (entry.getValue().isStopped()) {
                playerIterator.remove();
            }
        }

        Iterator<Map.Entry<Integer, HandgrenadeEntityCookLoopSoundInstance>> grenadeIterator =
                GRENADE_COOK_LOOPS.entrySet().iterator();

        while (grenadeIterator.hasNext()) {
            Map.Entry<Integer, HandgrenadeEntityCookLoopSoundInstance> entry = grenadeIterator.next();

            if (entry.getValue().isStopped()) {
                grenadeIterator.remove();
            }
        }
    }

    private static void stopAll() {

        for (HandgrenadePlayerCookLoopSoundInstance sound : PLAYER_COOK_LOOPS.values()) {
            sound.stop();
        }

        for (HandgrenadeEntityCookLoopSoundInstance sound : GRENADE_COOK_LOOPS.values()) {
            sound.stop();
        }

        PLAYER_COOK_LOOPS.clear();
        GRENADE_COOK_LOOPS.clear();
    }
}