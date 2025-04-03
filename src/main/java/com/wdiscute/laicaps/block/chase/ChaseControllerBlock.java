package com.wdiscute.laicaps.block.chase;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ChaseControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public ChaseControllerBlock(Properties properties)
    {
        super(properties);
    }

    public static IntegerProperty WAYPOINTS = IntegerProperty.create("waypoints",0 , 7);
    public static IntegerProperty WAYPOINTS_COMPLETED = IntegerProperty.create("waypoints_completed",0 , 7);


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof ChaseControllerBlockEntity ccbe && hand == InteractionHand.MAIN_HAND)
        {
            if(stack.is(ModItems.CHISEL))
            {
                if(ccbe.SetNextLinkedBlock(state.getValue(FACING), pos,stack.get(ModDataComponentTypes.COORDINATES.get())))
                {
                    player.displayClientMessage(Component.literal("Position " + stack.get(ModDataComponentTypes.COORDINATES.get()) + " set"), true);
                    ccbe.UpdateBlockState();
                }else
                {
                    player.displayClientMessage(Component.literal("Maximum number of waypoints reached"), false);
                }
                return ItemInteractionResult.SUCCESS;
            }

            ccbe.assignPlayer(player);

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
        pBuilder.add(WAYPOINTS);
        pBuilder.add(WAYPOINTS_COMPLETED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState bs = defaultBlockState();

        bs = bs.setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        bs = bs.setValue(WAYPOINTS, 0);
        bs = bs.setValue(WAYPOINTS_COMPLETED, 0);
        return bs;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.CHASE_CONTROLLER_BLOCK.get().create(pPos, pState);
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

}




