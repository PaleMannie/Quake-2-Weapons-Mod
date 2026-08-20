package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.entity.custom.HandgrenadeProjectileEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class HandgrenadeEntityCookLoopSoundInstance extends AbstractTickableSoundInstance {

    private final HandgrenadeProjectileEntity grenade;

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
    }

    @Override
    public void tick() {
        if (grenade == null || grenade.isRemoved()) {
            stop();
            return;
        }

        this.x = grenade.getX();
        this.y = grenade.getY();
        this.z = grenade.getZ();
    }
}