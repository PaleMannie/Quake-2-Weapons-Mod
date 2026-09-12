package mett.palemannie.q2w.item.client;

import mett.palemannie.q2w.item.custom.ChaingunItem;
import org.jetbrains.annotations.NotNull;

public class ChaingunRenderer extends AnimatedWeaponRenderer<@NotNull ChaingunItem> {
    public ChaingunRenderer() {
        super(new ChaingunModel());
    }
}
