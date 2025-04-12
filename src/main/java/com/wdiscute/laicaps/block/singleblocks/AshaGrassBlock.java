package com.wdiscute.laicaps.block.singleblocks;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class AshaGrassBlock extends RotatedPillarBlock
{

    public AshaGrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {

        if (level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos.above()))
        {
            level.setBlockAndUpdate(pos, ModBlocks.ASHA_DIRT.get().defaultBlockState());
            return;
        }

        BlockPos posTested = switch (random.nextInt(8))
        {
            case 0 -> pos.north();
            case 1 -> pos.east();
            case 2 -> pos.south();
            case 3 -> pos.west();
            case 4 -> pos.north().east();
            case 5 -> pos.north().west();
            case 6 -> pos.south().east();
            case 7 -> pos.south().west();
            default -> pos;
        };

        //if posTested (adjacent/diagonal) is dirt then becomes grass (1/8th chance on randomTick)
        if (level.getBlockState(posTested).is(ModBlocks.ASHA_DIRT) && !level.getBlockState(posTested.above()).isCollisionShapeFullBlock(level, posTested.above()))
        {
            level.setBlockAndUpdate(posTested, ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState());
            return;
        }


        //block bellow
        if (level.getBlockState(posTested.below()).is(ModBlocks.ASHA_DIRT) && !level.getBlockState(posTested).isCollisionShapeFullBlock(level, posTested))
        {
            level.setBlockAndUpdate(posTested.below(), ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState());
            return;
        }

        //block above
        if (level.getBlockState(posTested.above()).is(ModBlocks.ASHA_DIRT) && !level.getBlockState(posTested.above().above()).isCollisionShapeFullBlock(level, posTested.above().above()))
        {
            level.setBlockAndUpdate(posTested.above(), ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState());
            return;
        }

        super.randomTick(state, level, pos, random);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (level.isClientSide && stack.is(Items.BONE_MEAL)) return ItemInteractionResult.SUCCESS;

        if (stack.is(Items.BONE_MEAL))
        {
            RandomSource random = RandomSource.create();
            ServerLevel serverLevel = ((ServerLevel) level);
            ChunkGenerator chunkgenerator = serverLevel.getChunkSource().getGenerator();
            Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

            registry.getHolder(ModConfiguredFeatures.ASHA_GRASS_BLOCK_BONEMEAL).ifPresent((p_255920_) -> (p_255920_.value()).place(serverLevel, chunkgenerator, random, pos));
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
