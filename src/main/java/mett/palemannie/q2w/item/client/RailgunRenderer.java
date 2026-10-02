package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.RailgunItem;
import org.jetbrains.annotations.NotNull;
import com.geckolib.renderer.GeoItemRenderer;

public class RailgunRenderer extends GeoItemRenderer<@NotNull RailgunItem> {
    public RailgunRenderer() {
        super(new RailgunModel());
    }
}
