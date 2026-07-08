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

    public static final ResourceKey<DamageType> BLASTER_DAMAGE = register("blaster_damage");
    public static final ResourceKey<DamageType> SHOTGUN_DAMAGE = register("shotgun_damage");
    public static final ResourceKey<DamageType> SUPER_SHOTGUN_DAMAGE = register("super_shotgun_damage");
    public static final ResourceKey<DamageType> MACHINEGUN_DAMAGE = register("machinegun_damage");
    public static final ResourceKey<DamageType> CHAINGUN_DAMAGE = register("chaingun_damage");
    public static final ResourceKey<DamageType> GRENADE_DAMAGE = register("grenade_damage");
    public static final ResourceKey<DamageType> GRENADELAUNCHER_DAMAGE = register("grenadelauncher_damage");
    public static final ResourceKey<DamageType> ROCKETLAUNCHER_DAMAGE = register("rocketlauncher_damage");
    public static final ResourceKey<DamageType> HYPERBLASTER_DAMAGE = register("hyperblaster_damage");
    public static final ResourceKey<DamageType> RAILGUN_DAMAGE = register("railgun_damage");
    public static final ResourceKey<DamageType> BFG10K_DAMAGE = register("bfg10k_damage");
    public static final ResourceKey<DamageType> BFG10K_LASER_DAMAGE = register("bfg10k_laser");
    public static final ResourceKey<DamageType> BFG10K_SPLASH_DAMAGE = register("bfg10k_splash");
}
