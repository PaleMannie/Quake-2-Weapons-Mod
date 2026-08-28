package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SlugAmmopickupEntity extends AbstractAmmopickupEntity {

    private static final int AMOUNT = 10;

    public SlugAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SlugAmmopickupEntity(Level level, double x, double y, double z) {
        this(ModEntities.SLUGS_AMMOPICKUP.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.SLUG.get(), AMOUNT);
    }
}