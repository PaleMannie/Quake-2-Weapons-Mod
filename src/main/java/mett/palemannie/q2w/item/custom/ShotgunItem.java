package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.ShotgunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ShotgunItem extends AbstractQ2Weapon{

    public ShotgunItem(Properties properties) {
        super(properties, 24, 24);
    }

    @Override
    protected String animationPrefix() {
        return "shotgun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new ShotgunRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.SHELL.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleShotgunShoot(player);
    }
}
