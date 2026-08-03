package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.GrenadelauncherRenderer;
import mett.palemannie.q2w.item.client.RocketlauncherRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RocketlauncherItem extends AbstractQ2Weapon{

    public RocketlauncherItem(Properties pProperties) {
        super(pProperties,18,18);
    }

    @Override
    protected String animationPrefix() {
        return "rocketlauncher";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new RocketlauncherRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.ROCKET.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleRocketLauncherShoot(player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
