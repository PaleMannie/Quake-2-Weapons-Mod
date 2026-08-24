package mett.palemannie.q2w.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BfgExplosionParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    protected BfgExplosionParticle(ClientLevel level, double x, double y, double z,
                                   double xd, double yd, double zd, SpriteSet spriteSet) {
        super(level, x, y, z, 0d, 0d, 0d);

        this.spriteSet = spriteSet;

        this.xd = 0d;
        this.yd = 0d;
        this.zd = 0d;

        this.lifetime = 8;
        this.hasPhysics = false;
        this.gravity = 0f;
        this.friction = 1f;

        this.quadSize = 2.8f;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.alpha = 1f;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new BfgExplosionParticle(level, x, y, z, xd, yd, zd, spriteSet);
        }
    }
}