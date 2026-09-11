package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.MachinegunItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MachinegunRenderer extends GeoItemRenderer<@NotNull MachinegunItem> {
    public MachinegunRenderer() {
        super(new MachinegunModel());
    }
}
