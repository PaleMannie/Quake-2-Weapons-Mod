package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilencerItem extends AbstractPowerupItem{

    public SilencerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public MobEffect getPowerupEffect() {
        return ModEffects.ENVIROSUIT.get();
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack, int duration) {
        player.addEffect(new MobEffectInstance(ModEffects.ENVIROSUIT.get(), getPowerupDuration()));
    }
}
