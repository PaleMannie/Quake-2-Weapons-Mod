package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.HyperblasterItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HyperblasterRenderer extends GeoItemRenderer<@NotNull HyperblasterItem> {
    public HyperblasterRenderer() {
        super(new HyperblasterModel());
    }
}
