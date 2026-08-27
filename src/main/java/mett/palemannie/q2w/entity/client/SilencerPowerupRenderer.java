package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.SilencerPowerupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SilencerPowerupRenderer extends EntityRenderer<SilencerPowerupEntity> {

    private static final ResourceLocation ENVIROSUIT_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/powerups/silencer_powerup.png");
    private final SilencerPowerupModel<SilencerPowerupEntity> model;

    public SilencerPowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SilencerPowerupModel<>(context.bakeLayer(SilencerPowerupModel.SILENCER_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 4.375f;

    public void render(SilencerPowerupEntity rocketEntity, float v1, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0f, 2.75f, 0f);
        poseStack.scale(1.25f, 1.25f, 1.25f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));


        float ageInTicks = rocketEntity.tickCount + partialTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(ENVIROSUIT_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        VertexConsumer $$7 = bufferSource.getBuffer(RenderType.eyes(ENVIROSUIT_LOCATION));
        this.model.renderToBuffer(poseStack, $$7, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        poseStack.popPose();

        super.render(rocketEntity, v1, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(SilencerPowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SilencerPowerupEntity spit) { return ENVIROSUIT_LOCATION; }

}