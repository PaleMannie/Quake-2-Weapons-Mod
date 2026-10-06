package mett.palemannie.q2w.particle;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Quake2Weapons.MODID);

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BFG_LASER_PARTICLE =
            PARTICLES.register("q2w_bfg_laser_particle", () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BFG_EXPLOSION_PARTICLE =
            PARTICLES.register("q2w_bfg_explosion_particle", () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BFG_FLASH_PARTICLE =
            PARTICLES.register("q2w_bfg_flash_particle", () -> new SimpleParticleType(true));
}
