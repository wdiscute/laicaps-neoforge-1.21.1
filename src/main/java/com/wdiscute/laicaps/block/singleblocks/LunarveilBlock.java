package com.wdiscute.laicaps.block.singleblocks;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
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

public class LunarveilBlock extends BushBlock
{
    public LunarveilBlock(Properties properties)
    {
        super(properties);
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = Block.box((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)15.0F, (double)11.0F);
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

    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(OPEN))
        {
            pLevel.addParticle(
                    new DustParticleOptions(new Vec3(0.557f, 0.369f, 0.961f).toVector3f(), 3.0F)
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
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return defaultBlockState().setValue(OPEN, false);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (level.getDayTime() - (numberOfDays * 24000L) > 12000 && level.getDayTime() - (numberOfDays * 24000L) < 22500)
        {

            if (!state.getValue(LunarveilBlock.OPEN))
                level.setBlockAndUpdate(pos, state.setValue(LunarveilBlock.OPEN, true));
            return;
        }

        if (state.getValue(LunarveilBlock.OPEN))
            level.setBlockAndUpdate(pos, state.setValue(LunarveilBlock.OPEN, false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
    }

}
