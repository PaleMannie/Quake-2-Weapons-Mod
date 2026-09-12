package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.HyperblasterItem;
import org.jetbrains.annotations.NotNull;

public class HyperblasterRenderer extends AnimatedWeaponRenderer<@NotNull HyperblasterItem> {
    public HyperblasterRenderer() {
        super(new HyperblasterModel());
    }
}
