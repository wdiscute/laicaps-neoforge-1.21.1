package com.wdiscute.laicaps;

import com.wdiscute.laicaps.fishing.FishProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import static com.wdiscute.laicaps.Laicaps.*;

public class LaicapsKeys
{
    public static final ResourceKey<Registry<FishProperties>> FISH_REGISTRY = ResourceKey.createRegistryKey(Laicaps.rl("fish"));



    public static final ResourceKey<Level> EMBER                = dim(rl("ember"));
    public static final ResourceKey<Level> ASHA                 = dim(rl("asha"));
    public static final ResourceKey<Level> OVERWORLD            = dim(ResourceLocation.withDefaultNamespace("overworld"));
    public static final ResourceKey<Level> LUNAMAR              = dim(rl("lunamar"));
    public static final ResourceKey<Level> ICY_WORLD            = dim(rl("icy_world"));

    //asha biomes
    public static final ResourceKey<Biome> ASHA_FOREST          = biome(rl("asha_forest"));
    public static final ResourceKey<Biome> ASHA_FLOWER_FOREST   = biome(rl("asha_flower_forest"));
    public static final ResourceKey<Biome> ASHA_JUNGLE          = biome(rl("asha_jungle"));
    public static final ResourceKey<Biome> ASHA_MESA            = biome(rl("asha_mesa"));
    public static final ResourceKey<Biome> ASHA_MOUNTAINS       = biome(rl("asha_mountains"));
    public static final ResourceKey<Biome> ASHA_OCEAN           = biome(rl("asha_ocean"));
    public static final ResourceKey<Biome> ASHA_PLAINS          = biome(rl("asha_plains"));
    public static final ResourceKey<Biome> ASHA_RIVER           = biome(rl("asha_river"));

    //lunamar biomes
    public static final ResourceKey<Biome> LUNAMAR_ASHEN_SHORES = biome(rl("lunamar_ashen_shores"));
    public static final ResourceKey<Biome> LUNAMAR_DEEP = biome(rl("lunamar_deep"));
    public static final ResourceKey<Biome> LUNAMAR_MAR = biome(rl("lunamar_mar"));
    public static final ResourceKey<Biome> LUNAMAR_SHALLOW = biome(rl("lunamar_shallow"));
    public static final ResourceKey<Biome> LUNAMAR_SUBTIDAL = biome(rl("lunamar_subtidal"));
    public static final ResourceKey<Biome> LUNAMAR_VERDURE = biome(rl("lunamar_verdure"));






    private static ResourceKey<Biome> biome(ResourceLocation rl)
    {
        return ResourceKey.create(Registries.BIOME, rl);
    }

    private static ResourceKey<Level> dim(ResourceLocation rl)
    {
        return ResourceKey.create(Registries.DIMENSION, rl);
    }
}
