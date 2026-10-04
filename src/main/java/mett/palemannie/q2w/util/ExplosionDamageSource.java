package mett.palemannie.q2w.util;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

/** Explosive weapons use distinct messages for self-damage and kills by another entity. */
public final class ExplosionDamageSource extends DamageSource {
    public ExplosionDamageSource(Holder<DamageType> type, @Nullable Entity projectile, @Nullable Entity owner) {
        super(type, projectile, owner);
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity victim) {
        String key = "death.attack." + getMsgId();
        Entity attacker = getEntity();
        if (attacker != null && attacker != victim) {
            return Component.translatable(key + ".player", victim.getDisplayName(), attacker.getDisplayName());
        }
        return Component.translatable(key, victim.getDisplayName());
    }
}
