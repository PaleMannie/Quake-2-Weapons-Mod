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

public class EnvirosuitPowerupModel<T extends Entity> extends HierarchicalModel<T> {

	public static final ModelLayerLocation ENVIROSUIT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "invuln_powerup"), "main");
	private static final String MAIN = "main";
	private final ModelPart root;

	public EnvirosuitPowerupModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(26, 25).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-5.0F, -12.0F, 0.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-4.0F, -14.0F, 3.0F, 8.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(26, 34).addBox(-2.0F, -15.0F, -3.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(-2.0F, -12.5F, -4.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 11).addBox(3.0F, -11.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-8.0F, -11.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 5).addBox(-1.0F, -16.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 37).addBox(-1.0F, -16.0F, 1.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(10, 42).addBox(-3.0F, -11.5F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(42, 34).addBox(2.0F, -11.5F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(42, 41).addBox(-3.0F, -13.5F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 43).addBox(2.0F, -13.5F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(2.0F, -13.5F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(10, 37).addBox(-3.0F, -13.5F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(31, 13).addBox(-3.0F, -9.5F, -3.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 18).addBox(-1.0F, -9.0F, -3.475F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 38).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -4.5F, 0.0F, 0.0F, 0.7854F));

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

