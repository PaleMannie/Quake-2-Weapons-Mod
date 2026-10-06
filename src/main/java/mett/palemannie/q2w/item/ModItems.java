package mett.palemannie.q2w.item;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Quake2Weapons.MODID);

    /// Ammo

    public static final RegistryObject<Item> BULLET = ITEMS.register("q2w_bullet",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> CELL = ITEMS.register("q2w_cell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SHELL = ITEMS.register("q2w_shell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> ROCKET = ITEMS.register("q2w_rocket",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GRENADE = ITEMS.register("q2w_grenade",
            () -> new HandgrenadeItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SLUG = ITEMS.register("q2w_slug",
            () -> new Item(new Item.Properties().stacksTo(64)));

    /// Weapons

    public static final RegistryObject<Item> BLASTER = ITEMS.register("q2w_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("q2w_shotgun",
            () -> new ShotgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUPER_SHOTGUN = ITEMS.register("q2w_super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MACHINEGUN = ITEMS.register("q2w_machinegun",
            () -> new MachinegunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHAINGUN = ITEMS.register("q2w_chaingun",
            () -> new ChaingunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GRENADELAUNCHER = ITEMS.register("q2w_grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("q2w_rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> HYPERBLASTER = ITEMS.register("q2w_hyperblaster",
            () -> new HyperblasterItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RAILGUN = ITEMS.register("q2w_railgun",
            () -> new RailgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BFG10K = ITEMS.register("q2w_bfg10k",
            () -> new Bfg10kItem(new Item.Properties().stacksTo(1)));

    /// Powerup Items

    public static final RegistryObject<Item> QUAD_DAMAGE_ITEM = ITEMS.register("q2w_quad_damage_item",
            () -> new QuadDamageItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> INVULN_ITEM = ITEMS.register("q2w_invuln_item",
            () -> new InvulnerabilityItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ENVIROSUIT_ITEM = ITEMS.register("q2w_envirosuit_item",
            () -> new EnvirosuitItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> REBREATHER_ITEM = ITEMS.register("q2w_rebreather_item",
            () -> new RebreatherItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> SILENCER_ITEM = ITEMS.register("q2w_silencer_item",
            () -> new SilencerItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ADRENALINE_ITEM = ITEMS.register("q2w_adrenaline_item",
            () -> new AdrenalineItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> POWERSHIELD_ITEM = ITEMS.register("q2w_powershield_item",
            () -> new PowershieldItem(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
