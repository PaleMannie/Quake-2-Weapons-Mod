package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilencerItem extends AbstractConsumptionItem {

    public SilencerItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack) {
        if (level.isClientSide()) {
            return;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            WeaponAggroHandler.addSilencedShots(
                    serverPlayer,
                    WeaponAggroHandler.DEFAULT_SILENCER_SHOTS
            );
        }

        level.playSound(null, player.blockPosition(), ModSounds.ITEM_PICKUP.get(), SoundSource.PLAYERS, 1f, 1f);
    }
}