package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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


    private static SymbolsEnum CycleSymbol(SymbolsEnum sym)
    {

        if (sym == SymbolsEnum.ONE) return SymbolsEnum.TWO;
        if (sym == SymbolsEnum.TWO) return SymbolsEnum.THREE;
        if (sym == SymbolsEnum.THREE) return SymbolsEnum.FOUR;
        if (sym == SymbolsEnum.FOUR) return SymbolsEnum.FIVE;
        if (sym == SymbolsEnum.FIVE) return SymbolsEnum.SIX;
        if (sym == SymbolsEnum.SIX) return SymbolsEnum.SEVEN;
        if (sym == SymbolsEnum.SEVEN) return SymbolsEnum.EIGHT;
        if (sym == SymbolsEnum.EIGHT) return SymbolsEnum.NINE;
        if (sym == SymbolsEnum.NINE) return SymbolsEnum.TEN;

        return SymbolsEnum.ONE;


    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.getRandom()));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide() && pPlayer.getMainHandItem().getItem() == ModItems.CHISEL.get())
        {
            SymbolsEnum next = CycleSymbol(pState.getValue(SYMBOLS));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(SYMBOLS, next));
            pLevel.playSound(null, pPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1,0.5f);
            return InteractionResult.SUCCESS;
        }

        if (pLevel.isClientSide() && pPlayer.getMainHandItem().getItem() == ModItems.CHISEL.get())
        {
            return InteractionResult.SUCCESS;
        }

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
