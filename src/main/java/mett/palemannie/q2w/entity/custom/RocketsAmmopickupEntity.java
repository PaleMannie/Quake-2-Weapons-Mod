package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RocketsAmmopickupEntity extends AbstractItempickupEntity {

    private static final int AMOUNT = 5;

    public RocketsAmmopickupEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected SoundEvent getPickupSound() {
        return ModSounds.AMMO_PICKUP.get();
    }

    @Override
    protected void onPickup(Player player) {
        giveAmmoOrDrop(player, ModItems.ROCKET.get(), AMOUNT);
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