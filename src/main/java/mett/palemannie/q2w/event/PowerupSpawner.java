package mett.palemannie.q2w.event;


import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.entity.custom.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PowerupSpawner {

    private static int spawnInterval = 600;
    private static int spawnAttempts = 3;
    private static int searchRadius = 5;
    private static boolean debugEnabled = false;
    private static boolean powerupSpawningEnabled = true;

    private static long tickCounter = 0;

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent event) {

        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {
            reloadConfigValues();
        }
    }

    public static void reloadConfigValues() {

        try {
            spawnInterval = Q2WConfig.SERVER.powerupSpawnInterval.get();
            spawnAttempts = Q2WConfig.SERVER.powerupSpawnAttempts.get();
            searchRadius = Q2WConfig.SERVER.powerupSpawnSearchRadius.get();
            debugEnabled = Q2WConfig.SERVER.powerupDebug.get();
            powerupSpawningEnabled = Q2WConfig.SERVER.enablePowerups.get();

            System.out.println("[QuakeWeapons] PowerupSpawner config reloaded:");
            System.out.println(" interval=" + spawnInterval + " | attempts=" + spawnAttempts + " | radius=" + searchRadius + " | debug=" + debugEnabled
            + " | enablePowerups=" + powerupSpawningEnabled);

        } catch (Exception e) {
            System.err.println("[QuakeWeapons] Failed to load config values, using defaults!");
            spawnInterval = 600;
            spawnAttempts = 3;
            searchRadius = 5;
            debugEnabled = false;
            powerupSpawningEnabled = true;
        }

        System.out.println("[QuakeWeapons] Config values after load: powerupSpawnInterval:"
                + Q2WConfig.SERVER.powerupSpawnInterval.get() + ", powerupSpawnAttempts:"
                + Q2WConfig.SERVER.powerupSpawnAttempts.get() + ", powerupSpawnSearchRadius:"
                + Q2WConfig.SERVER.powerupSpawnSearchRadius.get() + ", enablePowerups:"
                + Q2WConfig.SERVER.enablePowerups.get() + ", powerupDebug;"
                + Q2WConfig.SERVER.powerupDebug.get());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        if (!powerupSpawningEnabled) {
            if (debugEnabled) {
                System.err.println("POWERUP SPAWNING DISABLED. DISABLE DEBUG MODE IN SERVER CONFIG OR ENABLE POWERUP SPAWNING");
            }
            return;
        }

        if (event.phase != TickEvent.Phase.END || event.level.isClientSide) return;

        int interval = spawnInterval;
        int attempts = spawnAttempts;
        ServerLevel level = (ServerLevel) event.level;
        long gameTime = level.getGameTime();


        if ((gameTime % interval) != 0L) {
            return;
        }

        for (ServerPlayer player : level.players()) {
            for (int i = 0; i < attempts; i++) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {

        RandomSource random = level.random;
        int x = player.blockPosition().getX() + Mth.nextInt(random, -128, 128);
        int z = player.blockPosition().getZ() + Mth.nextInt(random, -128, 128);
        int y = Mth.nextInt(random, level.getMinBuildHeight() + 5, level.getMaxBuildHeight() - 5);
        BlockPos candidate = new BlockPos(x, y, z);

        if (tryFindSpawnPos(level, candidate, searchRadius, searchRadius, pos -> {
            AbstractPowerupEntity entity = randomPowerup(level);
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);
            debug(level, "§aSpawned " + entity.getType().toShortString() + " at " + pos);
        })) return;

        debug(level, "§cNo valid spawn near " + candidate);
    }

    private static boolean tryFindSpawnPos(ServerLevel level, BlockPos center, int radius, int yRadius, Consumer<BlockPos> onFound) {

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -yRadius; dy <= yRadius; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState above = level.getBlockState(pos);
                    BlockState below = level.getBlockState(pos.below());
                    if (above.isAir() && (below.isSolid() || below.isFaceSturdy(level, pos.below(), Direction.UP))) {
                        onFound.accept(pos);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //TODO: Ammo pickups hinzufügen
    //TODO: Systen.out zu LOGGER wechseln

    private static AbstractPowerupEntity randomPowerup(ServerLevel level) {

        return switch (level.random.nextInt(6)) {
            case 0 -> new QuadDamagePowerupEntity(ModEntities.QUAD_DAMAGE_POWERUP.get(), level);
            case 1 -> new InvulnerabilityPowerupEntity(ModEntities.INVULN_POWERUP.get(), level);
            case 2 -> new EnvirosuitPowerupEntity(ModEntities.ENVIROSUIT_POWERUP.get(), level);
            case 3 -> new AdrenalinePowerupEntity(ModEntities.ADRENALINE_POWERUP.get(), level);
            case 4 -> new SilencerPowerupEntity(ModEntities.SILENCER_POWERUP.get(), level);
            default -> new RebreatherPowerupEntity(ModEntities.REBREATHER_POWERUP.get(), level);
        };
    }

    private static void debug(ServerLevel level, String msg) {

        if (!debugEnabled) return;
        Component comp = Component.literal("§d[PowerupSpawner]§r " + msg);
        for (ServerPlayer sp : level.players()) sp.sendSystemMessage(comp);
        System.out.println("[PowerupSpawner] " + msg.replaceAll("§.", ""));
    }
}