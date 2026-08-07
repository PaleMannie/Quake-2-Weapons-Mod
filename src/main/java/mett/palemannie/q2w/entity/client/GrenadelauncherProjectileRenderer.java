package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.GrenadelauncherProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GrenadelauncherProjectileRenderer extends EntityRenderer<GrenadelauncherProjectileEntity> {

    private static final ResourceLocation GRENADE_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/grenadelauncher_projectile/grenadelauncher_projectile.png");

    private final GrenadelauncherProjectileModel<GrenadelauncherProjectileEntity> model;

    public GrenadelauncherProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadelauncherProjectileModel<>(context.bakeLayer(GrenadelauncherProjectileModel.GRENADE_LAYER));
    }

    private static void applyGrenadeDirectionRotation(PoseStack poseStack, float pitch, float yaw) {

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw + 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }

    @Override
    public void render(GrenadelauncherProjectileEntity grenadeEntity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.scale(0.2F, 0.2F, 0.2F);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        Vec3 motion = grenadeEntity.getDeltaMovement();

        final double VISUAL_STOP_SPEED = 0.03D;
        final double VISUAL_STOP_SPEED_SQR = VISUAL_STOP_SPEED * VISUAL_STOP_SPEED;

        if (motion.lengthSqr() > VISUAL_STOP_SPEED_SQR && !grenadeEntity.hasStopped) {

            Vec3 direction = motion.normalize();

            float yaw = (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
            float pitch = (float) (-(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * Mth.RAD_TO_DEG));

            grenadeEntity.lastTumbleX = pitch;
            grenadeEntity.lastTumbleY = yaw;
            grenadeEntity.lastTumbleZ = 0.0F;

            applyGrenadeDirectionRotation(poseStack, pitch, yaw);
        } else {

            grenadeEntity.hasStopped = true;

            applyGrenadeDirectionRotation(poseStack, grenadeEntity.lastTumbleX, grenadeEntity.lastTumbleY);
        }

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(GRENADE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.popPose();

        super.render(grenadeEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(GrenadelauncherProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrenadelauncherProjectileEntity spit) { return GRENADE_LOCATION; }

}