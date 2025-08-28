package com.wdiscute.laicaps.block.hidden;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.util.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HiddenControllerBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public HiddenControllerBlock(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {

        //we only care about main hand to prevent double stuff
        if (hand == InteractionHand.OFF_HAND) return ItemInteractionResult.FAIL;

        if (stack.is(Items.BARRIER) && state.getValue(ACTIVE) && !level.isClientSide && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity hcbe)
        {
            hcbe.resetPlayersSaved();
            player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.players_reset"), true);
            return ItemInteractionResult.SUCCESS;
        }

        //add blockpos stored in chisel to linked
        if (stack.is(ModItems.CHISEL) && !level.isClientSide)
        {
            if (state.getValue(FACING) == Direction.EAST && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = x;
                int newz = z;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp, player);

            }
            if (state.getValue(FACING) == Direction.SOUTH && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = z;
                int newz = x * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.WEST && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity be)
            {
                int x = stack.get(ModDataComponents.COORDINATES.get()).getX() - pos.getX();
                int z = stack.get(ModDataComponents.COORDINATES.get()).getZ() - pos.getZ();
                int y = stack.get(ModDataComponents.COORDINATES.get()).getY() - pos.getY();

                int newx = x * -1;
                int newz = z * -1;

                BlockPos bp = new BlockPos(newx, y, newz);
                be.setNextLinkedBlock(bp, player);
            }
            if (state.getValue(FACING) == Direction.NORTH && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity be)
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

        if (state.getValue(ACTIVE) && !level.isClientSide && level.getBlockEntity(pos) instanceof HiddenControllerBlockEntity hcbe)
        {
            hcbe.CanPlayerObtainDrops(player);
        }

        if (!state.getValue(ACTIVE))
        {
            return ItemInteractionResult.FAIL;
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (Minecraft.getInstance().player.getMainHandItem().is(ModItems.CHISEL.get()) || Minecraft.getInstance().player.isCreative())
        {

            level.addParticle(
                    ParticleTypes.CLOUD,
                    pos.getX() + 0.5f,
                    pos.getY() + 0.5f,
                    pos.getZ() + 0.5f,
                    0, 0, 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE);
        pBuilder.add(FACING);
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (context instanceof EntityCollisionContext ecc && ecc.getEntity() instanceof Player player)
        {
            if (player.isCreative() || state.getValue(ACTIVE))
                return switch (state.getValue(FACING))
                {
                    case EAST, WEST -> Shapes.or(
                            Block.box(1, 0, 1, 15, 13, 15),
                            Block.box(4, 13, 1, 12, 14, 15));
                    default -> Shapes.or(
                            Block.box(1, 0, 1, 15, 13, 15),
                            Block.box(1, 13, 4, 15, 14, 12));
                };
            else
                return Block.box(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        }

        return switch (state.getValue(FACING))
        {
            case EAST, WEST -> Shapes.or(
                    Block.box(1, 0, 1, 15, 13, 15),
                    Block.box(4, 13, 1, 12, 14, 15));
            default -> Shapes.or(
                    Block.box(1, 0, 1, 15, 13, 15),
                    Block.box(1, 13, 4, 15, 14, 12));
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(ACTIVE))
        {
            return Block.box(1.0F, 0.0F, 1.0F, 15.0F, 14.0F, 15.0F);
        }

        return Block.box(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        bs = bs.setValue(ACTIVE, true);
        bs = bs.setValue(FACING, context.getHorizontalDirection().getOpposite());
        return bs;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.HIDDEN_CONTROLLER_BLOCK.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return TickableBlockEntity.getTicketHelper(pLevel);
    }
}
