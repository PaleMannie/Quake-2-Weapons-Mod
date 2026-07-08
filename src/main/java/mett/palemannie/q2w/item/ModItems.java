package mett.palemannie.q2w.item;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Quake2Weapons.MODID);


    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> CELL = ITEMS.register("cell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SHELL = ITEMS.register("shell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SLUG = ITEMS.register("slug",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
