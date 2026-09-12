package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.HandgrenadeProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class HandgrenadeProjectileRenderer extends EntityRenderer<HandgrenadeProjectileEntity, ProjectileRenderState> {

    private static final Identifier GRENADE_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/handgrenade_projectile.png");

    private final HandgrenadeProjectileModel model;

    public HandgrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new HandgrenadeProjectileModel(context.bakeLayer(HandgrenadeProjectileModel.HANDGRENADE_LAYER));
    }

   

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();
        poseStack.scale(0.2f, 0.2f, 0.2f);
        poseStack.translate(0f, 0.1f, 0f);

        poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.zRot));

        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.entityCutoutNoCull(GRENADE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(HandgrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(HandgrenadeProjectileEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        if (!entity.hasStopped) {
            RandomSource random = RandomSource.create(entity.getId());
            float tumbleSpeed = -25f;
            entity.lastTumbleX = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
            entity.lastTumbleY = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
            entity.lastTumbleZ = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
        }
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + entity.lastTumbleX;
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 180f + entity.lastTumbleY;
        state.zRot = entity.lastTumbleZ;
    }

}