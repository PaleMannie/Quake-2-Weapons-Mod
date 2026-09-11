package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.AdrenalinePickupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class AdrenalinePickupRenderer extends EntityRenderer<AdrenalinePickupEntity, PickupEntityRenderState> {

    private static final Identifier ADRENALINEPICKUP_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/itempickups/adrenaline_pickup.png");

    private final AdrenalinePickupModel model;

    public AdrenalinePickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new AdrenalinePickupModel(context.bakeLayer(AdrenalinePickupModel.ADRENALINEPICKUP_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 4.375f;

    public void submit(PickupEntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState pCameraRenderState) {

        poseStack.pushPose();

        poseStack.translate(0f, 1.75f, 0f);
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));


        float ageInTicks = state.tickCount + state.partialTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(ADRENALINEPICKUP_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(AdrenalinePickupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public PickupEntityRenderState createRenderState() {
        return new PickupEntityRenderState();
    }

    @Override
    public void extractRenderState(AdrenalinePickupEntity entity, PickupEntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.tickCount = entity.tickCount;
        state.partialTicks = partialTick;
        state.bobbingSpeed = this.bobbingSpeed;
        state.bobbingHeight = this.bobbingHeight;
        state.rotationSpeed = this.rotationSpeed;
    }
}
