package com.wdiscute.laicaps.block.custom;

import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FloweringOakheartLeavesBlock extends LeavesBlock
{
    public FloweringOakheartLeavesBlock(Properties properties)
    {
        super(properties);
    }

    public static final IntegerProperty AGE = IntegerProperty.create("age", 1, 4);
    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (stack.getItem() == Blocks.AIR.asItem() || stack.getItem() == ModItems.OAKHEART_BERRIES.get())
        {
            if (state.getValue(AGE) == 4)
            {
                BlockPos posToSpawnItem = pos;
                if(level.getBlockState(pos.below()).isAir()) posToSpawnItem = pos.below();

                ItemEntity itemEntity = new ItemEntity(level, posToSpawnItem.getX() + 0.5f, posToSpawnItem.getY(), posToSpawnItem.getZ() + 0.5f, new ItemStack(ModItems.OAKHEART_BERRIES.get()));
                level.addFreshEntity(itemEntity);
                //itemEntity.setDeltaMovement(0,0,0);

                level.setBlockAndUpdate(pos, state.setValue(AGE, 1));

                return ItemInteractionResult.SUCCESS;
            }
        }

        if (stack.getItem() == Items.SHEARS && state.getValue(AGE) != 0)
        {
            state = state.setValue(SHEARED, true);
            state.setValue(PERSISTENT, true);
            level.setBlockAndUpdate(pos, state);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (this.decaying(state))
        {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }


        //return if it's sheared so it doesn't grow
        if (state.getValue(SHEARED)) return;
        if ((int) (Math.random() * 30 + 1) > 29) // 1/30 chance to tick
        {
            if (state.getValue(AGE) == 3) level.setBlockAndUpdate(pos, state.setValue(AGE, 4));
            if (state.getValue(AGE) == 2) level.setBlockAndUpdate(pos, state.setValue(AGE, 3));
            if (state.getValue(AGE) == 1) level.setBlockAndUpdate(pos, state.setValue(AGE, 2));
        }
    }


    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
        pBuilder.add(SHEARED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState bs = defaultBlockState().setValue(SHEARED, false);
        bs = bs.setValue(AGE, 1);
        bs = bs.setValue(PERSISTENT, true);
        return bs;
    }


}
