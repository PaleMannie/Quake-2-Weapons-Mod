package mett.palemannie.q2w.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/** Explicit falloff also works for stereo assets, which bypass positional attenuation. */
final class WeaponSoundRange {
    static final float BLOCKS = 16f;

    private WeaponSoundRange() {}

    static float volume(Player source, float baseVolume, float range) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || source.level() != minecraft.level) return 0f;
        double distance = minecraft.gameRenderer.getMainCamera().getPosition().distanceTo(source.position());
        return baseVolume * (float) Math.max(0d, 1d - distance / range);
    }
}
