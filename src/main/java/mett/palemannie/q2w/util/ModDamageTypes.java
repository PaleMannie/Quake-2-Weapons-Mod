package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    /** Keeps real damage attribution while selecting the mod's self/attacker messages. */
    public static DamageSource weaponSource(
            Level level, ResourceKey<DamageType> type,
            Entity projectile, Entity owner) {
        var holder = level.damageSources().source(type, projectile, owner).typeHolder();
        return new DamageSource(holder, projectile, owner) {
            @Override
            public Component getLocalizedDeathMessage(LivingEntity victim) {
                String key = "death.attack." + type().msgId();
                if (owner == victim) {
                    return Component.translatable(key, victim.getDisplayName());
                }
                if (owner != null) {
                    return Component.translatable(key + ".player",
                            victim.getDisplayName(), owner.getDisplayName());
                }
                // An ownerless projectile is neither a suicide nor a hit by a previous attacker.
                return Component.translatable(
                        is(DamageTypeTags.IS_EXPLOSION) ? "death.attack.explosion" : "death.attack.generic",
                        victim.getDisplayName());
            }
        };
    }

    public static final ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, name));
    }

    public static final ResourceKey<DamageType> BLASTER_DAMAGE = register("blaster_damage");
    public static final ResourceKey<DamageType> SHOTGUN_DAMAGE = register("shotgun_damage");
    public static final ResourceKey<DamageType> SUPER_SHOTGUN_DAMAGE = register("super_shotgun_damage");
    public static final ResourceKey<DamageType> MACHINEGUN_DAMAGE = register("machinegun_damage");
    public static final ResourceKey<DamageType> CHAINGUN_DAMAGE = register("chaingun_damage");
    public static final ResourceKey<DamageType> HANDGRENADE_DAMAGE = register("handgrenade_damage");
    public static final ResourceKey<DamageType> HANDGRENADE_OVERCOOK_DAMAGE = register("handgrenade_overcook_damage");
    public static final ResourceKey<DamageType> GRENADELAUNCHER_DAMAGE = register("grenadelauncher_damage");
    public static final ResourceKey<DamageType> ROCKETLAUNCHER_DAMAGE = register("rocketlauncher_damage");
    public static final ResourceKey<DamageType> HYPERBLASTER_DAMAGE = register("hyperblaster_damage");
    public static final ResourceKey<DamageType> RAILGUN_DAMAGE = register("railgun_damage");
    public static final ResourceKey<DamageType> BFG10K_DAMAGE = register("bfg10k_damage");
    public static final ResourceKey<DamageType> BFG10K_LASER_DAMAGE = register("bfg10k_laser");
    public static final ResourceKey<DamageType> BFG10K_FLASH_DAMAGE = register("bfg10k_flash");
}
