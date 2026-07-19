package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.RocketlauncherItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RocketlauncherRenderer extends GeoItemRenderer<RocketlauncherItem> {
    public RocketlauncherRenderer() {
        super(new RocketlauncherModel());
    }
}
