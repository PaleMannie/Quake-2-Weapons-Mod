package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RebreatherPickupEntity extends AbstractItempickupEntity{

    public RebreatherPickupEntity(EntityType<? extends RebreatherPickupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) { giveAmmoOrDrop(player, ModItems.REBREATHER_ITEM.get(), 1); }

    @Override
    protected SoundEvent getPickupSound() {
        return ModSounds.ITEM_PICKUP.get();
    }
}
