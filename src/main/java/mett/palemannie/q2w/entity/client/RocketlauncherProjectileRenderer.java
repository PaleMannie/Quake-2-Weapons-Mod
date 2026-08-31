package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.RocketProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RocketlauncherProjectileRenderer extends EntityRenderer<RocketProjectileEntity> {

    private static final ResourceLocation ROCKET_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/rocketlauncher_projectile.png");
    private final RocketlauncherProjectileModel<RocketProjectileEntity> model;

    public RocketlauncherProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RocketlauncherProjectileModel<>(context.bakeLayer(RocketlauncherProjectileModel.ROCKET_LAYER));
    }

    public void render(RocketProjectileEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.0f, 0.0F);
        poseStack.scale(0.75f, 0.75f, 0.75f);

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(v2, nailEntity.yRotO, nailEntity.getYRot()) + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(v2, nailEntity.xRotO, nailEntity.getXRot()) ));


        this.model.setupAnim(nailEntity, v2, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(ROCKET_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(nailEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(RocketProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RocketProjectileEntity spit) { return ROCKET_LOCATION; }

}