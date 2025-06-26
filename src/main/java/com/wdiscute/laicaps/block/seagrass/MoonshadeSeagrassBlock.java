package com.wdiscute.laicaps.block.seagrass;

import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoonshadeSeagrassBlock extends SeagrassBlock
{

    public MoonshadeSeagrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        BlockState bottom = ModBlocks.MOONSHADE_TALL_SEAGRASS.get().defaultBlockState();
        BlockState top = bottom.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockpos = pos.above();
        if (level.getBlockState(blockpos).is(Blocks.WATER))
        {
            level.setBlock(pos, bottom, 2);
            level.setBlock(blockpos, top, 2);
        }

    }

}
