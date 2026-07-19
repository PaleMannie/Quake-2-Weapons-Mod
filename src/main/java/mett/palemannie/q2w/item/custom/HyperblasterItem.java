package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.HyperblasterRenderer;
import mett.palemannie.q2w.item.client.MachinegunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HyperblasterItem extends AbstractQ2Weapon{

    public HyperblasterItem(Properties pProperties) {
        super(pProperties,2,20);
    }

    @Override
    protected String animationPrefix() {
        return "hyperblaster";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new HyperblasterRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.CELL.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleHyperblasterShoot(player);
    }
}
