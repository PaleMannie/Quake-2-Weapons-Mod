package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BulletsAmmopickupEntity extends AbstractAmmopickupEntity {

    private static final int AMOUNT = 50;

    public BulletsAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public BulletsAmmopickupEntity(Level level, double x, double y, double z) {
        this(ModEntities.BULLETS_AMMOPICKUP.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.BULLET.get(), AMOUNT);
    }
}