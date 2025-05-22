package com.wdiscute.laicaps.block.single;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.ISystemReportExtender;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoonshadeKelpBlock extends BushBlock implements SimpleWaterloggedBlock
{

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 15);
    public static final BooleanProperty GROWN = BooleanProperty.create("grown");
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Logger log = LoggerFactory.getLogger(MoonshadeKelpBlock.class);


    public MoonshadeKelpBlock(Properties properties)
    {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(ModTags.Blocks.MOONSHADE_KELP_CAN_SURVIVE) && level.getFluidState(pos.above()).is(Fluids.WATER);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
        builder.add(CAN_GROW);
        builder.add(GROWN);
        builder.add(WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bs = defaultBlockState();
        bs = bs.setValue(AGE, 0);
        bs = bs.setValue(CAN_GROW, false);
        bs = bs.setValue(GROWN, false);
        bs = bs.setValue(WATERLOGGED, true);
        return bs;
    }


    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.MOONSHADE_KELP_CAN_SURVIVE)
                && level.getBlockState(pos).getFluidState().is(FluidTags.WATER);
    }

    @Override
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (state.getValue(GROWN))
        {
            popResource(level, pos, new ItemStack(ModItems.MOONSHADE_FRUIT.get()));
            level.setBlockAndUpdate(pos, state.setValue(GROWN, false));
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {

        int age = state.getValue(AGE);

        //grow fruit
        if (state.getValue(CAN_GROW) && random.nextFloat() > 0.70f)
        {
            level.setBlockAndUpdate(pos, state.setValue(GROWN, true));
            return;
        }

        //return if fully grown
        if (age == 15) return;


        //random chance every randomTick to go to max age so it doesn't grow more
        if (random.nextFloat() > 0.97f)
        {
            BlockState bs = defaultBlockState();
            bs = bs.setValue(AGE, 15);
            bs = bs.setValue(GROWN, false);
            bs = bs.setValue(WATERLOGGED, true);

            bs = bs.setValue(CAN_GROW, false);
            if (random.nextFloat() > 0.8f) bs = bs.setValue(CAN_GROW, true);

            level.setBlockAndUpdate(pos, bs);
            return;
        }


        //grows taller if 2 blocks above are water
        if (level.getBlockState(pos.above()).is(Blocks.WATER) && level.getBlockState(pos.above().above()).is(Blocks.WATER) && age == 3)
        {

            //same block
            BlockState bs = defaultBlockState();
            bs = bs.setValue(AGE, 15);
            bs = bs.setValue(GROWN, false);
            bs = bs.setValue(WATERLOGGED, true);

            bs = bs.setValue(CAN_GROW, false);
            if (random.nextFloat() > 0.8f) bs = bs.setValue(CAN_GROW, true);

            level.setBlockAndUpdate(pos, bs);


            //block above
            BlockState bs2 = defaultBlockState();
            bs2 = bs2.setValue(AGE, 0);
            bs2 = bs2.setValue(CAN_GROW, false);
            bs2 = bs2.setValue(WATERLOGGED, true);
            bs2 = bs2.setValue(GROWN, false);

            level.setBlockAndUpdate(pos.above(), bs2);
            return;
        }



        if (level.getBlockState(pos.above()).is(Blocks.WATER) && age < 3)
        {

            level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));

        }



    }
}
