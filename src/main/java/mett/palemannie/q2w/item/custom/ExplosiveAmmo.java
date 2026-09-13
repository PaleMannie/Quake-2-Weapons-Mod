package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

final class ExplosiveAmmo {
    static final TagKey<Item> GRENADES = tag("launcher_grenades");
    static final TagKey<Item> ROCKETS = tag("launcher_rockets");

    private static TagKey<Item> tag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, path));
    }

    private ExplosiveAmmo() {}
}
