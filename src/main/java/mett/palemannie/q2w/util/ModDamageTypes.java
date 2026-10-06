package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, name));
    }

    public static final ResourceKey<DamageType> BLASTER_DAMAGE = register("q2w_blaster_damage");
    public static final ResourceKey<DamageType> SHOTGUN_DAMAGE = register("q2w_shotgun_damage");
    public static final ResourceKey<DamageType> SUPER_SHOTGUN_DAMAGE = register("q2w_super_shotgun_damage");
    public static final ResourceKey<DamageType> MACHINEGUN_DAMAGE = register("q2w_machinegun_damage");
    public static final ResourceKey<DamageType> CHAINGUN_DAMAGE = register("q2w_chaingun_damage");
    public static final ResourceKey<DamageType> HANDGRENADE_DAMAGE = register("q2w_handgrenade_damage");
    public static final ResourceKey<DamageType> HANDGRENADE_OVERCOOK_DAMAGE = register("q2w_handgrenade_overcook_damage");
    public static final ResourceKey<DamageType> GRENADELAUNCHER_DAMAGE = register("q2w_grenadelauncher_damage");
    public static final ResourceKey<DamageType> ROCKETLAUNCHER_DAMAGE = register("q2w_rocketlauncher_damage");
    public static final ResourceKey<DamageType> HYPERBLASTER_DAMAGE = register("q2w_hyperblaster_damage");
    public static final ResourceKey<DamageType> RAILGUN_DAMAGE = register("q2w_railgun_damage");
    public static final ResourceKey<DamageType> BFG10K_DAMAGE = register("q2w_bfg10k_damage");
    public static final ResourceKey<DamageType> BFG10K_LASER_DAMAGE = register("q2w_bfg10k_laser");
    public static final ResourceKey<DamageType> BFG10K_FLASH_DAMAGE = register("q2w_bfg10k_flash");
}
