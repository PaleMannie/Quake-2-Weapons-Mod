package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.QuadDamagePowerupEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class QuaddamagePowerupRenderer extends EntityRenderer<QuadDamagePowerupEntity, EntityRenderState> {

    private static final Identifier QUAD_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/powerups/quad_damage_powerup.png");
    private final QuaddamagePowerupModel model;

    public QuaddamagePowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new QuaddamagePowerupModel(context.bakeLayer(QuaddamagePowerupModel.QUAD_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 5f;

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.translate(0f, 0.75f, 0f);
        poseStack.scale(0.75f, 0.75f, 0.75f);

        float ageInTicks = state.ageInTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(QUAD_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.eyes(QUAD_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(QuadDamagePowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}