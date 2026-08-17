package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class HyperblasterModel extends GeoModel<HyperblasterItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/hyperblaster.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/hyperblaster.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/hyperblaster.animation.json");

    @Override
    public ResourceLocation getModelResource(HyperblasterItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(HyperblasterItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(HyperblasterItem animatable) {

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

    private float drumAngle = 0f;

    private boolean wasFiring = false;
    private boolean returningToOrigin = false;

    private float returnStartAngle = 0f;
    private float returnTargetAngle = 0f;
    private float returnElapsedTicks = 0f;

    private float lastRenderTick = -1f;

    private float nextIdleTwitchTick = -1f;
    private float idleTwitchStartTick = -1f;

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
        LocalPlayer player = minecraft.player;

        if (player == null) {
            resetVisuals(hyperblaster, drum, fire);
            return;
        }

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
            wasFiring = false;
            return;
        }

        if (wasFiring && !firing) {
            startReturnToOrigin();
        }

        if (firing) {
            resetIdleTwitchState();
            returningToOrigin = false;

            animateShootingDrum(deltaTicks);
            animateWeaponRecoil(hyperblaster, useTicks, minecraft.getFrameTime());
            animateFire(fire, useTicks, minecraft.getFrameTime());
        } else if (emptyTryingToFire) {
            resetIdleTwitchState();
            returningToOrigin = false;

            animateEmptyClickDrum(drum, useTicks, minecraft.getFrameTime());
            resetWeaponAndFire(hyperblaster, fire);

            wasFiring = firing;
            return;
        } else if (returningToOrigin) {
            animateReturnToOrigin(deltaTicks);
            resetWeaponAndFire(hyperblaster, fire);
        } else {
            resetWeaponAndFire(hyperblaster, fire);
        }

        float idleOffset = 0f;

        if (holdingThisHyperblaster && !firing && !emptyTryingToFire && !returningToOrigin) {
            idleOffset = getIdleTwitchOffset(renderTick);
        }

        drum.setRotZ(drumAngle + idleOffset);

        wasFiring = firing;
    }

    private float getDeltaTicks(float renderTick) {

        if (lastRenderTick < 0f) {
            lastRenderTick = renderTick;
            return 0f;
        }

        float delta = renderTick - lastRenderTick;
        lastRenderTick = renderTick;

        if (delta < 0f || delta > 5f) {
            return 0f;
        }

        return delta;
    }

    private void animateShootingDrum(float deltaTicks) {

        drumAngle += HyperblasterItem.DRUM_SPIN_RADIANS_PER_TICK * deltaTicks;
        drumAngle = wrapNegative(drumAngle);
    }

    private void startReturnToOrigin() {
        returningToOrigin = true;
        returnElapsedTicks = 0f;

        returnStartAngle = wrapNegative(drumAngle);

        if (Math.abs(returnStartAngle) < 0.05f) {
            returnTargetAngle = 0f;
        } else {
            returnTargetAngle = Mth.TWO_PI;
        }
    }

    private void animateReturnToOrigin(float deltaTicks) {

        returnElapsedTicks += deltaTicks;

        float t = Mth.clamp(returnElapsedTicks / HyperblasterItem.DRUM_RETURN_TICKS, 0f, 1f);

        float eased = 1f - (float) Math.pow(1f - t, 3f);

        drumAngle = Mth.lerp(eased, returnStartAngle, returnTargetAngle);

        if (t >= 1f) {
            drumAngle = 0f;
            returningToOrigin = false;
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
        drum.setRotZ(drumAngle + EMPTY_TWITCH_AMOUNT * pulse);
    }

    private float getShotPulse(int useTicks, float partialTick) {

        float cycle = ((useTicks + partialTick) % HyperblasterItem.FIRE_INTERVAL_TICKS)
                / (float) HyperblasterItem.FIRE_INTERVAL_TICKS;

        return 0.5f - 0.5f * Mth.cos(cycle * Mth.TWO_PI);
    }

    private float getIdleTwitchOffset(float renderTick) {

        if (nextIdleTwitchTick < 0f) {
            scheduleNextIdleTwitch(renderTick);
            return 0f;
        }

        if (idleTwitchStartTick < 0f && renderTick >= nextIdleTwitchTick) {
            idleTwitchStartTick = renderTick;
        }

        if (idleTwitchStartTick < 0f) {
            return 0f;
        }

        int pulseBlockTicks = IDLE_TWITCH_DURATION_TICKS + IDLE_TWITCH_PAUSE_TICKS;
        float elapsed = renderTick - idleTwitchStartTick;
        float totalDuration = IDLE_TWITCH_COUNT * IDLE_TWITCH_DURATION_TICKS
                + (IDLE_TWITCH_COUNT - 1) * IDLE_TWITCH_PAUSE_TICKS;

        if (elapsed >= totalDuration) {
            scheduleNextIdleTwitch(renderTick);
            idleTwitchStartTick = -1f;
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
        nextIdleTwitchTick = renderTick + IDLE_REPEAT_BASE_TICKS + variation;
    }

    private void resetIdleTwitchState() {

        nextIdleTwitchTick = -1f;
        idleTwitchStartTick = -1f;
    }

    private boolean hasCellAmmo(LocalPlayer player) {

        if (player.isCreative()) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                return true;
            }
        }

        return false;
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

        drumAngle = 0f;
        drum.setRotX(0f);
        drum.setRotY(0f);
        drum.setRotZ(0f);

        wasFiring = false;
        returningToOrigin = false;
        returnStartAngle = 0f;
        returnTargetAngle = 0f;
        returnElapsedTicks = 0f;
        lastRenderTick = -1f;

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
