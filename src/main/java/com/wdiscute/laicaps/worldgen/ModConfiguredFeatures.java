package com.wdiscute.laicaps.worldgen;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class ModConfiguredFeatures
{

    public static final ResourceKey<ConfiguredFeature<?, ?>> OAKHEART_KEY = registerKey("oakheart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAKROOT_KEY = registerKey("oakroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHA_SWEETLILY_PATCH = registerKey("asha_sweetlily_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHA_GRASS_BLOCK_BONEMEAL = registerKey("asha_grass_block_bonemeal");






    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Laicaps.rl(name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}

