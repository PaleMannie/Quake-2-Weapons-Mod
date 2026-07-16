package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.Q2WConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AbstractPowerupItem extends Item {

    public AbstractPowerupItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            int duration = Q2WConfig.SERVER.powerupEffectDuration.get();
            onPowerupUse(level, player, stack, duration);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Nullable
    public MobEffect getPowerupEffect() {
        return null;
    }

    protected abstract void onPowerupUse(Level level, Player player, ItemStack stack, int duration);

    protected int getPowerupDuration() {
        return Q2WConfig.SERVER.powerupEffectDuration.get();
    }
}
