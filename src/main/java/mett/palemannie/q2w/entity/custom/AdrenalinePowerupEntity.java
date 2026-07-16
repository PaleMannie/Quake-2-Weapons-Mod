package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AdrenalinePowerupEntity extends AbstractPowerupEntity{

    public AdrenalinePowerupEntity(EntityType<? extends AdrenalinePowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.HEAL, getPowerupDuration(), 10, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, getPowerupDuration(), 1, false, false));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.ADRENALINE_ITEM.get();
    }
}
