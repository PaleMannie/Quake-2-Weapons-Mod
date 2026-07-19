package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.SuperShotgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SuperShotgunRenderer extends GeoItemRenderer<SuperShotgunItem> {
    public SuperShotgunRenderer() {
        super(new SuperShotgunModel());
    }
}
