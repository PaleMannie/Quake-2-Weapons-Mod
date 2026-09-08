package mett.palemannie.q2w.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
        return slotChanged;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 2_000_000_000;
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
            stack.getOrCreateTag().putBoolean("Q2WHasAmmo", hasAmmo);
            stack.getOrCreateTag().putBoolean("Q2WSilenced",
                    mett.palemannie.q2w.util.WeaponAggroHandler.hasSilencerActive(player));
        }

        if (stack.hasTag() && stack.getTag().getBoolean("WasDropped")) {
            hardStopTriggeredAnimations(livingEntity, serverLevel, stack);
            stack.getTag().remove("WasDropped");
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
