package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.gui.ClientSilencerData;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.world.entity.player.Player;
import mett.palemannie.q2w.item.client.WeaponPresentation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class HyperblasterFireLoopSoundInstance extends AbstractTickableSoundInstance {

    private final Player player;
    private final float baseVolume;

    public HyperblasterFireLoopSoundInstance(Player player, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;

        this.looping = true;
        this.delay = 0;
        this.volume = 0.75f;
        this.pitch = 1f;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        this.baseVolume = volume;
        this.volume = getCurrentVolume();
    }

    private float getCurrentVolume() {
        if (player == net.minecraft.client.Minecraft.getInstance().player
                ? ClientSilencerData.hasSilencerActive()
                : player.getMainHandItem().hasTag() && player.getMainHandItem().getTag().getBoolean("Q2WSilenced")) {
            return this.baseVolume * WeaponAggroHandler.SILENCED_WEAPON_VOLUME_MULTIPLIER;
        }

        return this.baseVolume;
    }

    @Override
    public void tick() {
        if (player == null || player.isRemoved() || !player.isAlive()) {
            stop();
            return;
        }

        ItemStack mainHand = player.getMainHandItem();

        boolean usingHyperblaster =
                mainHand.getItem() instanceof HyperblasterItem
                        && player.isUsingItem()
                        && player.getUseItem() == mainHand;

        boolean hasAmmo = hasBulletAmmo(player);

        if (!usingHyperblaster || !hasAmmo) {
            stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();

        this.volume = getCurrentVolume();
    }

    private boolean hasBulletAmmo(Player player) {
        return WeaponPresentation.hasAmmo(player, ModItems.CELL.get());
    }
}