package com.wdiscute.laicaps.block.receiversender;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SenderPuzzleBLock extends HorizontalDirectionalBlock
{
    public SenderPuzzleBLock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return super.getShape(state, level, pos, context);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        float speed = 0.5f;
        //WEST
        float x = -0.2f;
        float y = 0.5f;
        float z = 0.5f;
        float sx = -speed;
        float sy = 0;
        float sz = 0;

        if (pState.getValue(FACING) == Direction.EAST)
        {
            x = 1.2f;
            y = 0.5f;
            z = 0.5f;
            sx = speed;
            sy = 0;
            sz = 0;

        }

        if (pState.getValue(FACING) == Direction.NORTH)
        {
            x = 0.5f;
            y = 0.5f;
            z = -0.2f;
            sx = 0;
            sy = 0;
            sz = -speed;

        }

        if (pState.getValue(FACING) == Direction.SOUTH)
        {
            x = 0.5f;
            y = 0.5f;
            z = 1.2f;
            sx = 0;
            sy = 0;
            sz = speed;
        }

        pLevel.addParticle(ParticleTypes.CLOUD,
                (double) pPos.getX() + x,
                (double) pPos.getY() + y,
                (double) pPos.getZ() + z,
                sx,
                sy,
                sz

        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide())
        {
            if (pState.getValue(FACING) == Direction.NORTH)
            {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FACING, Direction.EAST));
            }
            if (pState.getValue(FACING) == Direction.EAST)
            {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FACING, Direction.SOUTH));
            }
            if (pState.getValue(FACING) == Direction.SOUTH)
            {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FACING, Direction.WEST));
            }
            if (pState.getValue(FACING) == Direction.WEST)
            {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FACING, Direction.NORTH));
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}




