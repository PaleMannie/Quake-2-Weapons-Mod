package mett.palemannie.q2w.item.custom;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

/*
 * Base class for all Q2W weapons.
 *
 * Handles:
 * - Minecraft use lifecycle
 * - GeckoLib cache
 * - common animation controllers
 * - common client renderer setup
 * - bow-style arm pose
 * - idle/shooting/ammoempty animation helpers
 */
public abstract class AbstractWeapon extends Item implements GeoItem {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWeapon(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    /**
     * Example:
     * blaster -> blaster.animations.shooting
     * bfg10k  -> bfg10k.animations.shooting
     */
    protected abstract String animationPrefix();

    /**
     * Each weapon still provides its renderer,
     * but initializeClient() itself lives here.
     */
    protected abstract BlockEntityWithoutLevelRenderer createRenderer();

    protected String shootingAnimationName() {
        return animationPrefix() + ".animations.shooting";
    }

    protected String ammoEmptyAnimationName() {
        return animationPrefix() + ".animations.ammoempty";
    }

    protected String idleAnimationName() {
        return animationPrefix() + ".animations.idle";
    }

    /**
     * Override in child classes if a weapon should apply a cooldown after releasing right click.
     */
    protected int getReleaseCooldownTicks() {
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        RawAnimation shootingAnim = RawAnimation.begin()
                .then(shootingAnimationName(), Animation.LoopType.LOOP);

        RawAnimation ammoEmptyAnim = RawAnimation.begin()
                .then(ammoEmptyAnimationName(), Animation.LoopType.LOOP);

        RawAnimation idleAnim = RawAnimation.begin()
                .then(idleAnimationName(), Animation.LoopType.LOOP);

        controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", shootingAnim));

        controllers.add(new AnimationController<>(this, "controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", ammoEmptyAnim));

        controllers.add(new AnimationController<>(this, "controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", idleAnim));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = createRenderer();
                }

                return this.renderer;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty() && entityLiving.getItemInHand(hand) == itemStack) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                return HumanoidModel.ArmPose.EMPTY;
            }
        });
    }

    public void setCurrentHand(InteractionHand hand, LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getItemInHand(hand);

        if (!itemStack.isEmpty() && !livingEntity.isUsingItem()) {
            livingEntity.useItem = itemStack;
            livingEntity.useItemRemaining = itemStack.getUseDuration();

            if (!livingEntity.level().isClientSide()) {
                livingEntity.setLivingEntityFlag(1, true);
                livingEntity.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                livingEntity.gameEvent(GameEvent.ITEM_INTERACT_START);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 2000000000;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));
        }

        setCurrentHand(usedHand, player);
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration);

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        executeWeaponFire(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);

        int releaseCooldown = getReleaseCooldownTicks();

        if (releaseCooldown > 0 && livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(this, releaseCooldown);
        }

        if (level instanceof ServerLevel serverLevel) {
            resetToIdle(livingEntity, serverLevel, stack);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        if (stack.hasTag() && stack.getTag().getBoolean("WasDropped")) {
            resetToIdle(livingEntity, serverLevel, stack);
            stack.getTag().remove("WasDropped");
        }

        if (entity instanceof Player player) {
            boolean usingThisStack = player.isUsingItem() && player.getUseItem() == stack;

            if (!selected || !usingThisStack) {
                resetToIdle(livingEntity, serverLevel, stack);
            }
        }
    }

    protected void resetToIdle(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopShootingAnimation(livingEntity, serverLevel, stack);
        stopAmmoEmptyAnimation(livingEntity, serverLevel, stack);
        startIdleAnimation(livingEntity, serverLevel, stack);
    }

    public void startShootingAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void startAmmoEmptyAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void stopAmmoEmptyAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void startIdleAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }

    public void stopIdleAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }
}