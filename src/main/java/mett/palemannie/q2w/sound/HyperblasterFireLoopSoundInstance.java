package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class HyperblasterFireLoopSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;

    public HyperblasterFireLoopSoundInstance(LocalPlayer player, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;

        this.looping = true;
        this.delay = 0;
        this.volume = 0.75f;
        this.pitch = 1f;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
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
    }

    private boolean hasBulletAmmo(LocalPlayer player) {
        if (player.isCreative()) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                return true;
            }
        }

        return false;
    }
}