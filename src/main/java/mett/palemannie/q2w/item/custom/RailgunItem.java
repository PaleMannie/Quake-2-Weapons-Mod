package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.RailgunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RailgunItem extends AbstractQ2Weapon{

    public RailgunItem(Properties properties) {
        super(properties, 36, 34);
    }

    @Override
    protected String animationPrefix() {
        return "railgun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new RailgunRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.SLUG.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleRailgunShoot(player);
    }
}
