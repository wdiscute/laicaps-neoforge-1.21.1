package com.wdiscute.laicaps.block.timer;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;


public class TimerControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{

    public TimerControllerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }


    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get().create(pPos, pState);
    }
}
