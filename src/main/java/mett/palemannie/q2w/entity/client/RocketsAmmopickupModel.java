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

public class RocketsAmmopickupModel<T extends Entity> extends HierarchicalModel<T> {

	public static final ModelLayerLocation ROCKETSPICKUP_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "rockets_ammopickup"), "main");
	private final ModelPart root;

	public RocketsAmmopickupModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(20, 16).addBox(2.475F, -12.001F, -2.0F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-3.4532F, -12.001F, -2.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(4, 33).addBox(-3.5F, -12.005F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(4, 41).addBox(-3.5F, 0.005F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-2.8971F, -5.9995F, -3.183F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 0).addBox(-1.9689F, -5.9995F, -3.183F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7519F, -5.9995F, 1.0825F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(30, 16).addBox(-4.7721F, -6.0005F, -1.8995F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.8439F, -6.0005F, -1.8995F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7519F, -5.9995F, 1.0825F, 0.0F, -1.0472F, 0.0F));

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

