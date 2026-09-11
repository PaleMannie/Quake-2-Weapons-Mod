package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class QuadDamagePowerupEntity extends AbstractPowerupEntity{

    public QuadDamagePowerupEntity(EntityType<? extends QuadDamagePowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.QUAD_DAMAGE.getHolder().get(), getPowerupDuration()));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.QUAD_DAMAGE_ITEM.get();
    }




    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {}

    @Override
    public boolean hurtServer(ServerLevel pLevel, DamageSource pDamageSource, float pAmount) { return false; }

    @Override
    protected void readAdditionalSaveData(ValueInput pInput) {}

    @Override
    protected void addAdditionalSaveData(ValueOutput pOutput) {}
}
