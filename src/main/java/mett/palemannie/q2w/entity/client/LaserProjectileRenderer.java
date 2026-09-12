package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.LaserProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class LaserProjectileRenderer extends EntityRenderer<LaserProjectileEntity, ProjectileRenderState> {

    private static final Identifier LASER_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/laser_projectile.png");
    private final LaserProjectileModel model;

    public LaserProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LaserProjectileModel(context.bakeLayer(LaserProjectileModel.LASER_LAYER));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.0f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot ));

        poseStack.scale(1f, 1f, 1f);
        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(LASER_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(LaserProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(LaserProjectileEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
    }

}