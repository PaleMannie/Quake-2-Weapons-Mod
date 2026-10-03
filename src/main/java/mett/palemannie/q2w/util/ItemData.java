package mett.palemannie.q2w.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Copy-on-write access to the custom data component of an item stack. */
public final class ItemData {
    private ItemData() {}

    public static CompoundTag read(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    public static boolean hasData(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

    public static void putBoolean(ItemStack stack, String key, boolean value) {
        if (read(stack).getBoolean(key) == value) return;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean(key, value));
    }

    public static void remove(ItemStack stack, String key) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(key));
    }
}
