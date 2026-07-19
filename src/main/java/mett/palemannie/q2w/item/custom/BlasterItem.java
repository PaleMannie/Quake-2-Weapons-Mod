package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.client.BlasterRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class BlasterItem extends AbstractQ2Weapon {

    public BlasterItem(Properties properties) {
        super(properties, 10, 10);
    }

    @Override
    protected String animationPrefix() {
        return "blaster";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new BlasterRenderer();
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleBlasterShoot(player);
    }
}