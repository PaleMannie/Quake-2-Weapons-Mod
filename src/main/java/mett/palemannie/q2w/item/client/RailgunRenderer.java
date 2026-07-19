package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.BlasterItem;
import mett.palemannie.q2w.item.custom.RailgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RailgunRenderer extends GeoItemRenderer<RailgunItem> {
    public RailgunRenderer() {
        super(new RailgunModel());
    }
}
