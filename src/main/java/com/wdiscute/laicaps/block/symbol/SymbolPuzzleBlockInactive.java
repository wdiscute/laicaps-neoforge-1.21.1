package com.wdiscute.laicaps.block.symbol;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        //if clicked with chisel then cycles to next symbol and plays sound
        if (!level.isClientSide() && stack.is(ModItems.CHISEL.get()))
        {
            level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.GetNextSymbol(state.getValue(SYMBOLS))));
            level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.5f);
            return ItemInteractionResult.SUCCESS;
        }

        if (!level.isClientSide() && stack.is(Items.ANVIL))
        {
            level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.RANDOM));

            return ItemInteractionResult.SUCCESS;
        }

        //code so client side plays hand animation when using chisel
        if (level.isClientSide() && ( stack.is(ModItems.CHISEL.get()) || stack.is(Items.ANVIL) ))
        {
            return ItemInteractionResult.SUCCESS;
        }

        //if not clicked with chisel doesn't play hand animation
        return ItemInteractionResult.FAIL;
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
