package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.entity.custom.HandgrenadeProjectileEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class HandgrenadeEntityCookLoopSoundInstance extends AbstractTickableSoundInstance {

    private final HandgrenadeProjectileEntity grenade;
    private final float baseVolume;

    public HandgrenadeEntityCookLoopSoundInstance(HandgrenadeProjectileEntity grenade, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.grenade = grenade;

        this.looping = true;
        this.delay = 0;
        this.volume = 0.8f;
        this.pitch = 1f;

        this.x = grenade.getX();
        this.y = grenade.getY();
        this.z = grenade.getZ();
        this.baseVolume = volume;
    }

    @Override
    public void tick() {

        if (grenade == null || grenade.isRemoved()) {
            stop();
            return;
        }

        var minecraft = net.minecraft.client.Minecraft.getInstance();

        if (minecraft.player != null) {

            double distance = minecraft.player.distanceTo(grenade);
            double maxDistance = 24d;

            float distanceScale = (float) Math.max(0d, 1d - distance / maxDistance);

            this.volume = this.baseVolume * distanceScale;
        }

        this.x = grenade.getX();
        this.y = grenade.getY();
        this.z = grenade.getZ();
    }
}