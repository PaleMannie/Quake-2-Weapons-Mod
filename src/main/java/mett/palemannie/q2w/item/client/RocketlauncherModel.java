package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.GrenadelauncherItem;
import mett.palemannie.q2w.item.custom.RocketlauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RocketlauncherModel extends GeoModel<RocketlauncherItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/rocketlauncher.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/rocketlauncher.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/rocketlauncher.animation.json");

    @Override
    public ResourceLocation getModelResource(RocketlauncherItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(RocketlauncherItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(RocketlauncherItem animatable) {

        return DEFAULT_ANIM;
    }
}
