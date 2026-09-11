package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.ShotgunItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<@NotNull ShotgunItem> {
    public ShotgunRenderer() {
        super(new ShotgunModel());
    }
}
