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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PowerupSpawner {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static int spawnInterval = 600;
    private static int spawnAttempts = 3;
    private static int searchRadius = 5;
    private static int maxNearbyPowerups = 16;
    private static final double NEARBY_RADIUS = 128d;
    private static final String AUTO_SPAWN_TAG = "Q2WAutomaticPowerup";
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
            maxNearbyPowerups = Q2WConfig.SERVER.maxNearbyPowerups.get();
            debugEnabled = Q2WConfig.SERVER.powerupDebug.get();
            powerupSpawningEnabled = Q2WConfig.SERVER.enablePowerups.get();

            LOGGER.info("[Quake2Weapons] PowerupSpawner config reloaded:");
            LOGGER.info("interval={} | attempts={} | radius={} | debug={} | enablePowerups={}, maxNearbyPowerups={}",
                    spawnInterval, spawnAttempts, searchRadius, debugEnabled, powerupSpawningEnabled, maxNearbyPowerups);

        } catch (Exception e) {
            LOGGER.error("[Quake2Weapons] Failed to load config values, using defaults!", e);
            spawnInterval = 600;
            spawnAttempts = 3;
            searchRadius = 5;
            maxNearbyPowerups = 3;
            debugEnabled = false;
            powerupSpawningEnabled = true;
        }

        LOGGER.info("[Quake2Weapons] Config values after load: powerupSpawnInterval={}, powerupSpawnAttempts={}, powerupSpawnSearchRadius={}, enablePowerups={}, powerupDebug={}, maxNearbyPowerups={}",
                Q2WConfig.SERVER.powerupSpawnInterval.get(),
                Q2WConfig.SERVER.powerupSpawnAttempts.get(),
                Q2WConfig.SERVER.powerupSpawnSearchRadius.get(),
                Q2WConfig.SERVER.enablePowerups.get(),
                Q2WConfig.SERVER.powerupDebug.get(),
                Q2WConfig.SERVER.maxNearbyPowerups);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        if (!powerupSpawningEnabled) {
            if (debugEnabled) {

                LOGGER.warn("POWERUP SPAWNING DISABLED. DISABLE DEBUG MODE IN SERVER CONFIG OR ENABLE POWERUP SPAWNING");
                debug(event.level.getServer().overworld(), "POWERUP SPAWNING DISABLED. DISABLE DEBUG MODE IN SERVER CONFIG OR ENABLE POWERUP SPAWNING");
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

    /// random xyz distribution relative to your position

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {

        if (nearbyLimitReached(level, player)) {
            debug(level, "Nearby automatic pickup limit reached for " + player.getScoreboardName());
            return;
        }

        RandomSource random = level.random;

        int x = player.blockPosition().getX() + Mth.nextInt(random, -128, 128);
        int z = player.blockPosition().getZ() + Mth.nextInt(random, -128, 128);
        int minY = Math.max(level.getMinBuildHeight() + 1, player.blockPosition().getY() - 64);
        int maxY = Math.min(level.getMaxBuildHeight() - 1, player.blockPosition().getY() + 64);
        int y = Mth.nextInt(random, minY, maxY);

        BlockPos candidate = new BlockPos(x, y, z);

        if (tryFindSpawnPos(level, player, candidate, searchRadius, searchRadius, pos -> {

            // Recheck immediately before insertion; earlier players/attempts in this tick count.
            Vec3 spawnPosition = Vec3.atBottomCenterOf(pos);
            for (ServerPlayer nearbyPlayer : level.players()) {
                if (nearbyPlayer.distanceToSqr(spawnPosition) <= NEARBY_RADIUS * NEARBY_RADIUS
                        && nearbyLimitReached(level, nearbyPlayer)) return;
            }

            Entity entity = randomPowerup(level);
            entity.getPersistentData().putBoolean(AUTO_SPAWN_TAG, true);
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);

            if (level.addFreshEntity(entity)) {
                List<Integer> bag = getPowerupBag(level);
                bag.remove(bag.size() - 1);

                debug(level, "§aSpawned "
                        + entity.getType().toShortString() + " at " + pos);
            } else {
                debug(level, "§cSpawn rejected for "
                        + entity.getType().toShortString() + " at " + pos);
            }
        })) return;

        debug(level, "§cNo valid spawn near " + candidate);
    }

    private static boolean nearbyLimitReached(ServerLevel level, ServerPlayer player) {
        if (maxNearbyPowerups == 0) return true;
        return level.getEntities(player, player.getBoundingBox().inflate(NEARBY_RADIUS),
                entity -> !entity.isRemoved()
                        && entity.getPersistentData().getBoolean(AUTO_SPAWN_TAG)
                        && entity.distanceToSqr(player) <= NEARBY_RADIUS * NEARBY_RADIUS)
                .size() >= maxNearbyPowerups;
    }

    private static boolean tryFindSpawnPos(ServerLevel level, ServerPlayer player, BlockPos center, int radius, int yRadius, Consumer<BlockPos> onFound) {

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -yRadius; dy <= yRadius; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (player.distanceToSqr(Vec3.atBottomCenterOf(pos)) > NEARBY_RADIUS * NEARBY_RADIUS) continue;
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

    /// pseudo random distribution of successfully spawning powerups

    private static final int POWERUP_TYPE_COUNT = 14;

    private static final Map<ServerLevel, List<Integer>> POWERUP_BAGS =
            new WeakHashMap<>();

    private static List<Integer> getPowerupBag(ServerLevel level) {

        List<Integer> bag = POWERUP_BAGS.computeIfAbsent(
                level, ignored -> new ArrayList<>(POWERUP_TYPE_COUNT)
        );

        if (bag.isEmpty()) {
            for (int i = 0; i < POWERUP_TYPE_COUNT; i++) {
                bag.add(i);
            }

            for (int i = bag.size() - 1; i > 0; i--) {

                int j = level.random.nextInt(i + 1);
                int temp = bag.get(i);
                bag.set(i, bag.get(j));
                bag.set(j, temp);
            }
        }

        return bag;
    }

    private static Entity randomPowerup(ServerLevel level) {

        List<Integer> bag = getPowerupBag(level);
        int type = bag.get(bag.size() - 1);

        return switch (type) {

            case 0 -> new QuadDamagePowerupEntity(ModEntities.QUAD_DAMAGE_POWERUP.get(), level);
            case 1 -> new InvulnerabilityPowerupEntity(ModEntities.INVULN_POWERUP.get(), level);
            case 2 -> new EnvirosuitPowerupEntity(ModEntities.ENVIROSUIT_POWERUP.get(), level);
            case 3 -> new AdrenalinePickupEntity(ModEntities.ADRENALINE_PICKUP.get(), level);
            case 4 -> new SilencerPowerupEntity(ModEntities.SILENCER_POWERUP.get(), level);
            case 5 -> new BulletsAmmopickupEntity(ModEntities.BULLETS_AMMOPICKUP.get(), level);
            case 6 -> new ShellsAmmopickupEntity(ModEntities.SHELLS_AMMOPICKUP.get(), level);
            case 7 -> new GrenadesAmmopickupEntity(ModEntities.GRENADES_AMMOPICKUP.get(), level);
            case 8 -> new RocketsAmmopickupEntity(ModEntities.ROCKETS_AMMOPICKUP.get(), level);
            case 9 -> new CellsAmmopickupEntity(ModEntities.CELLS_AMMOPICKUP.get(), level);
            case 10 -> new MegahealthPickupEntity(ModEntities.MEGAHEALTH_PICKUP.get(), level);
            case 11 -> new PowershieldPickupEntity(ModEntities.POWERSHIELD_PICKUP.get(), level);
            case 12 -> new RebreatherPickupEntity(ModEntities.REBREATHER_PICKUP.get(), level);
            default -> new SlugsAmmopickupEntity(ModEntities.SLUGS_AMMOPICKUP.get(), level);
        };
    }

    private static void debug(ServerLevel level, String msg) {

        if (!debugEnabled) return;
        Component comp = Component.literal("§d[PowerupSpawner]§r " + msg);
        for (ServerPlayer sp : level.players()) sp.sendSystemMessage(comp);
    }
}