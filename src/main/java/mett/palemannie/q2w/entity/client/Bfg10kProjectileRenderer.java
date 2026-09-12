package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.Bfg10kProjectileEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class Bfg10kProjectileRenderer extends EntityRenderer<Bfg10kProjectileEntity, Bfg10kProjectileRenderer.Bfg10kProjectileRenderState> {

    private static final Identifier FRAME_0 =
            Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/entity/projectiles/bfg_ball1.png");

    private static final Identifier FRAME_1 =
            Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/entity/projectiles/bfg_ball2.png");

    public Bfg10kProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(Bfg10kProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.mulPose(cameraState.orientation);
        poseStack.mulPose(Axis.YP.rotationDegrees(180f));

        float scale = 2f;
        poseStack.scale(scale, scale, scale);

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(state.texture), (pose, vertexConsumer) -> {
            addVertex(vertexConsumer, pose, -0.5f, -0.5f, 0f, 1f);
            addVertex(vertexConsumer, pose,  0.5f, -0.5f, 1f, 1f);
            addVertex(vertexConsumer, pose,  0.5f,  0.5f, 1f, 0f);
            addVertex(vertexConsumer, pose, -0.5f,  0.5f, 0f, 0f);
        });

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public Bfg10kProjectileRenderState createRenderState() {
        return new Bfg10kProjectileRenderState();
    }

    @Override
    public void extractRenderState(Bfg10kProjectileEntity entity, Bfg10kProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.texture = ((entity.tickCount / 2) & 1) == 0 ? FRAME_0 : FRAME_1;
    }

    private static void addVertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float u, float v) {
        vertexConsumer.addVertex(pose, x, y, 0f)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(pose, 0f, 1f, 0f);
    }

    @Override
    public boolean shouldRender(Bfg10kProjectileEntity entity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    public static class Bfg10kProjectileRenderState extends EntityRenderState {
        public Identifier texture = FRAME_0;
    }
}
