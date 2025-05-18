package com.wdiscute.laicaps.worldgen;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures
{
    public static final ResourceKey<PlacedFeature> ALEXANDRTITE_ORE_PLACED_KEY = registerKey("alexandrite_ore_placed");

    public static final ResourceKey<PlacedFeature> RANDOM_BLOCKS_PLACED_KEY = registerKey("random_blocks_placed_key");


    public static ResourceKey<PlacedFeature> registerKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, Laicaps.rl(name));
    }

}
