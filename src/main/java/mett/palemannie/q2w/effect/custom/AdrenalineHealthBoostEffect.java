package mett.palemannie.q2w.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class AdrenalineHealthBoostEffect extends MobEffect {

    public AdrenalineHealthBoostEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void fillEffectCures(java.util.Set<net.neoforged.neoforge.common.EffectCure> cures, net.minecraft.world.effect.MobEffectInstance instance) {
        cures.clear();
    }
}
