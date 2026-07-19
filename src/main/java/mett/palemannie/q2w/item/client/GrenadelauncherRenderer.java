package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.GrenadelauncherItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadelauncherRenderer extends GeoItemRenderer<GrenadelauncherItem> {
    public GrenadelauncherRenderer() {
        super(new GrenadelauncherModel());
    }
}
