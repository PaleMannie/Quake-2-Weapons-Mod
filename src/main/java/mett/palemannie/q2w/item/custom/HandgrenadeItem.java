package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.HandgrenadeRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ServerPlayHandler;
import mett.palemannie.q2w.util.WeaponAggroHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.LoopType;
import com.geckolib.animation.object.PlayState;
import com.geckolib.renderer.GeoItemRenderer;

import java.util.WeakHashMap;
import java.util.Map;

import java.util.function.Consumer;

public class HandgrenadeItem extends AbstractWeapon {

    public HandgrenadeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private HandgrenadeRenderer renderer;

            @Override
            public GeoItemRenderer<@NotNull HandgrenadeItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new HandgrenadeRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.GRENADE.get();
    }


    public static final int FUSE_TICKS = 84;
    public static final int PIN_SOUND_TICK = 5;
    public static final int COOK_START_TICK = 11;

    public static final int THROW_PROJECTILE_DELAY_TICKS = 4;

    private static final int RELEASE_COOLDOWN_TICKS = 30;

    private static final float MIN_THROW_VELOCITY = 0.65f;
    private static final float MAX_THROW_VELOCITY = 1.1f;

    private static final String HANDGRENADE_CONTROLLER = "handgrenade_controller";
    private static final String PRIME_TRIGGER = "prime";
    private static final String THROW_TRIGGER = "throw";

    private static final String OVERCOOK_TRIGGER = "overcook";

    private static final RawAnimation PRIME_ANIM = RawAnimation.begin()
            .then("handgrenade.animation.prime", LoopType.PLAY_ONCE);

    private static final RawAnimation THROW_ANIM = RawAnimation.begin()
            .then("handgrenade.animation.throw", LoopType.PLAY_ONCE);

    private static final RawAnimation OVERCOOK_ANIM = RawAnimation.begin()
            .then("handgrenade.animation.overcook", LoopType.PLAY_ONCE);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin()
            .then("handgrenade.animation.idle", LoopType.LOOP);

    private final Map<ServerPlayer, GrenadeState> states = new WeakHashMap<>();

    @Override
    protected String animationPrefix() {
        return "handgrenade";
    }

    @Override
    protected int getReleaseCooldownTicks() {
        return RELEASE_COOLDOWN_TICKS;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(HANDGRENADE_CONTROLLER, 0, state -> {
            // GeckoLib 5.4.5 sets timelineTime to -2 at the end, so the
            // controller's hasAnimationFinished() stays false. Check the frame.
            var point = state.controller().getCurrentAnimationPoint();
            if (point != null && point.hasFinished()) {
                state.controller().stopTriggeredAnimation();
            } else if (state.controller().isTriggeredAnimation(PRIME_TRIGGER) || state.controller().isTriggeredAnimation(THROW_TRIGGER) || state.controller().isTriggeredAnimation(OVERCOOK_TRIGGER)) {
                return PlayState.CONTINUE;
            }
            return state.setAndContinue(IDLE_ANIM);
        })
                .receiveTriggeredAnimations()
                .triggerableAnim(PRIME_TRIGGER, PRIME_ANIM)
                .triggerableAnim(THROW_TRIGGER, THROW_ANIM)
                .triggerableAnim(OVERCOOK_TRIGGER, OVERCOOK_ANIM));
    }

    private static final String OLD_STACK_ID_TAG = "Q2WHandgrenadeStackId";
    private static final String GECKOLIB_ID_TAG = "GeckoLibID";

    private static void cleanStackingTags(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        mett.palemannie.q2w.util.ItemData.remove(stack, OLD_STACK_ID_TAG);
        mett.palemannie.q2w.util.ItemData.remove(stack, GECKOLIB_ID_TAG);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        cleanStackingTags(stack);

        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }

        if (player instanceof ServerPlayer serverPlayer && states.containsKey(serverPlayer)) {
            return InteractionResult.FAIL;
        }

        if (!player.isCreative() && stack.isEmpty()) {
            return InteractionResult.FAIL;
        }

        setCurrentHand(usedHand, player);

        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            startGrenadeUse(serverLevel, serverPlayer, stack, usedHand);
        }

        return InteractionResult.CONSUME;
    }

    private void startGrenadeUse(ServerLevel level, ServerPlayer player, ItemStack stack, InteractionHand hand) {
        int slot = player.getInventory().selected;

        GrenadeState state = new GrenadeState(stack, level, slot, level.getGameTime());

        states.put(player, state);

        triggerPrimeAnimation(player, level, stack);
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);

        if (level.isClientSide()) {
            return false;
        }

        if (!(livingEntity instanceof ServerPlayer player)) {
            cleanStackingTags(stack);
            return false;
        }

        GrenadeState state = states.get(player);

        if (state == null) {
            cleanStackingTags(stack);
            return false;
        }

        state.releaseRequested = true;
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (entity instanceof ServerPlayer player) {
            GrenadeState state = states.get(player);
            if (state == null || state.stack != stack) cleanStackingTags(stack);
        }
    }

    /** Advance once per player tick, even when the original inventory slot is empty. */
    public void tickActiveGrenade(ServerPlayer player) {
        GrenadeState state = states.get(player);
        if (state == null) return;

        // A removed stack, death, or dimension change must never leave a permanent use lock.
        boolean ownsStack = player.getInventory().items.stream().anyMatch(stack -> stack == state.stack)
                || player.getOffhandItem() == state.stack;
        if (!player.isAlive() || player.level() != state.level || state.stack.isEmpty()
                || !state.stack.is(this) || !ownsStack) {
            states.remove(player);
            if (player.getUseItem() == state.stack) player.stopUsingItem();
            cleanStackingTags(state.stack);
            return;
        }

        boolean stillUsing = player.getInventory().selected == state.slot
                && player.getMainHandItem() == state.stack
                && player.isUsingItem() && player.getUseItem() == state.stack;
        if (!stillUsing) state.releaseRequested = true;
        tickGrenadeState(state.level, player, state.stack, state);
    }

    private void tickGrenadeState(ServerLevel level, ServerPlayer player, ItemStack stack, GrenadeState state) {

        long now = level.getGameTime();
        int age = (int) (now - state.startTick);

        if (!state.primeSoundPlayed && age >= PIN_SOUND_TICK) {

            state.primeSoundPlayed = true;
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HANDGRENADE_START.get(), SoundSource.PLAYERS, ServerPlayHandler.weaponSoundVolume(player, 1f), 1f);
        }

        if (!state.fuseStarted && age >= COOK_START_TICK) {

            state.fuseStarted = true;
            state.fuseStartTick = now;
        }

        if (state.fuseStarted && now - state.fuseStartTick >= FUSE_TICKS) {

            overcookInHand(level, player, stack);
            return;
        }

        if (state.releaseRequested && !state.throwStarted && age >= COOK_START_TICK) {

            state.throwStarted = true;
            state.throwStartTick = now;

            triggerThrowAnimation(player, level, stack);
        }

        if (state.throwStarted && now - state.throwStartTick >= THROW_PROJECTILE_DELAY_TICKS) {
            throwGrenade(level, player, stack, state);
        }
    }

    private void throwGrenade(ServerLevel level, ServerPlayer player, ItemStack stack, GrenadeState state) {

        if (!consumeOneGrenade(player, stack)) {

            ServerPlayHandler.playAmmoEmptySound(player);
            states.remove(player);
            return;
        }

        long now = level.getGameTime();

        int cookedTicks = 0;

        if (state.fuseStarted) {
            cookedTicks = (int) (now - state.fuseStartTick);
        }

        int remainingFuseTicks = Math.max(1, FUSE_TICKS - cookedTicks);

        float cookProgress = Mth.clamp(cookedTicks / (float) FUSE_TICKS, 0f, 1f);
        float velocity = Mth.lerp(cookProgress, MIN_THROW_VELOCITY, MAX_THROW_VELOCITY);

        ServerPlayHandler.handleHandgrenadeThrow(player, remainingFuseTicks, velocity);
        WeaponAggroHandler.onWeaponShot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                0f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f), player);


        player.getCooldowns().addCooldown(stack, RELEASE_COOLDOWN_TICKS);
        if (player.getUseItem() == stack) player.stopUsingItem();

        states.remove(player);
        cleanStackingTags(stack);
    }

    private void overcookInHand(ServerLevel level, ServerPlayer player, ItemStack stack) {

        consumeOneGrenade(player, stack);

        triggerOvercookAnimation(player, level, stack);

        ServerPlayHandler.handleHandgrenadeOvercook(player);
        WeaponAggroHandler.onWeaponShot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                0f,
                player.getRandom().nextBoolean() ? 30f : -30f,
                0f), player);

        player.getCooldowns().addCooldown(stack, RELEASE_COOLDOWN_TICKS);
        if (player.getUseItem() == stack) player.stopUsingItem();

        states.remove(player);
        cleanStackingTags(stack);
    }

    private boolean consumeOneGrenade(Player player, ItemStack stack) {
        if (player.isCreative()) {
            return true;
        }

        if (stack.isEmpty() || stack.getItem() != this) {
            return false;
        }

        stack.shrink(1);
        return true;
    }

    private void triggerPrimeAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), HANDGRENADE_CONTROLLER, PRIME_TRIGGER);
    }

    private void triggerThrowAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), HANDGRENADE_CONTROLLER, THROW_TRIGGER);
    }

    private void triggerOvercookAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), HANDGRENADE_CONTROLLER, OVERCOOK_TRIGGER);
    }

    private static class GrenadeState {
        private final ItemStack stack;
        private final ServerLevel level;
        private final int slot;
        private final long startTick;

        private boolean releaseRequested = false;
        private boolean throwStarted = false;
        private long throwStartTick = 0L;

        private boolean fuseStarted = false;
        private long fuseStartTick = 0L;

        private boolean primeSoundPlayed = false;

        private GrenadeState(ItemStack stack, ServerLevel level, int slot, long startTick) {
            this.stack = stack;
            this.level = level;
            this.slot = slot;
            this.startTick = startTick;
        }
    }
}
