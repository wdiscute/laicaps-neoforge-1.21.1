package com.wdiscute.laicaps.fishing;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public record Fishes()
{

    //
    //  ,--.                            ,--.
    //,-'  '-. ,--.--.  ,--,--.  ,---.  |  ,---.
    //'-.  .-' |  .--' ' ,-.  | (  .-'  |  .-.  |
    //  |  |   |  |    \ '-'  | .-'  `) |  | |  |
    //  `--'   `--'     `--`--' `----'  `--' `--'
    //

    public static final FishProperties STICK = new FishProperties(
            Items.STICK,
            null,
            null,
            1
    ).skipsMinigame();

    public static final FishProperties SEEDS = new FishProperties(
            Items.WHEAT_SEEDS,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            5
    ).skipsMinigame();


    //
    //                 ,--.
    // ,--,--.  ,---.  |  ,---.   ,--,--.
    //' ,-.  | (  .-'  |  .-.  | ' ,-.  |
    //\ '-'  | .-'  `) |  | |  | \ '-'  |
    // `--`--' `----'  `--' `--'  `--`--'
    //

    public static final FishProperties GOLD_NUGGET = new FishProperties(
            Items.GOLD_NUGGET,
            List.of(getDimResourceKey("asha")),
            null,
            1
    ).skipsMinigame();

    public static final FishProperties BLUETALE = new FishProperties(
            ModItems.BLUETALE.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    ).correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 20);


    public static final FishProperties REDTALE = new FishProperties(
            ModItems.REDTALE.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    ).correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 20);



    public static final FishProperties SUPER_SPECIAL_FISH = new FishProperties(
            Items.COD,                                      //item rewarded on fishing
            List.of(getDimResourceKey("asha")),   //list of dimensions it can "spawn" in - null for all
            null,                                           //list of biomes it can "spawn" in - null for all
            2                                               //base chance
    )
            .correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 20)
            .mustBeCaughtAboveY(100)
            .mustBeCaughtBellowY(120)
            .mustBeRaining()
            .mustBeThundering()
            .incorrectBaits(List.of(Items.ROTTEN_FLESH, Items.DIAMOND))
            .mustHaveCorrectBait();

    //
    //,--.
    //|  | ,--.,--. ,--,--,   ,--,--. ,--,--,--.  ,--,--. ,--.--.
    //|  | |  ||  | |      \ ' ,-.  | |        | ' ,-.  | |  .--'
    //|  | '  ''  ' |  ||  | \ '-'  | |  |  |  | \ '-'  | |  |
    //`--'  `----'  `--''--'  `--`--' `--`--`--'  `--`--' `--'
    //


    public static final List<FishProperties> entries = List.of(STICK, SEEDS, BLUETALE, REDTALE, GOLD_NUGGET);


    public static ResourceKey<Level> getDimResourceKey(String name)
    {
        return ResourceKey.create(Registries.DIMENSION, Laicaps.rl(name));
    }

    public static ResourceKey<Level> getDimResourceKey(String namespace, String name)
    {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    public static ResourceKey<Biome> getBiomeResourceKey(String name)
    {
        return ResourceKey.create(Registries.BIOME, Laicaps.rl(name));
    }

    public static ResourceKey<Biome> getBiomeResourceKey(String namespace, String name)
    {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(namespace, name));
    }


}
