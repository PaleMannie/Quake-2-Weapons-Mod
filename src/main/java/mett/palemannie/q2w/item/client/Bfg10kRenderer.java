package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.Bfg10kItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Bfg10kRenderer extends GeoItemRenderer<Bfg10kItem> {
    public Bfg10kRenderer() {
        super(new Bfg10kModel());
    }
}
