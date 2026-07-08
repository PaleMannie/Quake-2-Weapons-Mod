package mett.palemannie.q2w;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        public final ForgeConfigSpec.BooleanValue enableRocketTrailLight;
        public final ForgeConfigSpec.BooleanValue enableThunderboltTracer;
        public final ForgeConfigSpec.BooleanValue enableGore;
        public final ForgeConfigSpec.BooleanValue enableEnhancedModels;
        public final ForgeConfigSpec.DoubleValue axeDamage;
        public final ForgeConfigSpec.DoubleValue shotgunDamage;
        public final ForgeConfigSpec.DoubleValue superShotgunDamage;
        public final ForgeConfigSpec.DoubleValue nailgunDamage;
        public final ForgeConfigSpec.DoubleValue superNailgunDamage;
        public final ForgeConfigSpec.DoubleValue thunderboltDamage;
        public final ForgeConfigSpec.DoubleValue rocketlauncherDamage;
        public final ForgeConfigSpec.DoubleValue grenadelauncherDamage;


        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Effects");

            enableEnhancedModels = builder
                    .comment("\n[UNFINISHED] Enable/disable enhanced models for weapons")
                    .define("enableEnhancedModels", false);

            enableMuzzleFlash = builder.comment("\n[EXPERIMENTAL: EPILEPSY WARNING] Enables/Disables muzzle flash when shooting")
                    .define("enableMuzzleFlash", false);

            enableRocketTrailLight = builder.comment("\n[EXPERIMENTAL: EPILEPSY WARNING] Enables/Disables rocket trail lighting")
                    .define("enableRocketTrailLight", false);

            enableThunderboltTracer = builder.comment("\nEnables/Disables Thunderbolt hitscan tracers")
                    .define("enableThunderboltTracer", false);

            enableGore = builder.comment("\nEnables/Disables Gore particles when hitting a mob with Quake weapons")
                    .define("enableThunderboltTracer", false);

            builder.pop();
            builder.push("Weapon damage");

            axeDamage = builder
                    .comment("\nHow much damage the Quake axe deals")
                    .defineInRange("axeDamage", 10.0, 0.0, Float.MAX_VALUE);

            shotgunDamage = builder
                    .comment("\nHow much damage the Shotgun deals per pellet (6 pellets per shot)")
                    .defineInRange("shotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superShotgunDamage = builder
                    .comment("\nHow much damage the Double Barreled Shotgun deals per pellet (14 pellets per shot)")
                    .defineInRange("superShotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            nailgunDamage = builder
                    .comment("\nHow much damage the Nailgun deals per shot")
                    .defineInRange("nailgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superNailgunDamage = builder
                    .comment("\nHow much damage the Super Nailgun deals per shot")
                    .defineInRange("superNailgunDamage", 4.0, 0.0, Float.MAX_VALUE);

            rocketlauncherDamage = builder
                    .comment("\nHow much damage the Rocket Launcher deals per shot. WARNING: Damage increases blast radius")
                    .defineInRange("rocketlauncherDamage", 28, 0.0, Float.MAX_VALUE);

            grenadelauncherDamage = builder
                    .comment("\nHow much damage the Grenade Launcher deals per shot. WARNING: Damage increases blast radius")
                    .defineInRange("grenadelauncherDamage", 28, 0.0, Float.MAX_VALUE);

            thunderboltDamage = builder
                    .comment("\nHow much damage the Thunderbolt deals per shot")
                    .defineInRange("thunderboltDamage", 6.0, 0.0, Float.MAX_VALUE);

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

        public final ForgeConfigSpec.DoubleValue animationDroppedFixerSearchRadius;
        public final ForgeConfigSpec.IntValue animationDroppedFixerSearchInterval;
        public final ForgeConfigSpec.IntValue powerupSpawnInterval;
        public final ForgeConfigSpec.IntValue powerupSpawnAttempts;
        public final ForgeConfigSpec.IntValue powerupSpawnSearchRadius;
        public final ForgeConfigSpec.IntValue powerupEffectDuration;
        public final ForgeConfigSpec.IntValue powerupLifetime;
        public final ForgeConfigSpec.BooleanValue powerupDebug;
        public final ForgeConfigSpec.BooleanValue enablePowerups;

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

            builder.push("Dropped Weapon animation fixer values");

            animationDroppedFixerSearchRadius = builder
                    .comment("\nRadius around players where dropped Quake weapons are checked for active animations to stop them")
                    .defineInRange("animationDroppedFixerSearchRadius", 20d, 1d, 32d);

            animationDroppedFixerSearchInterval = builder
                    .comment("\nTick interval between nearby dropped weapon animation checks")
                    .defineInRange("animationDroppedFixerSearchInterval", 5, 1, Integer.MAX_VALUE-1);

            builder.pop();
        }
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
