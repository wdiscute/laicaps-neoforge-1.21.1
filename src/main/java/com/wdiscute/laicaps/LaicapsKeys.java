package com.wdiscute.laicaps;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class LaicapsKeys
{
    public static final ResourceKey<Level> EMBER = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
    public static final ResourceKey<Level> ASHA = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));
    public static final ResourceKey<Level> OVERWORLD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    public static final ResourceKey<Level> LUNAMAR = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));
    public static final ResourceKey<Level> ICY_WORLD = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("icy_world"));


}
