package com.wdiscute.laicaps.block.singleblocks;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RiverthorneThistle extends BushBlock
{
    public RiverthorneThistle(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }

    public static final BooleanProperty GROWN = BooleanProperty.create("grown");


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if ((int) (Math.random() * 30 + 1) > 29) // 1/30 chance to tick
        {
            if(!state.getValue(GROWN)){
                level.setBlockAndUpdate(pos, state.setValue(GROWN, true));
            }
        }


        super.randomTick(state, level, pos, random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if(state.getValue(GROWN))
        {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, new ItemStack(ModBlocks.RIVERTHORNE_THISTLE.get()));
            level.addFreshEntity(itemEntity);
            level.setBlockAndUpdate(pos, state.setValue(GROWN, false));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.RIVERTHORNE_CAN_SURVIVE);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.RIVERTHORNE_CAN_SURVIVE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return ModBlocks.RIVERTHORNE.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(GROWN);
    }

}
