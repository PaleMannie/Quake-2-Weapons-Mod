package mett.palemannie.q2w.particle.custom;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;

final class BfgParticleLayer {
    static final SingleQuadParticle.Layer EMISSIVE = new SingleQuadParticle.Layer(
            false,
            TextureAtlas.LOCATION_PARTICLES,
            RenderPipeline.builder(RenderPipelines.PARTICLE_SNIPPET)
                    .withLocation("q2w/pipeline/bfg_emissive_cutout_particle")
                    .withShaderDefine("ALPHA_CUTOUT", 0.1f)
                    .build()
    );

    private BfgParticleLayer() {
    }
}
