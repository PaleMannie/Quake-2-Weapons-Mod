package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class RebreatherPickupModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation REBREATHERPICKUP_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rebreather_pickup"), "main");
	private final ModelPart root;

	public RebreatherPickupModel(ModelPart root) {
        super(root);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-3.0F, -6.5F, -5.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 8).addBox(-8.0F, -5.0F, -5.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 32).addBox(3.0F, -5.0F, -5.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-9.0F, -5.0F, -1.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 20).addBox(5.0F, -5.0F, -1.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-2.0F, -13.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(38, 32).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -5.5F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}

