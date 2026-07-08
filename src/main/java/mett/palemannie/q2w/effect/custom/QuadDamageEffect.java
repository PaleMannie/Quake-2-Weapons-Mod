package mett.palemannie.q2w.effect.custom;

import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class QuadDamageEffect extends MobEffect {

    public QuadDamageEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    /// Effect done through Events
    /// Only Expiring sounds here

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        MobEffectInstance inst = entity.getEffect(ModEffects.QUAD_DAMAGE.get());
        if (inst != null) {
            int remaining = inst.getDuration();

            if (remaining == 60) {
                if (entity.level().isClientSide) {

                    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.QUAD_DAMAGE_EXPIRE.get(), SoundSource.PLAYERS, 3f, 1f, false);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
