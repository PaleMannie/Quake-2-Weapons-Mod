package mett.palemannie.q2w.sound;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FollowPlayerOneShotSoundInstance extends AbstractTickableSoundInstance {

    private final Player player;
    private final float baseVolume;
    private final float range;

    public FollowPlayerOneShotSoundInstance(Player player, SoundEvent soundEvent, float volume, float pitch) {
        this(player, soundEvent, volume, pitch, 0f);
    }

    public FollowPlayerOneShotSoundInstance(Player player, SoundEvent soundEvent, float volume, float pitch, float range) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;
        this.baseVolume = volume;
        this.range = range;
        this.relative = false;
        if (range > 0f) this.attenuation = SoundInstance.Attenuation.NONE;

        this.looping = false;
        this.delay = 0;
        this.volume = range > 0f ? WeaponSoundRange.volume(player, baseVolume, range) : baseVolume;
        this.pitch = pitch;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return range > 0f;
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
        if (range > 0f) this.volume = WeaponSoundRange.volume(player, baseVolume, range);
    }
}