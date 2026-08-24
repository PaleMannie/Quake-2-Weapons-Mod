package mett.palemannie.q2w.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public class FollowEntityLoopSoundInstance extends AbstractTickableSoundInstance {

    private final Entity entity;
    private final Predicate<Entity> stopPredicate;

    public FollowEntityLoopSoundInstance(Entity entity, SoundEvent soundEvent, float volume, float pitch, Predicate<Entity> stopPredicate) {
        super(soundEvent, SoundSource.AMBIENT, RandomSource.create());

        this.entity = entity;
        this.stopPredicate = stopPredicate;

        this.looping = true;
        this.delay = 0;

        this.relative = false;
        this.attenuation = SoundInstance.Attenuation.LINEAR;

        this.volume = volume;
        this.pitch = pitch;

        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();

        this.baseVolume = volume;
        this.volume = volume;
    }

    private final float baseVolume;

    @Override
    public void tick() {

        if (entity == null || entity.isRemoved() || stopPredicate.test(entity)) {
            stop();
            return;
        }

        var minecraft = net.minecraft.client.Minecraft.getInstance();

        if (minecraft.player != null) {

            double distance = minecraft.player.distanceTo(entity);
            double maxDistance = 36d;

            float distanceScale = (float) Math.max(0d, 1d - distance / maxDistance);

            this.volume = this.baseVolume * distanceScale;
        }

        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
    }
}