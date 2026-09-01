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

    public static final RegistryObject<SoundEvent> WEAPON_SWITCH = registerSoundEvents("weapon_switch");

    public static final RegistryObject<SoundEvent> AMMO_PICKUP = registerSoundEvents("ammo_pickup");
    public static final RegistryObject<SoundEvent> ITEM_PICKUP = registerSoundEvents("item_pickup");

    public static final RegistryObject<SoundEvent> BLASTER_SHOOT = registerSoundEvents("blaster_shoot");

    public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("shotgun_shoot");
    public static final RegistryObject<SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("super_shotgun_shoot");

    public static final RegistryObject<SoundEvent> BULLET_HIT = registerSoundEvents("bullet_hit");
    public static final RegistryObject<SoundEvent> BLASTER_HIT = registerSoundEvents("blaster_hit");

    public static final RegistryObject<SoundEvent> CHAINGUN_SHOOT = registerSoundEvents("chaingun_shoot");
    public static final RegistryObject<SoundEvent> CHAINGUN_SPINUP = registerSoundEvents("chaingun_spinup");
    public static final RegistryObject<SoundEvent> CHAINGUN_LOOP = registerSoundEvents("chaingun_loop");
    public static final RegistryObject<SoundEvent> CHAINGUN_SPINDOWN = registerSoundEvents("chaingun_spindown");

    public static final RegistryObject<SoundEvent> EXPLOSION = registerSoundEvents("explosion");

    public static final RegistryObject<SoundEvent> HANDGRENADE_START = registerSoundEvents("handgrenade_start");
    public static final RegistryObject<SoundEvent> HANDGRENADE_COOK = registerSoundEvents("handgrenade_cook");
    public static final RegistryObject<SoundEvent> HANDGRENADE_TOSS = registerSoundEvents("handgrenade_toss");
    public static final RegistryObject<SoundEvent> HANDGRENADE_BOUNCE = registerSoundEvents("handgrenade_bounce");

    public static final RegistryObject<SoundEvent> GRENADELAUNCHER_BOUNCE = registerSoundEvents("grenadelauncher_bounce");
    public static final RegistryObject<SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("grenadelauncher_shoot");

    public static final RegistryObject<SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("rocketlauncher_shoot");
    public static final RegistryObject<SoundEvent> ROCKET_LOOP = registerSoundEvents("rocket_loop");

    public static final RegistryObject<SoundEvent> HYPERBLASTER_SHOOT = registerSoundEvents("hyperblaster_shoot");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_LOOP = registerSoundEvents("hyperblaster_loop");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_SPINUP = registerSoundEvents("hyperblaster_spinup");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_SPINDOWN = registerSoundEvents("hyperblaster_spindown");

    public static final RegistryObject<SoundEvent> RAILGUN_SHOOT = registerSoundEvents("railgun_shoot");
    public static final RegistryObject<SoundEvent> RAILGUN_HUM = registerSoundEvents("railgun_hum");

    public static final RegistryObject<SoundEvent> BFG10K_WINDUP = registerSoundEvents("bfg10k_windup");
    public static final RegistryObject<SoundEvent> BFG10K_SHOOT = registerSoundEvents("bfg10k_shoot");
    public static final RegistryObject<SoundEvent> BFG10K_PROJECTILE_LOOP = registerSoundEvents("bfg10k_projectile_loop");
    public static final RegistryObject<SoundEvent> BFG10K_PROJECTILE_FLASH = registerSoundEvents("bfg10k_projectile_flash");
    public static final RegistryObject<SoundEvent> BFG10K_HUM = registerSoundEvents("bfg10k_hum");

    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("quad_damage_pickup");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("quad_damage_use");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("quad_damage_expire");

    public static final RegistryObject<SoundEvent> INVULN_PICKUP = registerSoundEvents("invuln_pickup");
    public static final RegistryObject<SoundEvent> INVULN_USE = registerSoundEvents("invuln_use");
    public static final RegistryObject<SoundEvent> INVULN_EXPIRE = registerSoundEvents("invuln_expire");

    public static final RegistryObject<SoundEvent> ENVIROSUIT_PICKUP = registerSoundEvents("envirosuit_pickup");
    public static final RegistryObject<SoundEvent> ENVIROSUIT_EXPIRE = registerSoundEvents("envirosuit_expire");

    public static final RegistryObject<SoundEvent> REBREATHER_USE = registerSoundEvents("rebreather_use");
    public static final RegistryObject<SoundEvent> ADRENALINE_USE = registerSoundEvents("adrenaline_use");

    public static final RegistryObject<SoundEvent> POWERSHIELD_ENABLE = registerSoundEvents("powershield_enable");
    public static final RegistryObject<SoundEvent> POWERSHIELD_DISABLE = registerSoundEvents("powershield_disable");

    public static final RegistryObject<SoundEvent> AMMOEMPTY = registerSoundEvents("ammoempty");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
