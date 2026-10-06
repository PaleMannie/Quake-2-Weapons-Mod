package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Quake2Weapons.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> WEAPON_SWITCH = registerSoundEvents("q2w_weapon_switch");

    public static final DeferredHolder<SoundEvent, SoundEvent> AMMO_PICKUP = registerSoundEvents("q2w_ammo_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PICKUP = registerSoundEvents("q2w_item_pickup");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_SHOOT = registerSoundEvents("q2w_blaster_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("q2w_shotgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("q2w_super_shotgun_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> BULLET_HIT = registerSoundEvents("q2w_bullet_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_HIT = registerSoundEvents("q2w_blaster_hit");

    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SHOOT = registerSoundEvents("q2w_chaingun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SPINUP = registerSoundEvents("q2w_chaingun_spinup");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_LOOP = registerSoundEvents("q2w_chaingun_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SPINDOWN = registerSoundEvents("q2w_chaingun_spindown");

    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION = registerSoundEvents("q2w_explosion");

    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_START = SOUND_EVENTS.register("q2w_handgrenade_start", () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_handgrenade_start"), 16f));
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_COOK = registerSoundEvents("q2w_handgrenade_cook");
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_TOSS = registerSoundEvents("q2w_handgrenade_toss");
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_BOUNCE = registerSoundEvents("q2w_handgrenade_bounce");

    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADELAUNCHER_BOUNCE = registerSoundEvents("q2w_grenadelauncher_bounce");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("q2w_grenadelauncher_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("q2w_rocketlauncher_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKET_LOOP = registerSoundEvents("q2w_rocket_loop");

    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SHOOT = registerSoundEvents("q2w_hyperblaster_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_LOOP = registerSoundEvents("q2w_hyperblaster_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SPINUP = registerSoundEvents("q2w_hyperblaster_spinup");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SPINDOWN = registerSoundEvents("q2w_hyperblaster_spindown");

    public static final DeferredHolder<SoundEvent, SoundEvent> RAILGUN_SHOOT = registerSoundEvents("q2w_railgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> RAILGUN_HUM = registerSoundEvents("q2w_railgun_hum");

    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_WINDUP = registerSoundEvents("q2w_bfg10k_windup");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_SHOOT = registerSoundEvents("q2w_bfg10k_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_PROJECTILE_LOOP = registerSoundEvents("q2w_bfg10k_projectile_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_PROJECTILE_FLASH = registerSoundEvents("q2w_bfg10k_projectile_flash");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_HUM = registerSoundEvents("q2w_bfg10k_hum");

    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("q2w_quad_damage_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("q2w_quad_damage_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("q2w_quad_damage_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_PICKUP = registerSoundEvents("q2w_invuln_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_USE = registerSoundEvents("q2w_invuln_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_EXPIRE = registerSoundEvents("q2w_invuln_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENVIROSUIT_PICKUP = registerSoundEvents("q2w_envirosuit_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENVIROSUIT_EXPIRE = registerSoundEvents("q2w_envirosuit_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> REBREATHER_USE = registerSoundEvents("q2w_rebreather_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ADRENALINE_USE = registerSoundEvents("q2w_adrenaline_use");

    public static final DeferredHolder<SoundEvent, SoundEvent> POWERSHIELD_ENABLE = registerSoundEvents("q2w_powershield_enable");
    public static final DeferredHolder<SoundEvent, SoundEvent> POWERSHIELD_DISABLE = registerSoundEvents("q2w_powershield_disable");

    public static final DeferredHolder<SoundEvent, SoundEvent> AMMOEMPTY = registerSoundEvents("q2w_ammoempty");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
