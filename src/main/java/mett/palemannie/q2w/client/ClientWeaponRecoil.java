package mett.palemannie.q2w.client;

import net.minecraft.util.Mth;

public final class ClientWeaponRecoil {

    private ClientWeaponRecoil() {}

    private static float previousPitchKick = 0f;
    private static float pitchKick = 0f;

    private static float previousRollKick = 0f;
    private static float rollKick = 0f;

    private static float previousYawKick = 0f;
    private static float yawKick = 0f;

    public static void kick(float pitchAmount, float rollAmount, float yawAmount) {

        pitchKick = Math.min(pitchKick + pitchAmount, 100f);
        rollKick = Mth.clamp(rollKick + rollAmount, -100f, 100f);
        yawKick = Math.min(yawKick + yawAmount, 100f);
    }

    public static void clientTick() {

        previousPitchKick = pitchKick;
        previousRollKick = rollKick;
        previousYawKick = yawKick;

        pitchKick *= 0.72f;
        rollKick *= 0.68f;
        yawKick *= 0.7f;

        if (Math.abs(pitchKick) < 0.02F) {
            pitchKick = 0.0F;
        }

        if (Math.abs(rollKick) < 0.02F) {
            rollKick = 0.0F;
        }

        if (Math.abs(yawKick) < 0.02F) {
            yawKick = 0.0F;
        }
    }

    public static float getPitchKick(float partialTick) {
        return Mth.lerp(partialTick, previousPitchKick, pitchKick);
    }

    public static float getRollKick(float partialTick) {
        return Mth.lerp(partialTick, previousRollKick, rollKick);
    }

    public static float getYawKick(float partialTick) {
        return Mth.lerp(partialTick, previousYawKick, yawKick);
    }
}