package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.ChaingunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class ChaingunModel extends GeoModel<ChaingunItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/chaingun.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/chaingun.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/chaingun.animation.json");

    @Override
    public ResourceLocation getModelResource(ChaingunItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ChaingunItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ChaingunItem animatable) {

        return DEFAULT_ANIM;
    }

    private float barrelAngle = 0.0F;
    private float spinSpeed = 0.0F;
    private float releaseSpinSpeed = 0.0F;
    private float afterspinRemaining = 0.0F;
    private float lastRenderTick = -1.0F;

    private static final int IDLE_TWITCH_INTERVAL_TICKS = 200; // 5 Sekunden
    private static final int IDLE_TWITCH_PULSE_TICKS = 5;

    private float nextIdleTwitchTick = -1.0F;
    private float idleTwitchStartTick = -1.0F;
    private int idleTwitchPulses = 1;

    @Override
    public void setCustomAnimations(ChaingunItem animatable, long instanceId, AnimationState<ChaingunItem> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone barrels = getAnimationProcessor().getBone("barrels");
        CoreGeoBone fire = getAnimationProcessor().getBone("fire");

        if (barrels == null || fire == null) { return; }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {

            resetVisuals(barrels, fire);
            return;
        }

        float renderTick = player.tickCount + minecraft.getFrameTime();
        float deltaTicks = getDeltaTicks(renderTick);

        ItemStack heldStack = player.getMainHandItem();

        boolean holdingThisChaingun = heldStack.getItem() == animatable;
        boolean usingThisChaingun =
                holdingThisChaingun
                        && player.isUsingItem()
                        && player.getUseItem() == heldStack;

        boolean hasAmmo = hasBulletAmmo(player);
        boolean firing = usingThisChaingun && hasAmmo;
        boolean jammed = usingThisChaingun && !hasAmmo;

        int useTicks = 0;

        if (usingThisChaingun) {

            useTicks = heldStack.getUseDuration() - player.getUseItemRemainingTicks();
        }

        updateSpinState(firing, jammed, useTicks, deltaTicks);
        applyBarrelRotation(barrels, renderTick, holdingThisChaingun, firing, jammed);
        applyFireScale(fire, firing, useTicks, minecraft.getFrameTime());
    }

    private float getDeltaTicks(float renderTick) {

        if (lastRenderTick < 0.0F) {

            lastRenderTick = renderTick;
            return 0.0F;
        }

        float delta = renderTick - lastRenderTick;
        lastRenderTick = renderTick;

        if (delta < 0.0F || delta > 5.0F) {

            return 0.0F;
        }

        return delta;
    }

    private void updateSpinState(boolean firing, boolean jammed, int useTicks, float deltaTicks) {

        if (firing) {

            spinSpeed = ChaingunItem.getVisualSpinSpeedRadiansPerTick(useTicks);
            releaseSpinSpeed = spinSpeed;
            afterspinRemaining = ChaingunItem.AFTERSPIN_TICKS;
        } else if (jammed) {

            spinSpeed = 0.0F;
            afterspinRemaining = 0.0F;
        } else if (afterspinRemaining > 0.0F) {

            afterspinRemaining = Math.max(0.0F, afterspinRemaining - deltaTicks);
            float t = afterspinRemaining / ChaingunItem.AFTERSPIN_TICKS;
            spinSpeed = releaseSpinSpeed * t * t;
        } else {

            spinSpeed = 0.0F;
        }

        barrelAngle += spinSpeed * deltaTicks;
        barrelAngle = barrelAngle % Mth.TWO_PI;
    }

    private void applyBarrelRotation(CoreGeoBone barrels, float renderTick, boolean holding, boolean firing, boolean jammed) {

        float angle = barrelAngle;

        if (jammed) {

            float jamJitter = Mth.sin(renderTick * 3.8F) * 0.16F;
            float jamSnap = Mth.sin(renderTick * 11.0F) * 0.035F;
            angle += jamJitter + jamSnap;
        } else if (holding && !firing && spinSpeed <= 0.001F && afterspinRemaining <= 0.0F) {

            angle += getIdleTwitchOffset(renderTick);
        } else {

            resetIdleTwitchState();
        }


        barrels.setRotZ(-angle);
    }

    private void applyFireScale(CoreGeoBone fire, boolean firing, int useTicks, float partialTick) {

        if (!firing) {

            fire.setScaleX(0.0F);
            fire.setScaleY(0.0F);
            fire.setScaleZ(0.0F);
            return;
        }

        float cycle = ((useTicks + partialTick) % ChaingunItem.FIRE_INTERVAL_TICKS) / (float) ChaingunItem.FIRE_INTERVAL_TICKS;

        float scale = 1.0F - cycle;

        fire.setScaleX(scale);
        fire.setScaleY(scale);
        fire.setScaleZ(scale);
    }

    private boolean hasBulletAmmo(LocalPlayer player) {

        if (player.isCreative()) {

            return true;
        }

        for (ItemStack stack : player.getInventory().items) {

            if (stack.is(ModItems.BULLET.get())) {

                return true;
            }
        }

        return false;
    }

    private float getIdleTwitchOffset(float renderTick) {

        if (nextIdleTwitchTick < 0.0F) {

            scheduleNextIdleTwitch(renderTick);
            return 0.0F;
        }

        if (idleTwitchStartTick < 0.0F && renderTick >= nextIdleTwitchTick) {

            idleTwitchStartTick = renderTick;
            idleTwitchPulses = ((int) renderTick / IDLE_TWITCH_INTERVAL_TICKS) % 2 == 0 ? 1 : 2;
        }

        if (idleTwitchStartTick < 0.0F) {
            return 0.0F;
        }

        float elapsed = renderTick - idleTwitchStartTick;
        float totalDuration = idleTwitchPulses * IDLE_TWITCH_PULSE_TICKS;

        if (elapsed >= totalDuration) {

            scheduleNextIdleTwitch(renderTick);
            idleTwitchStartTick = -1.0F;
            return 0.0F;
        }

        int pulseIndex = (int) (elapsed / IDLE_TWITCH_PULSE_TICKS);
        float pulseTime = elapsed - pulseIndex * IDLE_TWITCH_PULSE_TICKS;
        float t = pulseTime / IDLE_TWITCH_PULSE_TICKS;

        float twitch = Mth.sin(t * Mth.PI) * 0.22F;

        if (pulseIndex == 1) {

            twitch *= 0.65F;
        }

        return twitch;
    }

    private void scheduleNextIdleTwitch(float renderTick) {

        float variation = ((int) renderTick % 37) - 18;
        nextIdleTwitchTick = renderTick + IDLE_TWITCH_INTERVAL_TICKS + variation;
    }

    private void resetIdleTwitchState() {

        nextIdleTwitchTick = -1.0F;
        idleTwitchStartTick = -1.0F;
        idleTwitchPulses = 1;
    }

    private void resetVisuals(CoreGeoBone barrels, CoreGeoBone fire) {

        barrels.setRotZ(0.0F);

        fire.setScaleX(0.0F);
        fire.setScaleY(0.0F);
        fire.setScaleZ(0.0F);

        spinSpeed = 0.0F;
        releaseSpinSpeed = 0.0F;
        afterspinRemaining = 0.0F;
        lastRenderTick = -1.0F;

        resetIdleTwitchState();
    }
}
