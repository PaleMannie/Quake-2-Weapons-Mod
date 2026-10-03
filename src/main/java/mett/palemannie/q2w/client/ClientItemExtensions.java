package mett.palemannie.q2w.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.*;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public final class ClientItemExtensions {
    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {
        event.registerItem(weapon(Bfg10kRenderer::new), ModItems.BFG10K);
        event.registerItem(weapon(BlasterRenderer::new), ModItems.BLASTER);
        event.registerItem(weapon(ChaingunRenderer::new), ModItems.CHAINGUN);
        event.registerItem(weapon(GrenadelauncherRenderer::new), ModItems.GRENADELAUNCHER);
        event.registerItem(weapon(HandgrenadeRenderer::new), ModItems.GRENADE);
        event.registerItem(weapon(HyperblasterRenderer::new), ModItems.HYPERBLASTER);
        event.registerItem(weapon(MachinegunRenderer::new), ModItems.MACHINEGUN);
        event.registerItem(weapon(RailgunRenderer::new), ModItems.RAILGUN);
        event.registerItem(weapon(RocketlauncherRenderer::new), ModItems.ROCKETLAUNCHER);
        event.registerItem(weapon(ShotgunRenderer::new), ModItems.SHOTGUN);
        event.registerItem(weapon(SuperShotgunRenderer::new), ModItems.SUPER_SHOTGUN);
    }

    private static IClientItemExtensions weapon(Supplier<BlockEntityWithoutLevelRenderer> factory) {
        return new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) renderer = factory.get();
                return renderer;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
                                                   ItemStack stack, float partialTick, float equipProgress, float swingProgress) {
                if (!(stack.getItem() instanceof AbstractWeapon)) return false;
                int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate(side * 0.56f, -0.52f, -0.72f);
                return true;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
                return !stack.isEmpty() && entity.getItemInHand(hand) == stack
                        ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
            }
        };
    }
}
