package mett.palemannie.q2w.entity;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Quake2Weapons.MODID);

    /// Weapon projectiles

    public static final DeferredHolder<EntityType<?>, EntityType<MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("q2w_muzzleflash", () -> EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                    .sized(0.01f, 0.01f).fireImmune().build("q2w_muzzleflash"));

    public static final DeferredHolder<EntityType<?>, EntityType<LaserProjectileEntity>> LASER_PROJECTILE =
            ENTITY_TYPES.register("q2w_laser_projectile", () -> EntityType.Builder.<LaserProjectileEntity>of(LaserProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_laser_projectile"));

    public static final DeferredHolder<EntityType<?>, EntityType<HandgrenadeProjectileEntity>> HANDGRENADE_PROJECTILE =
            ENTITY_TYPES.register("q2w_handgrenade_projectile", () -> EntityType.Builder.<HandgrenadeProjectileEntity>of(HandgrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_handgrenade_projectile"));

    public static final DeferredHolder<EntityType<?>, EntityType<GrenadelauncherProjectileEntity>> GRENADELAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("q2w_grenadelauncher_projectile", () -> EntityType.Builder.<GrenadelauncherProjectileEntity>of(GrenadelauncherProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_grenadelauncher_projectile"));

    public static final DeferredHolder<EntityType<?>, EntityType<RocketProjectileEntity>> ROCKETLAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("q2w_rocketlauncher_projectile", () -> EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_rocketlauncher_projectile"));

    public static final DeferredHolder<EntityType<?>, EntityType<Bfg10kProjectileEntity>> BFG10K_PROJECTILE =
            ENTITY_TYPES.register("q2w_bfg10k_projectile", () -> EntityType.Builder.<Bfg10kProjectileEntity>of(Bfg10kProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_bfg10k_projectile"));

    /// Powerups

    public static final DeferredHolder<EntityType<?>, EntityType<QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("q2w_quad_damage_powerup", () -> EntityType.Builder.<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_quad_damage_powerup"));

    public static final DeferredHolder<EntityType<?>, EntityType<InvulnerabilityPowerupEntity>> INVULN_POWERUP =
            ENTITY_TYPES.register("q2w_invuln_powerup", () -> EntityType.Builder.<InvulnerabilityPowerupEntity>of(InvulnerabilityPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_invuln_powerup"));

    public static final DeferredHolder<EntityType<?>, EntityType<EnvirosuitPowerupEntity>> ENVIROSUIT_POWERUP =
            ENTITY_TYPES.register("q2w_envirosuit_powerup", () -> EntityType.Builder.<EnvirosuitPowerupEntity>of(EnvirosuitPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_envirosuit_powerup"));

    /// Ammo pickups

    public static final DeferredHolder<EntityType<?>, EntityType<BulletsAmmopickupEntity>> BULLETS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_bullets_ammopickup", () -> EntityType.Builder.<BulletsAmmopickupEntity>of(BulletsAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_bullets_ammopickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<ShellsAmmopickupEntity>> SHELLS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_shells_ammopickup", () -> EntityType.Builder.<ShellsAmmopickupEntity>of(ShellsAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_shells_ammopickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<GrenadesAmmopickupEntity>> GRENADES_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_grenades_ammopickup", () -> EntityType.Builder.<GrenadesAmmopickupEntity>of(GrenadesAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_grenades_ammopickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<RocketsAmmopickupEntity>> ROCKETS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_rockets_ammopickup", () -> EntityType.Builder.<RocketsAmmopickupEntity>of(RocketsAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_rockets_ammopickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<CellsAmmopickupEntity>> CELLS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_cells_ammopickup", () -> EntityType.Builder.<CellsAmmopickupEntity>of(CellsAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_cells_ammopickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<SlugsAmmopickupEntity>> SLUGS_AMMOPICKUP =
            ENTITY_TYPES.register("q2w_slugs_ammopickup", () -> EntityType.Builder.<SlugsAmmopickupEntity>of(SlugsAmmopickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_slugs_ammopickup"));

    /// Item pickups

    public static final DeferredHolder<EntityType<?>, EntityType<AdrenalinePickupEntity>> ADRENALINE_PICKUP =
            ENTITY_TYPES.register("q2w_adrenaline_pickup", () -> EntityType.Builder.<AdrenalinePickupEntity>of(AdrenalinePickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_adrenaline_pickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<MegahealthPickupEntity>> MEGAHEALTH_PICKUP =
            ENTITY_TYPES.register("q2w_megahealth_pickup", () -> EntityType.Builder.<MegahealthPickupEntity>of(MegahealthPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_megahealth_pickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<SilencerPowerupEntity>> SILENCER_POWERUP =
            ENTITY_TYPES.register("q2w_silencer_powerup", () -> EntityType.Builder.<SilencerPowerupEntity>of(SilencerPowerupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_silencer_powerup"));

    public static final DeferredHolder<EntityType<?>, EntityType<RebreatherPickupEntity>> REBREATHER_PICKUP =
            ENTITY_TYPES.register("q2w_rebreather_pickup", () -> EntityType.Builder.<RebreatherPickupEntity>of(RebreatherPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_rebreather_pickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<PowershieldPickupEntity>> POWERSHIELD_PICKUP =
            ENTITY_TYPES.register("q2w_powershield_pickup", () -> EntityType.Builder.<PowershieldPickupEntity>of(PowershieldPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("q2w_powershield_pickup"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
