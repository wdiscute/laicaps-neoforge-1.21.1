package com.wdiscute.laicaps.block.singleblocks;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SaltBlock extends Block
{
    public SaltBlock(Properties properties)
    {
        super(properties);
    }

    public static final EnumProperty<RedstoneSide> NORTH = RedStoneWireBlock.NORTH;
    public static final EnumProperty<RedstoneSide> SOUTH = RedStoneWireBlock.SOUTH;
    public static final EnumProperty<RedstoneSide> EAST = RedStoneWireBlock.EAST;
    public static final EnumProperty<RedstoneSide> WEST = RedStoneWireBlock.WEST;


    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston)
    {
        BlockState bs = state;

        bs = bs.setValue(NORTH, (level.getBlockState(pos.north()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(SOUTH, (level.getBlockState(pos.south()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(EAST, (level.getBlockState(pos.east()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(WEST, (level.getBlockState(pos.west()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);

        level.setBlockAndUpdate(pos, bs);

        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }


    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = ModBlocks.SALT.get().defaultBlockState();
        bs = bs.setValue(NORTH, (context.getLevel().getBlockState(context.getClickedPos().north()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(SOUTH, (context.getLevel().getBlockState(context.getClickedPos().south()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(EAST, (context.getLevel().getBlockState(context.getClickedPos().east()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        bs = bs.setValue(WEST, (context.getLevel().getBlockState(context.getClickedPos().west()).is(ModBlocks.SALT)) ? RedstoneSide.SIDE :RedstoneSide.NONE);
        return bs;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(NORTH, SOUTH, EAST, WEST);
    }


}




