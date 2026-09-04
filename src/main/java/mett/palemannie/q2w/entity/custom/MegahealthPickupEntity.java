package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MegahealthPickupEntity extends AbstractItempickupEntity {

    public MegahealthPickupEntity(EntityType<? extends MegahealthPickupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {

        player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 10, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 4, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 10, false, false));

        level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ADRENALINE_USE.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    protected SoundEvent getPickupSound() { return null; }
}
