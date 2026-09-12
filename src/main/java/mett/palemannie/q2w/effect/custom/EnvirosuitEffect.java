package mett.palemannie.q2w.effect.custom;

import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EnvirosuitEffect extends MobEffect {
    public EnvirosuitEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        level.playSound(null, entity, ModSounds.ENVIROSUIT_EXPIRE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Quake's expiration warning starts three seconds before the effect ends.
        return duration == 60;
    }
}
