package mett.palemannie.q2w.entity;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Quake2Weapons.MODID);

    /// Weapon projectiles

    public static final RegistryObject<EntityType<@NotNull MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("q2w_muzzleflash", () -> build(EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                            .sized(0.01f, 0.01f)
                            .fireImmune()
                    , "q2w_muzzleflash"));

    public static final RegistryObject<EntityType<@NotNull LaserProjectileEntity>> LASER_PROJECTILE =
            ENTITY_TYPES.register("q2w_laser_projectile", () -> build(EntityType.Builder.<LaserProjectileEntity>of(LaserProjectileEntity::new, MobCategory.MISC)
                            .sized(0.15f, 0.15f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_laser_projectile"));

    public static final RegistryObject<EntityType<@NotNull HandgrenadeProjectileEntity>> HANDGRENADE_PROJECTILE =
            ENTITY_TYPES.register("q2w_handgrenade_projectile", () -> build(EntityType.Builder.<HandgrenadeProjectileEntity>of(HandgrenadeProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_handgrenade_projectile"));

    public static final RegistryObject<EntityType<@NotNull GrenadelauncherProjectileEntity>> GRENADELAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("q2w_grenadelauncher_projectile", () -> build(EntityType.Builder.<GrenadelauncherProjectileEntity>of(GrenadelauncherProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_grenadelauncher_projectile"));

    public static final RegistryObject<EntityType<@NotNull RocketProjectileEntity>> ROCKETLAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("q2w_rocketlauncher_projectile", () -> build(EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_rocketlauncher_projectile"));

    public static final RegistryObject<EntityType<@NotNull Bfg10kProjectileEntity>> BFG10K_PROJECTILE =
            ENTITY_TYPES.register("q2w_bfg10k_projectile", () -> build(EntityType.Builder.<Bfg10kProjectileEntity>of(Bfg10kProjectileEntity::new, MobCategory.MISC)
                            .sized(0.1f, 0.1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_bfg10k_projectile"));

    /// Powerups

    public static final RegistryObject<EntityType<@NotNull QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("q2w_quad_damage_powerup", () -> build(EntityType.Builder.<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_quad_damage_powerup"));

    public static final RegistryObject<EntityType<@NotNull InvulnerabilityPowerupEntity>> INVULN_POWERUP =
            ENTITY_TYPES.register("q2w_invuln_powerup", () -> build(EntityType.Builder.<InvulnerabilityPowerupEntity>of(InvulnerabilityPowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_invuln_powerup"));

    public static final RegistryObject<EntityType<@NotNull EnvirosuitPowerupEntity>> ENVIROSUIT_POWERUP =
            ENTITY_TYPES.register("q2w_envirosuit_powerup", () -> build(EntityType.Builder.<EnvirosuitPowerupEntity>of(EnvirosuitPowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_envirosuit_powerup"));

    /// Ammo pickups

    public static final RegistryObject<EntityType<@NotNull BulletsAmmopickupEntity>> BULLETS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_bullets_ammopickup", () -> build(EntityType.Builder.<BulletsAmmopickupEntity>of(BulletsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_bullets_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull ShellsAmmopickupEntity>> SHELLS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_shells_ammopickup", () -> build(EntityType.Builder.<ShellsAmmopickupEntity>of(ShellsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_shells_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull GrenadesAmmopickupEntity>> GRENADES_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_grenades_ammopickup", () -> build(EntityType.Builder.<GrenadesAmmopickupEntity>of(GrenadesAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_grenades_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull RocketsAmmopickupEntity>> ROCKETS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_rockets_ammopickup", () -> build(EntityType.Builder.<RocketsAmmopickupEntity>of(RocketsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_rockets_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull CellsAmmopickupEntity>> CELLS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_cells_ammopickup", () -> build(EntityType.Builder.<CellsAmmopickupEntity>of(CellsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_cells_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull SlugsAmmopickupEntity>> SLUGS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_slugs_ammopickup", () -> build(EntityType.Builder.<SlugsAmmopickupEntity>of(SlugsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_slugs_ammopickup"));

    /// Item pickups

    public static final RegistryObject<EntityType<@NotNull AdrenalinePickupEntity>> ADRENALINE_PICKUP =
            ENTITY_TYPES.register("q2w_adrenaline_pickup", () -> build(EntityType.Builder.<AdrenalinePickupEntity>of(AdrenalinePickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_adrenaline_pickup"));

    public static final RegistryObject<EntityType<@NotNull MegahealthPickupEntity>> MEGAHEALTH_PICKUP =
            ENTITY_TYPES.register("q2w_megahealth_pickup", () -> build(EntityType.Builder.<MegahealthPickupEntity>of(MegahealthPickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_megahealth_pickup"));

    public static final RegistryObject<EntityType<@NotNull SilencerPowerupEntity>> SILENCER_POWERUP =
            ENTITY_TYPES.register("q2w_silencer_powerup", () -> build(EntityType.Builder.<SilencerPowerupEntity>of(SilencerPowerupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_silencer_powerup"));

    public static final RegistryObject<EntityType<@NotNull RebreatherPickupEntity>> REBREATHER_PICKUP =
            ENTITY_TYPES.register("q2w_rebreather_pickup", () -> build(EntityType.Builder.<RebreatherPickupEntity>of(RebreatherPickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_rebreather_pickup"));

    public static final RegistryObject<EntityType<@NotNull PowershieldPickupEntity>> POWERSHIELD_PICKUP =
            ENTITY_TYPES.register("q2w_powershield_pickup", () -> build(EntityType.Builder.<PowershieldPickupEntity>of(PowershieldPickupEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "q2w_powershield_pickup"));

    private static <T extends Entity> EntityType<T> build(EntityType.Builder<T> builder, String type) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, type));
        return builder.build(key);
    }

    public static void register(BusGroup eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
