package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.Bfg10kItem;
import mett.palemannie.q2w.item.custom.MachinegunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MachinegunModel extends GeoModel<MachinegunItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "geo/machinegun.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/machinegun.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "animations/machinegun.animation.json");

    @Override
    public ResourceLocation getModelResource(MachinegunItem animatable) {

        return DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(MachinegunItem animatable) {

        return DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MachinegunItem animatable) {

        return DEFAULT_ANIM;
    }
}
