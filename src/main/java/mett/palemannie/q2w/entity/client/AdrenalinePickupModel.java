package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class AdrenalinePickupModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation ADRENALINEPICKUP_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "adrenaline_pickup"), "main");
	private final ModelPart root;

	public AdrenalinePickupModel(ModelPart root) {
        super(root);
        this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 10).addBox(-5.0F, -3.0F, -3.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-3.0F, -11.0F, -1.0F, 6.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -15.0F, -3.0F, 10.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(16, 19).addBox(1.0F, -6.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 26).addBox(1.0F, -11.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 29).addBox(-4.0F, -11.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(30, 19).addBox(-4.0F, -6.0F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}

