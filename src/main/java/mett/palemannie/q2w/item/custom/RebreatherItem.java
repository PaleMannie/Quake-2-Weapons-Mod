package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RebreatherItem extends AbstractConsumptionItem{

    public RebreatherItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack) {

        level.playSound(null, player.blockPosition(), ModSounds.REBREATHER_USE.get(), SoundSource.PLAYERS, 1f, 1f);
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0, true, true));
    }
}
