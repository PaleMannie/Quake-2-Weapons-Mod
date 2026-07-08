package mett.palemannie.q2w.block;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.block.custom.LightWaterBlock;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Quake2Weapons.MODID);


    public static final RegistryObject<Block> LIGHT_WATER = BLOCKS.register("light_water", () ->
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable()
                    .noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable()
                    .liquid().sound(SoundType.EMPTY).lightLevel((x) -> x.getValue(BlockStateProperties.POWER))));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
