package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.ChaingunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ChaingunModel extends GeoModel<@NotNull ChaingunItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "chaingun");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/chaingun.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "chaingun");

    @Override
    public @NotNull Identifier getModelResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_MODEL;
    }

    @Override
    public @NotNull Identifier getTextureResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_TEXTURE;
    }

    @Override
    public @NotNull Identifier getAnimationResource(ChaingunItem animatable) {

        return DEFAULT_ANIM;
    }

    private static final int IDLE_TWITCH_INTERVAL_TICKS = 200; // 5 Sekunden
    private static final int IDLE_TWITCH_PULSE_TICKS = 5;

    private static class VisualState {
        float barrelAngle = 0.0F;
        float spinSpeed = 0.0F;
        float releaseSpinSpeed = 0.0F;
        float afterspinRemaining = 0.0F;
        float lastRenderTick = -1.0F;
        float nextIdleTwitchTick = -1.0F;
        float idleTwitchStartTick = -1.0F;
        int idleTwitchPulses = 1;
    }

    private final java.util.Map<Player, VisualState> visuals = new java.util.WeakHashMap<>();
    private VisualState visual = new VisualState();

    @Override
    public void setCustomAnimations(ChaingunItem animatable, long instanceId, AnimationState<ChaingunItem> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone barrels = getAnimationProcessor().getBone("barrels");
        CoreGeoBone fire = getAnimationProcessor().getBone("fire");

        if (barrels == null || fire == null) { return; }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = WeaponPresentation.holder(animationState.getData(DataTickets.ITEMSTACK));

        if (player == null) {
            visual = new VisualState();

            resetVisuals(barrels, fire);
            return;
        }

        visual = visuals.computeIfAbsent(player, ignored -> new VisualState());
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

        if (visual.lastRenderTick < 0.0F) {

            visual.lastRenderTick = renderTick;
            return 0.0F;
        }

        float delta = renderTick - visual.lastRenderTick;
        visual.lastRenderTick = renderTick;

        if (delta < 0.0F || delta > 5.0F) {

            return 0.0F;
        }

        return delta;
    }

    private void updateSpinState(boolean firing, boolean jammed, int useTicks, float deltaTicks) {

        if (firing) {

            visual.spinSpeed = ChaingunItem.getVisualSpinSpeedRadiansPerTick(useTicks);
            visual.releaseSpinSpeed = visual.spinSpeed;
            visual.afterspinRemaining = ChaingunItem.AFTERSPIN_TICKS;
        } else if (jammed) {

            visual.spinSpeed = 0.0F;
            visual.afterspinRemaining = 0.0F;
        } else if (visual.afterspinRemaining > 0.0F) {

            visual.afterspinRemaining = Math.max(0.0F, visual.afterspinRemaining - deltaTicks);
            float t = visual.afterspinRemaining / ChaingunItem.AFTERSPIN_TICKS;
            visual.spinSpeed = visual.releaseSpinSpeed * t * t;
        } else {

            visual.spinSpeed = 0.0F;
        }

        visual.barrelAngle += visual.spinSpeed * deltaTicks;
        visual.barrelAngle = visual.barrelAngle % Mth.TWO_PI;
    }

    private void applyBarrelRotation(CoreGeoBone barrels, float renderTick, boolean holding, boolean firing, boolean jammed) {

        float angle = visual.barrelAngle;

        if (jammed) {

            float jamJitter = Mth.sin(renderTick * 3.8F) * 0.16F;
            float jamSnap = Mth.sin(renderTick * 11.0F) * 0.035F;
            angle += jamJitter + jamSnap;
        } else if (holding && !firing && visual.spinSpeed <= 0.001F && visual.afterspinRemaining <= 0.0F) {

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

    private boolean hasBulletAmmo(Player player) {
        return WeaponPresentation.hasAmmo(player, ModItems.BULLET.get());
    }

    private float getIdleTwitchOffset(float renderTick) {

        if (visual.nextIdleTwitchTick < 0.0F) {

            scheduleNextIdleTwitch(renderTick);
            return 0.0F;
        }

        if (visual.idleTwitchStartTick < 0.0F && renderTick >= visual.nextIdleTwitchTick) {

            visual.idleTwitchStartTick = renderTick;
            visual.idleTwitchPulses = ((int) renderTick / IDLE_TWITCH_INTERVAL_TICKS) % 2 == 0 ? 1 : 2;
        }

        if (visual.idleTwitchStartTick < 0.0F) {
            return 0.0F;
        }

        float elapsed = renderTick - visual.idleTwitchStartTick;
        float totalDuration = visual.idleTwitchPulses * IDLE_TWITCH_PULSE_TICKS;

        if (elapsed >= totalDuration) {

            scheduleNextIdleTwitch(renderTick);
            visual.idleTwitchStartTick = -1.0F;
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
        visual.nextIdleTwitchTick = renderTick + IDLE_TWITCH_INTERVAL_TICKS + variation;
    }

    private void resetIdleTwitchState() {

        visual.nextIdleTwitchTick = -1.0F;
        visual.idleTwitchStartTick = -1.0F;
        visual.idleTwitchPulses = 1;
    }

    private void resetVisuals(CoreGeoBone barrels, CoreGeoBone fire) {

        barrels.setRotZ(0.0F);

        fire.setScaleX(0.0F);
        fire.setScaleY(0.0F);
        fire.setScaleZ(0.0F);

        visual.spinSpeed = 0.0F;
        visual.releaseSpinSpeed = 0.0F;
        visual.afterspinRemaining = 0.0F;
        visual.lastRenderTick = -1.0F;

        resetIdleTwitchState();
    }
}
