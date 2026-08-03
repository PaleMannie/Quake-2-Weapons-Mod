package mett.palemannie.q2w.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FollowPlayerOneShotSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;

    public FollowPlayerOneShotSoundInstance(LocalPlayer player, SoundEvent soundEvent, float volume, float pitch) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;

        this.looping = false;
        this.delay = 0;
        this.volume = volume;
        this.pitch = pitch;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        if (player == null || player.isRemoved()) {
            stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}