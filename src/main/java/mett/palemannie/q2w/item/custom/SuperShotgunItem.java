package mett.palemannie.q2w.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.SuperShotgunRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SuperShotgunItem extends AbstractQ2Weapon{

    public SuperShotgunItem(Properties properties) {
        super(properties, 31, 29);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {

            private SuperShotgunRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {

                if (this.renderer == null) {
                    this.renderer = new SuperShotgunRenderer();
                }

                return this.renderer;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

                if (itemInHand.getItem() instanceof AbstractWeapon) {

                    int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                    poseStack.translate(side * 0.56f, -0.52f, -0.72f);

                    return true;
                }

                return false;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {

                if (!itemStack.isEmpty() && entityLiving.getItemInHand(hand) == itemStack) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                return HumanoidModel.ArmPose.EMPTY;
            }
        });
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.SHELL.get();
    }

    @Override
    protected String animationPrefix() {
        return "super_shotgun";
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
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                4f,
                0f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}