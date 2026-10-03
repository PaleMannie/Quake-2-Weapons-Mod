package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.util.ItemData;
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
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
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
                .then(shootingAnimationName(), Animation.LoopType.PLAY_ONCE);

        RawAnimation ammoEmptyAnim = RawAnimation.begin()
                .then(ammoEmptyAnimationName(), Animation.LoopType.PLAY_ONCE);

        RawAnimation idleAnim = RawAnimation.begin()
                .then(idleAnimationName(), Animation.LoopType.LOOP);

        controllers.add(new AnimationController<>(this, "weapon_controller", 0, state -> {
            state.setAndContinue(idleAnim);
            return PlayState.CONTINUE;
        })
                .triggerableAnim("shoot", shootingAnim)
                .triggerableAnim("ammoempty", ammoEmptyAnim));
    }

    public void setCurrentHand(InteractionHand hand, LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getItemInHand(hand);

        if (!itemStack.isEmpty() && !livingEntity.isUsingItem()) {
            livingEntity.useItem = itemStack;
            livingEntity.useItemRemaining = itemStack.getUseDuration(livingEntity);

            if (!livingEntity.level().isClientSide()) {
                livingEntity.setLivingEntityFlag(1, true);
                livingEntity.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                livingEntity.gameEvent(GameEvent.ITEM_INTERACT_START);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 2_000_000_000;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(usedHand));
        }

        setCurrentHand(usedHand, player);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration);

    protected abstract void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        executeWeaponFire(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);

        int releaseCooldown = getReleaseCooldownTicks();

        // A zero-length entry replaces an already active cooldown for this item.
        // The BFG uses its own post-shot cooldown, so releasing it must not erase it.
        if (releaseCooldown > 0 && livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(this, releaseCooldown);
        }

        afterShooting(stack, level, livingEntity, timeCharged);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (!(level instanceof ServerLevel serverLevel)) return;
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
            ItemData.putBoolean(stack, "Q2WHasAmmo", hasAmmo);
        }

        if (selected && entity instanceof net.minecraft.server.level.ServerPlayer player
                && (this instanceof ChaingunItem || this instanceof HyperblasterItem
                    || this instanceof RailgunItem || this instanceof Bfg10kItem)) {
            ItemData.putBoolean(stack, "Q2WSilenced",
                    mett.palemannie.q2w.util.WeaponAggroHandler.hasSilencerActive(player));
        }

        if (ItemData.hasData(stack) && ItemData.read(stack).getBoolean("WasDropped")) {
            hardStopTriggeredAnimations(livingEntity, serverLevel, stack);
            ItemData.remove(stack, "WasDropped");
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
