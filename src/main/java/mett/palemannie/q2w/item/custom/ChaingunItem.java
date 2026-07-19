package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.ChaingunRenderer;
import mett.palemannie.q2w.item.client.MachinegunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChaingunItem extends AbstractQ2Weapon{

    public ChaingunItem(Properties pProperties) {
        super(pProperties,2,2);
    }

    @Override
    protected String animationPrefix() {
        return "chaingun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new ChaingunRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.BULLET.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected int shotsPerTrigger(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        if (useTicks < 10) {
            return 1;
        }

        if (useTicks < 22) {
            return 2;
        }

        return 3;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleChaingunShoot(player);
    }
}
