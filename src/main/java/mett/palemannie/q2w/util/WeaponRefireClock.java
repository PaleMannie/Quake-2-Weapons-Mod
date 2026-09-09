package mett.palemannie.q2w.util;

import java.util.HashMap;
import java.util.Map;

/// One clock per player; keys identify weapon types, never inventory slots or stacks.
public final class WeaponRefireClock<K> {
    private final Map<K, Long> lastAttempts = new HashMap<>();

    public boolean tryFire(K weapon, long currentTick, int intervalTicks) {
        Long lastTick = lastAttempts.get(weapon);
        if (lastTick != null && currentTick >= lastTick && currentTick - lastTick < intervalTicks) {
            return false;
        }
        // Empty-ammo attempts are paced too, so clicks/sounds cannot run every tick.
        lastAttempts.put(weapon, currentTick);
        return true;
    }
}
