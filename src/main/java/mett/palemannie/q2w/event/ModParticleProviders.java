package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.particle.custom.BfgExplosionParticle;
import mett.palemannie.q2w.particle.custom.BfgFlashParticle;
import mett.palemannie.q2w.particle.custom.BfgLaserParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleProviders {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BFG_LASER_PARTICLE.get(), BfgLaserParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BFG_EXPLOSION_PARTICLE.get(), BfgExplosionParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BFG_FLASH_PARTICLE.get(), BfgFlashParticle.Provider::new);
    }
}