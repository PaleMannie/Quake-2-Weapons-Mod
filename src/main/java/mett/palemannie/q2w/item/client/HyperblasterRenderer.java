package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.HandgrenadeItem;
import mett.palemannie.q2w.item.custom.HyperblasterItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HyperblasterRenderer extends GeoItemRenderer<HyperblasterItem> {
    public HyperblasterRenderer() {
        super(new HyperblasterModel());
    }
}
