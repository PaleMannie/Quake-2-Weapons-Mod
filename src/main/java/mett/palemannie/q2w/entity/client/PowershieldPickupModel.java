package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class PowershieldPickupModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation POWERSHIELDPICKUP_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "powershield_pickup"), "main");
	private final ModelPart root;

	public PowershieldPickupModel(ModelPart root) {
        super(root);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, -3.0F, -7.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(-5.0F, -3.0F, -7.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(24, 51).addBox(-4.0F, -3.0F, -7.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 51).addBox(-4.0F, -3.0F, 6.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(3.0F, -3.975F, -5.0F, 3.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(30, 0).addBox(3.25F, -7.975F, -6.0F, 3.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(4.0F, -11.975F, -5.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(48, 55).addBox(4.0F, -15.975F, -3.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(52, 34).addBox(3.525F, -12.975F, 1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(24, 55).addBox(3.525F, -12.975F, -4.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(54, 55).addBox(4.0F, -15.975F, 1.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 48).addBox(-4.5F, -16.975F, 1.5F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 48).addBox(-4.5F, -16.975F, -3.5F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 16).addBox(-6.0F, -7.975F, -5.0F, 3.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(36, 55).addBox(-5.0F, -15.975F, -3.5F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(42, 55).addBox(-5.0F, -15.975F, 1.5F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 34).addBox(-6.0F, -12.975F, -5.0F, 3.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}

