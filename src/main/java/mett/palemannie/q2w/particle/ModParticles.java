package mett.palemannie.q2w.particle;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Quake2Weapons.MODID);


    public static final RegistryObject<SimpleParticleType> BFG_LASER_PARTICLE =
            PARTICLES.register("bfg_laser_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BFG_EXPLOSION_PARTICLE =
            PARTICLES.register("bfg_explosion_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BFG_FLASH_PARTICLE =
            PARTICLES.register("bfg_flash_particle", () -> new SimpleParticleType(true));
}