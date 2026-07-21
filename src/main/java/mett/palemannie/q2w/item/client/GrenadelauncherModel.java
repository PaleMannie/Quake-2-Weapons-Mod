package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.Bfg10kItem;
import mett.palemannie.q2w.item.custom.GrenadelauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/grenadelauncher.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/grenadelauncher.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/grenadelauncher.animation.json");

    @Override
    public ResourceLocation getModelResource(GrenadelauncherItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GrenadelauncherItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return DEFAULT_ANIM;
    }
}
