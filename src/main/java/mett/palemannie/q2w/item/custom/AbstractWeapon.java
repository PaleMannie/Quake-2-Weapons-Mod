package mett.palemannie.q2w.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

/*
 *   Major credit goes to byteManiaks MCQuake3 - semi-appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */

public abstract class AbstractWeapon extends Item implements GeoItem {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWeapon(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    public void setCurrentHand(InteractionHand hand, LivingEntity player) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.isEmpty() && !player.isUsingItem()) {
            player.useItem = itemStack;
            player.useItemRemaining = itemStack.getUseDuration();
            if (!player.level().isClientSide()) {
                player.setLivingEntityFlag(1, true);
                player.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                player.gameEvent(GameEvent.ITEM_INTERACT_START);
            }

        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return false;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 2000000000;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {


        /// TODO: Eventuelle kompatibilität mit QW
        /*pPlayer.removeEffect(ModEffects.QW_INVIS.get());
        pPlayer.setInvisible(false);*/

        if (pUsedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        } else {
            setCurrentHand(pUsedHand, (LivingEntity) pPlayer);
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration);

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);

        executeWeaponFire(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    int cooldown;

    @Override
    public void releaseUsing(ItemStack stack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(stack, pLevel, pLivingEntity, pTimeCharged);

        if(pLivingEntity instanceof Player) ((Player) pLivingEntity).getCooldowns().addCooldown(this, cooldown);

        if (pLevel instanceof ServerLevel serverLevel){

            stopShootingAnimation(pLivingEntity, serverLevel, stack);
            stopAmmoEmptyAnimation(pLivingEntity, serverLevel, stack);
            startIdleAnimation(pLivingEntity, serverLevel, stack);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {

        if(entity instanceof  Player player && level instanceof ServerLevel serverLevel) {

            if (stack.hasTag() && stack.getTag().getBoolean("WasDropped")) {

                stopShootingAnimation((LivingEntity) entity, serverLevel, stack);
                stopAmmoEmptyAnimation((LivingEntity) entity, serverLevel, stack);
                startIdleAnimation((LivingEntity) entity, serverLevel, stack);
                stack.getTag().remove("WasDropped");
            }

            if ((!selected || !player.isUsingItem()) ) {

                stopShootingAnimation((LivingEntity) entity, serverLevel, stack);
                stopAmmoEmptyAnimation((LivingEntity) entity, serverLevel, stack);
                startIdleAnimation((LivingEntity) entity, serverLevel, stack);
            }
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }

    public void startShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void startAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void stopAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void startIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }

    public void stopIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){
        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }
}
