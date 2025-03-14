package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.item.ModItems;
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


public class SymbolPuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public static final EnumProperty<SymbolsEnum> SYMBOLS = EnumProperty.create("symbol", SymbolsEnum.class);

    public SymbolPuzzleBlock(Properties properties)
    {
        super(properties);
    }


    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (state.getValue(SYMBOLS) == SymbolsEnum.RANDOM)
        {
            int rint = random.nextInt(10) + 1;

            switch (rint)
            {
                case 1 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.ONE));
                case 2 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.TWO));
                case 3 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.THREE));
                case 4 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.FOUR));
                case 5 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.FIVE));
                case 6 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.SIX));
                case 7 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.SEVEN));
                case 8 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.EIGHT));
                case 9 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.NINE));
                case 10 -> level.setBlockAndUpdate(pos, state.setValue(SYMBOLS, SymbolsEnum.TEN));
            }

        }

    }


    private static SymbolsEnum GetNextSymbolInCycle(BlockState state)
    {

        SymbolsEnum sym = state.getValue(SYMBOLS);

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

            if (!pLevel.isClientSide() && pStack.getItem() == Items.AIR)
            {
                pLevel.playSound(null, pPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1f, 0.5f);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(SYMBOLS, GetNextSymbolInCycle(pState)));
                return ItemInteractionResult.SUCCESS;
            }

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
