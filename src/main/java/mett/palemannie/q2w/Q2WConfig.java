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
            builder.push("Effects");

            enableMuzzleFlash = builder.comment("\n[EXPERIMENTAL: EPILEPSY WARNING] Enables/Disables muzzle flash when shooting")
                    .define("enableMuzzleFlash", false);

            enableProjectileTrailLight = builder.comment("\n[EXPERIMENTAL: EPILEPSY WARNING] Enables/Disables projectile trail lighting (Blaster(s), Rocket, BFG10k)")
                    .define("enableProjectileTrailLight", false);

            enableGore = builder.comment("\nEnables/Disables Gore particles when hitting a mob with Quake weapons")
                    .define("enableGore", false);

            builder.pop();
            builder.push("Weapon damage");

            blasterDamage = builder
                    .comment("\nHow much damage the Blaster deals")
                    .defineInRange("blasterDamage", 3.0, 0.0, Float.MAX_VALUE);

            shotgunDamage = builder
                    .comment("\nHow much damage the Shotgun deals per pellet (12 pellets per shot)")
                    .defineInRange("shotgunDamage", 0.8, 0.0, Float.MAX_VALUE);

            superShotgunDamage = builder
                    .comment("\nHow much damage the Double Barreled Shotgun deals per pellet (20 pellets per shot)")
                    .defineInRange("superShotgunDamage", 1.2, 0.0, Float.MAX_VALUE);

            machinegunDamage = builder
                    .comment("\nHow much damage the Machine Gun deals per shot")
                    .defineInRange("machinegunDamage", 1.6, 0.0, Float.MAX_VALUE);

            chaingunDamage = builder
                    .comment("\nHow much damage the Chaingun deals per shot")
                    .defineInRange("chaingunDamage", 1.2, 0.0, Float.MAX_VALUE);

            handgrenadeDamage = builder
                    .comment("\nMaximum damage the Grenade deals")
                    .defineInRange("handgrenadeDamage", 28.0, 0.0, Float.MAX_VALUE);

            handgrenadeRadius = builder
                    .comment("\nBlast radius of the Handgrenade")
                    .defineInRange("handgrenadeRadius", 5.0, 0.0, Float.MAX_VALUE);

            grenadelauncherDamage = builder
                    .comment("\nMaximum the Grenade Launcher deals per shot")
                    .defineInRange("grenadelauncherDamage", 28.0, 0.0, Float.MAX_VALUE);

            grenadelauncherRadius = builder
                    .comment("\nBlast radius of the Grenade launcher")
                    .defineInRange("grenadelauncherRadius", 5.0, 0.0, Float.MAX_VALUE);

            rocketlauncherDamage = builder
                    .comment("\nMaximum damage the Rocket Launcher deals per shot")
                    .defineInRange("rocketlauncherDamage", 28.0, 0.0, Float.MAX_VALUE);

            rocketlauncherRadius = builder
                    .comment("\nBlast radius of the Rocket launcher")
                    .defineInRange("rocketlauncherRadius", 4.0, 0.0, Float.MAX_VALUE);

            hyperblasterDamage = builder
                    .comment("\nHow much damage the Hyperblaster deals per shot.")
                    .defineInRange("hyperblasterDamage", 4.0, 0.0, Float.MAX_VALUE);

            railgunDamage = builder
                    .comment("\nHow much damage the Railgun deals per shot.")
                    .defineInRange("railgunDamage", 30.0, 0.0, Float.MAX_VALUE);

            bfg10kDamage = builder
                    .comment("\nHow much damage the BFG10K deals upon impact.")
                    .defineInRange("bfg10kDamage", 40.0, 0.0, Float.MAX_VALUE);

            bfg10kLaserDamage = builder
                    .comment("\nHow much damage the BFG10K lasers deal per tick")
                    .defineInRange("bfg10kLaserDamage", 1.0, 0.0, Float.MAX_VALUE);

            bfg10kFlashDamage = builder
                    .comment("\nHow much damage the BFG10K deals in its flash.")
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
                    .comment("\nEnables/Disables the spawning of powerups in your world")
                    .define("enablePowerups", true);

            powerupDebug = builder
                    .comment("\nPowerup spawn attempts are visible in chat")
                    .define("powerupDebug", false);

            powerupEffectDuration = builder
                    .comment("\nDuration of powerups effects upon picking up in ticks")
                    .defineInRange("powerupEffectDuration", 600, 1, Integer.MAX_VALUE-1);

            powerupLifetime = builder
                    .comment("\nDuration of powerups in the world in ticks until despawning")
                    .defineInRange("powerupLifetime", 6000, 1, Integer.MAX_VALUE-1);

            powerupSpawnInterval = builder
                    .comment("\nSpawns a powerup every x ticks in the world randomly")
                    .defineInRange("powerupSpawnInterval", 600, 20, Integer.MAX_VALUE-1);

            powerupSpawnAttempts = builder
                    .comment("\nAttemts of spawning a powerup at each interval")
                    .defineInRange("powerupSpawnAttempts", 5, 1, 512);

            powerupSpawnSearchRadius = builder
                    .comment("\nSearch radius for a suitable spawning place at each spawning attempt")
                    .defineInRange("powerupSpawnSearchRadius", 5, 1, 512);

            builder.pop();

            builder.push("Weapon aggro values");

            weaponAggroRange = builder
                    .comment("\nRange in blocks where monsters aggro when weapons are fired or loud weapons are held")
                    .defineInRange("weaponAggroRange", 32, 0, 256);

            builder.pop();

            builder.push("Power Shield values");

            powershieldAbsorbRatio = builder
                    .comment("\nHow much damage will be absorbed by the Power Shield")
                    .defineInRange("powershieldAbsorbRatio", 0.66d, 0d, 1d);

            powershieldDamagePreCellConsumed = builder
                    .comment("\nHow much damage each cell will absorb")
                    .defineInRange("powershieldDamagePreCellConsumed", 2d, 0d, Double.MAX_VALUE-1d);


            builder.pop();

        }
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
