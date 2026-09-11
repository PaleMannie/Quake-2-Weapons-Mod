package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.ChaingunItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ChaingunRenderer extends GeoItemRenderer<@NotNull ChaingunItem> {
    public ChaingunRenderer() {
        super(new ChaingunModel());
    }
}
