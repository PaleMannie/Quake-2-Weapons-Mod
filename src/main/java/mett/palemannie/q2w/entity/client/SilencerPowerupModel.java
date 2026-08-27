package mett.palemannie.q2w.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SilencerPowerupModel<T extends Entity> extends HierarchicalModel<T> {

	public static final ModelLayerLocation SILENCER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "silencer_powerup"), "main");
	private static final String MAIN = "main";
	private final ModelPart root;

	public SilencerPowerupModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 17).addBox(-2.45F, -6.0F, 5.75F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 27).addBox(-1.55F, -5.0F, 3.75F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.55F, -7.0F, -6.25F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(18, 17).addBox(-3.55F, -7.0F, -9.25F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(18, 24).addBox(-3.55F, -3.0F, -8.25F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 26).addBox(-3.55F, -2.0F, -7.25F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() { return this.root; }

	@Override
	public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

	}
}

