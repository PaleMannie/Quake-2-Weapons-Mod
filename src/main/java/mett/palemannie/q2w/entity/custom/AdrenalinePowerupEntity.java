package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AdrenalinePowerupEntity extends AbstractItempickupEntity {

    public AdrenalinePowerupEntity(EntityType<? extends AdrenalinePowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.ADRENALINE_ITEM.get(), 1);
    }

}
