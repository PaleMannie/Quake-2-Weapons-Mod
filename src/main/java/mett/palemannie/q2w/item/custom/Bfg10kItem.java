package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.Bfg10kRenderer;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bfg10kItem extends AbstractWeapon {

    public static final int WINDUP_TICKS = 16;

    public static final int FIRE_SEQUENCE_INTERVAL_TICKS = 50;

    public static final int POST_FIRE_END_TICKS = 8;
    public static final int AMMO_COST = 50;

    private static final String BFG_CONTROLLER = "bfg_controller";

    private static final String WINDUP_TRIGGER = "windup";
    private static final String SHOOT_TRIGGER = "shoot";
    private static final String AMMO_EMPTY_TRIGGER = "ammoempty";

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin()
            .then("bfg10k.animation.idle", Animation.LoopType.LOOP);

    private static final RawAnimation WINDUP_ANIM = RawAnimation.begin()
            .then("bfg10k.animation.windup", Animation.LoopType.PLAY_ONCE);

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin()
            .then("bfg10k.animation.shooting", Animation.LoopType.PLAY_ONCE);

    private static final RawAnimation AMMO_EMPTY_ANIM = RawAnimation.begin()
            .then("bfg10k.animation.ammoempty", Animation.LoopType.PLAY_ONCE);

    private final Map<UUID, BfgState> states = new HashMap<>();

    public Bfg10kItem(Properties properties) {
        super(properties);
    }

    @Override
    protected String animationPrefix() {
        return "bfg10k";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new Bfg10kRenderer();
    }

    @Override
    protected int getReleaseCooldownTicks() {
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, BFG_CONTROLLER, 0, state -> {
            state.setAndContinue(IDLE_ANIM);
            return PlayState.CONTINUE;
        })
                .triggerableAnim(WINDUP_TRIGGER, WINDUP_ANIM)
                .triggerableAnim(SHOOT_TRIGGER, SHOOT_ANIM)
                .triggerableAnim(AMMO_EMPTY_TRIGGER, AMMO_EMPTY_ANIM));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        setCurrentHand(usedHand, player);

        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {

            UUID uuid = serverPlayer.getUUID();

            if (!states.containsKey(uuid)) {
                if (!hasEnoughAmmo(serverPlayer)) {
                    onAmmoEmpty(serverLevel, serverPlayer, stack);
                    return InteractionResultHolder.consume(stack);
                }

                startSequence(serverLevel, serverPlayer, stack);
            }
        }

        return InteractionResultHolder.consume(stack);
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {

        super.inventoryTick(stack, level, entity, slot, selected);

        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof ServerPlayer player)) return;

        BfgState state = states.get(player.getUUID());

        if (state == null) {
            return;
        }

        if (!selected && !state.hasFired) {

            states.remove(player.getUUID());
            return;
        }

        tickSequence(serverLevel, player, stack, state);
    }

    private void startSequence(ServerLevel level, ServerPlayer player, ItemStack stack) {

        long now = level.getGameTime();

        BfgState state = new BfgState(now);
        states.put(player.getUUID(), state);

        triggerWindupAnimation(player, level, stack);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BFG10K_WINDUP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    private void restartSequence(ServerLevel level, ServerPlayer player, ItemStack stack, BfgState state) {

        long now = level.getGameTime();

        state.sequenceStartTick = now;
        state.hasFired = false;
        state.fireTick = 0;

        triggerWindupAnimation(player, level, stack);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BFG10K_WINDUP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    private void tickSequence(ServerLevel level, ServerPlayer player, ItemStack stack, BfgState state) {

        long now = level.getGameTime();
        long age = now - state.sequenceStartTick;

        if (!state.hasFired && age >= WINDUP_TICKS) {

            fireBFG(level, player, stack, state);
            return;
        }

        if (state.hasFired) {

            boolean stillHoldingTrigger = player.isUsingItem() && player.getUseItem() == stack && player.getMainHandItem() == stack;

            if (stillHoldingTrigger) {
                if (now - state.sequenceStartTick >= FIRE_SEQUENCE_INTERVAL_TICKS) {
                    if (hasEnoughAmmo(player)) {
                        restartSequence(level, player, stack, state);
                    } else {
                        onAmmoEmpty(level, player, stack);
                        states.remove(player.getUUID());
                    }
                }
            } else {

                if (now - state.fireTick >= POST_FIRE_END_TICKS) {
                    states.remove(player.getUUID());
                }
            }
        }
    }

    private void fireBFG(ServerLevel level, ServerPlayer player, ItemStack stack, BfgState state) {

        if (!consumeAmmo(player, ModItems.CELL.get(), AMMO_COST)) {
            onAmmoEmpty(level, player, stack);
            states.remove(player.getUUID());
            return;
        }

        state.hasFired = true;
        state.fireTick = level.getGameTime();

        triggerShootAnimation(player, level, stack);

        ServerPlayHandler.handleBfg10kShoot(player);

        player.getCooldowns().addCooldown(this, FIRE_SEQUENCE_INTERVAL_TICKS - WINDUP_TICKS);
    }

    private boolean hasEnoughAmmo(Player player) {

        if (player.isCreative()) {
            return true;
        }

        int available = 0;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.is(ModItems.CELL.get())) {
                available += inventoryStack.getCount();
            }
        }

        return available >= AMMO_COST;
    }

    private boolean consumeAmmo(Player player, Item ammoItem, int amount) {

        if (player.isCreative()) {
            return true;
        }

        int available = 0;

        for (ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.is(ammoItem)) {
                available += inventoryStack.getCount();
            }
        }

        if (available < amount) {
            return false;
        }

        int remaining = amount;

        for (ItemStack inventoryStack : player.getInventory().items) {

            if (!inventoryStack.is(ammoItem)) continue;

            int removed = Math.min(remaining, inventoryStack.getCount());
            inventoryStack.shrink(removed);
            remaining -= removed;

            if (remaining <= 0) {

                player.getInventory().setChanged();
                return true;
            }
        }

        player.getInventory().setChanged();
        return true;
    }

    private void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {

        ServerPlayHandler.playAmmoEmptySound(player);
        triggerAmmoEmptyAnimation(player, level, stack);
    }

    private void triggerWindupAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), BFG_CONTROLLER, WINDUP_TRIGGER);
    }

    private void triggerShootAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), BFG_CONTROLLER, SHOOT_TRIGGER);
    }

    public void triggerAmmoEmptyAnimation(LivingEntity entity, ServerLevel level, ItemStack stack) {
        triggerAnim(entity, GeoItem.getOrAssignId(stack, level), BFG_CONTROLLER, AMMO_EMPTY_TRIGGER);
    }

    private static class BfgState {

        private long sequenceStartTick;

        private boolean hasFired = false;
        private long fireTick = 0;

        private BfgState(long sequenceStartTick) {
            this.sequenceStartTick = sequenceStartTick;
        }
    }
}