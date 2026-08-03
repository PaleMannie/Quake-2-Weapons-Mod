package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.SuperShotgunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SuperShotgunItem extends AbstractQ2Weapon{

    public SuperShotgunItem(Properties properties) {
        super(properties, 31, 29);
    }

    @Override
    protected String animationPrefix() {
        return "super_shotgun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new SuperShotgunRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.SHELL.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 2;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleSuperShotgunShoot(player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}