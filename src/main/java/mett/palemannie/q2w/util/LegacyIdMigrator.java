package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.block.ModBlocks;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Resolves pre-prefix saved registry names to the current entries during world loading. */
public final class LegacyIdMigrator {
    private LegacyIdMigrator() {}

    public static void registerAliases() {
        // Keep this historical list explicit: future entries need no legacy alias.
        aliases(ModItems.ITEMS,
                "bullet",
                "cell",
                "shell",
                "rocket",
                "grenade",
                "slug",
                "blaster",
                "shotgun",
                "super_shotgun",
                "machinegun",
                "chaingun",
                "grenadelauncher",
                "rocketlauncher",
                "hyperblaster",
                "railgun",
                "bfg10k",
                "quad_damage_item",
                "invuln_item",
                "envirosuit_item",
                "rebreather_item",
                "silencer_item",
                "adrenaline_item",
                "powershield_item");
        aliases(ModEntities.ENTITY_TYPES,
                "muzzleflash",
                "laser_projectile",
                "handgrenade_projectile",
                "grenadelauncher_projectile",
                "rocketlauncher_projectile",
                "bfg10k_projectile",
                "quad_damage_powerup",
                "invuln_powerup",
                "envirosuit_powerup",
                "bullets_ammopickup",
                "shells_ammopickup",
                "grenades_ammopickup",
                "rockets_ammopickup",
                "cells_ammopickup",
                "slugs_ammopickup",
                "adrenaline_pickup",
                "megahealth_pickup",
                "silencer_powerup",
                "rebreather_pickup",
                "powershield_pickup");
        aliases(ModBlocks.BLOCKS,
                "light_water",
                "quake_light_air");
        aliases(ModEffects.MOB_EFFECTS,
                "quad_damage_effect",
                "invuln_effect",
                "envirosuit_effect",
                "adrenaline_health_boost_effect");
        aliases(ModParticles.PARTICLES,
                "bfg_laser_particle",
                "bfg_explosion_particle",
                "bfg_flash_particle");
        aliases(ModSounds.SOUND_EVENTS,
                "weapon_switch",
                "ammo_pickup",
                "item_pickup",
                "blaster_shoot",
                "shotgun_shoot",
                "super_shotgun_shoot",
                "bullet_hit",
                "blaster_hit",
                "chaingun_shoot",
                "chaingun_spinup",
                "chaingun_loop",
                "chaingun_spindown",
                "explosion",
                "handgrenade_start",
                "handgrenade_cook",
                "handgrenade_toss",
                "handgrenade_bounce",
                "grenadelauncher_bounce",
                "grenadelauncher_shoot",
                "rocketlauncher_shoot",
                "rocket_loop",
                "hyperblaster_shoot",
                "hyperblaster_loop",
                "hyperblaster_spinup",
                "hyperblaster_spindown",
                "railgun_shoot",
                "railgun_hum",
                "bfg10k_windup",
                "bfg10k_shoot",
                "bfg10k_projectile_loop",
                "bfg10k_projectile_flash",
                "bfg10k_hum",
                "quad_damage_pickup",
                "quad_damage_use",
                "quad_damage_expire",
                "invuln_pickup",
                "invuln_use",
                "invuln_expire",
                "envirosuit_pickup",
                "envirosuit_expire",
                "rebreather_use",
                "adrenaline_use",
                "powershield_enable",
                "powershield_disable",
                "ammoempty");
    }

    private static void aliases(DeferredRegister<?> registry, String... oldPaths) {
        for (String oldPath : oldPaths) {
            Identifier oldId = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, oldPath);
            Identifier newId = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_" + oldPath);
            if (registry.getEntries().stream().noneMatch(entry -> entry.getId().equals(newId))) {
                throw new IllegalStateException("Missing Q2W migration target: " + newId);
            }
            registry.addAlias(oldId, newId);
        }
    }
}
