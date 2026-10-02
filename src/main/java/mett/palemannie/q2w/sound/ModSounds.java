package mett.palemannie.q2w.sound;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Quake2Weapons.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> WEAPON_SWITCH = registerSoundEvents("weapon_switch");

    public static final DeferredHolder<SoundEvent, SoundEvent> AMMO_PICKUP = registerSoundEvents("ammo_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PICKUP = registerSoundEvents("item_pickup");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_SHOOT = registerSoundEvents("blaster_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("shotgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("super_shotgun_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> BULLET_HIT = registerSoundEvents("bullet_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_HIT = registerSoundEvents("blaster_hit");

    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SHOOT = registerSoundEvents("chaingun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SPINUP = registerSoundEvents("chaingun_spinup");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_LOOP = registerSoundEvents("chaingun_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINGUN_SPINDOWN = registerSoundEvents("chaingun_spindown");

    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION = registerSoundEvents("explosion");

    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_START = SOUND_EVENTS.register("handgrenade_start", () -> SoundEvent.createFixedRangeEvent(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "handgrenade_start"), 16f));
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_COOK = registerSoundEvents("handgrenade_cook");
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_TOSS = registerSoundEvents("handgrenade_toss");
    public static final DeferredHolder<SoundEvent, SoundEvent> HANDGRENADE_BOUNCE = registerSoundEvents("handgrenade_bounce");

    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADELAUNCHER_BOUNCE = registerSoundEvents("grenadelauncher_bounce");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("grenadelauncher_shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("rocketlauncher_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKET_LOOP = registerSoundEvents("rocket_loop");

    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SHOOT = registerSoundEvents("hyperblaster_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_LOOP = registerSoundEvents("hyperblaster_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SPINUP = registerSoundEvents("hyperblaster_spinup");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYPERBLASTER_SPINDOWN = registerSoundEvents("hyperblaster_spindown");

    public static final DeferredHolder<SoundEvent, SoundEvent> RAILGUN_SHOOT = registerSoundEvents("railgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> RAILGUN_HUM = registerSoundEvents("railgun_hum");

    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_WINDUP = registerSoundEvents("bfg10k_windup");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_SHOOT = registerSoundEvents("bfg10k_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_PROJECTILE_LOOP = registerSoundEvents("bfg10k_projectile_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_PROJECTILE_FLASH = registerSoundEvents("bfg10k_projectile_flash");
    public static final DeferredHolder<SoundEvent, SoundEvent> BFG10K_HUM = registerSoundEvents("bfg10k_hum");

    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("quad_damage_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("quad_damage_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("quad_damage_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_PICKUP = registerSoundEvents("invuln_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_USE = registerSoundEvents("invuln_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> INVULN_EXPIRE = registerSoundEvents("invuln_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENVIROSUIT_PICKUP = registerSoundEvents("envirosuit_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENVIROSUIT_EXPIRE = registerSoundEvents("envirosuit_expire");

    public static final DeferredHolder<SoundEvent, SoundEvent> REBREATHER_USE = registerSoundEvents("rebreather_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ADRENALINE_USE = registerSoundEvents("adrenaline_use");

    public static final DeferredHolder<SoundEvent, SoundEvent> POWERSHIELD_ENABLE = registerSoundEvents("powershield_enable");
    public static final DeferredHolder<SoundEvent, SoundEvent> POWERSHIELD_DISABLE = registerSoundEvents("powershield_disable");

    public static final DeferredHolder<SoundEvent, SoundEvent> AMMOEMPTY = registerSoundEvents("ammoempty");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
