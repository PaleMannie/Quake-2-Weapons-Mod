package mett.palemannie.q2w.event;


import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.AbstractPowerupItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID)
public class PowerupFeedHandler {

    @SubscribeEvent
    public static void onFeedPowerup(PlayerInteractEvent.EntityInteract event) {

        Player player = event.getEntity();
        Level level = player.level();
        ItemStack held = event.getItemStack();
        Entity target = event.getTarget();

        if (level.isClientSide) return;
        if (!(target instanceof LivingEntity living)) return;
        if (!(held.getItem() instanceof AbstractPowerupItem powerupItem)) return;

        if (powerupItem.getPowerupEffect() != null) {

            living.addEffect(new MobEffectInstance(powerupItem.getPowerupEffect(), 600, 0, false, false, true));
            level.playSound(null, living.blockPosition(), SoundEvents.HORSE_EAT, net.minecraft.sounds.SoundSource.PLAYERS, 1f, 1f);

            if (!player.getAbilities().instabuild) {

                held.shrink(1);
            }

            event.setCanceled(true);
        }
    }
}