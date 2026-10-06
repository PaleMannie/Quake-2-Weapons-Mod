package mett.palemannie.q2w.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;

/** Optional QW integration without a compile-time dependency on Quake Weapons. */
public final class QWRingCompatibility {
    private static final Identifier RING_EFFECT = Identifier.fromNamespaceAndPath("quakeweapons", "qw_invis");

    private QWRingCompatibility() {}

    public static void onWeaponShot(ServerPlayer player) {
        BuiltInRegistries.MOB_EFFECT.get(RING_EFFECT).ifPresent(effect -> {
            if (!player.hasEffect(effect)) return;

            // Match QW's attack behavior. Normal removal also triggers QW's
            // visibility packet and removes the ring's waypoint modifier.
            player.removeEffect(MobEffects.INVISIBILITY);
            player.removeEffect(effect);
            player.setInvisible(player.hasEffect(MobEffects.INVISIBILITY) || player.hasEffect(effect));
        });
    }
}
