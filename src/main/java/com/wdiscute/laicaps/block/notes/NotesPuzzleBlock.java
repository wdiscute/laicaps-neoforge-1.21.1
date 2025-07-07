package com.wdiscute.laicaps.block.notes;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotesPuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public  NotesPuzzleBlock(Properties properties)
    {
        super(properties);
    }

    public static final EnumProperty<NotesEnum> NOTE = EnumProperty.create("note", NotesEnum.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        //we only care about main hand to prevent double stuff
        if (hand == InteractionHand.OFF_HAND) return ItemInteractionResult.FAIL;

        //add blockpos stored in chisel to linked
        if (stack.is(ModItems.CHISEL))
        {
            if (state.getValue(FACING) == Direction.EAST && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = x;
                int newz = z;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);

            }
            if (state.getValue(FACING) == Direction.SOUTH && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = z;
                int newz = x * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.WEST && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = x * -1;
                int newz = z * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.NORTH && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = z * -1;
                int newz = x;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
            return ItemInteractionResult.SUCCESS;
        }

        //cycle note, should prob change the block used or something
        if (stack.is(Items.ANVIL) && !level.isClientSide())
        {
            level.setBlockAndUpdate(pos, state.setValue(NOTE, NotesEnum.GetNextNote(state.getValue(NOTE))));
            level.playSound(null, pos, state.getValue(NOTE).getSound(), SoundSource.BLOCKS, 1f, state.getValue(NOTE).getPitch());
            player.displayClientMessage(Component.literal("Changed note to  " + state.getValue(NOTE)), true);
            return ItemInteractionResult.SUCCESS;
        }

        if (stack.is(Items.ANVIL) && level.isClientSide())
        {
            return ItemInteractionResult.SUCCESS;
        }

        //checks if can play notes and sends signals to controller and sounds if so
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity npbe && !state.getValue(ACTIVE))
        {
            //gets decoded block pos of controller
            BlockPos decodedLinkedPos = DecodeBlockPosWithOffset(level, pos, npbe.getLinkedBlock());
            if (level.getBlockEntity(decodedLinkedPos) instanceof NotesControllerBlockEntity ncbe)
            {
                //if controller state is idle or listening then plays note
                if (ncbe.getState() == 0 || ncbe.getState() == 2 && hand == InteractionHand.MAIN_HAND)
                {
                    npbe.playNote(10);
                    BlockPos dwa = new BlockPos(npbe.getLinkedBlock().getX() * -1, npbe.getLinkedBlock().getY() * -1, npbe.getLinkedBlock().getZ() * -1);
                    ncbe.receiveClicked(dwa);
                    return ItemInteractionResult.SUCCESS;
                }
            }

        }

        //if controller state is idle or listening then plays hand animation on client side
        if (level.isClientSide && !state.getValue(ACTIVE) && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity npbe)
        {
            if (level.getBlockEntity(DecodeBlockPosWithOffset(level, pos, npbe.getLinkedBlock())) instanceof NotesControllerBlockEntity ncbe)
            {
                if (ncbe.getState() == 0 || ncbe.getState() == 2)
                {
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        //skip hand animation if player couldn't play note
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private BlockPos DecodeBlockPosWithOffset(Level plevel, BlockPos pos, BlockPos posOffset)
    {
        //returns the world coords of the linked block based on the offset stored
        BlockState pState = plevel.getBlockState(pos);

        if (pState.getValue(NotesPuzzleBlock.FACING) == Direction.SOUTH)
            return new BlockPos(pos.getX() + (posOffset.getZ() * -1), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getX());

        if (pState.getValue(NotesPuzzleBlock.FACING) == Direction.WEST)
            return new BlockPos(pos.getX() + (posOffset.getX() * -1), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getZ() * -1));

        if (pState.getValue(NotesPuzzleBlock.FACING) == Direction.NORTH)
            return new BlockPos(pos.getX() + posOffset.getZ(), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getX() * -1));

        if (pState.getValue(NotesPuzzleBlock.FACING) == Direction.EAST)
            return new BlockPos(pos.getX() + posOffset.getX(), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getZ());

        return new BlockPos(0, 0, 0);

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(NOTE);
        pBuilder.add(ACTIVE);
        pBuilder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        bs = bs.setValue(ACTIVE, false);
        bs = bs.setValue(NOTE, NotesEnum.RANDOM);
        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        return bs;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.NOTES_PUZZLE_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHelper(pLevel);
    }
}
