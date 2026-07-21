package mett.palemannie.q2w.item;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.BlasterItem;
import mett.palemannie.q2w.item.custom.ShotgunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Quake2Weapons.MODID);

    /// Ammo

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

    /// Weapons

    public static final RegistryObject<Item> BLASTER = ITEMS.register("blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new ShotgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUPER_SHOTGUN = ITEMS.register("super_shotgun",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MACHINEGUN = ITEMS.register("machinegun",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHAINGUN = ITEMS.register("chaingun",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GRENADELAUNCHER = ITEMS.register("grenadelauncher",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> HYPERBLASTER = ITEMS.register("hyperblaster",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RAILGUN = ITEMS.register("railgun",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BFG10K = ITEMS.register("bfg10k",
            () -> new Item(new Item.Properties().stacksTo(1)));

    /// Powerup Items

    public static final RegistryObject<Item> QUAD_DAMAGE_ITEM = ITEMS.register("quad_damage_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> INVULN_ITEM = ITEMS.register("invuln_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ENVIROSUIT_ITEM = ITEMS.register("envirosuit_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> REBREATHER_ITEM = ITEMS.register("rebreather_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SILENCER_ITEM = ITEMS.register("silencer_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ADRENALINE_ITEM = ITEMS.register("adrenaline_item",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> POWER_SHIELD = ITEMS.register("power_shield",
            () -> new Item(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
