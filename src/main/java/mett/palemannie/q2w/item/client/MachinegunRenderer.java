package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.MachinegunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MachinegunRenderer extends GeoItemRenderer<MachinegunItem> {
    public MachinegunRenderer() {
        super(new MachinegunModel());
    }
}
