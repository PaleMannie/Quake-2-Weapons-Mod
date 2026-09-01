package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.AdrenalinePickupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AdrenalinePickupRenderer extends EntityRenderer<AdrenalinePickupEntity> {

    private static final ResourceLocation ADRENALINEPICKUP_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/itempickups/adrenaline_pickup.png");
    private final AdrenalinePickupModel<AdrenalinePickupEntity> model;

    public AdrenalinePickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new AdrenalinePickupModel<>(context.bakeLayer(AdrenalinePickupModel.ADRENALINEPICKUP_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 4.375f;

    public void render(AdrenalinePickupEntity rocketEntity, float v1, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0f, 1.75f, 0f);
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));


        float ageInTicks = rocketEntity.tickCount + partialTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(ADRENALINEPICKUP_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        poseStack.popPose();

        super.render(rocketEntity, v1, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(AdrenalinePickupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AdrenalinePickupEntity spit) { return ADRENALINEPICKUP_LOCATION; }

}