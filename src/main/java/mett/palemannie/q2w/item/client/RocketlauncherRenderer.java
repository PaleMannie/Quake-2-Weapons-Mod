package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.RocketlauncherItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RocketlauncherRenderer extends GeoItemRenderer<@NotNull RocketlauncherItem> {
    public RocketlauncherRenderer() {
        super(new RocketlauncherModel());
    }
}
