package mett.palemannie.q2w.effect;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.effect.custom.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Quake2Weapons.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> QUAD_DAMAGE = MOB_EFFECTS.register("quad_damage_effect", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 4034242));
    public static final DeferredHolder<MobEffect, MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invuln_effect", ()-> new InvulnerabilityEffect(MobEffectCategory.BENEFICIAL, 16765184));
    public static final DeferredHolder<MobEffect, MobEffect> ENVIROSUIT = MOB_EFFECTS.register("envirosuit_effect", ()-> new EnvirosuitEffect(MobEffectCategory.BENEFICIAL, 65408));
    public static final DeferredHolder<MobEffect, MobEffect> ADRENALINE_HEALTH_BOOST = MOB_EFFECTS.register("adrenaline_health_boost_effect",
            () -> new AdrenalineHealthBoostEffect(MobEffectCategory.BENEFICIAL, 16284963)
                    .addAttributeModifier(Attributes.MAX_HEALTH, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "adrenaline_health_boost"),
                            2.0, AttributeModifier.Operation.ADD_VALUE));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
