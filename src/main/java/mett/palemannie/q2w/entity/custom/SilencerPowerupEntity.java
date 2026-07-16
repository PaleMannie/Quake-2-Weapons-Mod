package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SilencerPowerupEntity extends AbstractPowerupEntity{

    public SilencerPowerupEntity(EntityType<? extends SilencerPowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.SILENCER.get(), getPowerupDuration(), 0, false, false));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.SILENCER_ITEM.get();
    }
}
