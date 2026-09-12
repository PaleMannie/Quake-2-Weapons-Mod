package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.RocketProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class RocketlauncherProjectileRenderer extends EntityRenderer<RocketProjectileEntity, ProjectileRenderState> {

    private static final Identifier ROCKET_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/rocketlauncher_projectile.png");
    private final RocketlauncherProjectileModel model;

    public RocketlauncherProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RocketlauncherProjectileModel(context.bakeLayer(RocketlauncherProjectileModel.ROCKET_LAYER));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.0f, 0.0F);
        poseStack.scale(0.75f, 0.75f, 0.75f);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot ));
        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(ROCKET_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(RocketProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(RocketProjectileEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
    }

}