package mett.palemannie.q2w.util;

import java.util.Set;
import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.resources.Identifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.MissingMappingsEvent;

/** Migrates saved pre-prefix Q2W registry entries using Forge's persistent aliases. */
public final class LegacyIdMigrator {
    private LegacyIdMigrator() {}

    public static void onMissingMappings(MissingMappingsEvent event) {
        remap(event, ForgeRegistries.ITEMS, Set.of(
                "adrenaline_item",
                "bfg10k",
                "blaster",
                "bullet",
                "cell",
                "chaingun",
                "envirosuit_item",
                "grenade",
                "grenadelauncher",
                "hyperblaster",
                "invuln_item",
                "machinegun",
                "powershield_item",
                "quad_damage_item",
                "railgun",
                "rebreather_item",
                "rocket",
                "rocketlauncher",
                "shell",
                "shotgun",
                "silencer_item",
                "slug",
                "super_shotgun"));
        remap(event, ForgeRegistries.BLOCKS, Set.of(
                "light_water",
                "quake_light_air"));
        remap(event, ForgeRegistries.ENTITY_TYPES, Set.of(
                "adrenaline_pickup",
                "bfg10k_projectile",
                "bullets_ammopickup",
                "cells_ammopickup",
                "envirosuit_powerup",
                "grenadelauncher_projectile",
                "grenades_ammopickup",
                "handgrenade_projectile",
                "invuln_powerup",
                "laser_projectile",
                "megahealth_pickup",
                "muzzleflash",
                "powershield_pickup",
                "quad_damage_powerup",
                "rebreather_pickup",
                "rocketlauncher_projectile",
                "rockets_ammopickup",
                "shells_ammopickup",
                "silencer_powerup",
                "slugs_ammopickup"));
        remap(event, ForgeRegistries.MOB_EFFECTS, Set.of(
                "adrenaline_health_boost_effect",
                "envirosuit_effect",
                "invuln_effect",
                "quad_damage_effect"));
        remap(event, ForgeRegistries.PARTICLE_TYPES, Set.of(
                "bfg_explosion_particle",
                "bfg_flash_particle",
                "bfg_laser_particle"));
        remap(event, ForgeRegistries.SOUND_EVENTS, Set.of(
                "adrenaline_use",
                "ammo_pickup",
                "ammoempty",
                "bfg10k_hum",
                "bfg10k_projectile_flash",
                "bfg10k_projectile_loop",
                "bfg10k_shoot",
                "bfg10k_windup",
                "blaster_hit",
                "blaster_shoot",
                "bullet_hit",
                "chaingun_loop",
                "chaingun_shoot",
                "chaingun_spindown",
                "chaingun_spinup",
                "envirosuit_expire",
                "envirosuit_pickup",
                "explosion",
                "grenadelauncher_bounce",
                "grenadelauncher_shoot",
                "handgrenade_bounce",
                "handgrenade_cook",
                "handgrenade_start",
                "handgrenade_toss",
                "hyperblaster_loop",
                "hyperblaster_shoot",
                "hyperblaster_spindown",
                "hyperblaster_spinup",
                "invuln_expire",
                "invuln_pickup",
                "invuln_use",
                "item_pickup",
                "powershield_disable",
                "powershield_enable",
                "quad_damage_expire",
                "quad_damage_pickup",
                "quad_damage_use",
                "railgun_hum",
                "railgun_shoot",
                "rebreather_use",
                "rocket_loop",
                "rocketlauncher_shoot",
                "shotgun_shoot",
                "super_shotgun_shoot",
                "weapon_switch"));
    }

    private static <T> void remap(MissingMappingsEvent event, IForgeRegistry<T> registry,
                                  Set<String> legacyPaths) {
        for (var mapping : event.getMappings(registry.getRegistryKey(), Quake2Weapons.MODID)) {
            String oldPath = mapping.getKey().getPath();
            if (!legacyPaths.contains(oldPath)) continue;
            Identifier target = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_" + oldPath);
            if (registry.containsKey(target)) {
                mapping.remap(registry.getValue(target));
                Quake2Weapons.LOGGER.info("Migrated {} from {} to {}",
                        registry.getRegistryName(), mapping.getKey(), target);
            }
        }
    }
}
