package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShellsAmmopickupEntity extends AbstractItempickupEntity {

    private static final int AMOUNT = 10;

    public ShellsAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.SHELL.get(), AMOUNT);
    }

    @Override
    protected SoundEvent getPickupSound() {
        return ModSounds.AMMO_PICKUP.get();
    }
}