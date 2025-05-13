package com.wdiscute.laicaps.block.single;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TreasureChestBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
    public TreasureChestBlock(Properties properties)
    {
        super(properties);
    }

    public static final IntegerProperty MARKER = IntegerProperty.create("marker", 0, 7);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        //if there are 0 markers go straight to 1 instead of changing active since theres no active for 0
        if (state.getValue(MARKER) == 0)
        {
            level.setBlockAndUpdate(pos, state.setValue(MARKER, 1));
            return ItemInteractionResult.SUCCESS;
        }

        if (state.getValue(ACTIVE))
        {
            //if marker is 7 then flips back to 0 and sets active to false
            if (state.getValue(MARKER) == 7)
            {
                BlockState bs = state.setValue(MARKER, 0);
                bs = bs.setValue(ACTIVE, false);
                level.setBlockAndUpdate(pos, bs);
                return ItemInteractionResult.SUCCESS;
            }

            // if marker is not 7 then goes to next number and flips active to false
            BlockState bs = state.setValue(ACTIVE, false);
            level.setBlockAndUpdate(pos, bs.setValue(MARKER, bs.getValue(MARKER) + 1));

        } else
        {
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(FACING))
        {
            case EAST, WEST:
                return Shapes.or(Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(4, 13, 1, 12, 14, 15));
            default:
                return Shapes.or(Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(1, 13, 4, 15, 14, 12));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING);
        pBuilder.add(MARKER);
        pBuilder.add(ACTIVE);
        pBuilder.add(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }



    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        bs = bs.setValue(MARKER, 0);
        bs = bs.setValue(ACTIVE, false);
        bs = bs.setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));

        return bs;
    }


}




