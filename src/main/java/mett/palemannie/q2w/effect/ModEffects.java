package mett.palemannie.q2w.effect;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.effect.custom.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Quake2Weapons.MODID);

    public static final RegistryObject<MobEffect> QUAD_DAMAGE = MOB_EFFECTS.register("quad_damage", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 4034242));
    public static final RegistryObject<MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invulnerability", ()-> new InvulnerabilityEffect(MobEffectCategory.BENEFICIAL, 16765184));
    public static final RegistryObject<MobEffect> ENVIROSUIT = MOB_EFFECTS.register("envirosuit", ()-> new EnvirosuitEffect(MobEffectCategory.BENEFICIAL, 65408));
    public static final RegistryObject<MobEffect> SILENCER = MOB_EFFECTS.register("silencer", ()-> new SilencerEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> REBREATHER = MOB_EFFECTS.register("silencer", ()-> new RebreatherEffect(MobEffectCategory.BENEFICIAL, 0));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
