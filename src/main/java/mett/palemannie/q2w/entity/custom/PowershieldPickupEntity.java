package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PowershieldPickupEntity extends AbstractItempickupEntity {

    public PowershieldPickupEntity(EntityType<? extends PowershieldPickupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.ADRENALINE_ITEM.get(), 1);
    }

    @Override
    protected SoundEvent getPickupSound() {
        return ModSounds.ITEM_PICKUP.get();
    }
}
