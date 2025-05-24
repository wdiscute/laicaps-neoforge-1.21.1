package com.wdiscute.laicaps.block.single;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class IllumaBlock extends BushBlock implements SimpleWaterloggedBlock
{

    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(!state.getValue(OPEN)) return;
        Random r = new Random();
        if (r.nextInt(6) == 4)
        {
            level.addParticle(ModParticles.WATER_FLOWER_PARTICLES.get(),
                    pos.getX() + 0.4f + r.nextFloat(0.2f),
                    pos.getY() + 0.7f,
                    pos.getZ() + 0.4f + r.nextFloat(0.2f),
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        //open when raining
        if (state.getValue(WATERLOGGED))
        {
            if (!state.getValue(OPEN))
            {
                level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
            }
            return;
        }

        if (level.rainLevel == 1f)
        {
            level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
        } else
        {
            level.setBlockAndUpdate(pos, state.setValue(OPEN, false));
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState().setValue(OPEN, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
        bs = bs.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
        return bs;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.ILLUMA_CAN_SURVIVE);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.ILLUMA_CAN_SURVIVE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
        pBuilder.add(WATERLOGGED);
    }


    public IllumaBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = Block.box((double) 5.0F, (double) 0.0F, (double) 5.0F, (double) 11.0F, (double) 15.0F, (double) 11.0F);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }


}
