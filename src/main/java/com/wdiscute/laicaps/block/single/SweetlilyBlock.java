package com.wdiscute.laicaps.block.single;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SweetlilyBlock extends BushBlock
{
    public SweetlilyBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 13.0F, 11.0F);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
            return true;

        if (state.is(ModBlocks.ASHA_DIRT.get()))
            return true;

        return false;
    }
}
