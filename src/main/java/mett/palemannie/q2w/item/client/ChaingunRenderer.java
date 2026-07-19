package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.ChaingunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ChaingunRenderer extends GeoItemRenderer<ChaingunItem> {
    public ChaingunRenderer() {
        super(new ChaingunModel());
    }
}
