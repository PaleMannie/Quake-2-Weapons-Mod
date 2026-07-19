package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.BlasterItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BlasterRenderer extends GeoItemRenderer<BlasterItem> {
    public BlasterRenderer() {
        super(new BlasterModel());
    }
}
