package mett.palemannie.q2w.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.LoopType;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractWeapon extends Item implements GeoItem {

    @org.jetbrains.annotations.Nullable
    public Item getAmmoItem() {
        return null;
    }

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected static final String SHOOT_CONTROLLER = "shoot_controller";
    protected static final String AMMO_EMPTY_CONTROLLER = "ammo_empty_controller";
    protected static final String IDLE_CONTROLLER = "idle_controller";

    protected static final String SHOOT_TRIGGER = "shoot";
    protected static final String AMMO_EMPTY_TRIGGER = "ammoempty";

    public AbstractWeapon(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected abstract String animationPrefix();

    protected String shootingAnimationName() {
        return animationPrefix() + ".animation.shooting";
    }

    protected String ammoEmptyAnimationName() {
        return animationPrefix() + ".animation.ammoempty";
    }

    protected String idleAnimationName() {
        return animationPrefix() + ".animation.idle";
    }

    protected int getReleaseCooldownTicks() {
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        RawAnimation shootingAnim = RawAnimation.begin()
                .then(shootingAnimationName(), LoopType.PLAY_ONCE);

        RawAnimation ammoEmptyAnim = RawAnimation.begin()
                .then(ammoEmptyAnimationName(), LoopType.PLAY_ONCE);

        RawAnimation idleAnim = RawAnimation.begin()
                .then(idleAnimationName(), LoopType.LOOP);

        controllers.add(new AnimationController<>("weapon_controller", 0, state -> {
            // GeckoLib 5.4.5 sets timelineTime to -2 at the end, so the
            // controller's hasAnimationFinished() stays false. Check the frame.
            var point = state.controller().getCurrentAnimationPoint();
            if (point != null && point.hasFinished()) {
                state.controller().stopTriggeredAnimation();
            } else if (state.controller().isTriggeredAnimation("shoot") || state.controller().isTriggeredAnimation("ammoempty")) {
                return PlayState.CONTINUE;
            }
            return state.setAndContinue(idleAnim);
        })
                .receiveTriggeredAnimations()
                .triggerableAnim("shoot", shootingAnim)
                .triggerableAnim("ammoempty", ammoEmptyAnim));
    }

    public void setCurrentHand(InteractionHand hand, LivingEntity player) {

        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.isEmpty() && !player.isUsingItem()) {

            player.useItem = itemStack;
            player.useItemRemaining = itemStack.getUseDuration(player);
            if (!player.level().isClientSide()) {

                player.setLivingEntityFlag(1, true);
                player.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                player.gameEvent(GameEvent.ITEM_INTERACT_START);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 2_000_000_000;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {

        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }

        setCurrentHand(usedHand, player);
        return InteractionResult.CONSUME;
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration);

    protected abstract void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        executeWeaponFire(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);

        int releaseCooldown = getReleaseCooldownTicks();

        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(stack, releaseCooldown);
        }

        afterShooting(stack, level, livingEntity, timeCharged);
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, net.minecraft.world.entity.EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        boolean selected = slot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;

        ServerLevel serverLevel = level;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        if (selected && entity instanceof net.minecraft.server.level.ServerPlayer player
                && (this instanceof ChaingunItem || this instanceof HyperblasterItem)) {
            boolean hasAmmo = player.isCreative();
            for (ItemStack ammo : player.getInventory().items) {
                if (ammo.is(getAmmoItem()) && !ammo.isEmpty()) {
                    hasAmmo = true;
                    break;
                }
            }
            mett.palemannie.q2w.util.ItemData.setBoolean(stack, "Q2WHasAmmo", hasAmmo);
        }

        if (selected && entity instanceof net.minecraft.server.level.ServerPlayer player
                && (this instanceof ChaingunItem || this instanceof HyperblasterItem
                    || this instanceof RailgunItem || this instanceof Bfg10kItem)) {
            mett.palemannie.q2w.util.ItemData.setBoolean(stack, "Q2WSilenced",
                    mett.palemannie.q2w.util.WeaponAggroHandler.hasSilencerActive(player));
        }

        if (mett.palemannie.q2w.util.ItemData.getBoolean(stack, "WasDropped")) {
            hardStopTriggeredAnimations(livingEntity, serverLevel, stack);
            mett.palemannie.q2w.util.ItemData.remove(stack, "WasDropped");
        }

        if (entity instanceof Player player) {
            boolean usingThisStack = player.isUsingItem() && player.getUseItem() == stack;

            if (!selected && !usingThisStack) {
                hardStopTriggeredAnimations(livingEntity, serverLevel, stack);
            }
        }
    }

    protected void triggerShootingAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(
                livingEntity,
                GeoItem.getOrAssignId(stack, serverLevel),
                "weapon_controller",
                "shoot"
        );
    }

    protected void triggerAmmoEmptyAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(
                livingEntity,
                GeoItem.getOrAssignId(stack, serverLevel),
                "weapon_controller",
                "ammoempty"
        );
    }

    public void hardStopTriggeredAnimations(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(
                livingEntity,
                GeoItem.getOrAssignId(stack, serverLevel),
                "weapon_controller",
                "shoot"
        );

        stopTriggeredAnim(
                livingEntity,
                GeoItem.getOrAssignId(stack, serverLevel),
                "weapon_controller",
                "ammoempty"
        );
    }
}
