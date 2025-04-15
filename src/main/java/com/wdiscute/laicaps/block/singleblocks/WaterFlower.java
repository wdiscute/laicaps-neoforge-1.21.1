package com.wdiscute.laicaps.block.singleblocks;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WaterFlower extends BushBlock implements SimpleWaterloggedBlock
{

    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        if (state.is(ModBlocks.ASHA_DIRT)) return true;
        if (state.is(ModBlocks.ASHA_GRASS_BLOCK)) return true;
        return super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        //open when raining
        if (state.getValue(WATERLOGGED))
        {
            if (!state.getValue(OPEN))
            {
                level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
            }
            return;
        }

        if (level.rainLevel == 1f)
        {
            level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
        } else
        {
            level.setBlockAndUpdate(pos, state.setValue(OPEN, false));
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState().setValue(OPEN, false);
        bs = bs.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
        return bs;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
        pBuilder.add(WATERLOGGED);
    }


    public WaterFlower(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = Block.box((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)15.0F, (double)11.0F);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }


}
