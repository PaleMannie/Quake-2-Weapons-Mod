package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.RocketsAmmopickupEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class RocketsAmmopickupRenderer extends EntityRenderer<RocketsAmmopickupEntity, EntityRenderState> {

    private static final Identifier BULLETPICKUP_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/itempickups/rockets_ammopickup.png");
    private final RocketsAmmopickupModel model;

    public RocketsAmmopickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RocketsAmmopickupModel(context.bakeLayer(RocketsAmmopickupModel.ROCKETSPICKUP_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 4.375f;

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.translate(0f, 2.1f, 0f);
        poseStack.scale(1.25f, 1.25f, 1.25f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));


        float ageInTicks = state.ageInTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(BULLETPICKUP_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(RocketsAmmopickupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}