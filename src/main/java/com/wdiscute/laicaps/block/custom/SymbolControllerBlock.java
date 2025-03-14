package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.SymbolControllerBlockEntity;
import com.wdiscute.laicaps.blockentity.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class SymbolControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public SymbolControllerBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(ACTIVE))
        {
            Random r = new Random();

            pLevel.addParticle(ParticleTypes.END_ROD,
                    pPos.getX() - 0.5 + r.nextFloat(2f),
                    pPos.getY() + 0 + r.nextFloat(1.5f),
                    pPos.getZ() - 0.5 + r.nextFloat(2f),
                    0,
                    0,
                    0);

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
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        boolean active = true;
        for (int i = 0; i <= 10; i++)
        {
            if (pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity scbe)
            {


                BlockPos zeroBP = new BlockPos(0, 0, 0);
                if (Objects.equals(scbe.getLinkedBlock(i), zeroBP))
                {
                    //getLinkedBlock is 0,0,0 so we assume theres no more linked blocks and break the for loop
                    break;
                } else
                {

                    BlockPos blockPosLinkedToController = DecodeBlockPosWithOffset(pLevel, pPos, scbe.getLinkedBlock(i));
                    BlockState symbolActiveBS = pLevel.getBlockState(DecodeBlockPosWithOffset(pLevel, pPos, scbe.getLinkedBlock(i)));
                    if (pLevel.getBlockEntity(blockPosLinkedToController) instanceof SymbolPuzzleBlockEntity spbe)
                    {
                        //gets the blockstate SYMBOL_PUZZLE_BLOCK_INACTIVE linked in SYMBOL_PUZZLE_BLOCK
                        BlockState symbolInactiveBS = pLevel.getBlockState(DecodeBlockPosWithOffset(pLevel, blockPosLinkedToController, spbe.getBlockLinkedOffset()));

                        //makes sure the blocks linked are a SYMBOL_PUZZLE_BLOCK_INACTIVE
                        if (symbolInactiveBS.is(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get()))
                        {
                            SymbolsEnum symbolInactive = symbolInactiveBS.getValue(SymbolPuzzleBlockInactive.SYMBOLS);
                            SymbolsEnum symbolActive = symbolActiveBS.getValue(SymbolPuzzleBlock.SYMBOLS);

                            if (symbolInactive != symbolActive)
                            {
                                //if the two linked blocks don't match symbols
                                active = false;
                                break;
                            }
                        } else
                        {
                            //if symbolInactiveBS isn't a SYMBOL_PUZZLE_BLOCK_INACTIVE
                            active = false;
                            break;
                        }

                    } else
                    {
                        //if symbolActiveBS isn't a SYMBOL_PUZZLE_BLOCK
                        active = false;
                        break;
                    }

                }


            }
        }


        //if no link ACTIVE = false were found then controller ACTIVE is true
        if (active)
        {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(ACTIVE, true));
        } else
        {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(ACTIVE, false));

        }


    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide)
        {
            //reset
            if (pPlayer.getOffhandItem().getItem() == ModItems.CHISEL.get())
            {
                if (pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity blockEntity) blockEntity.reset();
                return ItemInteractionResult.SUCCESS;
            }

            //debug: print all 10 linked blocks
            if (pPlayer.getMainHandItem().getItem() != ModItems.CHISEL.get() && pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity blockEntity)
            {
                System.out.println("link0: " + blockEntity.getLinkedBlock(0));
                System.out.println("link1: " + blockEntity.getLinkedBlock(1));
                System.out.println("link2: " + blockEntity.getLinkedBlock(2));
                System.out.println("link3: " + blockEntity.getLinkedBlock(3));
                System.out.println("link4: " + blockEntity.getLinkedBlock(4));
                System.out.println("link5: " + blockEntity.getLinkedBlock(5));
                System.out.println("link6: " + blockEntity.getLinkedBlock(6));
                System.out.println("link7: " + blockEntity.getLinkedBlock(7));
                System.out.println("link8: " + blockEntity.getLinkedBlock(8));
                System.out.println("link9: " + blockEntity.getLinkedBlock(9));
                System.out.println("link10: " + blockEntity.getLinkedBlock(10));

                return ItemInteractionResult.SUCCESS;
            }


            //add blockpos stored in chisel to linked
            if (pState.getValue(FACING) == Direction.EAST && pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                int newx = x;
                int newz = z;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp);

            }
            if (pState.getValue(FACING) == Direction.SOUTH && pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                int newx = z;
                int newz = x * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp);
            }
            if (pState.getValue(FACING) == Direction.WEST && pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                int newx = x * -1;
                int newz = z * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp);
            }
            if (pState.getValue(FACING) == Direction.NORTH && pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                int newx = z * -1;
                int newz = x;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp);
            }


        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE);
        pBuilder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.SYMBOL_CONTROLLER_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }


}
