package com.wdiscute.laicaps.block.custom.notes;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.blockentity.NotesControllerBlockEntity;
import com.wdiscute.laicaps.blockentity.TickableBlockEntity;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class NotesControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public NotesControllerBlock(Properties properties)
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

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {

    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide)
        {
            //debug: print all 10 linked blocks
            if (pPlayer.getMainHandItem().isEmpty() && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity blockEntity)
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

                return ItemInteractionResult.SUCCESS;
            }

            if (!pPlayer.getMainHandItem().is(ModItems.CHISEL) && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity blockEntity)
            {
                //sends signal to start
                blockEntity.start();
            }

            //add blockpos stored in chisel to linked
            if(pStack.is(ModItems.CHISEL))
            {
                if (pState.getValue(FACING) == Direction.EAST && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity be)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x;
                    int newz = z;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    be.setNextLinkedBlock(bp);

                }
                if (pState.getValue(FACING) == Direction.SOUTH && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity be)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = z;
                    int newz = x * -1;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    be.setNextLinkedBlock(bp);
                }
                if (pState.getValue(FACING) == Direction.WEST && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity be)
                {
                    int x = pStack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = pStack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = pStack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x * -1;
                    int newz = z * -1;

                    BlockPos bp = new BlockPos(newx, y, newz);
                    be.setNextLinkedBlock(bp);
                }
                if (pState.getValue(FACING) == Direction.NORTH && pLevel.getBlockEntity(pPos) instanceof NotesControllerBlockEntity be)
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
        return ModBlockEntity.NOTES_CONTROLLER_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }


}
