package mett.palemannie.q2w;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

public class Q2WConfig {

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue enableMuzzleFlash;
        public final ModConfigSpec.BooleanValue enableProjectileTrailLight;
        public final ModConfigSpec.BooleanValue enableGore;
        public final ModConfigSpec.DoubleValue blasterDamage;
        public final ModConfigSpec.DoubleValue shotgunDamage;
        public final ModConfigSpec.DoubleValue superShotgunDamage;
        public final ModConfigSpec.DoubleValue machinegunDamage;
        public final ModConfigSpec.DoubleValue chaingunDamage;
        public final ModConfigSpec.DoubleValue handgrenadeDamage;
        public final ModConfigSpec.DoubleValue handgrenadeRadius;
        public final ModConfigSpec.DoubleValue grenadelauncherDamage;
        public final ModConfigSpec.DoubleValue grenadelauncherRadius;
        public final ModConfigSpec.DoubleValue rocketlauncherDamage;
        public final ModConfigSpec.DoubleValue rocketlauncherRadius;
        public final ModConfigSpec.DoubleValue hyperblasterDamage;
        public final ModConfigSpec.DoubleValue railgunDamage;
        public final ModConfigSpec.DoubleValue bfg10kDamage;
        public final ModConfigSpec.DoubleValue bfg10kLaserDamage;
        public final ModConfigSpec.DoubleValue bfg10kFlashDamage;
        public final ModConfigSpec.DoubleValue explosionSelfDamageMultiplier;

        public Common(ModConfigSpec.Builder builder) {
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

            explosionSelfDamageMultiplier = builder
                    .comment("\nSelf splash damage multiplier for rockets, grenades and the BFG blast."
                            + "\n0 = no self damage, 0.5 = half damage, 1 = full damage."
                            + "\nThis does not change blast impulse or damage dealt to other entities.")
                    .defineInRange("explosionSelfDamageMultiplier", 0.5D, 0.0D, 1.0D);

            builder.pop();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SERVER = new Server(builder);
        SERVER_SPEC = builder.build();
    }

    public static class Server {

        public final ModConfigSpec.IntValue powerupSpawnInterval;
        public final ModConfigSpec.IntValue powerupSpawnAttempts;
        public final ModConfigSpec.IntValue maxNearbyPowerups;
        public final ModConfigSpec.IntValue powerupSpawnSearchRadius;
        public final ModConfigSpec.IntValue powerupEffectDuration;
        public final ModConfigSpec.IntValue powerupLifetime;
        public final ModConfigSpec.BooleanValue powerupDebug;
        public final ModConfigSpec.BooleanValue enablePowerups;
        public final ModConfigSpec.IntValue weaponAggroRange;
        public final ModConfigSpec.DoubleValue powershieldAbsorbRatio;
        public final ModConfigSpec.DoubleValue powershieldDamagePreCellConsumed;

        public Server(ModConfigSpec.Builder builder) {

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

    public static void registerConfigs(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        container.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
