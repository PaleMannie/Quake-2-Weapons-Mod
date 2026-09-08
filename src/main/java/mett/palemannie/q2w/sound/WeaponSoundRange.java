package mett.palemannie.q2w.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

final class WeaponSoundRange {
    static final float BLOCKS = 16f;

    private WeaponSoundRange() {}

    static float volume(Entity source, float baseVolume, float range) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || source.level() != minecraft.level) return 0f;
        double distance = minecraft.gameRenderer.getMainCamera().getPosition().distanceTo(source.position());
        return baseVolume * (float) Math.max(0d, 1d - distance / range);
    }
}
