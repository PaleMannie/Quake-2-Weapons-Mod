package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.Bfg10kProjectileEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class Bfg10kProjectileRenderer extends EntityRenderer<Bfg10kProjectileEntity> {

    private static final ResourceLocation FRAME_0 =
            ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/entity/projectiles/bfg_ball1.png");

    private static final ResourceLocation FRAME_1 =
            ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/entity/projectiles/bfg_ball2.png");

    public Bfg10kProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Bfg10kProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0d, 0d, 0d);

        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180f));

        float scale = 2f;
        poseStack.scale(scale, scale, scale);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(
                RenderType.entityTranslucent(getCurrentFrame(entity))
        );

        int light = LightTexture.FULL_BRIGHT;
        
        addVertex(vertexConsumer, matrix4f, matrix3f, -0.5f, -0.5f, 0f, 0f, 1f, light);
        addVertex(vertexConsumer, matrix4f, matrix3f,  0.5f, -0.5f, 0f, 1f, 1f, light);
        addVertex(vertexConsumer, matrix4f, matrix3f,  0.5f,  0.5f, 0f, 1f, 0f, light);
        addVertex(vertexConsumer, matrix4f, matrix3f, -0.5f,  0.5f, 0f, 0f, 0f, light);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private ResourceLocation getCurrentFrame(Bfg10kProjectileEntity entity) {
        return ((entity.tickCount / 2) & 1) == 0 ? FRAME_0 : FRAME_1;
    }

    private static void addVertex(VertexConsumer vertexConsumer, Matrix4f poseMatrix, Matrix3f normalMatrix, float x, float y, float z, float u, float v, int packedLight) {

        vertexConsumer.vertex(poseMatrix, x, y, z).color(255, 255, 255, 255).uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0f, 1f, 0f).endVertex();
    }

    @Override
    public boolean shouldRender(Bfg10kProjectileEntity entity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Bfg10kProjectileEntity entity) {
        return FRAME_0;
    }
}