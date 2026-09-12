package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.GrenadelauncherProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class GrenadelauncherProjectileRenderer extends EntityRenderer<GrenadelauncherProjectileEntity, ProjectileRenderState> {

    private static final Identifier GRENADE_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/grenadelauncher_projectile.png");

    private final GrenadelauncherProjectileModel model;

    public GrenadelauncherProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadelauncherProjectileModel(context.bakeLayer(GrenadelauncherProjectileModel.GRENADE_LAYER));
    }

    private static void applyGrenadeDirectionRotation(PoseStack poseStack, float pitch, float yaw) {

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw + 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.scale(0.3F, 0.3F, 0.3F);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        applyGrenadeDirectionRotation(poseStack, state.xRot, state.yRot);

        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.entityCutoutNoCull(GRENADE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(GrenadelauncherProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(GrenadelauncherProjectileEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        Vec3 motion = entity.getDeltaMovement();
        if (motion.lengthSqr() > 0.03D * 0.03D && !entity.hasStopped) {
            Vec3 direction = motion.normalize();
            entity.lastTumbleY = (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
            entity.lastTumbleX = (float) (-Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * Mth.RAD_TO_DEG);
            entity.lastTumbleZ = 0f;
        } else {
            entity.hasStopped = true;
        }
        state.xRot = entity.lastTumbleX;
        state.yRot = entity.lastTumbleY;
    }

}