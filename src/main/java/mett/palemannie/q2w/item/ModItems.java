package mett.palemannie.q2w.item;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.*;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Quake2Weapons.MODID);

    /// Ammo

    public static final DeferredHolder<Item, Item> BULLET = ITEMS.register("bullet",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "bullet"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> CELL = ITEMS.register("cell",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "cell"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> SHELL = ITEMS.register("shell",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "shell"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> ROCKET = ITEMS.register("rocket",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rocket"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> GRENADE = ITEMS.register("grenade",
            () -> new HandgrenadeItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "grenade"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> SLUG = ITEMS.register("slug",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "slug"))).stacksTo(64)));

    /// Weapons

    public static final DeferredHolder<Item, Item> BLASTER = ITEMS.register("blaster",
            () -> new BlasterItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "blaster"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new ShotgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "shotgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> SUPER_SHOTGUN = ITEMS.register("super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "super_shotgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> MACHINEGUN = ITEMS.register("machinegun",
            () -> new MachinegunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "machinegun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> CHAINGUN = ITEMS.register("chaingun",
            () -> new ChaingunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "chaingun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> GRENADELAUNCHER = ITEMS.register("grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "grenadelauncher"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rocketlauncher"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> HYPERBLASTER = ITEMS.register("hyperblaster",
            () -> new HyperblasterItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "hyperblaster"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> RAILGUN = ITEMS.register("railgun",
            () -> new RailgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "railgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> BFG10K = ITEMS.register("bfg10k",
            () -> new Bfg10kItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "bfg10k"))).stacksTo(1)));

    /// Powerup Items

    public static final DeferredHolder<Item, Item> QUAD_DAMAGE_ITEM = ITEMS.register("quad_damage_item",
            () -> new QuadDamageItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "quad_damage_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> INVULN_ITEM = ITEMS.register("invuln_item",
            () -> new InvulnerabilityItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "invuln_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> ENVIROSUIT_ITEM = ITEMS.register("envirosuit_item",
            () -> new EnvirosuitItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "envirosuit_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> REBREATHER_ITEM = ITEMS.register("rebreather_item",
            () -> new RebreatherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "rebreather_item"))).stacksTo(16)));

    public static final DeferredHolder<Item, Item> SILENCER_ITEM = ITEMS.register("silencer_item",
            () -> new SilencerItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "silencer_item"))).stacksTo(16)));

    public static final DeferredHolder<Item, Item> ADRENALINE_ITEM = ITEMS.register("adrenaline_item",
            () -> new AdrenalineItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "adrenaline_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> POWERSHIELD_ITEM = ITEMS.register("powershield_item",
            () -> new PowershieldItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "powershield_item"))).stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
