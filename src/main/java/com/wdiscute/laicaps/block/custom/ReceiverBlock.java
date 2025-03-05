package com.wdiscute.laicaps.block.custom;

import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.ReceiverBlockEntity;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.blockentity.TickableBlockEntity;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
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

import java.util.UUID;


public class ReceiverBlock extends Block implements EntityBlock
{

    public static BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static BooleanProperty NORTH_ENABLED = BooleanProperty.create("north_enabled");
    public static BooleanProperty WEST_ENABLED = BooleanProperty.create("west_enabled");
    public static BooleanProperty SOUTH_ENABLED = BooleanProperty.create("south_enabled");
    public static BooleanProperty EAST_ENABLED = BooleanProperty.create("east_enabled");
    public static BooleanProperty NORTH_ACTIVE = BooleanProperty.create("north_active");
    public static BooleanProperty WEST_ACTIVE = BooleanProperty.create("west_active");
    public static BooleanProperty SOUTH_ACTIVE = BooleanProperty.create("south_active");
    public static BooleanProperty EAST_ACTIVE = BooleanProperty.create("east_active");


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level pLevel, BlockPos pPos, Player player, BlockHitResult hitResult)
    {
        if (state.getValue(ACTIVE) && !pLevel.isClientSide())
        {

            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof ReceiverBlockEntity blockEntity)
            {
                if (!blockEntity.CheckPlayerUUID(player.getUUID()))
                    blockEntity.SavePlayerUUID(player.getUUID());
                    //summon drops
                return InteractionResult.SUCCESS;
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(ACTIVE))
        {

            pLevel.addParticle(ParticleTypes.END_ROD, pPos.getX() + 0.5, pPos.getY() + 1.5, pPos.getZ() + 0.5,
                    0.05, 0.05, 0.05);

        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        pState = pState.setValue(NORTH_ENABLED, false);
        pState = pState.setValue(EAST_ENABLED, false);
        pState = pState.setValue(SOUTH_ENABLED, false);
        pState = pState.setValue(WEST_ENABLED, false);

        //NORTH_ENABLED etc
        for (int g = 0; g < 5; g++)
        {
            for (int k = 0; k < 4; k++)
            {
                Direction dir = Direction.NORTH;
                if (k == 1) dir = Direction.EAST;
                if (k == 2) dir = Direction.SOUTH;
                if (k == 3) dir = Direction.WEST;

                for (int i = 0; i <= 10; i++)
                {
                    // initialize a new variable currentPos which will be given a .direction.bla() for each i increase
                    BlockPos currentPos = pPos.relative(dir);
                    if (g == 1) currentPos = currentPos.above();
                    if (g == 2) currentPos = currentPos.below();
                    if (g == 3) currentPos = currentPos.above().above();
                    if (g == 4) currentPos = currentPos.below().below();

                    for (int j = 0; j < i; j++)
                        currentPos = currentPos.relative(dir);


                    BlockState bsds = pLevel.getBlockState(currentPos).getBlock().defaultBlockState();

                    if (bsds == ModBlocks.SENDER_PUZZLE_BLOCK.get().defaultBlockState())
                    {
                        if (dir == Direction.NORTH)
                            pState = pState.setValue(NORTH_ENABLED, true);

                        if (dir == Direction.EAST)
                            pState = pState.setValue(EAST_ENABLED, true);

                        if (dir == Direction.SOUTH)
                            pState = pState.setValue(SOUTH_ENABLED, true);

                        if (dir == Direction.WEST)
                            pState = pState.setValue(WEST_ENABLED, true);
                    }

                }

            }
        }

        pLevel.setBlockAndUpdate(pPos, pState);

        //NORTH_ACTIVE etc
        for (int k = 0; k < 4; k++)
        {
            Direction dir = Direction.NORTH;
            if (k == 1) dir = Direction.EAST;
            if (k == 2) dir = Direction.SOUTH;
            if (k == 3) dir = Direction.WEST;

            boolean foundBlock = false;

            for (int i = 0; i <= 10; i++)
            {
                // initialize a new variable currentPos which will be given a .direction.bla() for each i increase
                BlockPos currentPos = pPos.relative(dir);
                for (int j = 0; j < i; j++)
                {
                    currentPos = currentPos.relative(dir);
                }
                BlockState blockStateBeingTested = pLevel.getBlockState(currentPos);

                if (blockStateBeingTested.getBlock().defaultBlockState() == ModBlocks.SENDER_PUZZLE_BLOCK.get().defaultBlockState()
                        && blockStateBeingTested.getValue(HorizontalDirectionalBlock.FACING) == dir.getOpposite())
                {
                    foundBlock = true;
                    break;
                }

                //if block is not air finished looking in this direction
                if (!blockStateBeingTested.getBlock().defaultBlockState().isAir())
                {
                    break;
                }
            }
            if (foundBlock)
            {
                ChangeDirActive(pLevel, pPos, dir, true);
            } else
            {
                ChangeDirActive(pLevel, pPos, dir, false);
            }
        }

        CheckActive(pLevel, pPos);
    }

    private void CheckActive(Level level, BlockPos pos)
    {
        BlockState bs = level.getBlockState(pos);
        boolean shouldBeActive = true;

        if (bs.getValue(NORTH_ENABLED) && !bs.getValue(NORTH_ACTIVE))
            shouldBeActive = false;

        if (bs.getValue(WEST_ENABLED) && !bs.getValue(WEST_ACTIVE))
            shouldBeActive = false;

        if (bs.getValue(SOUTH_ENABLED) && !bs.getValue(SOUTH_ACTIVE))
            shouldBeActive = false;

        if (bs.getValue(EAST_ENABLED) && !bs.getValue(EAST_ACTIVE))
            shouldBeActive = false;

        if (
                !bs.getValue(NORTH_ENABLED) &&
                        !bs.getValue(WEST_ENABLED) &&
                        !bs.getValue(SOUTH_ENABLED) &&
                        !bs.getValue(EAST_ENABLED))
        {
            shouldBeActive = false;
        }

        //System.out.println("ran check with " + shouldBeActive);
        level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(ACTIVE, shouldBeActive));

    }

    private void ChangeDirActive(Level level, BlockPos pos, Direction dir, boolean bActive)
    {
        BlockState bs = level.getBlockState(pos);
        //System.out.println("Changed active with dir " + dir + " and bactive " + bActive);

        if (dir == Direction.NORTH)
            level.setBlockAndUpdate(pos, bs.setValue(NORTH_ACTIVE, bActive));

        if (dir == Direction.WEST)
            level.setBlockAndUpdate(pos, bs.setValue(WEST_ACTIVE, bActive));

        if (dir == Direction.SOUTH)
            level.setBlockAndUpdate(pos, bs.setValue(SOUTH_ACTIVE, bActive));

        if (dir == Direction.EAST)
            level.setBlockAndUpdate(pos, bs.setValue(EAST_ACTIVE, bActive));
    }

    public ReceiverBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH_ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH_ENABLED, false));
        this.registerDefaultState(this.defaultBlockState().setValue(WEST_ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(WEST_ENABLED, false));
        this.registerDefaultState(this.defaultBlockState().setValue(SOUTH_ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(SOUTH_ENABLED, false));
        this.registerDefaultState(this.defaultBlockState().setValue(EAST_ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(EAST_ENABLED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE);
        pBuilder.add(NORTH_ACTIVE);
        pBuilder.add(NORTH_ENABLED);
        pBuilder.add(WEST_ACTIVE);
        pBuilder.add(WEST_ENABLED);
        pBuilder.add(SOUTH_ACTIVE);
        pBuilder.add(SOUTH_ENABLED);
        pBuilder.add(EAST_ACTIVE);
        pBuilder.add(EAST_ENABLED);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.RECEIVER_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }
}




