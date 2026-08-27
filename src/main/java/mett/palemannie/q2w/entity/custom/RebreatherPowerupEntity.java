package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RebreatherPowerupEntity extends AbstractPowerupEntity{

    public RebreatherPowerupEntity(EntityType<? extends RebreatherPowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, getPowerupDuration(), 0, true, true));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.REBREATHER_ITEM.get();
    }
}
