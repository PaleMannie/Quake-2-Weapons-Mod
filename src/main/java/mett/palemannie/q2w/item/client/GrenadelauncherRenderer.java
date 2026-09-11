package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.GrenadelauncherItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadelauncherRenderer extends GeoItemRenderer<@NotNull GrenadelauncherItem> {
    public GrenadelauncherRenderer() {
        super(new GrenadelauncherModel());
    }
}
