package mett.palemannie.q2w.entity;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Quake2Weapons.MODID);

    /// Weapon projectiles

    public static final RegistryObject<EntityType<MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("muzzleflash", () -> EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                    .sized(0.01f, 0.01f).fireImmune().build("muzzleflash"));

    public static final RegistryObject<EntityType<LaserProjectileEntity>> LASER_PROJECTILE =
            ENTITY_TYPES.register("laser_projectile", () -> EntityType.Builder.<LaserProjectileEntity>of(LaserProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f).fireImmune().clientTrackingRange(256).updateInterval(1).build("laser_projectile"));

    public static final RegistryObject<EntityType<HandgrenadeProjectileEntity>> HANDGRENADE_PROJECTILE =
            ENTITY_TYPES.register("handgrenade_projectile", () -> EntityType.Builder.<HandgrenadeProjectileEntity>of(HandgrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("handgrenade_projectile"));

    public static final RegistryObject<EntityType<GrenadelauncherProjectileEntity>> GRENADELAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("grenadelauncher_projectile", () -> EntityType.Builder.<GrenadelauncherProjectileEntity>of(GrenadelauncherProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("grenadelauncher_projectile"));

    public static final RegistryObject<EntityType<RocketProjectileEntity>> ROCKETLAUNCHER_PROJECTILE =
            ENTITY_TYPES.register("rocketlauncher_projectile", () -> EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1).build("rocketlauncher_projectile"));

    public static final RegistryObject<EntityType<Bfg10kProjectileEntity>> BFG10K_PROJECTILE =
            ENTITY_TYPES.register("bfg10k_projectile", () -> EntityType.Builder.<Bfg10kProjectileEntity>of(Bfg10kProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().clientTrackingRange(256).updateInterval(1).build("bfg10k_projectile"));

    /// Powerups

    public static final RegistryObject<EntityType<QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("quad_damage_powerup", () -> EntityType.Builder.<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("quad_damage_powerup"));

    public static final RegistryObject<EntityType<InvulnerabilityPowerupEntity>> INVULN_POWERUP =
            ENTITY_TYPES.register("invuln_powerup", () -> EntityType.Builder.<InvulnerabilityPowerupEntity>of(InvulnerabilityPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("invuln_powerup"));

    public static final RegistryObject<EntityType<EnvirosuitPowerupEntity>> ENVIROSUIT_POWERUP =
            ENTITY_TYPES.register("envirosuit_powerup", () -> EntityType.Builder.<EnvirosuitPowerupEntity>of(EnvirosuitPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("envirosuit_powerup"));

    public static final RegistryObject<EntityType<RebreatherPowerupEntity>> REBREATHER_POWERUP =
            ENTITY_TYPES.register("rebreather_powerup", () -> EntityType.Builder.<RebreatherPowerupEntity>of(RebreatherPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("rebreather_powerup"));

    public static final RegistryObject<EntityType<SilencerPowerupEntity>> SILENCER_POWERUP =
            ENTITY_TYPES.register("silencer_powerup", () -> EntityType.Builder.<SilencerPowerupEntity>of(SilencerPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("silencer_powerup"));

    public static final RegistryObject<EntityType<AdrenalinePowerupEntity>> ADRENALINE_POWERUP =
            ENTITY_TYPES.register("adrenaline_powerup", () -> EntityType.Builder.<AdrenalinePowerupEntity>of(AdrenalinePowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("adrenaline_powerup"));

    /// Ammo pickups
    /// TODO: Einzelne Entityklassen daraus machen

    public static final RegistryObject<EntityType<FireworkRocketEntity>> BULLETS_AMMOPACK =
            ENTITY_TYPES.register("bullets_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("bullets_ammopack"));

    public static final RegistryObject<EntityType<FireworkRocketEntity>> SHELLS_AMMOPACK =
            ENTITY_TYPES.register("shells_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("shells_ammopack"));

    public static final RegistryObject<EntityType<FireworkRocketEntity>> GRENADES_AMMOPACK =
            ENTITY_TYPES.register("grenades_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("grenades_ammopack"));

    public static final RegistryObject<EntityType<FireworkRocketEntity>> ROCKETS_AMMOPACK =
            ENTITY_TYPES.register("rockets_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("rockets_ammopack"));

    public static final RegistryObject<EntityType<FireworkRocketEntity>> CELLS_AMMOPACK =
            ENTITY_TYPES.register("cells_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("cells_ammopack"));

    public static final RegistryObject<EntityType<FireworkRocketEntity>> SLUGS_AMMOPACK =
            ENTITY_TYPES.register("slugs_ammopack", () -> EntityType.Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).build("slugs_ammopack"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
