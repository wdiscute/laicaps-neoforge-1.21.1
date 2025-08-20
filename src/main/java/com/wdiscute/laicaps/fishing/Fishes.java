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

    //                                                               ,--.    ,--.
    // ,---.  ,--.  ,--.  ,---.  ,--.--. ,--.   ,--.  ,---.  ,--.--. |  |  ,-|  |
    //| .-. |  \  `'  /  | .-. : |  .--' |  |.'.|  | | .-. | |  .--' |  | ' .-. |
    //' '-' '   \    /   \   --. |  |    |   .'.   | ' '-' ' |  |    |  | \ `-' |
    // `---'     `--'     `----' `--'    '--'   '--'  `---'  `--'    `--'  `---'
    //

    public static final FishProperties STICK = new FishProperties(
            Items.STICK,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties LEATHER_BOOTS = new FishProperties(
            Items.LEATHER_BOOTS,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties WHEAT_SEEDS = new FishProperties(
            Items.WHEAT_SEEDS,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties BONE = new FishProperties(
            Items.BONE,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties STRING = new FishProperties(
            Items.STRING,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties BOWL = new FishProperties(
            Items.BOWL,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties INK_SAC = new FishProperties(
            Items.INK_SAC,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties TRIPWIRE_HOOK = new FishProperties(
            Items.TRIPWIRE_HOOK,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties ROTTEN_FLESH = new FishProperties(
            Items.ROTTEN_FLESH,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties BAMBOO = new FishProperties(
            Items.BAMBOO,
            List.of(getDimResourceKey("minecraft", "overworld")),
            List.of(
                    getBiomeResourceKey("minecraft", "jungle"),
                    getBiomeResourceKey("minecraft", "sparse_jungle"),
                    getBiomeResourceKey("minecraft", "bamboo_jungle")),
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties LILY_PAD = new FishProperties(
            Items.LILY_PAD,
            List.of(getDimResourceKey("minecraft", "overworld")),
            List.of(
                    getBiomeResourceKey("minecraft", "swamp"),
                    getBiomeResourceKey("minecraft", "mangrove_swamp")),
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties SEAGRASS = new FishProperties(
            Items.SEAGRASS,
            List.of(getDimResourceKey("minecraft", "overworld")),
            List.of(
                    getBiomeResourceKey("minecraft", "river"),
                    getBiomeResourceKey("minecraft", "frozen_river")
            ),
            1
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties SNOWBALL = new FishProperties(
            Items.SNOWBALL,
            List.of(getDimResourceKey("minecraft", "overworld")),
            List.of(
                    getBiomeResourceKey("minecraft", "frozen_river"),
                    getBiomeResourceKey("minecraft", "frozen_peaks"),
                    getBiomeResourceKey("minecraft", "frozen_ocean"),
                    getBiomeResourceKey("minecraft", "deep_frozen_ocean"),
                    getBiomeResourceKey("minecraft", "snowy_beach"),
                    getBiomeResourceKey("minecraft", "snowy_plains"),
                    getBiomeResourceKey("minecraft", "snowy_slopes"),
                    getBiomeResourceKey("minecraft", "snowy_taiga")
            ),
            3
    )
            .skipsMinigame()
            .canBeBucketed(Items.POWDER_SNOW_BUCKET)
            .doesNotConsumeBait();


    public static final FishProperties SALMON = new FishProperties(
            Items.SALMON,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            20
    )
            .mustBeCaughtAboveY(50)
            .canBeBucketed(Items.SALMON_BUCKET);


    public static final FishProperties COD = new FishProperties(
            Items.COD,
            List.of(getDimResourceKey("minecraft", "overworld")),
            null,
            20
    )
            .mustBeCaughtAboveY(50)
            .canBeBucketed(Items.COD_BUCKET);


    public static final FishProperties AXOLOTL_BUCKET = new FishProperties(
            Items.AXOLOTL_BUCKET,
            List.of(getDimResourceKey("minecraft", "overworld")),
            List.of(getBiomeResourceKey("minecraft", "lush_caves")),
            0
    )
            .mustBeCaughtBellowY(50)
            .mustHaveCorrectBait()
            .correctBaitChanceAdded(Items.BUCKET, 10);


    //
    //                 ,--.
    // ,--,--.  ,---.  |  ,---.   ,--,--.
    //' ,-.  | (  .-'  |  .-.  | ' ,-.  |
    //\ '-'  | .-'  `) |  | |  | \ '-'  |
    // `--`--' `----'  `--' `--'  `--`--'
    //

    public static final FishProperties STICK_ASHA = new FishProperties(
            Items.STICK,
            List.of(getDimResourceKey("asha")),
            null,
            2
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties GOLD_NUGGET = new FishProperties(
            Items.GOLD_NUGGET,
            List.of(getDimResourceKey("asha")),
            null,
            2
    ).skipsMinigame().doesNotConsumeBait();

    public static final FishProperties GOLD_INGOT = new FishProperties(
            Items.GOLD_INGOT,
            List.of(getDimResourceKey("asha")),
            null,
            3
    )
            .skipsMinigame()
            .doesNotConsumeBait()
            .timeRestrictions(FishProperties.daytime.MIDNIGHT);

    public static final FishProperties BLUETALE = new FishProperties(
            ModItems.BLUETALE.get(),
            List.of(getDimResourceKey("asha")),
            null,
            522
    )
            .correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 20)
            .canBeBucketed(ModItems.BLUETALE_BUCKET.get());


    public static final FishProperties REDTALE = new FishProperties(
            ModItems.REDTALE.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    )
            .correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 20)
            .canBeBucketed(ModItems.REDTALE_BUCKET.get());





    public static final FishProperties RED_HERRING = new FishProperties(
            ModItems.RED_HERRING.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    );


    public static final FishProperties AVIAN = new FishProperties(
            ModItems.AVIAN.get(),
            List.of(getDimResourceKey("asha")),
            null,
            2
    ).timeRestrictions(FishProperties.daytime.NIGHT);


    public static final FishProperties TWILIGHT_TROUT = new FishProperties(
            ModItems.TWILIGHT_TROUT.get(),
            List.of(getDimResourceKey("asha")),
            null,
            10
    ).timeRestrictions(FishProperties.daytime.MIDNIGHT);


    public static final FishProperties THUNDERCHARGED_EEL = new FishProperties(
            ModItems.THUNDERCHARGED_EEL.get(),
            List.of(getDimResourceKey("asha")),
            null,
            25
    )
            .mustBeThundering()
            .timeRestrictions(FishProperties.daytime.NIGHT);


    public static final FishProperties EEL = new FishProperties(
            ModItems.EEL.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    )
            .timeRestrictions(FishProperties.daytime.NIGHT);


    public static final FishProperties MEADOW_PERCH = new FishProperties(
            ModItems.MEADOW_PERCH.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    )
            .timeRestrictions(FishProperties.daytime.DAY);


    public static final FishProperties SOLAR_CARP = new FishProperties(
            ModItems.SOLAR_CARP.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    )
            .timeRestrictions(FishProperties.daytime.DAY)
            .biomeBlacklist(getBiomeResourceKey("asha_ocean"));


    public static final FishProperties VERY_TINY_SHARK = new FishProperties(
            ModItems.VERY_TINY_SHARK.get(),
            List.of(getDimResourceKey("asha")),
            List.of(getBiomeResourceKey("asha_ocean")),
            2
    );

    public static final FishProperties AZURE_TUNA = new FishProperties(
            ModItems.AZURE_TUNA.get(),
            List.of(getDimResourceKey("asha")),
            List.of(getBiomeResourceKey("asha_ocean")),
            5
    );

    public static final FishProperties SCARLET_TUNA = new FishProperties(
            ModItems.AZURE_TUNA.get(),
            List.of(getDimResourceKey("asha")),
            List.of(getBiomeResourceKey("asha_ocean")),
            5
    );

    public static final FishProperties SAGE_TUNA = new FishProperties(
            ModItems.AZURE_TUNA.get(),
            List.of(getDimResourceKey("asha")),
            List.of(getBiomeResourceKey("asha_ocean")),
            5
    );

    public static final FishProperties KARPENJOE = new FishProperties(
            ModItems.KARPENJOE.get(),
            List.of(getDimResourceKey("asha")),
            null,
            1
    )
            .timeRestrictions(FishProperties.daytime.DAY)
            .mustBeClear()
            .correctBaitChanceAdded(ModItems.OAKHEART_BERRIES_BAIT.get(), 10)
            .biomeBlacklist(getBiomeResourceKey("asha_ocean"));

    public static final FishProperties STORMSAIL_RAY = new FishProperties(
            ModItems.STORMSAIL_RAY.get(),
            List.of(getDimResourceKey("asha")),
            List.of(getBiomeResourceKey("asha_ocean")),
            15
    )
            .mustBeRaining();

    public static final FishProperties SUNFANG_EEL = new FishProperties(
            ModItems.SUNFANG_EEL.get(),
            List.of(getDimResourceKey("asha")),
            null,
            5
    )
            .timeRestrictions(FishProperties.daytime.DAY)
            .mustBeClear();





    //
    //,--.
    //|  | ,--.,--. ,--,--,   ,--,--. ,--,--,--.  ,--,--. ,--.--.
    //|  | |  ||  | |      \ ' ,-.  | |        | ' ,-.  | |  .--'
    //|  | '  ''  ' |  ||  | \ '-'  | |  |  |  | \ '-'  | |  |
    //`--'  `----'  `--''--'  `--`--' `--`--`--'  `--`--' `--'
    //



    public static final FishProperties GLIMPUFF = new FishProperties(
            ModItems.GLIMPUFF.get(),
            List.of(getDimResourceKey("lunamar")),
            null,
            5
    )
            .canBeBucketed(ModItems.GLIMPUFF_BUCKET.get());

    public static final FishProperties BUBBLEMOUTH = new FishProperties(
            ModItems.BUBBLEMOUTH.get(),
            List.of(getDimResourceKey("lunamar")),
            null,
            5
    )
            .canBeBucketed(ModItems.BUBBLEMOUTH_BUCKET.get());

    public static final FishProperties MOONRAY = new FishProperties(
            ModItems.MOONRAY.get(),
            List.of(getDimResourceKey("lunamar")),
            null,
            5
    )
            .canBeBucketed(ModItems.MOONRAY_BUCKET.get());


    public static final FishProperties SUPER_SPECIAL_FISH = new FishProperties(//
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
            .mustHaveCorrectBait()
            .doesNotConsumeBait()
            .skipsMinigame();

    public static ResourceKey<Level> getDimResourceKey(String laicapsName)
    {
        return ResourceKey.create(Registries.DIMENSION, Laicaps.rl(laicapsName));
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


    public static final List<FishProperties> entries = List.of(
            LEATHER_BOOTS,
            WHEAT_SEEDS,
            BONE, STRING,
            BOWL, INK_SAC,
            TRIPWIRE_HOOK,
            ROTTEN_FLESH, BAMBOO,
            LILY_PAD, SALMON,
            COD,AXOLOTL_BUCKET,
            STICK_ASHA, GOLD_INGOT,
            GOLD_NUGGET,
            BLUETALE, REDTALE,
            SEAGRASS, SNOWBALL,
            GLIMPUFF, BUBBLEMOUTH,
            SUNFANG_EEL, STORMSAIL_RAY,
            MOONRAY, KARPENJOE,
            AZURE_TUNA, SCARLET_TUNA, SAGE_TUNA,
            VERY_TINY_SHARK,
            SOLAR_CARP, MEADOW_PERCH,
            EEL, THUNDERCHARGED_EEL,
            RED_HERRING, AVIAN,
            TWILIGHT_TROUT



    );

}
