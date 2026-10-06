package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

final class QWAmmoTags {
    static final TagKey<Item> SHELLS = tag("q2w_shotgun_shells");
    static final TagKey<Item> GRENADES = tag("q2w_launcher_grenades");
    static final TagKey<Item> ROCKETS = tag("q2w_launcher_rockets");

    private static TagKey<Item> tag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, path));
    }

    private QWAmmoTags() {}
}
