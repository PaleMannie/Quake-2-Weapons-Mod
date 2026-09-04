package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.PowershieldItem;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.Q2WConfigStats;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PowerShieldEventHandler {

    private static float ABSORB_RATIO = 0.66f;
    private static float DAMAGE_PER_CELL = 2f;

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent event) {

        if (event.getConfig().getSpec() == Q2WConfig.SERVER_SPEC) {
            reloadConfigValues();
        }
    }

    public static void reloadConfigValues() {

        try {

            ABSORB_RATIO = Q2WConfigStats.PowershieldAbsorbRatio;
            DAMAGE_PER_CELL = Q2WConfigStats.PowershieldDamagePreCellConsumed;
            Quake2Weapons.LOGGER.info("[Quake2Weapons] Power Shield config reloaded:");
            Quake2Weapons.LOGGER.info("powershieldAbsorbRatio={} | powershieldDamagePreCellConsumed={}",
                    ABSORB_RATIO, DAMAGE_PER_CELL);

        } catch (Exception e) {

            Quake2Weapons.LOGGER.error("[Quake2Weapons] Failed to load config values, using defaults!", e);
            ABSORB_RATIO = 0.66f;
            DAMAGE_PER_CELL = 2f;

        }

        Quake2Weapons.LOGGER.info("[Quake2Weapons] Config values after load: powershieldAbsorbRatio={} | powershieldDamagePreCellConsumed={}",
                ABSORB_RATIO, DAMAGE_PER_CELL);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.getAmount() <= 0.0F) {
            return;
        }

        Optional<ItemStack> optionalShield = PowershieldItem.findActiveShield(player);

        if (optionalShield.isEmpty()) {
            return;
        }

        ItemStack shieldStack = optionalShield.get();

        if (!PowershieldItem.hasCells(player)) {

            PowershieldItem.setActive(shieldStack, false);
            playPowerDown(player);
            return;
        }

        float incomingDamage = event.getAmount();

        float requestedAbsorb = incomingDamage * ABSORB_RATIO;

        int availableCells = PowershieldItem.countCells(player);

        float maxAbsorbFromCells = availableCells * DAMAGE_PER_CELL;

        float actualAbsorb = Math.min(requestedAbsorb, maxAbsorbFromCells);

        int cellsToConsume = (int) Math.ceil(actualAbsorb / DAMAGE_PER_CELL);

        if (!PowershieldItem.consumeCells(player, cellsToConsume)) {

            PowershieldItem.setActive(shieldStack, false);
            playPowerDown(player);
            return;
        }

        float newDamage = Math.max(0.0F, incomingDamage - actualAbsorb);
        event.setAmount(newDamage);

        spawnPowerShieldHitParticles(player);

        if (!PowershieldItem.hasCells(player)) {
            PowershieldItem.setActive(shieldStack, false);
            playPowerDown(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PowershieldItem.deactivateAll(player);
        }
    }
    
    private static void playPowerDown(ServerPlayer player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.POWERSHIELD_DISABLE.get(), SoundSource.PLAYERS, 1f, 0.85f);
    }

    private static void spawnPowerShieldHitParticles(ServerPlayer player) {
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        Vec3 center = player.getBoundingBox().getCenter();

        int pointsPerRing = 18;

        double radius = Math.max(0.75D, player.getBbWidth() * 0.9D);
        double[] yOffsets = {
                -0.75D,
                -0.25D,
                0.25D,
                0.75D
        };

        double spinOffset = player.tickCount * 0.35D;

        for (double yOffset : yOffsets) {
            for (int i = 0; i < pointsPerRing; i++) {
                double angle = ((Math.PI * 2.0D) / pointsPerRing) * i + spinOffset;

                double x = center.x + Math.cos(angle) * radius;
                double y = center.y + yOffset;
                double z = center.z + Math.sin(angle) * radius;

                level.sendParticles(
                        ModParticles.BFG_LASER_PARTICLE.get(),
                        x,
                        y,
                        z,
                        1,
                        0.0D,
                        0.0D,
                        0.0D,
                        0.0D
                );
            }
        }

        level.sendParticles(
                ModParticles.BFG_LASER_PARTICLE.get(),
                center.x,
                center.y,
                center.z,
                10,
                player.getBbWidth() * 0.35D,
                player.getBbHeight() * 0.35D,
                player.getBbWidth() * 0.35D,
                0.0D
        );
    }
}