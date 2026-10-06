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

    public static final DeferredHolder<Item, Item> BULLET = ITEMS.register("q2w_bullet",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_bullet"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> CELL = ITEMS.register("q2w_cell",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_cell"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> SHELL = ITEMS.register("q2w_shell",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_shell"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> ROCKET = ITEMS.register("q2w_rocket",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_rocket"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> GRENADE = ITEMS.register("q2w_grenade",
            () -> new HandgrenadeItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_grenade"))).stacksTo(64)));

    public static final DeferredHolder<Item, Item> SLUG = ITEMS.register("q2w_slug",
            () -> new Item(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_slug"))).stacksTo(64)));

    /// Weapons

    public static final DeferredHolder<Item, Item> BLASTER = ITEMS.register("q2w_blaster",
            () -> new BlasterItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_blaster"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> SHOTGUN = ITEMS.register("q2w_shotgun",
            () -> new ShotgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_shotgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> SUPER_SHOTGUN = ITEMS.register("q2w_super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_super_shotgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> MACHINEGUN = ITEMS.register("q2w_machinegun",
            () -> new MachinegunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_machinegun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> CHAINGUN = ITEMS.register("q2w_chaingun",
            () -> new ChaingunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_chaingun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> GRENADELAUNCHER = ITEMS.register("q2w_grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_grenadelauncher"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> ROCKETLAUNCHER = ITEMS.register("q2w_rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_rocketlauncher"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> HYPERBLASTER = ITEMS.register("q2w_hyperblaster",
            () -> new HyperblasterItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_hyperblaster"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> RAILGUN = ITEMS.register("q2w_railgun",
            () -> new RailgunItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_railgun"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> BFG10K = ITEMS.register("q2w_bfg10k",
            () -> new Bfg10kItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_bfg10k"))).stacksTo(1)));

    /// Powerup Items

    public static final DeferredHolder<Item, Item> QUAD_DAMAGE_ITEM = ITEMS.register("q2w_quad_damage_item",
            () -> new QuadDamageItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_quad_damage_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> INVULN_ITEM = ITEMS.register("q2w_invuln_item",
            () -> new InvulnerabilityItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_invuln_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> ENVIROSUIT_ITEM = ITEMS.register("q2w_envirosuit_item",
            () -> new EnvirosuitItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_envirosuit_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> REBREATHER_ITEM = ITEMS.register("q2w_rebreather_item",
            () -> new RebreatherItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_rebreather_item"))).stacksTo(16)));

    public static final DeferredHolder<Item, Item> SILENCER_ITEM = ITEMS.register("q2w_silencer_item",
            () -> new SilencerItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_silencer_item"))).stacksTo(16)));

    public static final DeferredHolder<Item, Item> ADRENALINE_ITEM = ITEMS.register("q2w_adrenaline_item",
            () -> new AdrenalineItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_adrenaline_item"))).stacksTo(1)));

    public static final DeferredHolder<Item, Item> POWERSHIELD_ITEM = ITEMS.register("q2w_powershield_item",
            () -> new PowershieldItem(new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(ITEMS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "q2w_powershield_item"))).stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
