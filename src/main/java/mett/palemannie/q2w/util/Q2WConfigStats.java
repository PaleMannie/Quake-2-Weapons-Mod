package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class Q2WConfigStats {

    private static final ResourceLocation QUAKEWEAPONS_QUAD_DAMAGE =
            ResourceLocation.fromNamespaceAndPath("quakeweapons", "quad_damage");

    public static float BlasterDamage = Q2WConfig.COMMON.blasterDamage.get().floatValue();
    public static float HyperBlasterDamage = Q2WConfig.COMMON.hyperblasterDamage.get().floatValue();
    public static float ShotgunDamage = Q2WConfig.COMMON.shotgunDamage.get().floatValue();
    public static float SuperShotgunDamage = Q2WConfig.COMMON.superShotgunDamage.get().floatValue();
    public static float MachinegunDamage = Q2WConfig.COMMON.machinegunDamage.get().floatValue();
    public static float ChaingunDamage = Q2WConfig.COMMON.chaingunDamage.get().floatValue();
    public static float HandGrenadeDamage = Q2WConfig.COMMON.handgrenadeDamage.get().floatValue();
    public static float HandGrenadeRadius = Q2WConfig.COMMON.handgrenadeRadius.get().floatValue();
    public static float GrenadelauncherDamage = Q2WConfig.COMMON.grenadelauncherDamage.get().floatValue();
    public static float GrenadelauncherRadius = Q2WConfig.COMMON.grenadelauncherRadius.get().floatValue();
    public static float RocketlauncherDamage = Q2WConfig.COMMON.rocketlauncherDamage.get().floatValue();
    public static float RocketlauncherRadius = Q2WConfig.COMMON.rocketlauncherRadius.get().floatValue();
    public static float RailgunDamage = Q2WConfig.COMMON.railgunDamage.get().floatValue();
    public static float Bfg10kDamage = Q2WConfig.COMMON.bfg10kDamage.get().floatValue();
    public static float Bfg10kLaserDamage = Q2WConfig.COMMON.bfg10kLaserDamage.get().floatValue();
    public static float Bfg10kFlashDamage = Q2WConfig.COMMON.bfg10kFlashDamage.get().floatValue();
    public static float PowershieldAbsorbRatio = Q2WConfig.SERVER.powershieldAbsorbRatio.get().floatValue();
    public static float PowershieldDamagePreCellConsumed = Q2WConfig.SERVER.powershieldDamagePreCellConsumed.get().floatValue();

    public static float applyQuadDamage(float baseDamage, @Nullable Entity attacker) {
        if (attacker instanceof LivingEntity livingEntity) {
            // Resolve the optional effect through the registry; Quakeweapons is not required.
            MobEffect quakeweaponsQuad = ForgeRegistries.MOB_EFFECTS.getValue(QUAKEWEAPONS_QUAD_DAMAGE);
            if (livingEntity.hasEffect(ModEffects.QUAD_DAMAGE.get())
                    || (quakeweaponsQuad != null && livingEntity.hasEffect(quakeweaponsQuad))) {
                return baseDamage * 4.0F;
            }
        }

        return baseDamage;
    }

    public static float shotgunDamage(@Nullable Entity attacker) {
        return applyQuadDamage(ShotgunDamage, attacker);
    }

    public static float superShotgunDamage(@Nullable Entity attacker) {
        return applyQuadDamage(SuperShotgunDamage, attacker);
    }

    public static float machinegunDamage(@Nullable Entity attacker) {
        return applyQuadDamage(MachinegunDamage, attacker);
    }

    public static float chaingunDamage(@Nullable Entity attacker) {
        return applyQuadDamage(ChaingunDamage, attacker);
    }

    public static float railgunDamage(@Nullable Entity attacker) {
        return applyQuadDamage(RailgunDamage, attacker);
    }
}
