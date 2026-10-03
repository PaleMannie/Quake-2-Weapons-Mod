package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.particle.custom.BfgExplosionParticle;
import mett.palemannie.q2w.particle.custom.BfgFlashParticle;
import mett.palemannie.q2w.particle.custom.BfgLaserParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Quake2Weapons.MODID, value = Dist.CLIENT)
public class ModParticleProviders {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BFG_LASER_PARTICLE.get(), BfgLaserParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BFG_EXPLOSION_PARTICLE.get(), BfgExplosionParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BFG_FLASH_PARTICLE.get(), BfgFlashParticle.Provider::new);
    }
}
