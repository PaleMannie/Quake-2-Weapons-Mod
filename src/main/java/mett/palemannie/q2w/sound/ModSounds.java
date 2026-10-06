package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Quake2Weapons.MODID);

    public static final RegistryObject<SoundEvent> WEAPON_SWITCH = registerSoundEvents("q2w_weapon_switch");

    public static final RegistryObject<SoundEvent> AMMO_PICKUP = registerSoundEvents("q2w_ammo_pickup");
    public static final RegistryObject<SoundEvent> ITEM_PICKUP = registerSoundEvents("q2w_item_pickup");

    public static final RegistryObject<SoundEvent> BLASTER_SHOOT = registerSoundEvents("q2w_blaster_shoot");

    public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("q2w_shotgun_shoot");
    public static final RegistryObject<SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("q2w_super_shotgun_shoot");

    public static final RegistryObject<SoundEvent> BULLET_HIT = registerSoundEvents("q2w_bullet_hit");
    public static final RegistryObject<SoundEvent> BLASTER_HIT = registerSoundEvents("q2w_blaster_hit");

    public static final RegistryObject<SoundEvent> CHAINGUN_SHOOT = registerSoundEvents("q2w_chaingun_shoot");
    public static final RegistryObject<SoundEvent> CHAINGUN_SPINUP = registerSoundEvents("q2w_chaingun_spinup");
    public static final RegistryObject<SoundEvent> CHAINGUN_LOOP = registerSoundEvents("q2w_chaingun_loop");
    public static final RegistryObject<SoundEvent> CHAINGUN_SPINDOWN = registerSoundEvents("q2w_chaingun_spindown");

    public static final RegistryObject<SoundEvent> EXPLOSION = registerSoundEvents("q2w_explosion");

    public static final RegistryObject<SoundEvent> HANDGRENADE_START = SOUND_EVENTS.register("q2w_handgrenade_start", () -> SoundEvent.createFixedRangeEvent(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_handgrenade_start"), 16f));
    public static final RegistryObject<SoundEvent> HANDGRENADE_COOK = registerSoundEvents("q2w_handgrenade_cook");
    public static final RegistryObject<SoundEvent> HANDGRENADE_TOSS = registerSoundEvents("q2w_handgrenade_toss");
    public static final RegistryObject<SoundEvent> HANDGRENADE_BOUNCE = registerSoundEvents("q2w_handgrenade_bounce");

    public static final RegistryObject<SoundEvent> GRENADELAUNCHER_BOUNCE = registerSoundEvents("q2w_grenadelauncher_bounce");
    public static final RegistryObject<SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("q2w_grenadelauncher_shoot");

    public static final RegistryObject<SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("q2w_rocketlauncher_shoot");
    public static final RegistryObject<SoundEvent> ROCKET_LOOP = registerSoundEvents("q2w_rocket_loop");

    public static final RegistryObject<SoundEvent> HYPERBLASTER_SHOOT = registerSoundEvents("q2w_hyperblaster_shoot");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_LOOP = registerSoundEvents("q2w_hyperblaster_loop");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_SPINUP = registerSoundEvents("q2w_hyperblaster_spinup");
    public static final RegistryObject<SoundEvent> HYPERBLASTER_SPINDOWN = registerSoundEvents("q2w_hyperblaster_spindown");

    public static final RegistryObject<SoundEvent> RAILGUN_SHOOT = registerSoundEvents("q2w_railgun_shoot");
    public static final RegistryObject<SoundEvent> RAILGUN_HUM = registerSoundEvents("q2w_railgun_hum");

    public static final RegistryObject<SoundEvent> BFG10K_WINDUP = registerSoundEvents("q2w_bfg10k_windup");
    public static final RegistryObject<SoundEvent> BFG10K_SHOOT = registerSoundEvents("q2w_bfg10k_shoot");
    public static final RegistryObject<SoundEvent> BFG10K_PROJECTILE_LOOP = registerSoundEvents("q2w_bfg10k_projectile_loop");
    public static final RegistryObject<SoundEvent> BFG10K_PROJECTILE_FLASH = registerSoundEvents("q2w_bfg10k_projectile_flash");
    public static final RegistryObject<SoundEvent> BFG10K_HUM = registerSoundEvents("q2w_bfg10k_hum");

    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("q2w_quad_damage_pickup");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("q2w_quad_damage_use");
    public static final RegistryObject<SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("q2w_quad_damage_expire");

    public static final RegistryObject<SoundEvent> INVULN_PICKUP = registerSoundEvents("q2w_invuln_pickup");
    public static final RegistryObject<SoundEvent> INVULN_USE = registerSoundEvents("q2w_invuln_use");
    public static final RegistryObject<SoundEvent> INVULN_EXPIRE = registerSoundEvents("q2w_invuln_expire");

    public static final RegistryObject<SoundEvent> ENVIROSUIT_PICKUP = registerSoundEvents("q2w_envirosuit_pickup");
    public static final RegistryObject<SoundEvent> ENVIROSUIT_EXPIRE = registerSoundEvents("q2w_envirosuit_expire");

    public static final RegistryObject<SoundEvent> REBREATHER_USE = registerSoundEvents("q2w_rebreather_use");
    public static final RegistryObject<SoundEvent> ADRENALINE_USE = registerSoundEvents("q2w_adrenaline_use");

    public static final RegistryObject<SoundEvent> POWERSHIELD_ENABLE = registerSoundEvents("q2w_powershield_enable");
    public static final RegistryObject<SoundEvent> POWERSHIELD_DISABLE = registerSoundEvents("q2w_powershield_disable");

    public static final RegistryObject<SoundEvent> AMMOEMPTY = registerSoundEvents("q2w_ammoempty");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, name)));
    }

    public static void register(BusGroup eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
