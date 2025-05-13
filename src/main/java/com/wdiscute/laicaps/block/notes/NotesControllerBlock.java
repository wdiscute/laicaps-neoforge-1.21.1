package com.wdiscute.laicaps.block.notes;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class NotesControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{

    public static final IntegerProperty WAVES = IntegerProperty.create("waves", 1, 5);
    public static final IntegerProperty WAVES_COMPLETE = IntegerProperty.create("waves_complete", 0, 5);
    public static final BooleanProperty WAVE_IN_PROGRESS = BooleanProperty.create("wave_in_progress");

    public NotesControllerBlock(Properties properties)
    {
        super(properties);

        BlockState state = defaultBlockState();

        state = state.setValue(WAVES, 5);
        state = state.setValue(WAVES_COMPLETE, 0);
        state = state.setValue(WAVE_IN_PROGRESS, false);
        this.registerDefaultState(state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(state.getValue(WAVES_COMPLETE).equals(state.getValue(WAVES)))
        {
            Random r = new Random();
            
            level.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() - 0.5 + r.nextFloat(2f),
                    pos.getY() + 0 + r.nextFloat(1.5f),
                    pos.getZ() - 0.5 + r.nextFloat(2f),
                    0,
                    0,
                    0);
        }
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult HitResult)
    {
        if (hand == InteractionHand.OFF_HAND) return ItemInteractionResult.FAIL;

        if (hand == InteractionHand.MAIN_HAND && !level.isClientSide)
        {

            if(stack.is(Items.BARRIER) && level.getBlockEntity(pos) instanceof NotesControllerBlockEntity ncbe)
            {
                ncbe.resetPlayersSaved();
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.players_reset"), true);
                return ItemInteractionResult.SUCCESS;
            }

            //add blockpos stored in chisel to linked
            if (stack.is(ModItems.CHISEL))
            {
                //check if blocked stored in chisel is a notes block

                //if not, returns with no hand animation and displays error message
                if (!level.getBlockState(stack.get(ModDataComponents.COORDINATES.get())).is(ModBlocks.NOTES_PUZZLE_BLOCK.get()))
                {
                    return ItemInteractionResult.FAIL;
                }

                //if yes, then store the block linked's relative coords
                if (level.getBlockEntity(pos) instanceof NotesControllerBlockEntity be)
                {
                    if (state.getValue(FACING) == Direction.EAST)
                    {
                        int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                        int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                        int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                        int newx = x;
                        int newz = z;

                        BlockPos bp = new BlockPos(newx, y, newz);
                        be.setNextLinkedBlock(bp, player);

                    }
                    if (state.getValue(FACING) == Direction.SOUTH)
                    {
                        int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                        int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                        int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                        int newx = z;
                        int newz = x * -1;

                        BlockPos bp = new BlockPos(newx, y, newz);
                        be.setNextLinkedBlock(bp, player);
                    }
                    if (state.getValue(FACING) == Direction.WEST)
                    {
                        int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                        int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                        int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                        int newx = x * -1;
                        int newz = z * -1;

                        BlockPos bp = new BlockPos(newx, y, newz);
                        be.setNextLinkedBlock(bp, player);
                    }
                    if (state.getValue(FACING) == Direction.NORTH)
                    {
                        int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                        int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                        int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                        int newx = z * -1;
                        int newz = x;

                        BlockPos bp = new BlockPos(newx, y, newz);
                        be.setNextLinkedBlock(bp, player);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }

            //if item is not chisel sends start command
            if (level.getBlockEntity(pos) instanceof NotesControllerBlockEntity ncbe)
            {
                ncbe.start();
                ncbe.CanPlayerObtainDrops(player);
            }
        }


        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(FACING))
        {
            case EAST, WEST:
                return Shapes.or(Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(4, 13, 1, 12, 14, 15));
            default:
                return Shapes.or(Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(1, 13, 4, 15, 14, 12));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
        pBuilder.add(WAVES);
        pBuilder.add(WAVES_COMPLETE);
        pBuilder.add(WAVE_IN_PROGRESS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        bs = bs.setValue(WAVES, 1);
        bs = bs.setValue(WAVES_COMPLETE, 0);
        bs = bs.setValue(WAVE_IN_PROGRESS, false);

        return bs;
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
