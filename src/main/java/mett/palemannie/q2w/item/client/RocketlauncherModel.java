package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.RocketlauncherItem;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RocketlauncherModel extends GeoModel<@NotNull RocketlauncherItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rocketlauncher");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "textures/item/rocketlauncher.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rocketlauncher");

    @Override
    public @NotNull Identifier getModelResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_MODEL;
    }

    @Override
    public @NotNull Identifier getTextureResource(@NotNull GeoRenderState geoRenderState) {
        return DEFAULT_TEXTURE;
    }

    @Override
    public @NotNull Identifier getAnimationResource(RocketlauncherItem animatable) {

        return DEFAULT_ANIM;
    }
}
