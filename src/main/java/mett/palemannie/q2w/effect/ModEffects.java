package mett.palemannie.q2w.effect;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.effect.custom.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Quake2Weapons.MODID);

    public static final RegistryObject<MobEffect> QUAD_DAMAGE = MOB_EFFECTS.register("quad_damage_effect", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 4034242));
    public static final RegistryObject<MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invuln_effect", ()-> new InvulnerabilityEffect(MobEffectCategory.BENEFICIAL, 16765184));
    public static final RegistryObject<MobEffect> ENVIROSUIT = MOB_EFFECTS.register("envirosuit_effect", ()-> new EnvirosuitEffect(MobEffectCategory.BENEFICIAL, 65408));
    public static final RegistryObject<MobEffect> ADRENALINE_HEALTH_BOOST = MOB_EFFECTS.register("adrenaline_health_boost_effect",
            () -> new AdrenalineHealthBoostEffect(MobEffectCategory.BENEFICIAL, 16284963)
                    .addAttributeModifier(Attributes.MAX_HEALTH, "a754a37e-7042-4d39-b427-8c36455d2e1a",
                            2.0, AttributeModifier.Operation.ADDITION));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
