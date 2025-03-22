package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SymbolPuzzleBlockInactive extends HorizontalDirectionalBlock
{
    public SymbolPuzzleBlockInactive(Properties properties)
    {
        super(properties);
    }

    public static final EnumProperty<SymbolsEnum> SYMBOLS = EnumProperty.create("symbol", SymbolsEnum.class);

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {

        //if clicked with chisel then cycles to next symbol and plays sound
        if (!pLevel.isClientSide() && pPlayer.getMainHandItem().getItem() == ModItems.CHISEL.get())
        {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(SYMBOLS, SymbolsEnum.GetNextSymbol(pState.getValue(SYMBOLS))));
            pLevel.playSound(null, pPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.5f);
            return InteractionResult.SUCCESS;
        }

        //code so client side plays hand animation when using chisel
        if (pLevel.isClientSide() && pPlayer.getMainHandItem().getItem() == ModItems.CHISEL.get())
        {
            return InteractionResult.SUCCESS;
        }

        //if not clicked with chisel doesnt play hand animation
        return InteractionResult.FAIL;

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        this.defaultBlockState().setValue(SYMBOLS, SymbolsEnum.RANDOM);
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
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
        pBuilder.add(SYMBOLS);
        pBuilder.add(FACING);

    }


}
