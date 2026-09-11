package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.HandgrenadeItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HandgrenadeRenderer extends GeoItemRenderer<@NotNull HandgrenadeItem> {
    public HandgrenadeRenderer() {
        super(new HandgrenadeModel());
    }
}
