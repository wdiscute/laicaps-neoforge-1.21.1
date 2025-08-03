package com.wdiscute.laicaps.worldgen;

import com.mojang.serialization.Codec;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.seagrass.MoonshadeSeagrassBlock;
import com.wdiscute.laicaps.block.single.MoonshadeKelpBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MoonshadeSeagrassFeature extends Feature<NoneFeatureConfiguration>
{
    public MoonshadeSeagrassFeature(Codec<NoneFeatureConfiguration> p_66219_)
    {
        super(p_66219_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        WorldGenLevel level = context.level();
        BlockPos originBP = context.origin();

        int originHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR, originBP.getX(), originBP.getZ());
        originBP = new BlockPos(originBP.getX(), originHeight, originBP.getZ());

        BlockState originBS = level.getBlockState(new BlockPos(originBP.getX(), originHeight, originBP.getZ()));
        BlockState originBSAbove = level.getBlockState(new BlockPos(originBP.getX(), originHeight + 1, originBP.getZ()));

        if (originBS.is(Blocks.WATER) && originBSAbove.is(Blocks.WATER))
        {
            BlockState tinySeagrass = ModBlocks.MOONSHADE_SEAGRASS.get().defaultBlockState();

            BlockState tallSeagrassLower = ModBlocks.MOONSHADE_TALL_SEAGRASS.get().defaultBlockState();
            tallSeagrassLower = tallSeagrassLower.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);

            BlockState tallSeagrassUpper = ModBlocks.MOONSHADE_TALL_SEAGRASS.get().defaultBlockState();
            tallSeagrassUpper = tallSeagrassUpper.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);


            if (context.random().nextFloat() > 0.5)
            {
                level.setBlock(originBP, tinySeagrass, 2);
            }
            else
            {
                level.setBlock(originBP, tallSeagrassLower, 2);
                level.setBlock(originBP.above(), tallSeagrassUpper, 2);

            }
        }

        return true;
    }
}