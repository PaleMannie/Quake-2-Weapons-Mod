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

public class RocketlauncherProjectileModel<T extends Entity> extends HierarchicalModel<T> {

	public static final ModelLayerLocation ROCKET_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "rocketlauncher_projectile"), "main");
	private static final String MAIN = "main";
	private final ModelPart root;

	public RocketlauncherProjectileModel(ModelPart root) {
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-0.5F, 4.0F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(18, 20).addBox(2.0F, 1.5F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 29).addBox(-0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(18, 29).addBox(-3.0F, 1.5F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(36, 20).addBox(-1.0F, 1.0F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

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