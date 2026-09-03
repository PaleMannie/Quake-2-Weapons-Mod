package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.gui.ClientSilencerData;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WeaponHoldLoopSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;
    private final Item weaponItem;
    private final float baseVolume;

    public WeaponHoldLoopSoundInstance(LocalPlayer player, Item weaponItem, SoundEvent soundEvent, float volume, float pitch) {

        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;
        this.weaponItem = weaponItem;

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
        if (ClientSilencerData.hasSilencerActive()) {
            return this.baseVolume * WeaponAggroHandler.SILENCED_WEAPON_VOLUME_MULTIPLIER;
        }

        return this.baseVolume;
    }

    @Override
    public void tick() {

        if (player == null || player.isRemoved() || !player.isAlive()) {

            this.stop();
            return;
        }

        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.isEmpty() || mainHand.getItem() != this.weaponItem) {

            this.stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        this.volume = getCurrentVolume();
    }
}