package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class GrenadelauncherProjectileModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation GRENADE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "grenadelauncher_projectile"), "main");
	private final ModelPart grenade;

	public GrenadelauncherProjectileModel(ModelPart root) {
        super(root);
		this.grenade = root.getChild("grenade");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition grenade = partdefinition.addOrReplaceChild("grenade", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.5F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-3.5F, -3.5F, -6.5F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(18, 20).addBox(-2.5F, -2.5F, -7.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}