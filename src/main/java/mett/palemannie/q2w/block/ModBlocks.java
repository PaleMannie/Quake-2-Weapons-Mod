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
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Quake2Weapons.MODID);


    public static final RegistryObject<Block> QUAKE_LIGHT_WATER = BLOCKS.register("light_water", () ->
            new QuakeLightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(BLOCKS.key("light_water"))
                    .mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F)
                    .pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));

    public static final RegistryObject<Block> QUAKE_LIGHT_AIR =
            BLOCKS.register("quake_light_air", () ->
                    new QuakeLightAirBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("quake_light_air"))
                            .replaceable().noCollision().noLootTable().air().randomTicks().lightLevel((x)
                                    -> x.getValue(BlockStateProperties.POWER)).noLootTable().air()));

    public static void register(BusGroup eventBus) {
        BLOCKS.register(eventBus);
    }
}
