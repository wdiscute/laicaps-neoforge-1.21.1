package com.wdiscute.laicaps.block.symbol;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(ACTIVE))
        {
            Random r = new Random();

            pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    pPos.getX() - 0.5 + r.nextFloat(2f),
                    pPos.getY() + 0 + r.nextFloat(1.5f),
                    pPos.getZ() - 0.5 + r.nextFloat(2f),
                    0,
                    0,
                    0);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState pState, Level level, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (!level.isClientSide)
        {

            if(stack.is(Items.BARRIER) && level.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity scbe)
            {
                scbe.resetPlayersSaved();
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.players_reset"), true);
                return ItemInteractionResult.SUCCESS;
            }

            //disable ticking when anvil is on main hand
            if (stack.is(Items.ANVIL) && level.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity blockEntity)
            {
                player.sendSystemMessage(Component.literal("Set Ticking to " + blockEntity.setTicking()));
                level.playSound(null, pPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1, 0.5f);
                return ItemInteractionResult.SUCCESS;
            }

            //add blockpos stored in chisel to linked
            if(stack.is(ModItems.CHISEL) && level.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                if (pState.getValue(FACING) == Direction.EAST)
                {
                    int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x;
                    int newz = z;

                    be.setNextLinkedBlock(new BlockPos(newx, y, newz));
                }
                if (pState.getValue(FACING) == Direction.SOUTH)
                {
                    int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = z;
                    int newz = x * -1;

                    be.setNextLinkedBlock(new BlockPos(newx, y, newz));
                }
                if (pState.getValue(FACING) == Direction.WEST)
                {
                    int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = x * -1;
                    int newz = z * -1;

                    be.setNextLinkedBlock(new BlockPos(newx, y, newz));
                }
                if (pState.getValue(FACING) == Direction.NORTH)
                {
                    int x = stack.get(ModDataComponentTypes.COORDINATES.get()).getX() - pPos.getX();
                    int z = stack.get(ModDataComponentTypes.COORDINATES.get()).getZ() - pPos.getZ();
                    int y = stack.get(ModDataComponentTypes.COORDINATES.get()).getY() - pPos.getY();

                    int newx = z * -1;
                    int newz = x;

                    be.setNextLinkedBlock(new BlockPos(newx, y, newz));
                }
                return ItemInteractionResult.SUCCESS;
            }

            if(pState.getValue(ACTIVE) && level.getBlockEntity(pPos) instanceof SymbolControllerBlockEntity be)
            {
                be.CanPlayerObtainDrops(player);
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
