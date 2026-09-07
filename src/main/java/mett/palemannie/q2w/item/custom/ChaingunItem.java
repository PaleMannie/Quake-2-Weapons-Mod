package mett.palemannie.q2w.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.ChaingunRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ChaingunItem extends AbstractQ2Weapon {

    public ChaingunItem(Properties properties) {
        super(properties, FIRE_INTERVAL_TICKS, 40);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {

            private ChaingunRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {

                if (this.renderer == null) {
                    this.renderer = new ChaingunRenderer();
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
        return ModItems.BULLET.get();
    }
    public static final int FIRE_INTERVAL_TICKS = 2;
    public static final int AFTERSPIN_TICKS = 35;
    public static final int STAGE_1_END_TICKS = 10;

    public static final int STAGE_2_END_TICKS = 22;

    @Override
    protected String animationPrefix() {
        return "chaingun";
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
        return getShotsPerTriggerForUseTicks(useTicks);
    }

    public static int getShotsPerTriggerForUseTicks(int useTicks) {

        if (useTicks < STAGE_1_END_TICKS) { return 1; }
        if (useTicks < STAGE_2_END_TICKS) { return 2; }

        return 3;
    }

    public static float getVisualSpinSpeedRadiansPerTick(int useTicks) {

        float base = Mth.HALF_PI / 2 / FIRE_INTERVAL_TICKS;
        return base * getShotsPerTriggerForUseTicks(useTicks);
    }

    public static int useticks = 0;

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        useticks = useTicks;
        ServerPlayHandler.handleChaingunShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

        useticks = 0;
    }
}