package com.wdiscute.laicaps.block.custom;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LunarveilBlock extends BushBlock
{
    public LunarveilBlock(Properties properties)
    {
        super(properties);
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
