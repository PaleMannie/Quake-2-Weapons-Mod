package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.HandgrenadeProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class HandgrenadeProjectileRenderer extends EntityRenderer<HandgrenadeProjectileEntity> {

    private static final ResourceLocation GRENADE_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/handgrenade_projectile/handgrenade_projectile.png");

    private final HandgrenadeProjectileModel<HandgrenadeProjectileEntity> model;

    public HandgrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new HandgrenadeProjectileModel<>(context.bakeLayer(HandgrenadeProjectileModel.HANDGRENADE_LAYER));
    }

   

    @Override
    public void render(HandgrenadeProjectileEntity grenadeEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();
        poseStack.scale(0.2f, 0.2f, 0.2f);
        poseStack.translate(0f, 0.1f, 0f);

        RandomSource random = RandomSource.create(grenadeEntity.getId());

        if (!grenadeEntity.hasStopped) {

            float tumbleSpeed = -25f;
            float tumbleX = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleY = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleZ = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));

            grenadeEntity.lastTumbleX = tumbleX;
            grenadeEntity.lastTumbleY = tumbleY;
            grenadeEntity.lastTumbleZ = tumbleZ;

            poseStack.mulPose(Axis.XP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.xRotO, grenadeEntity.getXRot())) + tumbleX));
            poseStack.mulPose(Axis.YP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.yRotO, grenadeEntity.getYRot()) + 180f) + tumbleY));
            poseStack.mulPose(Axis.ZP.rotationDegrees(tumbleZ));

        } else {

            poseStack.mulPose(Axis.XP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.xRotO, grenadeEntity.getXRot())) + grenadeEntity.lastTumbleX));
            poseStack.mulPose(Axis.YP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.yRotO, grenadeEntity.getYRot()) + 180f) + grenadeEntity.lastTumbleY));
            poseStack.mulPose(Axis.ZP.rotationDegrees(grenadeEntity.lastTumbleZ));
        }

        VertexConsumer normal = bufferSource.getBuffer(RenderType.entityCutoutNoCull(GRENADE_LOCATION));
        this.model.renderToBuffer(poseStack, normal, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();

        super.render(grenadeEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(HandgrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HandgrenadeProjectileEntity spit) { return GRENADE_LOCATION; }

}