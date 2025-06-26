package com.wdiscute.laicaps.block.seagrass;

import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoonshadeTallSeagrassBlock extends TallSeagrassBlock
{

    public MoonshadeTallSeagrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state)
    {
        return new ItemStack(ModBlocks.MOONSHADE_SEAGRASS);
    }
}
