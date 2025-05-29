package com.wdiscute.laicaps.worldgen;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(BuiltInRegistries.FEATURE, Laicaps.MOD_ID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> MOONSHADE_KELP = FEATURES.register("moonshade_kelp",
            () -> new MoonshadeKelpFeature(NoneFeatureConfiguration.CODEC));


    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}