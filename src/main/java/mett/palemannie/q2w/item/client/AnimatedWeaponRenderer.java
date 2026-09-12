package mett.palemannie.q2w.item.client;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class AnimatedWeaponRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
    public AnimatedWeaponRenderer(AnimatedWeaponModel<T> model) {
        super(model);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<GeoRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        var pose = renderPassInfo.renderState().getGeckolibData(AnimatedWeaponModel.WEAPON_POSE);
        if (pose == null) return;
        pose.bones().forEach((name, source) -> snapshots.ifPresent(name, source::applyTo));
    }
}
