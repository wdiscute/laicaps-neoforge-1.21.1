package com.wdiscute.laicaps.block.custom.notes;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.blockentity.NotesPuzzleBlockEntity;
import com.wdiscute.laicaps.blockentity.TickableBlockEntity;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotesPuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public NotesPuzzleBlock(Properties properties)
    {
        super(properties);
    }

    public static final EnumProperty<NotesEnum> NOTE = EnumProperty.create("note", NotesEnum.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (stack.isEmpty() && !level.isClientSide())
        {
            level.playSound(null, pos, state.getValue(NOTE).getSound(), SoundSource.BLOCKS, 1f, state.getValue(NOTE).getPitch());
        }

        //add blockpos stored in chisel to linked
        if(stack.is(ModItems.CHISEL))
        {
            if (state.getValue(FACING) == Direction.EAST && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pos.getY();

                int newx = x;
                int newz = z;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);

            }
            if (state.getValue(FACING) == Direction.SOUTH && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pos.getY();

                int newx = z;
                int newz = x * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.WEST && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pos.getY();

                int newx = x * -1;
                int newz = z * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.NORTH && level.getBlockEntity(pos) instanceof NotesPuzzleBlockEntity be)
            {
                int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pos.getY();

                int newx = z * -1;
                int newz = x;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setLinkedBlock(bp, player);
            }
        }

        if (stack.is(Items.ANVIL) && !level.isClientSide())
        {
            level.setBlockAndUpdate(pos, state.setValue(NOTE, NotesEnum.GetNextNote(state.getValue(NOTE))));
        }

        return ItemInteractionResult.SUCCESS;
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
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }
}
