package com.wdiscute.laicaps.worldgen;

import com.mojang.serialization.Codec;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.single.MoonshadeKelpBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.KelpFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoonshadeKelpFeature extends Feature<NoneFeatureConfiguration>
{
    public MoonshadeKelpFeature(Codec<NoneFeatureConfiguration> p_66219_)
    {
        super(p_66219_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        WorldGenLevel level = context.level();
        BlockPos originBP = context.origin();
        RandomSource randomsource = context.random();

        int originHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR, originBP.getX(), originBP.getZ());
        originBP = new BlockPos(originBP.getX(), originHeight, originBP.getZ());

        BlockState originBS = level.getBlockState(new BlockPos(originBP.getX(), originHeight, originBP.getZ()));


        if (originBS.is(Blocks.WATER))
        {
            BlockState kelpCanGrow = ModBlocks.MOONSHADE_KELP.get().defaultBlockState();
            kelpCanGrow = kelpCanGrow.setValue(MoonshadeKelpBlock.AGE, 15);
            kelpCanGrow = kelpCanGrow.setValue(MoonshadeKelpBlock.CAN_GROW, true);
            kelpCanGrow = kelpCanGrow.setValue(MoonshadeKelpBlock.GROWN, true);
            kelpCanGrow = kelpCanGrow.setValue(MoonshadeKelpBlock.WATERLOGGED, true);


            BlockState kelpCanNotGrow = kelpCanGrow.setValue(MoonshadeKelpBlock.CAN_GROW, false);
            kelpCanNotGrow = kelpCanNotGrow.setValue(MoonshadeKelpBlock.GROWN, false);


            int k = 10 + randomsource.nextInt(20);

            for (int i = 0; i <= k; i++)
            {
                if(i > 5 && randomsource.nextFloat() > 0.9) break;

                if (level.getBlockState(originBP.above()).is(Blocks.WATER) && kelpCanGrow.canSurvive(level, originBP))
                {
                    if (randomsource.nextFloat() > 0.8f)
                        level.setBlock(originBP, kelpCanGrow, 2);
                    else
                        level.setBlock(originBP, kelpCanNotGrow, 2);

                }else
                {
                    break;
                }

                originBP = originBP.above();
            }
        }

        return true;
    }
}