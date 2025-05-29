package com.wdiscute.laicaps.block.combat;

import com.mojang.serialization.MapCodec;
import com.sun.jna.platform.win32.WinRas;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CombatControllerBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock
{
    public CombatControllerBlock(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty MOBS_TO_SPAWN = IntegerProperty.create("mobs_to_spawn", 0, 7);
    public static final IntegerProperty MOBS_KILLED = IntegerProperty.create("mobs_killed", 0, 7);


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (state.getValue(MOBS_KILLED).equals(state.getValue(MOBS_TO_SPAWN)))
        {
            level.addParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5 + random.nextFloat() * 2 - 1,
                    pos.getY() + random.nextFloat() * 1.5,
                    pos.getZ() + 0.5 + random.nextFloat() * 2 - 1,
                    0,
                    0,
                    0);
        }


        super.animateTick(state, level, pos, random);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {

        if (!level.isClientSide && level.getBlockEntity(pos) instanceof CombatControllerBlockEntity ccbe && hand == InteractionHand.MAIN_HAND)
        {
            if (stack.is(Items.BARRIER))
            {
                ccbe.resetPlayersSaved();
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.players_reset"), true);
                return ItemInteractionResult.SUCCESS;
            }

            ccbe.CanPlayerObtainDrops(player);

            ccbe.start();

        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING);
        pBuilder.add(WATERLOGGED);
        pBuilder.add(MOBS_TO_SPAWN);
        pBuilder.add(MOBS_KILLED);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        if (context.getLevel().getFluidState(context.getClickedPos()).is(FluidTags.WATER))
            bs = bs.setValue(WATERLOGGED, true);
        else
            bs = bs.setValue(WATERLOGGED, false);

        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        return bs;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.COMBAT_CONTROLLER_BLOCK.get().create(pPos, pState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(FACING))
        {
            case EAST, WEST:
                return Shapes.or(
                        Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(4, 13, 1, 12, 14, 15));
            default:
                return Shapes.or(
                        Block.box(1, 0, 1, 15, 13, 15),
                        Block.box(1, 13, 4, 15, 14, 12));
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHBelper(pLevel);
    }

}




