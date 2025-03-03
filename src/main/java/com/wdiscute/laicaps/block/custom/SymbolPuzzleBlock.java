package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SymbolPuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public SymbolPuzzleBlock(Properties properties)
    {
        super(properties);
    }


    public static final EnumProperty<SymbolsEnum> SYMBOLS = EnumProperty.create("symbol", SymbolsEnum.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(ACTIVE))
        {
            pLevel.addParticle(
                    new DustParticleOptions(new Vec3(0.557f, 0.369f, 0.961f).toVector3f(), 3.0F){},
                    (double) pPos.getX() + 0.5f,
                    (double) pPos.getY() + 1.2f,
                    (double) pPos.getZ() + 0.5f,
                    3.0,
                    3.0,
                    3.0
            );
        }
    }


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
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide() && pStack.getItem() == ModItems.CHISEL.get())
        {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof SymbolPuzzleBlockEntity blockEntity)
            {
                blockEntity.setBlockLinked(pStack.get(ModDataComponentTypes.COORDINATES.get()));
                return ItemInteractionResult.SUCCESS;
            }
        }

        if (!pLevel.isClientSide())
        {
            SymbolsEnum next = CycleSymbol(pState.getValue(SYMBOLS));
            pState = pState.setValue(SYMBOLS, next);

            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof SymbolPuzzleBlockEntity blockEntity)
            {
                if (pLevel.getBlockState(blockEntity.getBlockLinked()).getBlock() == ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get())
                {
                    SymbolsEnum symbollinked = pLevel.getBlockState(blockEntity.getBlockLinked()).getValue(SymbolPuzzleBlockInactive.SYMBOLS);
                    if (pState.getValue(SYMBOLS) == symbollinked)
                    {
                        System.out.println("True and real");
                        pState = pState.setValue(ACTIVE, true);
                        pLevel.setBlockAndUpdate(pPos, pState);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
            pState = pState.setValue(ACTIVE, false);
        }
        //always returns success
        pLevel.setBlockAndUpdate(pPos, pState);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState bs = defaultBlockState().setValue(ACTIVE, false);
        bs = bs.setValue(SYMBOLS, SymbolsEnum.TWO);
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
        pBuilder.add(ACTIVE);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get().create(pPos, pState);
    }
}
