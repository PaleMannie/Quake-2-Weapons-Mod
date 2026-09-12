package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class HandgrenadeProjectileModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation HANDGRENADE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "handgrenade_projectile"), "main");
	private final ModelPart handgrenade;

	public HandgrenadeProjectileModel(ModelPart root) {
        super(root);
		this.handgrenade = root.getChild("handgrenade");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition handgrenade = partdefinition.addOrReplaceChild("handgrenade", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(3.0F, -7.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-9.0F, -7.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 10).addBox(-3.0F, -7.0F, 3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 20).addBox(-3.0F, -7.0F, -9.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 30).addBox(-3.0F, 3.0F, 3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(3.0F, 3.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 40).addBox(-3.0F, 3.0F, -9.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-9.0F, 3.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 28).addBox(-3.0F, 7.0F, -7.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(-7.0F, 7.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 33).addBox(-3.0F, 7.0F, 3.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 7).addBox(3.0F, 7.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 14).addBox(3.0F, -8.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 38).addBox(-3.0F, -8.0F, -7.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 21).addBox(-7.0F, -8.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 43).addBox(-3.0F, -8.0F, 3.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}