package mett.palemannie.q2w.block;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.block.custom.QuakeLightWaterBlock;
import mett.palemannie.q2w.block.custom.QuakeLightAirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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


    public static final RegistryObject<Block> QUAKE_LIGHT_WATER =
            registerBlockWithoutItem("light_water", () ->
            new QuakeLightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable()
                    .noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable()
                    .liquid().sound(SoundType.EMPTY).lightLevel((x) -> x.getValue(BlockStateProperties.POWER))));

    public static final RegistryObject<Block> QUAKE_LIGHT_AIR =
            registerBlockWithoutItem("quake_light_air", () -> new QuakeLightAirBlock(
                    BlockBehaviour.Properties.copy(Blocks.AIR)
                            .lightLevel(state -> 15)
                            .noCollission()
                            .noOcclusion()
                            .air()
            ));

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
