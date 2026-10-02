package mett.palemannie.q2w.block;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.block.custom.QuakeLightAirBlock;
import mett.palemannie.q2w.block.custom.QuakeLightWaterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, Quake2Weapons.MODID);


    public static final DeferredHolder<Block, Block> QUAKE_LIGHT_WATER = BLOCKS.register("light_water", () ->
            new QuakeLightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(BLOCKS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "light_water")))
                    .mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F)
                    .pushReaction(PushReaction.POPPED).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));

    public static final DeferredHolder<Block, Block> QUAKE_LIGHT_AIR =
            BLOCKS.register("quake_light_air", () ->
                    new QuakeLightAirBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(BLOCKS.getRegistryKey(), net.minecraft.resources.Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "quake_light_air")))
                            .replaceable().noCollision().noLootTable().air().randomTicks().lightLevel((x)
                                    -> x.getValue(BlockStateProperties.POWER)).noLootTable().air()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
