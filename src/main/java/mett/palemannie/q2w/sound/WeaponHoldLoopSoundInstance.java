package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.gui.ClientSilencerData;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;

public class WeaponHoldLoopSoundInstance extends AbstractTickableSoundInstance {

    private final Player player;
    private final Item weaponItem;
    private final float baseVolume;

    public WeaponHoldLoopSoundInstance(Player player, Item weaponItem, SoundEvent soundEvent, float volume, float pitch) {

        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;
        this.weaponItem = weaponItem;

        this.relative = false;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.looping = true;
        this.delay = 0;
        this.volume = volume;
        this.pitch = pitch;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        this.baseVolume = volume;
        this.volume = getCurrentVolume();
    }

    private float getCurrentVolume() {
        boolean silenced = player == Minecraft.getInstance().player
                ? ClientSilencerData.hasSilencerActive()
                : mett.palemannie.q2w.util.ItemData.getBoolean(player.getMainHandItem(), "Q2WSilenced");
        if (silenced) {
            return WeaponSoundRange.volume(player, this.baseVolume * WeaponAggroHandler.SILENCED_WEAPON_VOLUME_MULTIPLIER, WeaponSoundRange.BLOCKS);
        }

        return WeaponSoundRange.volume(player, this.baseVolume, WeaponSoundRange.BLOCKS);
    }

    public boolean isValid() {
        return !player.isRemoved() && player.isAlive() && !player.isSpectator()
                && player.getMainHandItem().is(this.weaponItem);
    }

    @Override
    public boolean canStartSilent() {
        // Keep the loop ticking outside its radius so approaching listeners hear it again.
        return true;
    }

    @Override
    public void tick() {

        if (!isValid() || player.level() != Minecraft.getInstance().level) {
            stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        this.volume = getCurrentVolume();
    }
}