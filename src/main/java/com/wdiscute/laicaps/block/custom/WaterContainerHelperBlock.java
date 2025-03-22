package com.wdiscute.laicaps.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WaterContainerHelperBlock extends Block
{
    public WaterContainerHelperBlock(Properties properties)
    {
        super(properties);
    }


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
