package com.wdiscute.laicaps;


import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.item.StarcatcherFishingRod;
import com.wdiscute.laicaps.item.*;


import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);

    //
    //,------. ,--.  ,---.   ,--.  ,--. ,--. ,--.  ,--.  ,----.
    //|  .---' |  | '   .-'  |  '--'  | |  | |  ,'.|  | '  .-./
    //|  `--,  |  | `.  `-.  |  .--.  | |  | |  |' '  | |  | .---.
    //|  |`    |  | .-'    | |  |  |  | |  | |  | `   | '  '--'  |
    //`--'     `--' `-----'  `--'  `--' `--' `--'  `--'  `------'
    //

    public static final DeferredItem<Item> STARCATCHER_FISHING_ROD = ITEMS.register(
            "starcatcher_fishing_rod",
            () -> new StarcatcherFishingRod(
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .stacksTo(1)
                            .component(ModDataComponents.BOBBER.get(), ItemContainerContents.fromItems(List.of(ItemStack.EMPTY)))
                            .component(ModDataComponents.BAIT.get(), ItemContainerContents.fromItems(List.of(ItemStack.EMPTY))))
    );

    public static final DeferredItem<Item> OAKHEART_BERRIES_BAIT = ITEMS.register("oakheart_berries_bait", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STARCATCHER_TWINE = ITEMS.register("starcatcher_twine", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CREEPER_BOBBER = ITEMS.register("creeper_bobber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TREASURE_BOBBER = ITEMS.register("treasure_bobber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BAIT_SAVING_BOBBER = ITEMS.register("bait_saving_bobber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIFFICULTY_BOBBER = ITEMS.register("difficulty_bobber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FAST_BITING_BOBBER = ITEMS.register("fast_biting_bobber", () -> new Item(new Item.Properties()));

    //
    //,--.   ,--. ,--.  ,---.    ,-----.
    //|   `.'   | |  | '   .-'  '  .--./
    //|  |'.'|  | |  | `.  `-.  |  |
    //|  |   |  | |  | .-'    | '  '--'\
    //`--'   `--' `--' `-----'   `-----'
    //

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> SPACESHIP_BLUEPRINT = ITEMS.register("spaceship_blueprint", () -> new SpaceshipItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> SPACESHIP_BLUEPRINT_SKETCH = ITEMS.register("spaceship_blueprint_sketch", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> ASTRONOMY_NOTEBOOK = ITEMS.register("astronomy_notebook", () -> new
            NotebookItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1),
            "menu_entries", "entry1", "menu"));

    public static final DeferredItem<Item> EMBER_ENTRY = ITEMS.register(
            "ember_entry", () ->
                    new EntryItem("ember",new Item.Properties()
                            .rarity(Rarity.UNCOMMON)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Ember Entry Page")
                    ));

    public static final DeferredItem<Item> ASHA_ENTRY = ITEMS.register(
            "asha_entry", () ->
                    new EntryItem("asha", new Item.Properties().rarity(Rarity.RARE)
                            .rarity(Rarity.UNCOMMON)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Asha Entry Page")));

    public static final DeferredItem<Item> OVERWORLD_ENTRY = ITEMS.register(
            "overworld_entry", () ->
                    new EntryItem("overworld", new Item.Properties().rarity(Rarity.RARE)
                            .rarity(Rarity.UNCOMMON)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Overworld Entry Page")
                    ));

    public static final DeferredItem<Item> LUNAMAR_ENTRY = ITEMS.register(
            "lunamar_entry", () ->
                    new EntryItem("lunamar", new Item.Properties().rarity(Rarity.RARE)
                            .rarity(Rarity.UNCOMMON)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Lunamar Entry Page")
                    ));

    //
    // ,-----. ,--.  ,--. ,--. ,------.   ,---.
    //'  .--./ |  '--'  | |  | |  .--. ' '   .-'
    //|  |     |  .--.  | |  | |  '--' | `.  `-.
    //'  '--'\ |  |  |  | |  | |  | --'  .-'    |
    // `-----' `--'  `--' `--' `--'      `-----'
    //

    public static final DeferredItem<Item> BASIC_MICROCHIP = ITEMS.register("basic_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> REFINED_MICROCHIP = ITEMS.register("refined_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ADVANCED_MICROCHIP = ITEMS.register("advanced_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ELITE_MICROCHIP = ITEMS.register("elite_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    //
    //,--------. ,------. ,--.    ,------.  ,---.    ,-----.  ,-----.  ,------.  ,------.
    //'--.  .--' |  .---' |  |    |  .---' '   .-'  '  .--./ '  .-.  ' |  .--. ' |  .---'
    //   |  |    |  `--,  |  |    |  `--,  `.  `-.  |  |     |  | |  | |  '--' | |  `--,
    //   |  |    |  `---. |  '--. |  `---. .-'    | '  '--'\ '  '-'  ' |  | --'  |  `---.
    //   `--'    `------' `-----' `------' `-----'   `-----'  `-----'  `--'      `------'
    //


    public static final DeferredItem<Item> TELESCOPE_UPGRADE_KIT = ITEMS.register("telescope_upgrade_kit", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final DeferredItem<Item> TELESCOPE_LENSES = ITEMS.register("telescope_lenses", () -> new Item(new Item.Properties().stacksTo(16)));


    //
    //,------.   ,-----.   ,-----. ,--. ,--. ,------. ,--------.
    //|  .--. ' '  .-.  ' '  .--./ |  .'   / |  .---' '--.  .--'
    //|  '--'.' |  | |  | |  |     |  .   '  |  `--,     |  |
    //|  |\  \  '  '-'  ' '  '--'\ |  |\   \ |  `---.    |  |
    //`--' '--'  `-----'   `-----' `--' '--' `------'    `--'
    //

    public static final DeferredItem<Item> ENDERBLAZE_FUEL = ITEMS.register("enderblaze_fuel", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> PRISTINE_ENDERPEARL_DUST = ITEMS.register("pristine_enderpearl_dust", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> EMBER = ITEMS.register("ember", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> ASHA = ITEMS.register("asha", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> OVERWORLD = ITEMS.register("overworld", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> LUNAMAR = ITEMS.register("lunamar", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> UNKNOWN = ITEMS.register("unknown", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));


    //
    //  ,---.    ,---.   ,--.  ,--.   ,---.
    // /  O  \  '   .-'  |  '--'  |  /  O  \
    //|  .-.  | `.  `-.  |  .--.  | |  .-.  |
    //|  | |  | .-'    | |  |  |  | |  | |  |
    //`--' `--' `-----'  `--'  `--' `--' `--'
    //


    public static final DeferredItem<Item> STARFLIES_JAR = ITEMS.register("starflies_jar", () -> new Starflies_jar(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> JAR = ITEMS.register("jar", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> SWEETLILY_SUGAR = ITEMS.register("sweetlily_sugar", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SNUFFLER_CHOP = ITEMS.register("snuffler_chop", () -> new Item(new Item.Properties().food(ModFoodProperties.SNUFFLER_CHOP)));
    public static final DeferredItem<Item> COOKED_SNUFFLER_CHOP = ITEMS.register("cooked_snuffler_chop", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_SNUFFLER_CHOP)));
    public static final DeferredItem<Item> SNUFFLER_SPAWN_EGG = ITEMS.register("snuffler_spawn_egg", () -> new SpawnEggItem(ModEntities.SNUFFLER.get(), 15971928, 13534776, new Item.Properties()));


    public static final DeferredItem<Item> SWIBBLE_SPAWN_EGG = ITEMS.register("swibble_spawn_egg", () -> new SpawnEggItem(ModEntities.SNUFFLER.get(), 4892577, 13534776, new Item.Properties()));


    public static final DeferredItem<Item> BLUETALE = ITEMS.register("bluetale", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> COOKED_BLUETALE = ITEMS.register("cooked_bluetale", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BASIC_FISH)));
    public static final DeferredItem<Item> BLUETALE_BUCKET = ITEMS.register("bluetale_bucket", () -> new MobBucketItem(ModEntities.BLUETALE.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> BLUETALE_SPAWN_EGG = ITEMS.register("bluetale_spawn_egg", () -> new SpawnEggItem(ModEntities.BLUETALE.get(), 9429956, 10858979, new Item.Properties()));


    public static final DeferredItem<Item> REDTALE = ITEMS.register("redtale", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> COOKED_REDTALE = ITEMS.register("cooked_redtale", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BASIC_FISH)));
    public static final DeferredItem<Item> REDTALE_BUCKET = ITEMS.register("redtale_bucket", () -> new MobBucketItem(ModEntities.REDTALE.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> REDTALE_SPAWN_EGG = ITEMS.register("redtale_spawn_egg", () -> new SpawnEggItem(ModEntities.BUBBLEMOUTH.get(), 9429956, 14919099, new Item.Properties()));

    public static final DeferredItem<Item> BUBBLEMOUTH = ITEMS.register("bubblemouth", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> COOKED_BUBBLEMOUTH = ITEMS.register("cooked_bubblemouth", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BASIC_FISH)));
    public static final DeferredItem<Item> BUBBLEMOUTH_BUCKET = ITEMS.register("bubblemouth_bucket", () -> new MobBucketItem(ModEntities.BUBBLEMOUTH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> BUBBLEMOUTH_SPAWN_EGG = ITEMS.register("bubblemouth_spawn_egg", () -> new SpawnEggItem(ModEntities.BUBBLEMOUTH.get(), 9429956, 14919099, new Item.Properties()));

    public static final DeferredItem<Item> MOONRAY = ITEMS.register("moonray", () -> new Item(new Item.Properties().food(ModFoodProperties.MOONRAY)));
    public static final DeferredItem<Item> MOONRAY_BUCKET = ITEMS.register("moonray_bucket", () -> new MobBucketItem(ModEntities.MOONRAY.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> MOONRAY_SPAWN_EGG = ITEMS.register("moonray_spawn_egg", () -> new SpawnEggItem(ModEntities.MOONRAY.get(), 9429956, 14919099, new Item.Properties()));


    public static final DeferredItem<Item> GLIMPUFF = ITEMS.register("glimpuff", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> COOKED_GLIMPUFF = ITEMS.register("cooked_glimpuff", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BASIC_FISH)));
    public static final DeferredItem<Item> GLIMPUFF_BUCKET = ITEMS.register("glimpuff_bucket", () -> new MobBucketItem(ModEntities.GLIMPUFF.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> GLIMPUFF_SPAWN_EGG = ITEMS.register("glimpuff_spawn_egg", () -> new SpawnEggItem(ModEntities.GLIMPUFF.get(), 9429956, 14919099, new Item.Properties()));


    public static final DeferredItem<Item> NIMBLE_SPAWN_EGG = ITEMS.register("nimble_spawn_egg", () -> new SpawnEggItem(ModEntities.NIMBLE.get(), 14531970, 14714721, new Item.Properties()));
    public static final DeferredItem<Item> NIMBLE_SWEET_TREAT = ITEMS.register("nimble_sweet_treat", () -> new Item(new Item.Properties()));

    //fishing fishes
    public static final DeferredItem<Item> RED_HERRING = ITEMS.register("red_herring", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> AVIAN = ITEMS.register("avian", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> TWILIGHT_TROUT = ITEMS.register("twilight_trout", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> EEL = ITEMS.register("eel", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> MEADOW_PERCH = ITEMS.register("meadow_perch", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> SOLAR_CARP = ITEMS.register("solar_carp", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> VERY_TINY_SHARK = ITEMS.register("very_tiny_shark", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> AZURE_TUNA = ITEMS.register("azure_tuna", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> SCARLET_TUNA = ITEMS.register("scarlet_tuna", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> SAGE_TUNA = ITEMS.register("sage_tuna", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> KARPENJOE = ITEMS.register("karpenjoe", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> STORMSAIL_RAY = ITEMS.register("stormsail_ray", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> SUNFANG_EEL = ITEMS.register("sunfang_eel", () -> new Item(new Item.Properties().food(ModFoodProperties.BASIC_RAW_FISH)));
    public static final DeferredItem<Item> CRIMSON_STARFISH = ITEMS.register("crimson_starfish", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> LUMEN_DRIFTER = ITEMS.register("lumen_drifter", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> RADIANT_SEAHORSE = ITEMS.register("radiant_seahorse", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> VERDANT_SEAHORSE = ITEMS.register("verdant_seahorse", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> OCTO = ITEMS.register("octo", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));
    public static final DeferredItem<Item> RED_REEF_OCTO = ITEMS.register("red_reef_octo", () -> new Item(new Item.Properties().food(ModFoodProperties.EEL)));


    //todo add mobeffect that makes player unable to move because of being zapped
    public static final DeferredItem<Item> THUNDERCHARGED_EEL = ITEMS.register(
            "thundercharged_eel", () -> new ThunderchargedEel(new Item.Properties().food(ModFoodProperties.THUNDERCHARGED_EEL)));









    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register(
            "oakheart_berries", () ->
                    new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES)));

    public static final DeferredItem<Item> OAKHEART_BERRIES_JAM = ITEMS.register(
            "oakheart_berries_jam", () ->
                    new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES_JAM).stacksTo(16)));


    public static final DeferredItem<Item> OAKHEART_DOOR = ITEMS.register(
            "oakheart_door",
            () -> new DoubleHighBlockItem(ModBlocks.OAKHEART_DOOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> OAKHEART_SIGN = ITEMS.register("oakheart_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKHEART_SIGN.get(), ModBlocks.OAKHEART_WALL_SIGN.get()));
    public static final DeferredItem<Item> OAKHEART_HANGING_SIGN = ITEMS.register("oakheart_hanging_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKHEART_HANGING_SIGN.get(), ModBlocks.OAKHEART_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> OAKHEART_BOAT = ITEMS.register("oakheart_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.OAKHEART, new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> OAKHEART_CHEST_BOAT = ITEMS.register("oakheart_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.OAKHEART, new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> OAKROOT_DOOR = ITEMS.register("oakroot_door", () -> new DoubleHighBlockItem(ModBlocks.OAKROOT_DOOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> OAKROOT_HANGING_SIGN = ITEMS.register("oakroot_hanging_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKROOT_HANGING_SIGN.get(), ModBlocks.OAKROOT_WALL_HANGING_SIGN.get()));
    public static final DeferredItem<Item> OAKROOT_SIGN = ITEMS.register("oakroot_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKROOT_SIGN.get(), ModBlocks.OAKROOT_WALL_SIGN.get()));
    public static final DeferredItem<Item> OAKROOT_CHEST_BOAT = ITEMS.register("oakroot_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.OAKROOT, new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> OAKROOT_BOAT = ITEMS.register("oakroot_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.OAKROOT, new Item.Properties().stacksTo(16)));


    //
    //,--.    ,--. ,--. ,--.  ,--.   ,---.   ,--.   ,--.   ,---.   ,------.
    //|  |    |  | |  | |  ,'.|  |  /  O  \  |   `.'   |  /  O  \  |  .--. '
    //|  |    |  | |  | |  |' '  | |  .-.  | |  |'.'|  | |  .-.  | |  '--'.'
    //|  '--. '  '-'  ' |  | `   | |  | |  | |  |   |  | |  | |  | |  |\  \
    //`-----'  `-----'  `--'  `--' `--' `--' `--'   `--' `--' `--' `--' '--'
    //


    public static final DeferredItem<Item> MOONSHADE_FRUIT = ITEMS.register("moonshade_fruit", () -> new Item(new Item.Properties().food(ModFoodProperties.MOONSHADE_FRUIT)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


}
