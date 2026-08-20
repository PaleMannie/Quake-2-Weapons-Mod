package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.item.custom.HandgrenadeItem;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HandgrenadePlayerCookLoopSoundInstance extends AbstractTickableSoundInstance {

    private final Player player;

    private int releaseGraceTicks = HandgrenadeItem.THROW_PROJECTILE_DELAY_TICKS + 2;

    public HandgrenadePlayerCookLoopSoundInstance(Player player, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, RandomSource.create());

        this.player = player;

        this.looping = true;
        this.delay = 0;
        this.volume = 0.8f;
        this.pitch = 1f;

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
        this.y = player.getY() + player.getBbHeight() * 0.5d;
        this.z = player.getZ();

        if (isCookingHandgrenade(player)) {
            releaseGraceTicks = HandgrenadeItem.THROW_PROJECTILE_DELAY_TICKS + 2;
            return;
        }

        if (releaseGraceTicks > 0) {
            releaseGraceTicks--;
            return;
        }

        stop();
    }

    private boolean isCookingHandgrenade(Player player) {

        if (!player.isUsingItem()) {
            return false;
        }

        ItemStack useStack = player.getUseItem();

        if (!(useStack.getItem() instanceof HandgrenadeItem)) {
            return false;
        }

        int useTicks = useStack.getUseDuration() - player.getUseItemRemainingTicks();

        return useTicks >= HandgrenadeItem.COOK_START_TICK;
    }
}