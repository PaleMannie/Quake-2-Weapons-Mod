package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SlugsAmmopickupEntity extends AbstractItempickupEntity {

    private static final int AMOUNT = 10;

    public SlugsAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.SLUG.get(), AMOUNT);
    }

    @Override
    protected SoundEvent getPickupSound() {
        return ModSounds.AMMO_PICKUP.get();
    }
}