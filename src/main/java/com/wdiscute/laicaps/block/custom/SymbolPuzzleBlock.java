package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
                    new DustParticleOptions(new Vec3(0.557f, 0.369f, 0.961f).toVector3f(), 3.0F)
                    {
                    },
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

            }

            if (!pLevel.isClientSide() && pStack.getItem() == Items.AIR)
            {

                System.out.println("block changed to diamond: " + getLinkedBlockPos(pLevel, pPos));
                pLevel.setBlockAndUpdate(getLinkedBlockPos(pLevel, pPos), Blocks.DIAMOND_BLOCK.defaultBlockState());

            }

        }

        return ItemInteractionResult.SUCCESS;
    }


    private BlockPos getLinkedBlockPos(Level plevel, BlockPos pPos)
    {

        BlockState pState = plevel.getBlockState(pPos);
        if (plevel.getBlockEntity(pPos) instanceof SymbolPuzzleBlockEntity be)
        {

            if (pState.getValue(FACING) == Direction.SOUTH)
            {
                //finalx = be.getZ() * -1;
                //finalz = be.getX();
                System.out.println("SOUTH");
                return new BlockPos(pPos.getX() + (be.getZ() * -1), pPos.getY() + be.getY(), pPos.getZ() + be.getX());

            }

            if (pState.getValue(FACING) == Direction.WEST)
            {
                //finalx = be.getX() * -1;
                //finalz = be.getZ() * -1;
                System.out.println("WEST");
                return new BlockPos(pPos.getX() + (be.getX() * -1), pPos.getY() + be.getY(), pPos.getZ() + (be.getZ() * -1));
            }

            if (pState.getValue(FACING) == Direction.NORTH)
            {
                //finalx = be.getZ();
                //finalz = be.getX() * -1;
                System.out.println("NORTH");
                return new BlockPos(pPos.getX() + be.getZ(), pPos.getY() + be.getY(), pPos.getZ() + (be.getX() * -1));
            }
            System.out.println("EAST");
            return new BlockPos(pPos.getX() + be.getX(), pPos.getY() + be.getY(), pPos.getZ() + be.getZ());
        }
        return new BlockPos(0, 0, 0);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState bs = defaultBlockState().setValue(ACTIVE, false);

        Random r = new Random();
        int randInt = r.nextInt(10);
        System.out.println(randInt);

        bs = bs.setValue(SYMBOLS, SymbolsEnum.ONE);
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
