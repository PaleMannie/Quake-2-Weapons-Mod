package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AdrenalineItem extends AbstractPowerupItem{

    public AdrenalineItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public MobEffect getPowerupEffect() {
        return MobEffects.HEALTH_BOOST;
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack, int duration) {
        if (level.isClientSide) {
            return;
        }

        MobEffectInstance currentHealthBoost = player.getEffect(MobEffects.HEALTH_BOOST);

        int newAmplifier = 0;

        if (currentHealthBoost != null) {
            newAmplifier = currentHealthBoost.getAmplifier() + 1;
        }

        newAmplifier = Math.min(newAmplifier, 255);

        player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE - 1, newAmplifier, false, false, true));

        player.addEffect(new MobEffectInstance(MobEffects.HEAL, 2, 10));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 10));

        level.playSound(null, player.blockPosition(), ModSounds.ADRENALINE_USE.get(), SoundSource.PLAYERS, 1f, 1f);
    }
}
