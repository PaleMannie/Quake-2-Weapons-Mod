package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.Bfg10kProjectileEntity;
import mett.palemannie.q2w.entity.custom.RocketProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ProjectileLoopSoundHandler {

    private static final Map<Integer, AbstractTickableSoundInstance> PROJECTILE_LOOPS = new HashMap<>();

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

        for (Entity entity : level.entitiesForRendering()) {

            if (entity instanceof Bfg10kProjectileEntity bfg) {
                startBfgLoop(minecraft, bfg);
            }

            if (entity instanceof RocketProjectileEntity rocket) {
                startRocketLoop(minecraft, rocket);
            }
        }

        cleanupStoppedLoops();
    }

    private static void startBfgLoop(Minecraft minecraft, Bfg10kProjectileEntity bfg) {

        if (bfg.isExploding()) {
            stopLoopForEntity(bfg);
            return;
        }

        int id = bfg.getId();

        AbstractTickableSoundInstance existing = PROJECTILE_LOOPS.get(id);

        if (existing != null && !existing.isStopped()) {
            return;
        }

        FollowEntityLoopSoundInstance sound = new FollowEntityLoopSoundInstance(bfg, ModSounds.BFG10K_PROJECTILE_LOOP.get(), 0.5f, 1f, entity -> entity instanceof Bfg10kProjectileEntity bfgEntity && bfgEntity.isExploding());

        PROJECTILE_LOOPS.put(id, sound);
        minecraft.getSoundManager().play(sound);
    }

    private static void startRocketLoop(Minecraft minecraft, RocketProjectileEntity rocket) {

        int id = rocket.getId();

        AbstractTickableSoundInstance existing = PROJECTILE_LOOPS.get(id);

        if (existing != null && !existing.isStopped()) {
            return;
        }

        FollowEntityLoopSoundInstance sound = new FollowEntityLoopSoundInstance(rocket, ModSounds.ROCKET_LOOP.get(), 0.5f, 1f, entity -> false);

        PROJECTILE_LOOPS.put(id, sound);
        minecraft.getSoundManager().play(sound);
    }

    private static void stopLoopForEntity(Entity entity) {

        AbstractTickableSoundInstance sound = PROJECTILE_LOOPS.remove(entity.getId());

        if (sound != null) {
            sound.stop();
        }
    }

    private static void cleanupStoppedLoops() {

        Iterator<Map.Entry<Integer, AbstractTickableSoundInstance>> iterator = PROJECTILE_LOOPS.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Integer, AbstractTickableSoundInstance> entry = iterator.next();

            if (entry.getValue().isStopped()) {
                iterator.remove();
            }
        }
    }

    private static void stopAll() {

        for (AbstractTickableSoundInstance sound : PROJECTILE_LOOPS.values()) {
            sound.stop();
        }

        PROJECTILE_LOOPS.clear();
    }
}