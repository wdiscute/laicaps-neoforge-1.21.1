package com.wdiscute.laicaps.block.custom;

import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.SymbolControllerBlockEntity;
import com.wdiscute.laicaps.blockentity.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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

public class SymbolControllerBlock extends Block implements EntityBlock
{
    public SymbolControllerBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
    }

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(ACTIVE))
        {

            pLevel.addParticle(
                    new DustParticleOptions(new Vec3(0.557f, 0.369f, 0.961f).toVector3f(), 3.0F) //FLOAT = SCALE
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

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        for (int i = 0; i <= 10; i++)
        {
            if (pLevel.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity blockEntity)
            {
                //if first link is 0,0,0 cancels
                if (Objects.equals(blockEntity.getLinkedBlock(1), new BlockPos(0, 0, 0)))
                {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(ACTIVE, false));
                    return;
                }

                //if link ACTIVE is false then contreller ACTIVE = false
                BlockState bs = pLevel.getBlockState(blockEntity.getLinkedBlock(i));
                if (bs.getBlock() == ModBlocks.SYMBOL_PUZZLE_BLOCK.get())
                {
                    if (!bs.getValue(SymbolPuzzleBlock.ACTIVE))
                    {
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(ACTIVE, false));
                        return;
                    }
                }
            }
        }

        //if no link ACTIVE = false were found then controller ACTIVE is true
        pLevel.setBlockAndUpdate(pPos, pState.setValue(ACTIVE, true));


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
            }

            //print all 10 linked blocks
            if (pPlayer.getMainHandItem().getItem() != ModItems.CHISEL.get())
            {
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof SymbolControllerBlockEntity blockEntity)
                {
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

                }
            }

            //add blockpos stored in chisel to linked
            if (pPlayer.getMainHandItem().getItem() == ModItems.CHISEL.get())
            {
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof SymbolControllerBlockEntity blockEntity)
                {
                    blockEntity.setNextLinkedBlock(pStack.get(ModDataComponentTypes.COORDINATES.get()));
                }
            }

        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE);

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
