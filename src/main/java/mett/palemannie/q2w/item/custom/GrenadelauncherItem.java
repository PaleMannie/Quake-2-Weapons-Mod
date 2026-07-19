package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.GrenadelauncherRenderer;
import mett.palemannie.q2w.item.client.MachinegunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GrenadelauncherItem extends AbstractQ2Weapon{

    public GrenadelauncherItem(Properties pProperties) {
        super(pProperties,24,24);
    }

    @Override
    protected String animationPrefix() {
        return "grenadelauncher";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new GrenadelauncherRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.GRENADE.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleGrenadeLauncherShoot(player);
    }
}
