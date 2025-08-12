package com.wdiscute.laicaps.block.symbol;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (pLevel.getBlockEntity(pos) instanceof SymbolPuzzleBlockEntity be)
        {
            if (!pLevel.isClientSide() && stack.is(ModItems.CHISEL.get()))
            {
                //gets the coords from the chisel and stores offset on be
                if (state.getValue(FACING) == Direction.EAST)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = x;
                    int newz = z;

                    be.setLinkedBLock(new BlockPos(newx, y, newz));
                }

                if (state.getValue(FACING) == Direction.SOUTH)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = z;
                    int newz = x * -1;

                    be.setLinkedBLock(new BlockPos(newx, y, newz));
                }

                if (state.getValue(FACING) == Direction.WEST)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = x * -1;
                    int newz = z * -1;

                    be.setLinkedBLock(new BlockPos(newx, y, newz));
                }

                if (state.getValue(FACING) == Direction.NORTH)
                {
                    int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                    int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                    int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                    int newx = z * -1;
                    int newz = x;

                    be.setLinkedBLock(new BlockPos(newx, y, newz));
                }

                return ItemInteractionResult.SUCCESS;
            }

            if (!pLevel.isClientSide() && stack.is(Items.ANVIL))
            {
                pLevel.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.RANDOM));

                return ItemInteractionResult.SUCCESS;
            }

            //if clicked with air then cycles to next symbol and plays sound

            if (!pLevel.isClientSide())
            {
                SymbolsEnum nextSymbol = SymbolsEnum.GetNextSymbol(state.getValue(SYMBOLS));
                String symbolsAvailable = be.getSymbols();

                for (int i = 0; i < Arrays.stream(SymbolsEnum.values()).count(); i++)
                {
                    if (symbolsAvailable.contains(nextSymbol.getSerializedName()) || symbolsAvailable.equals("all"))
                    {
                        //play sound and set block to the next symbol that is found inside the list
                        pLevel.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1f, 0.5f);
                        pLevel.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, 0.5f);
                        pLevel.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, 0.5f);
                        pLevel.setBlockAndUpdate(pos, state.setValue(SYMBOLS, nextSymbol));
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
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState().setValue(SYMBOLS, SymbolsEnum.RANDOM);
        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get().create(pos, state);
    }
}
