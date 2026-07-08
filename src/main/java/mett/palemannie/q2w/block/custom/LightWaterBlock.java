package mett.palemannie.q2w.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;


public class LightWaterBlock extends LiquidBlock {

    ///Water source block with light level so that muzzle flash works underwater

    public LightWaterBlock(FlowingFluid water, Properties properties) {
        super(water, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 0).setValue(BlockStateProperties.POWER, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWER);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) { return 15; }
}