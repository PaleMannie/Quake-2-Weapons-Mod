package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class HyperblasterModel extends GeoModel<@NotNull HyperblasterItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "hyperblaster");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/hyperblaster.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "hyperblaster");

    @Override
    public @NotNull Identifier getModelResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_MODEL;
    }

    @Override
    public @NotNull Identifier getTextureResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_TEXTURE;
    }

    @Override
    public @NotNull Identifier getAnimationResource(HyperblasterItem animatable) {

        return DEFAULT_ANIM;
    }

    private static final RandomSource RANDOM = RandomSource.create();

    private static final int IDLE_REPEAT_BASE_TICKS = 200;
    private static final int IDLE_REPEAT_VARIATION_TICKS = 60;
    private static final int IDLE_TWITCH_COUNT = 3;
    private static final int IDLE_TWITCH_DURATION_TICKS = 5;
    private static final int IDLE_TWITCH_PAUSE_TICKS = 10;
    private static final float IDLE_TWITCH_AMOUNT = 0.18f;

    private static final float EMPTY_TWITCH_AMOUNT = 0.22f;
    private static final float WEAPON_RECOIL_AMOUNT = 2f;

    private static class VisualState {
        float drumAngle = 0f;
        boolean wasFiring = false;
        boolean returningToOrigin = false;
        float returnStartAngle = 0f;
        float returnTargetAngle = 0f;
        float returnElapsedTicks = 0f;
        float lastRenderTick = -1f;
        float nextIdleTwitchTick = -1f;
        float idleTwitchStartTick = -1f;
    }

    private final java.util.Map<Player, VisualState> visuals = new java.util.WeakHashMap<>();
    private VisualState visual = new VisualState();

    @Override
    public void setCustomAnimations(HyperblasterItem animatable, long instanceId, AnimationState<HyperblasterItem> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone hyperblaster = getAnimationProcessor().getBone("hyperblaster");
        CoreGeoBone drum = getAnimationProcessor().getBone("drum");
        CoreGeoBone fire = getAnimationProcessor().getBone("fire");

        if (hyperblaster == null || drum == null || fire == null) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = WeaponPresentation.holder(animationState.getData(DataTickets.ITEMSTACK));

        if (player == null) {
            visual = new VisualState();
            resetVisuals(hyperblaster, drum, fire);
            return;
        }

        visual = visuals.computeIfAbsent(player, ignored -> new VisualState());
        float renderTick = player.tickCount + minecraft.getFrameTime();
        float deltaTicks = getDeltaTicks(renderTick);

        ItemStack heldStack = player.getMainHandItem();

        boolean holdingThisHyperblaster = heldStack.getItem() == animatable;
        boolean usingThisHyperblaster =
                holdingThisHyperblaster
                        && player.isUsingItem()
                        && player.getUseItem() == heldStack;

        boolean hasAmmo = hasCellAmmo(player);
        boolean firing = usingThisHyperblaster && hasAmmo;
        boolean emptyTryingToFire = usingThisHyperblaster && !hasAmmo;

        int useTicks = 0;

        if (usingThisHyperblaster) {
            useTicks = heldStack.getUseDuration() - player.getUseItemRemainingTicks();
        }

        if (!holdingThisHyperblaster) {
            resetVisuals(hyperblaster, drum, fire);
            visual.wasFiring = false;
            return;
        }

        if (visual.wasFiring && !firing) {
            startReturnToOrigin();
        }

        if (firing) {
            resetIdleTwitchState();
            visual.returningToOrigin = false;

            animateShootingDrum(deltaTicks);
            animateWeaponRecoil(hyperblaster, useTicks, minecraft.getFrameTime());
            animateFire(fire, useTicks, minecraft.getFrameTime());
        } else if (emptyTryingToFire) {
            resetIdleTwitchState();
            visual.returningToOrigin = false;

            animateEmptyClickDrum(drum, useTicks, minecraft.getFrameTime());
            resetWeaponAndFire(hyperblaster, fire);

            visual.wasFiring = firing;
            return;
        } else if (visual.returningToOrigin) {
            animateReturnToOrigin(deltaTicks);
            resetWeaponAndFire(hyperblaster, fire);
        } else {
            resetWeaponAndFire(hyperblaster, fire);
        }

        float idleOffset = 0f;

        if (holdingThisHyperblaster && !firing && !emptyTryingToFire && !visual.returningToOrigin) {
            idleOffset = getIdleTwitchOffset(renderTick);
        }

        drum.setRotZ(visual.drumAngle + idleOffset);

        visual.wasFiring = firing;
    }

    private float getDeltaTicks(float renderTick) {

        if (visual.lastRenderTick < 0f) {
            visual.lastRenderTick = renderTick;
            return 0f;
        }

        float delta = renderTick - visual.lastRenderTick;
        visual.lastRenderTick = renderTick;

        if (delta < 0f || delta > 5f) {
            return 0f;
        }

        return delta;
    }

    private void animateShootingDrum(float deltaTicks) {

        visual.drumAngle += HyperblasterItem.DRUM_SPIN_RADIANS_PER_TICK * deltaTicks;
        visual.drumAngle = wrapNegative(visual.drumAngle);
    }

    private void startReturnToOrigin() {
        visual.returningToOrigin = true;
        visual.returnElapsedTicks = 0f;

        visual.returnStartAngle = wrapNegative(visual.drumAngle);

        if (Math.abs(visual.returnStartAngle) < 0.05f) {
            visual.returnTargetAngle = 0f;
        } else {
            visual.returnTargetAngle = Mth.TWO_PI;
        }
    }

    private void animateReturnToOrigin(float deltaTicks) {

        visual.returnElapsedTicks += deltaTicks;

        float t = Mth.clamp(visual.returnElapsedTicks / HyperblasterItem.DRUM_RETURN_TICKS, 0f, 1f);

        float eased = 1f - (float) Math.pow(1f - t, 3f);

        visual.drumAngle = Mth.lerp(eased, visual.returnStartAngle, visual.returnTargetAngle);

        if (t >= 1f) {
            visual.drumAngle = 0f;
            visual.returningToOrigin = false;
        }
    }

    private void animateWeaponRecoil(CoreGeoBone hyperblaster, int useTicks, float partialTick) {

        float pulse = getShotPulse(useTicks, partialTick);
        hyperblaster.setPosZ(pulse * WEAPON_RECOIL_AMOUNT);
    }

    private void animateFire(CoreGeoBone fire, int useTicks, float partialTick) {

        float pulse = getShotPulse(useTicks, partialTick);

        fire.setScaleX(pulse);
        fire.setScaleY(pulse);
        fire.setScaleZ(pulse);
    }

    private void animateEmptyClickDrum(CoreGeoBone drum, int useTicks, float partialTick) {

        float pulse = getShotPulse(useTicks, partialTick);
        drum.setRotZ(visual.drumAngle + EMPTY_TWITCH_AMOUNT * pulse);
    }

    private float getShotPulse(int useTicks, float partialTick) {

        float cycle = ((useTicks + partialTick) % HyperblasterItem.FIRE_INTERVAL_TICKS)
                / (float) HyperblasterItem.FIRE_INTERVAL_TICKS;

        return 0.5f - 0.5f * Mth.cos(cycle * Mth.TWO_PI);
    }

    private float getIdleTwitchOffset(float renderTick) {

        if (visual.nextIdleTwitchTick < 0f) {
            scheduleNextIdleTwitch(renderTick);
            return 0f;
        }

        if (visual.idleTwitchStartTick < 0f && renderTick >= visual.nextIdleTwitchTick) {
            visual.idleTwitchStartTick = renderTick;
        }

        if (visual.idleTwitchStartTick < 0f) {
            return 0f;
        }

        int pulseBlockTicks = IDLE_TWITCH_DURATION_TICKS + IDLE_TWITCH_PAUSE_TICKS;
        float elapsed = renderTick - visual.idleTwitchStartTick;
        float totalDuration = IDLE_TWITCH_COUNT * IDLE_TWITCH_DURATION_TICKS
                + (IDLE_TWITCH_COUNT - 1) * IDLE_TWITCH_PAUSE_TICKS;

        if (elapsed >= totalDuration) {
            scheduleNextIdleTwitch(renderTick);
            visual.idleTwitchStartTick = -1f;
            return 0f;
        }

        int pulseIndex = (int) (elapsed / pulseBlockTicks);
        float pulseLocalTime = elapsed - pulseIndex * pulseBlockTicks;

        if (pulseIndex >= IDLE_TWITCH_COUNT || pulseLocalTime > IDLE_TWITCH_DURATION_TICKS) {
            return 0f;
        }

        float t = pulseLocalTime / IDLE_TWITCH_DURATION_TICKS;

        return Mth.sin(t * Mth.PI) * IDLE_TWITCH_AMOUNT;
    }

    private void scheduleNextIdleTwitch(float renderTick) {

        int variation = RANDOM.nextInt(IDLE_REPEAT_VARIATION_TICKS * 2 + 1) - IDLE_REPEAT_VARIATION_TICKS;
        visual.nextIdleTwitchTick = renderTick + IDLE_REPEAT_BASE_TICKS + variation;
    }

    private void resetIdleTwitchState() {

        visual.nextIdleTwitchTick = -1f;
        visual.idleTwitchStartTick = -1f;
    }

    private boolean hasCellAmmo(Player player) {
        return WeaponPresentation.hasAmmo(player, ModItems.CELL.get());
    }

    private void resetWeaponAndFire(CoreGeoBone hyperblaster, CoreGeoBone fire) {

        hyperblaster.setPosX(0f);
        hyperblaster.setPosY(0f);
        hyperblaster.setPosZ(0f);

        fire.setScaleX(0f);
        fire.setScaleY(0f);
        fire.setScaleZ(0f);
    }

    private void resetVisuals(CoreGeoBone hyperblaster, CoreGeoBone drum, CoreGeoBone fire) {

        resetWeaponAndFire(hyperblaster, fire);

        visual.drumAngle = 0f;
        drum.setRotX(0f);
        drum.setRotY(0f);
        drum.setRotZ(0f);

        visual.wasFiring = false;
        visual.returningToOrigin = false;
        visual.returnStartAngle = 0f;
        visual.returnTargetAngle = 0f;
        visual.returnElapsedTicks = 0f;
        visual.lastRenderTick = -1f;

        resetIdleTwitchState();
    }

    private float wrapNegative(float angle) {

        angle = angle % Mth.TWO_PI;

        if (angle > 0f) {
            angle -= Mth.TWO_PI;
        }

        return angle;
    }
}
