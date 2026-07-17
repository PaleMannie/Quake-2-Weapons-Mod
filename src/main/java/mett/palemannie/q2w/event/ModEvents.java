package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onQuadDamageHurt(LivingHurtEvent event) {

        /// Quad Damage apply damage
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if(attacker.hasEffect(ModEffects.QUAD_DAMAGE.get())) {

                event.setAmount(event.getAmount() * 4.0F);
            }
        }

        /// Pentagram apply invulnerability
        if (event.getEntity().hasEffect(ModEffects.INVULNERABILITY.get())) {

            event.setCanceled(true);
            event.getEntity().level().playSound(null, event.getEntity().blockPosition(), ModSounds.INVULN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        /// Biosuit apply poison and wither immunities & Fire resistance
        if (event.getEntity().hasEffect(ModEffects.ENVIROSUIT.get())) {

            DamageSource src = event.getSource();

            if (src.is(DamageTypes.MAGIC) || src.is(DamageTypes.WITHER) || src.is(DamageTypes.CACTUS) || src.is(DamageTypes.SWEET_BERRY_BUSH)
                    || src.is(DamageTypes.DROWN) || src.is(DamageTypes.INDIRECT_MAGIC) || src.is(DamageTypes.MAGIC)
                    || src.is(DamageTypes.WITHER_SKULL) || src.is(DamageTypes.THORNS) || src.is(DamageTypes.STING)) {
                event.setCanceled(true);
            }

            if(src.is(DamageTypes.IN_FIRE) || src.is(DamageTypes.ON_FIRE) || src.is(DamageTypes.UNATTRIBUTED_FIREBALL) || src.is(DamageTypes.CRAMMING)
                    || src.is(DamageTypes.FIREBALL) || src.is(DamageTypes.LAVA) || src.is(DamageTypes.HOT_FLOOR) || src.is(DamageTypes.FREEZE)
                    || src.is(DamageTypes.LIGHTNING_BOLT) || src.is(DamageTypes.DRAGON_BREATH) || src.is(DamageTypes.SONIC_BOOM)){

                event.setAmount(event.getAmount() / 2f);
            }
        }
    }

    /// Effect pickup sounds
    @SubscribeEvent
    public static void onEffectGotten(MobEffectEvent.Added event){

        LivingEntity entity = event.getEntity();

        if(event.getEffectInstance().getEffect().equals(ModEffects.QUAD_DAMAGE.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.QUAD_DAMAGE_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if(event.getEffectInstance().getEffect().equals(ModEffects.INVULNERABILITY.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.INVULN_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if(event.getEffectInstance().getEffect().equals(ModEffects.ENVIROSUIT.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.ENVIROSUIT_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    ///Quad Damage play use sound when attacking with anything or using any item
    ///additional Invis cancelation when under Ring of Shadows effect

    @SubscribeEvent
    public static void onQuadDamageAttack1(PlayerInteractEvent.LeftClickEmpty event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack2(PlayerInteractEvent.LeftClickBlock event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack3(PlayerInteractEvent.EntityInteract event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack4(AttackEntityEvent event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getEntity().level().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack5(ArrowLooseEvent event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack6(PlayerInteractEvent.RightClickItem event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }
}