package com.wdiscute.laicaps.block.single;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StarfliesBlock extends Block
{

    public StarfliesBlock(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (context instanceof EntityCollisionContext ecc && ecc.getEntity() instanceof Player player)
        {
            if (player.isCreative() || context.isHoldingItem(ModItems.STARFLIES_JAR.get()) || context.isHoldingItem(ModItems.JAR.get()))
                return Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F);
            else
                return Block.box(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }

        return Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player)
    {
        return new ItemStack(ModItems.STARFLIES_JAR.get());
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        Random r = new Random();
        if (state.getValue(ACTIVE) && r.nextFloat(1) > 0.5f)
        {
            level.addParticle(
                    ModParticles.LUNARVEIL_PARTICLES.get(),
                    //randomness of 4, +0.5 to center to the block, -2 to offset the randomness by 2
                    (double) pos.getX() + r.nextFloat(4) + 0.5 - 2f,
                    (double) pos.getY() + 0.2f + r.nextFloat(4) - 2,
                    (double) pos.getZ() + r.nextFloat(4) + 0.5 - 2f,
                    0,
                    0,
                    0
            );
        }


        if(Minecraft.getInstance().player.getMainHandItem().is(ModItems.STARFLIES_JAR.asItem()))
        {

            level.addParticle(
                    ParticleTypes.CLOUD,
                    pos.getX() + 0.5f,
                    pos.getY() + 0.5f,
                    pos.getZ() + 0.5f,
                    0,0,0);
        }

    }
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide && stack.is(ModItems.JAR))
        {
            stack.consume(1, player);
            player.setItemInHand(hand, stack);
            player.addItem(new ItemStack(ModItems.STARFLIES_JAR.get()));

            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            return ItemInteractionResult.SUCCESS;
        }

        if (level.isClientSide && stack.is(ModItems.JAR)) return ItemInteractionResult.SUCCESS;

        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }




    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (level.getDayTime() - (numberOfDays * 24000L) > 12000 && level.getDayTime() - (numberOfDays * 24000L) < 22500)
        {
            return defaultBlockState().setValue(ACTIVE, true);
        }

        return defaultBlockState().setValue(ACTIVE, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (level.getDayTime() - (numberOfDays * 24000L) > 12000 && level.getDayTime() - (numberOfDays * 24000L) < 22500)
        {

            if (!state.getValue(ACTIVE))
                level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
            return;
        }

        if (state.getValue(ACTIVE))
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));

    }

}
