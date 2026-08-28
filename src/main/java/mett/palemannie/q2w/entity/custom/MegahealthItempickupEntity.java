package mett.palemannie.q2w.entity.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MegahealthItempickupEntity extends AbstractItempickupEntity {

    public MegahealthItempickupEntity(EntityType<? extends MegahealthItempickupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {

        player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 10, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 5, false, false));
    }
}
