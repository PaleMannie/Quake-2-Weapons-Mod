package mett.palemannie.q2w.effect.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EnvirosuitEffect extends MobEffect {

    public EnvirosuitEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int pAmplifier) {

        Level level = entity.level();
        if (!level.isClientSide()) {

            if (entity instanceof ServerPlayer player) {

            }
        }
    }

    @Override
    public boolean applyEffectTick(ServerLevel sevel, LivingEntity entity, int amplifier) {

        Level level = entity.level();
        if (!level.isClientSide()) {

            if (entity instanceof ServerPlayer player) {

            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 60;
    }
}
