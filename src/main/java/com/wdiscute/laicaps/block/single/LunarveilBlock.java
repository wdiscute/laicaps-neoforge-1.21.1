package com.wdiscute.laicaps.block.single;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class LunarveilBlock extends BushBlock
{
    public LunarveilBlock(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        Random r = new Random();
        if (state.getValue(OPEN) && r.nextFloat(1) > 0.8f)
        {
            level.addParticle(
                    ModParticles.LUNARVEIL_PARTICLES.get(),
                    //randomness of 4, +0.5 to center to the block, -2 to offset the randomness by 2
                    (double) pos.getX() + r.nextFloat(4) + 0.5 - 2f,
                    (double) pos.getY() + 0.2f + r.nextFloat(2),
                    (double) pos.getZ() + r.nextFloat(4) + 0.5 - 2f,
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 15.0F, 11.0F);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState())
            return true;

        if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_DIRT.get().defaultBlockState())
            return true;

        return false;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }




    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (level.getDayTime() - (numberOfDays * 24000L) > 12000 && level.getDayTime() - (numberOfDays * 24000L) < 22500)
        {
            return defaultBlockState().setValue(OPEN, true);
        }

        return defaultBlockState().setValue(OPEN, false);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (level.getDayTime() - (numberOfDays * 24000L) > 12000 && level.getDayTime() - (numberOfDays * 24000L) < 22500)
        {

            if (!state.getValue(OPEN))
                level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
            return;
        }

        if (state.getValue(OPEN))
            level.setBlockAndUpdate(pos, state.setValue(OPEN, false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
    }

}
