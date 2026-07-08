package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Quake2Weapons.MODID);

    public static final RegistryObject<SoundEvent> NAILGUN_SHOOT = registerSoundEvents("nailgun_shoot");
    public static final RegistryObject<SoundEvent> SUPER_NAILGUN_SHOOT = registerSoundEvents("super_nailgun_shoot");
    public static final RegistryObject<SoundEvent> NAILGUN_HIT = registerSoundEvents("nailgun_hit");
    public static final RegistryObject<SoundEvent> THUNDERBOLT_START = registerSoundEvents("thunderbolt_start");
    public static final RegistryObject<SoundEvent> THUNDERBOLT_LOOP = registerSoundEvents("thunderbolt_loop");
    public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("shotgun_shoot");
    public static final RegistryObject<SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("super_shotgun_shoot");
    public static final RegistryObject<SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("rocketlauncher_shoot");
    public static final RegistryObject<SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("grenadelauncher_shoot");
    public static final RegistryObject<SoundEvent> GRENADE_BOUNCE = registerSoundEvents("grenade_bounce");
    public static final RegistryObject<SoundEvent> EXPLOSION = registerSoundEvents("explosion");
    public static final RegistryObject<SoundEvent> AXE_HIT_AIR = registerSoundEvents("axe_hit_air");
    public static final RegistryObject<SoundEvent> AXE_HIT_SOLID = registerSoundEvents("axe_hit_solid");
    public static final RegistryObject<SoundEvent> AXE_HIT_ENTITY = registerSoundEvents("axe_hit_entity");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("quad_damage_pickup");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("quad_damage_use");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("quad_damage_expire");
    public static final RegistryObject<SoundEvent> PENTAGRAM_PICKUP = registerSoundEvents("pentagram_pickup");
    public static final RegistryObject<SoundEvent> PENTAGRAM_USE = registerSoundEvents("pentagram_use");
    public static final RegistryObject<SoundEvent> PENTAGRAM_EXPIRE = registerSoundEvents("pentagram_expire");
    public static final RegistryObject<SoundEvent> RING_PICKUP = registerSoundEvents("ring_pickup");
    public static final RegistryObject<SoundEvent> RING_USE = registerSoundEvents("ring_use");
    public static final RegistryObject<SoundEvent> RING_EXPIRE = registerSoundEvents("ring_expire");
    public static final RegistryObject<SoundEvent> BIOSUIT_PICKUP = registerSoundEvents("biosuit_pickup");
    public static final RegistryObject<SoundEvent> BIOSUIT_EXPIRE = registerSoundEvents("biosuit_expire");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
