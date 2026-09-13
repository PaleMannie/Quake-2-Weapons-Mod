package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

final class ShellAmmo {
    static final TagKey<Item> TAG = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, "shotgun_shells"));

    private ShellAmmo() {}
}
