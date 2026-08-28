package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GrenadeAmmopickupEntity extends AbstractAmmopickupEntity {

    private static final int AMOUNT = 5;

    public GrenadeAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public GrenadeAmmopickupEntity(Level level, double x, double y, double z) {
        this(ModEntities.GRENADES_AMMOPICKUP.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.GRENADE.get(), AMOUNT);
    }
}