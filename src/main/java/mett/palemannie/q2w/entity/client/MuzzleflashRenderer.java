package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.MuzzleflashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MuzzleflashRenderer extends EntityRenderer<MuzzleflashEntity> {

    private static final ResourceLocation FLASH_LOCATION = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/muzzleflash/muzzleflash.png");
    private final MuzzleflashModel<MuzzleflashEntity> model;

    public MuzzleflashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MuzzleflashModel<>(context.bakeLayer(MuzzleflashModel.FLASH_LAYER));
    }

    public void render(MuzzleflashEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MuzzleflashEntity spit) { return FLASH_LOCATION; }

}