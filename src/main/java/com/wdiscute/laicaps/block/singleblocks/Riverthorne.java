package com.wdiscute.laicaps.block.singleblocks;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class Riverthorne extends BushBlock implements SimpleWaterloggedBlock
{

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {

        if (level.getBlockState(pos.above()) == Blocks.WATER.defaultBlockState())
        {
            level.setBlockAndUpdate(pos.above(), ModBlocks.RIVERTHORNE.get().defaultBlockState().setValue(WATERLOGGED, true));
        }

        if (level.getBlockState(pos.above()).isAir())
        {
            level.setBlockAndUpdate(pos.above(), ModBlocks.RIVERTHORNE_THISTLE.get().defaultBlockState().setValue(RiverthorneThistle.GROWN, true));
        }

    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {

        if (!level.getBlockState(pos).getFluidState().is(FluidTags.WATER))
        {
            return false;
        }

        if (!level.getBlockState(pos.below()).is(ModTags.Blocks.RIVERTHORNE_CAN_SURVIVE))
        {
            return false;
        }

        return true;
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return defaultBlockState().setValue(WATERLOGGED, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    public Riverthorne(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }
}
