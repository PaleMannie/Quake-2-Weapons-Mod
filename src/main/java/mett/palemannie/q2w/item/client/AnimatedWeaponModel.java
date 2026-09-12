package mett.palemannie.q2w.item.client;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public abstract class AnimatedWeaponModel<T extends Item & GeoAnimatable> extends GeoModel<T> {
    public record WeaponPose(Map<String, WeaponBonePose> bones) {}
    public static final DataTicket<WeaponPose> WEAPON_POSE = DataTicket.create("q2w_weapon_pose", WeaponPose.class);

    @Override
    public void addAdditionalStateData(T animatable, Object relatedObject, GeoRenderState state) {
        super.addAdditionalStateData(animatable, relatedObject, state);
        if (!(relatedObject instanceof GeoItemRenderer.RenderData renderData)) return;
        var model = getBakedModel(getModelResource(state));
        Map<String, WeaponBonePose> poses = new HashMap<>();
        java.util.function.Function<String, WeaponBonePose> bones = name -> model.getBone(name)
                .map(bone -> poses.computeIfAbsent(name, ignored -> new WeaponBonePose())).orElse(null);
        captureAnimations(animatable, renderData, state, bones);
        state.addGeckolibData(WEAPON_POSE, new WeaponPose(Map.copyOf(poses)));
    }

    protected abstract void captureAnimations(T animatable, GeoItemRenderer.RenderData renderData,
                                              GeoRenderState state, java.util.function.Function<String, WeaponBonePose> bones);
}
