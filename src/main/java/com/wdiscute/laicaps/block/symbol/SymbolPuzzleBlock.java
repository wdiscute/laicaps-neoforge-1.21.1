package com.wdiscute.laicaps.block.symbol;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


public class SymbolPuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public static final EnumProperty<SymbolsEnum> SYMBOLS = EnumProperty.create("symbol", SymbolsEnum.class);

    public SymbolPuzzleBlock(Properties properties)
    {
        super(properties);
    }


    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource random)
    {
        if (pLevel.getBlockEntity(pPos) instanceof SymbolPuzzleBlockEntity be)
        {
            //if ACTIVE symbol block is random
            if (pState.getValue(SYMBOLS) == SymbolsEnum.RANDOM)
            {
                String symbolsAvailable = be.getSymbols();
                for (int i = 0; i < 150; i++)
                {
                    SymbolsEnum randomSymbolActive = SymbolsEnum.getRandom();
                    if (symbolsAvailable.contains(randomSymbolActive.getSerializedName()) || symbolsAvailable.equals("all"))
                    {
                        //play sound and set block to the next symbol that is found inside the list
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(SYMBOLS, randomSymbolActive));
                        break;
                    }
                }
            }

            //if INACTIVE symbol block is random

            BlockState inactiveState = pLevel.getBlockState(DecodeBlockPosWithOffset(pLevel, pPos, be.getBlockLinkedOffset()));
            BlockPos posInactive = DecodeBlockPosWithOffset(pLevel, pPos, be.getBlockLinkedOffset());

            if(!inactiveState.is(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE))
                return;

            if (inactiveState.getValue(SymbolPuzzleBlockInactive.SYMBOLS) == SymbolsEnum.RANDOM)
            {
                String symbolsAvailable = be.getSymbols();
                //System.out.println("symbolsAvailable " + symbolsAvailable);

                for (int i = 0; i < 150; i++)
                {
                    SymbolsEnum randomSymbolInactive = SymbolsEnum.getRandom();
                    //System.out.println("atempted " + randomSymbolInactive);
                    if (symbolsAvailable.contains(randomSymbolInactive.getSerializedName()) && randomSymbolInactive != pState.getValue(SYMBOLS) || symbolsAvailable.equals("all"))
                    {
                        //System.out.println("sucess with " + randomSymbolInactive + " which is different than " + pState.getValue(SYMBOLS));
                        //play sound and set block to the next symbol that is found inside the list
                        pLevel.setBlockAndUpdate(posInactive, inactiveState.setValue(SymbolPuzzleBlockInactive.SYMBOLS, randomSymbolInactive));
                        break;
                    }
                }
            }

        }

    }


    private BlockPos DecodeBlockPosWithOffset(Level plevel, BlockPos pos, BlockPos posOffset)
    {
        //returns the world coords of the linked block based on the offset stored
        BlockState pState = plevel.getBlockState(pos);

        if (pState.getValue(FACING) == Direction.SOUTH)
            return new BlockPos(pos.getX() + (posOffset.getZ() * -1), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getX());

        if (pState.getValue(FACING) == Direction.WEST)
            return new BlockPos(pos.getX() + (posOffset.getX() * -1), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getZ() * -1));

        if (pState.getValue(FACING) == Direction.NORTH)
            return new BlockPos(pos.getX() + posOffset.getZ(), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getX() * -1));

        if (pState.getValue(FACING) == Direction.EAST)
            return new BlockPos(pos.getX() + posOffset.getX(), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getZ());

        return new BlockPos(0, 0, 0);

    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (pLevel.getBlockEntity(pPos) instanceof SymbolPuzzleBlockEntity be)
        {
            if (!pLevel.isClientSide() && pStack.getItem() == ModItems.CHISEL.get())
            {
                //gets the coords from the chisel and rotates to align with block rotation
                //saves the relative coord directions on the BE
                if (pState.getValue(FACING) == Direction.EAST)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x;
                    int newz = z;

                    be.setX(newx);
                    be.setY(y);
                    be.setZ(newz);

                }
                if (pState.getValue(FACING) == Direction.SOUTH)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = z;
                    int newz = x * -1;

                    be.setX(newx);
                    be.setY(y);
                    be.setZ(newz);
                }
                if (pState.getValue(FACING) == Direction.WEST)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x * -1;
                    int newz = z * -1;

                    be.setX(newx);
                    be.setY(y);
                    be.setZ(newz);
                }
                if (pState.getValue(FACING) == Direction.NORTH)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = z * -1;
                    int newz = x;

                    be.setX(newx);
                    be.setY(y);
                    be.setZ(newz);
                }

                return ItemInteractionResult.SUCCESS;
            }

            if (!pLevel.isClientSide())
            {
                //if clicked with air then cycles to next symbol and plays sound

                SymbolsEnum nextSymbol = SymbolsEnum.GetNextSymbol(pState.getValue(SYMBOLS));
                String symbolsAvailable = be.getSymbols();

                for (int i = 0; i < Arrays.stream(SymbolsEnum.values()).count(); i++)
                {
                    if (symbolsAvailable.contains(nextSymbol.getSerializedName()) || symbolsAvailable.equals("all"))
                    {
                        //play sound and set block to the next symbol that is found inside the list
                        pLevel.playSound(null, pPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1f, 0.5f);
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(SYMBOLS, nextSymbol));
                        break;
                    }
                    nextSymbol = SymbolsEnum.GetNextSymbol(nextSymbol);
                }
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.SUCCESS;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState bs = defaultBlockState().setValue(SYMBOLS, SymbolsEnum.RANDOM);
        bs = bs.setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        return bs;
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


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get().create(pPos, pState);
    }
}
