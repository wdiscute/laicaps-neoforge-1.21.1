package com.wdiscute.laicaps.block.watercontainer;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterContainerHelperBlock extends Block implements EntityBlock
{
    public WaterContainerHelperBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        if(level.getBlockEntity(pos) instanceof WaterContainerHelperBlockEntity wchbe)
        {
            wchbe.linkBlock(pos);
        }

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.WATER_CONTAINER_HELPER_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }

}
