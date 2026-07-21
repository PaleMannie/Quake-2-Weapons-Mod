package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.ShotgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/shotgun.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/shotgun.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/shotgun.animation.json");

    @Override
    public ResourceLocation getModelResource(ShotgunItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ShotgunItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ShotgunItem animatable) {

        return DEFAULT_ANIM;
    }
}
