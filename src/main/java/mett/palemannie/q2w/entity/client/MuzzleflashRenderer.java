package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.MuzzleflashEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class MuzzleflashRenderer extends EntityRenderer<MuzzleflashEntity, EntityRenderState> {

    private static final Identifier FLASH_LOCATION = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID,"textures/entity/projectiles/muzzleflash.png");
    private final MuzzleflashModel model;

    public MuzzleflashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MuzzleflashModel(context.bakeLayer(MuzzleflashModel.FLASH_LAYER));
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}