package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.BlasterItem;
import org.jetbrains.annotations.NotNull;
import com.geckolib.renderer.GeoItemRenderer;

public class BlasterRenderer extends GeoItemRenderer<@NotNull BlasterItem> {
    public BlasterRenderer() {
        super(new BlasterModel());
    }
}
