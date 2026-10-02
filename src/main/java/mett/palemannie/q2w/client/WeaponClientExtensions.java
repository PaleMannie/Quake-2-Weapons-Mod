package mett.palemannie.q2w.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
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
public final class WeaponClientExtensions {
    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {
        var extensions = new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poses, PlayerRenderState player, HumanoidArm arm,
                    ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
                int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                poses.translate(side * 0.56f, -0.52f, -0.72f);
                return true;
            }
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
                return !stack.isEmpty() && entity.getItemInHand(hand) == stack
                        ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
            }
        };
        ModItems.ITEMS.getEntries().stream().map(holder -> holder.get())
                .filter(item -> item instanceof AbstractWeapon)
                .forEach(item -> event.registerItem(extensions, item));
    }
}
