package mett.palemannie.q2w.item;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Quake2Weapons.MODID);

    /// Ammo

    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new Item(new Item.Properties().setId(ITEMS.key("bullet")).stacksTo(64)));

    public static final RegistryObject<Item> CELL = ITEMS.register("cell",
            () -> new Item(new Item.Properties().setId(ITEMS.key("cell")).stacksTo(64)));

    public static final RegistryObject<Item> SHELL = ITEMS.register("shell",
            () -> new Item(new Item.Properties().setId(ITEMS.key("shell")).stacksTo(64)));

    public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket",
            () -> new Item(new Item.Properties().setId(ITEMS.key("rocket")).stacksTo(64)));

    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade",
            () -> new HandgrenadeItem(new Item.Properties().setId(ITEMS.key("grenade")).stacksTo(64)));

    public static final RegistryObject<Item> SLUG = ITEMS.register("slug",
            () -> new Item(new Item.Properties().setId(ITEMS.key("slug")).stacksTo(64)));

    /// Weapons

    public static final RegistryObject<Item> BLASTER = ITEMS.register("blaster",
            () -> new BlasterItem(new Item.Properties().setId(ITEMS.key("blaster")).stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new ShotgunItem(new Item.Properties().setId(ITEMS.key("shotgun")).stacksTo(1)));

    public static final RegistryObject<Item> SUPER_SHOTGUN = ITEMS.register("super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties().setId(ITEMS.key("super_shotgun")).stacksTo(1)));

    public static final RegistryObject<Item> MACHINEGUN = ITEMS.register("machinegun",
            () -> new MachinegunItem(new Item.Properties().setId(ITEMS.key("machinegun")).stacksTo(1)));

    public static final RegistryObject<Item> CHAINGUN = ITEMS.register("chaingun",
            () -> new ChaingunItem(new Item.Properties().setId(ITEMS.key("chaingun")).stacksTo(1)));

    public static final RegistryObject<Item> GRENADELAUNCHER = ITEMS.register("grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties().setId(ITEMS.key("grenadelauncher")).stacksTo(1)));

    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties().setId(ITEMS.key("rocketlauncher")).stacksTo(1)));

    public static final RegistryObject<Item> HYPERBLASTER = ITEMS.register("hyperblaster",
            () -> new HyperblasterItem(new Item.Properties().setId(ITEMS.key("hyperblaster")).stacksTo(1)));

    public static final RegistryObject<Item> RAILGUN = ITEMS.register("railgun",
            () -> new RailgunItem(new Item.Properties().setId(ITEMS.key("railgun")).stacksTo(1)));

    public static final RegistryObject<Item> BFG10K = ITEMS.register("bfg10k",
            () -> new Bfg10kItem(new Item.Properties().setId(ITEMS.key("bfg10k")).stacksTo(1)));

    /// Powerup Items

    public static final RegistryObject<Item> QUAD_DAMAGE_ITEM = ITEMS.register("quad_damage_item",
            () -> new QuadDamageItem(new Item.Properties().setId(ITEMS.key("quad_damage_item")).stacksTo(1)));

    public static final RegistryObject<Item> INVULN_ITEM = ITEMS.register("invuln_item",
            () -> new InvulnerabilityItem(new Item.Properties().setId(ITEMS.key("invuln_item")).stacksTo(1)));

    public static final RegistryObject<Item> ENVIROSUIT_ITEM = ITEMS.register("envirosuit_item",
            () -> new EnvirosuitItem(new Item.Properties().setId(ITEMS.key("envirosuit_item")).stacksTo(1)));

    public static final RegistryObject<Item> REBREATHER_ITEM = ITEMS.register("rebreather_item",
            () -> new RebreatherItem(new Item.Properties().setId(ITEMS.key("rebreather_item")).stacksTo(16)));

    public static final RegistryObject<Item> SILENCER_ITEM = ITEMS.register("silencer_item",
            () -> new SilencerItem(new Item.Properties().setId(ITEMS.key("silencer_item")).stacksTo(16)));

    public static final RegistryObject<Item> ADRENALINE_ITEM = ITEMS.register("adrenaline_item",
            () -> new AdrenalineItem(new Item.Properties().setId(ITEMS.key("adrenaline_item")).stacksTo(1)));

    public static final RegistryObject<Item> POWERSHIELD_ITEM = ITEMS.register("powershield_item",
            () -> new PowershieldItem(new Item.Properties().setId(ITEMS.key("powershield_item")).stacksTo(1)));


    public static void register(BusGroup eventBus) {
        ITEMS.register(eventBus);
    }
}
