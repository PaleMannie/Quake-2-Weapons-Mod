package mett.palemannie.q2w.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SilencerEffect extends MobEffect {

    public SilencerEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    /// Effect done through Events
    /// Only Expiring sounds here

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
