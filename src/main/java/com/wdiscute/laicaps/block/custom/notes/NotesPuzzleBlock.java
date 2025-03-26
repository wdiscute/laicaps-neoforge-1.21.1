package com.wdiscute.laicaps.block.custom.notes;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.custom.symbol.SymbolsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class NotesPuzzleBlock extends HorizontalDirectionalBlock
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


        if (stack.is(ModItems.CHISEL) && !level.isClientSide())
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
}
