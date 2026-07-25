package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.MachinegunRenderer;
import mett.palemannie.q2w.item.client.ShotgunRenderer;
import mett.palemannie.q2w.item.client.SuperShotgunRenderer;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

public class MachinegunItem extends AbstractQ2Weapon{

    public MachinegunItem(Properties pProperties) {
        super(pProperties,2,1);
    }

    @Override
    protected String animationPrefix() {
        return "machinegun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new MachinegunRenderer();
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
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleMachinegunShoot(player);
    }
}
