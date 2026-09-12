package mett.palemannie.q2w.entity.client;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class Bfg10kProjectileModel extends EntityModel<EntityRenderState> {

	public static final ModelLayerLocation BALL_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "bfg_ball"), "main");
	private static final String MAIN = "main";
	private final ModelPart root;

	public Bfg10kProjectileModel(ModelPart root) {
		super(root);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();

		return LayerDefinition.create(meshdefinition, 48, 48);
	}
}