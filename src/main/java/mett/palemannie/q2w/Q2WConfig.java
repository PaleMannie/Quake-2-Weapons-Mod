package mett.palemannie.q2w;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Q2WConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableMuzzleFlash;
        public final ForgeConfigSpec.BooleanValue enableProjectileTrailLight;
        public final ForgeConfigSpec.BooleanValue enableGore;
        public final ForgeConfigSpec.DoubleValue blasterDamage;
        public final ForgeConfigSpec.DoubleValue shotgunDamage;
        public final ForgeConfigSpec.DoubleValue superShotgunDamage;
        public final ForgeConfigSpec.DoubleValue machinegunDamage;
        public final ForgeConfigSpec.DoubleValue chaingunDamage;
        public final ForgeConfigSpec.DoubleValue handgrenadeDamage;
        public final ForgeConfigSpec.DoubleValue handgrenadeRadius;
        public final ForgeConfigSpec.DoubleValue grenadelauncherDamage;
        public final ForgeConfigSpec.DoubleValue grenadelauncherRadius;
        public final ForgeConfigSpec.DoubleValue rocketlauncherDamage;
        public final ForgeConfigSpec.DoubleValue rocketlauncherRadius;
        public final ForgeConfigSpec.DoubleValue hyperblasterDamage;
        public final ForgeConfigSpec.DoubleValue railgunDamage;
        public final ForgeConfigSpec.DoubleValue bfg10kDamage;
        public final ForgeConfigSpec.DoubleValue bfg10kLaserDamage;
        public final ForgeConfigSpec.DoubleValue bfg10kFlashDamage;


        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Visual Effects");

            enableMuzzleFlash = builder.comment("\nEnables muzzle flashes when firing weapons.\nEXPERIMENTAL: Contains flashing lights that may trigger photosensitive seizures.")
                    .define("enableMuzzleFlash", false);

            enableProjectileTrailLight = builder.comment("\nEnables dynamic lighting for Blaster, Hyperblaster, Rocket and BFG10K projectiles.\nEXPERIMENTAL: Contains flashing lights that may trigger photosensitive seizures.")
                    .define("enableProjectileTrailLight", false);

            enableGore = builder.comment("\nEnables gore particles when living entities are hit by Quake weapons.")
                    .define("enableGore", false);

            builder.pop();
            builder.push("Weapon damage");

            blasterDamage = builder
                    .comment("\nBase damage per Blaster projectile hit.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("blasterDamage", 3.0, 0.0, Float.MAX_VALUE);

            shotgunDamage = builder
                    .comment("\nBase damage per Shotgun pellet. Each shot fires 12 pellets.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("shotgunDamage", 0.8, 0.0, Float.MAX_VALUE);

            superShotgunDamage = builder
                    .comment("\nBase damage per Super Shotgun pellet. Each shot fires 20 pellets.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("superShotgunDamage", 1.2, 0.0, Float.MAX_VALUE);

            machinegunDamage = builder
                    .comment("\nBase damage per Machine Gun bullet.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("machinegunDamage", 1.6, 0.0, Float.MAX_VALUE);

            chaingunDamage = builder
                    .comment("\nBase damage per Chaingun bullet.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("chaingunDamage", 1.2, 0.0, Float.MAX_VALUE);

            handgrenadeDamage = builder
                    .comment("\nMaximum base damage dealt by a Hand Grenade explosion.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("handgrenadeDamage", 28.0, 0.0, Float.MAX_VALUE);

            handgrenadeRadius = builder
                    .comment("\nBlast radius of Hand Grenade explosions, in blocks.")
                    .defineInRange("handgrenadeRadius", 5.0, 0.0, Float.MAX_VALUE);

            grenadelauncherDamage = builder
                    .comment("\nMaximum base damage dealt by a Grenade Launcher explosion.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("grenadelauncherDamage", 28.0, 0.0, Float.MAX_VALUE);

            grenadelauncherRadius = builder
                    .comment("\nBlast radius of Grenade Launcher explosions, in blocks.")
                    .defineInRange("grenadelauncherRadius", 5.0, 0.0, Float.MAX_VALUE);

            rocketlauncherDamage = builder
                    .comment("\nMaximum base damage dealt by a Rocket Launcher explosion.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("rocketlauncherDamage", 28.0, 0.0, Float.MAX_VALUE);

            rocketlauncherRadius = builder
                    .comment("\nBlast radius of Rocket Launcher explosions, in blocks.")
                    .defineInRange("rocketlauncherRadius", 4.0, 0.0, Float.MAX_VALUE);

            hyperblasterDamage = builder
                    .comment("\nBase damage per Hyperblaster projectile hit.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("hyperblasterDamage", 4.0, 0.0, Float.MAX_VALUE);

            railgunDamage = builder
                    .comment("\nBase damage dealt to each entity hit by a Railgun shot.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("railgunDamage", 30.0, 0.0, Float.MAX_VALUE);

            bfg10kDamage = builder
                    .comment("\nBase damage dealt to the entity directly hit by a BFG10K projectile and its surrounding blast radius.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("bfg10kDamage", 40.0, 0.0, Float.MAX_VALUE);

            bfg10kLaserDamage = builder
                    .comment("\nBase damage per BFG10K laser hit while the projectile is in flight.\nLaser attacks are processed every 2 game ticks.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("bfg10kLaserDamage", 1.0, 0.0, Float.MAX_VALUE);

            bfg10kFlashDamage = builder
                    .comment("\nMaximum base damage of the BFG10K flash after impact.\nDamage decreases with distance from the explosion.\nDamage is measured in health points: 2 points = 1 heart.")
                    .defineInRange("bfg10kFlashDamage", 40, 0.0, Float.MAX_VALUE);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SERVER = new Server(builder);
        SERVER_SPEC = builder.build();
    }

    public static class Server {

        public final ForgeConfigSpec.IntValue powerupSpawnInterval;
        public final ForgeConfigSpec.IntValue powerupSpawnAttempts;
        public final ForgeConfigSpec.IntValue maxNearbyPowerups;
        public final ForgeConfigSpec.IntValue powerupSpawnSearchRadius;
        public final ForgeConfigSpec.IntValue powerupEffectDuration;
        public final ForgeConfigSpec.IntValue powerupLifetime;
        public final ForgeConfigSpec.BooleanValue powerupDebug;
        public final ForgeConfigSpec.BooleanValue enablePowerups;
        public final ForgeConfigSpec.IntValue weaponAggroRange;
        public final ForgeConfigSpec.DoubleValue powershieldAbsorbRatio;
        public final ForgeConfigSpec.DoubleValue powershieldDamagePreCellConsumed;

        public Server(ForgeConfigSpec.Builder builder) {

            builder.push("Powerup Spawner values");

            enablePowerups = builder
                    .comment("\nEnables automatic spawning of powerups, pickups, ammo and health near players.")
                    .define("enablePowerups", true);

            powerupDebug = builder
                    .comment("\nReports successful spawns and failed spawn attempts in chat and the server console.\nChat messages are sent to all players in the affected dimension.")
                    .define("powerupDebug", false);

            powerupEffectDuration = builder
                    .comment("\nDuration of powerup effects, in game ticks.\nAt normal game speed, 20 ticks = 1 second; 600 ticks = 30 seconds.")
                    .defineInRange("powerupEffectDuration", 600, 1, Integer.MAX_VALUE-1);

            powerupLifetime = builder
                    .comment("\nTime before an uncollected powerup or pickup despawns, in game ticks.\nAt normal game speed, 20 ticks = 1 second; 6000 ticks = 5 minutes.")
                    .defineInRange("powerupLifetime", 6000, 1, Integer.MAX_VALUE-1);

            powerupSpawnInterval = builder
                    .comment("\nInterval between automatic spawn attempt rounds, in game ticks.\nEach round performs the configured number of attempts for every player.\nAn attempt only spawns a pickup if a suitable location is found.\nAt normal game speed, 600 ticks = 30 seconds.")
                    .defineInRange("powerupSpawnInterval", 600, 20, Integer.MAX_VALUE-1);

            powerupSpawnAttempts = builder
                    .comment("\nNumber of spawn attempts per player during each spawn round.\nEach attempt can spawn one pickup. Failed attempts do not spawn anything.\nHigher values increase spawn opportunities and server workload.")
                    .defineInRange("powerupSpawnAttempts", 5, 1, 512);

            powerupSpawnSearchRadius = builder
                    .comment("\nSearch distance in blocks along each axis around a randomly chosen spawn candidate.\nThis controls the local search area, not the distance from the player.\nHigher values search more blocks and can significantly increase server workload.")
                    .defineInRange("powerupSpawnSearchRadius", 5, 1, 512);

            maxNearbyPowerups = builder
                    .comment("Maximum automatically spawned powerups and pickups within 128 blocks of a player. Includes ammo and health.\nManually placed pickups do not count. Existing pickups are not removed when lowering this limit.")
                    .defineInRange("maxNearbyPowerups", 4, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.push("Weapon aggro values");

            weaponAggroRange = builder
                    .comment("\nDistance in blocks within which monsters target a player firing a Quake weapon or holding a loud weapon.\nAn active Silencer prevents this weapon-triggered aggression.\nSet to 0 to disable weapon-triggered aggression.")
                    .defineInRange("weaponAggroRange", 24, 0, 256);

            builder.pop();

            builder.push("Power Shield values");

            powershieldAbsorbRatio = builder
                    .comment("\nFraction of incoming damage absorbed by an active Power Shield, limited by available Cells.\n0.0 = 0%, 0.66 = 66%, 1.0 = 100%.")
                    .defineInRange("powershieldAbsorbRatio", 0.66d, 0d, 1d);

            powershieldDamagePreCellConsumed = builder
                    .comment("\nDamage points absorbed per Cell consumed by the Power Shield.\nHigher values make each Cell last longer. 2 damage points = 1 heart.\nCell consumption is rounded up to whole Cells for each hit.")
                    .defineInRange("powershieldDamagePreCellConsumed", 2d, 0d, Double.MAX_VALUE-1d);

            builder.pop();
        }
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
