package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.Identifier;

public class MuzzleflashModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation FLASH_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "muzzleflash"), "main");
    private static final String MAIN = "main";
    private final ModelPart root;

    public MuzzleflashModel(ModelPart root) {
        super(root);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
}