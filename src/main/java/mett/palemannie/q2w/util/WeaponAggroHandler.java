package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.SilencedShotsSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

public final class WeaponAggroHandler {

    private WeaponAggroHandler() {}

    private static final String SILENCED_SHOTS_TAG = "Q2WSilencedShots";

    public static final int DEFAULT_SILENCER_SHOTS = 30;

    public static final float SILENCED_WEAPON_VOLUME_MULTIPLIER = 0.33F;

    public static boolean shouldQuietWeaponSounds(ServerPlayer player) {
        return hasSilencerActive(player);
    }

    public static float getWeaponSoundVolume(ServerPlayer player, float normalVolume) {
        if (shouldQuietWeaponSounds(player)) {
            return normalVolume * SILENCED_WEAPON_VOLUME_MULTIPLIER;
        }

        return normalVolume;
    }

    public static void addSilencedShots(ServerPlayer player, int amount) {

        if (amount <= 0) {
            return;
        }

        CompoundTag data = player.getPersistentData();
        int current = data.getInt(SILENCED_SHOTS_TAG);
        int next = current + amount;

        data.putInt(SILENCED_SHOTS_TAG, next);
        syncSilencerShots(player);
    }

    public static int getSilencedShots(ServerPlayer player) {
        return player.getPersistentData().getInt(SILENCED_SHOTS_TAG);
    }

    public static boolean hasSilencerActive(ServerPlayer player) {
        return getSilencedShots(player) > 0;
    }

    public static void onWeaponShot(ServerPlayer player) {

        if (player.level().isClientSide) {
            return;
        }

        if (consumeSilencerShot(player)) {
            return;
        }

        int range = Q2WConfig.SERVER.weaponAggroRange.get();

        if (range <= 0) {
            return;
        }

        aggroMonsters(player, range);
    }

    public static void onLoudWeaponHeld(ServerPlayer player) {

        if (player.level().isClientSide) {
            return;
        }

        if (hasSilencerActive(player)) {
            return;
        }

        int range = Q2WConfig.SERVER.weaponAggroRange.get();

        if (range <= 0) {
            return;
        }

        aggroMonsters(player, range);
    }

    private static boolean consumeSilencerShot(ServerPlayer player) {

        CompoundTag data = player.getPersistentData();
        int shots = data.getInt(SILENCED_SHOTS_TAG);

        if (shots <= 0) {
            syncSilencerShots(player);
            return false;
        }

        shots--;

        if (shots <= 0) {
            data.remove(SILENCED_SHOTS_TAG);
        } else {
            data.putInt(SILENCED_SHOTS_TAG, shots);
        }

        syncSilencerShots(player);
        return true;
    }

    private static void aggroMonsters(ServerPlayer player, int range) {

        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        double radius = range;
        double radiusSqr = radius * radius;

        AABB area = player.getBoundingBox().inflate(radius);

        for (Monster monster : level.getEntitiesOfClass(Monster.class, area, monster ->
                monster.isAlive()
                        && !monster.isSpectator()
                        && monster.distanceToSqr(player) <= radiusSqr
        )) {

            monster.setTarget(player);
            monster.setAggressive(true);
        }
    }

    public static void syncSilencerShots(ServerPlayer player) {
        ModMessages.sendToPlayer(new SilencedShotsSyncS2CPacket(getSilencedShots(player)), player);
    }
}