package com.wdiscute.laicaps.block.rotating;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RotatingControllerBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock
{
    public RotatingControllerBlock(Properties properties)
    {
        super(properties);
    }

    public static final IntegerProperty BLOCKS = IntegerProperty.create("blocks",0 , 7);
    public static final IntegerProperty CORRECT_BLOCKS = IntegerProperty.create("blocks_completed",0 , 7);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {

        if(!level.isClientSide && level.getBlockEntity(pos) instanceof RotatingControllerBlockentity rcbe && hand == InteractionHand.MAIN_HAND)
        {
            if(stack.is(Items.BARRIER))
            {
                rcbe.resetPlayersSaved();
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.players_reset"), true);
                return ItemInteractionResult.SUCCESS;
            }

            if(stack.is(ModItems.CHISEL))
            {
                if (state.getValue(FACING) == Direction.EAST)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = x;
                    int newz = z;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    rcbe.setNextLinkedBlock(bp, player);

                }
                if (state.getValue(FACING) == Direction.SOUTH)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = z;
                    int newz = x * -1;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    rcbe.setNextLinkedBlock(bp, player);
                }
                if (state.getValue(FACING) == Direction.WEST)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = x * -1;
                    int newz = z * -1;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    rcbe.setNextLinkedBlock(bp, player);
                }
                if (state.getValue(FACING) == Direction.NORTH)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = z * -1;
                    int newz = x;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    rcbe.setNextLinkedBlock(bp, player);
                }
                return ItemInteractionResult.SUCCESS;
            }

            rcbe.canPlayerObtainDrops(player);
        }

        return ItemInteractionResult.SUCCESS;
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
        pBuilder.add(BLOCKS);
        pBuilder.add(CORRECT_BLOCKS);
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
        if(context.getLevel().getFluidState(context.getClickedPos()).is(FluidTags.WATER))
            bs = bs.setValue(WATERLOGGED, true);
        else
            bs = bs.setValue(WATERLOGGED, false);

        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        bs = bs.setValue(BLOCKS, 0);
        bs = bs.setValue(CORRECT_BLOCKS, 0);
        return bs;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.ROTATING_CONTROLLER.get().create(pPos, pState);
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
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHelper(pLevel);
    }

}




