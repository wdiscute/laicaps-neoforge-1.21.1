package com.wdiscute.laicaps.worldgen;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers
{

    public static final ResourceKey<BiomeModifier> ADD_ALEXANDRITE_ORE = registerKey("add_alexandrite_ore");
    public static final ResourceKey<BiomeModifier> ADD_WALNUT_TREE = registerKey("add_walnut_tree");

    public static void bootstrap(BootstrapContext<BiomeModifier> context)
    {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ALEXANDRITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                //HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BEACH)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ALEXANDRTITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));


        context.register(ADD_WALNUT_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.END_BARRENS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.WALNUT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

    }


    private static ResourceKey<BiomeModifier> registerKey(String name)
    {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, name));
    }

}
