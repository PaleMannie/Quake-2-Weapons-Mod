package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SilencerPowerupEntity extends AbstractItempickupEntity {

    public SilencerPowerupEntity(EntityType<? extends SilencerPowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {

        giveAmmoOrDrop(player, ModItems.SILENCER_ITEM.get(), 1);
    }
}
