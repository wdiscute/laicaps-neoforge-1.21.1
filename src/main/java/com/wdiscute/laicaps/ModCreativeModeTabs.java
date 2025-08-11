package com.wdiscute.laicaps;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Laicaps.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }


    public static final Supplier<CreativeModeTab> LAICAPS =
            CREATIVE_MODE_TABS.register(
                    "laicaps", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ASTRONOMY_NOTEBOOK.get()))
                            .title(Component.translatable("creativetab.laicaps.laicaps"))
                            .displayItems((itemDisplayParameters, output) ->
                            {

                                //telescope and astronomy stuff
                                output.accept(ModItems.ASTRONOMY_NOTEBOOK.get());
                                output.accept(ModBlocks.TELESCOPE_STAND.get());
                                output.accept(ModBlocks.TELESCOPE.get());

                                output.accept(ModItems.TELESCOPE_LENSES.get());
                                output.accept(ModItems.TELESCOPE_UPGRADE_KIT.get());

                                output.accept(ModBlocks.ASTRONOMY_RESEARCH_TABLE.get());

                                output.accept(ModBlocks.OVERWORLD_GLOBE.get());
                                output.accept(ModItems.OVERWORLD_ENTRY.get());


                                //fishing
                                output.accept(ModItems.STARCATCHER_FISHING_ROD.get());
                                output.accept(ModItems.STARCATCHER_TWINE.get());

                                output.accept(ModItems.OAKHEART_BERRIES_BAIT.get());

                                output.accept(ModItems.BAIT_SAVING_BOBBER.get());
                                output.accept(ModItems.DIFFICULTY_BOBBER.get());
                                output.accept(ModItems.CREEPER_BOBBER.get());
                                output.accept(ModItems.TREASURE_BOBBER.get());
                                output.accept(ModItems.FAST_BITING_BOBBER.get());


                                //rocket stuff
                                output.accept(ModItems.SPACESHIP_BLUEPRINT.get());
                                output.accept(ModItems.SPACESHIP_BLUEPRINT_SKETCH.get());

                                output.accept(ModBlocks.REFUEL_STATION.get());
                                output.accept(ModItems.TANK.get());
                                output.accept(ModItems.MEDIUM_TANK.get());
                                output.accept(ModItems.LARGE_TANK.get());
                                output.accept(ModItems.ENDERBLAZE_FUEL.get());
                                output.accept(ModItems.PRISTINE_ENDERPEARL_DUST.get());


                                //teleporters
                                output.accept(ModBlocks.ASHA_TELEPORTER.get());
                                output.accept(ModBlocks.LUNAMAR_TELEPORTER.get());
                                output.accept(ModBlocks.EMBER_TELEPORTER.get());

                                //chips
                                output.accept(ModItems.BASIC_MICROCHIP.get());
                                output.accept(ModItems.REFINED_MICROCHIP.get());
                                output.accept(ModItems.ADVANCED_MICROCHIP.get());
                                output.accept(ModItems.ELITE_MICROCHIP.get());

                                //spawn eggs
                                output.accept(ModItems.BLUETALE_SPAWN_EGG.get());
                                output.accept(ModItems.REDTALE_SPAWN_EGG.get());
                                output.accept(ModItems.BUBBLEMOUTH_SPAWN_EGG.get());
                                output.accept(ModItems.GLIMPUFF_SPAWN_EGG.get());
                                output.accept(ModItems.SWIBBLE_SPAWN_EGG.get());
                                output.accept(ModItems.SNUFFLER_SPAWN_EGG.get());
                                output.accept(ModItems.NIMBLE_SPAWN_EGG.get());



                            })
                            .build()
            );

    public static final Supplier<CreativeModeTab> LAICAPS_ASHA =
            CREATIVE_MODE_TABS.register(
                    "laicaps_asha", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.LUNARVEIL.get()))
                            .title(Component.translatable("creativetab.laicaps.laicaps_asha"))
                            .displayItems((itemDisplayParameters, output) ->
                            {
                                output.accept(ModBlocks.ASHA_GLOBE.get());
                                output.accept(ModBlocks.ASHA_TELEPORTER.get());
                                output.accept(ModItems.ASHA_ENTRY.get());

                                //oakheart
                                output.accept(ModBlocks.OAKHEART_SAPLING.get());
                                output.accept(ModBlocks.OAKHEART_LEAVES.get());
                                output.accept(ModBlocks.FLOWERING_OAKHEART_LEAVES.get());
                                output.accept(ModBlocks.OAKHEART_LOG.get());
                                output.accept(ModBlocks.STRIPPED_OAKHEART_LOG.get());
                                output.accept(ModBlocks.OAKHEART_WOOD.get());
                                output.accept(ModBlocks.STRIPPED_OAKHEART_WOOD.get());
                                output.accept(ModBlocks.OAKHEART_PLANKS.get());
                                output.accept(ModBlocks.OAKHEART_STAIRS.get());
                                output.accept(ModBlocks.OAKHEART_SLAB.get());
                                output.accept(ModBlocks.OAKHEART_FENCE.get());
                                output.accept(ModBlocks.OAKHEART_FENCE_GATE.get());
                                output.accept(ModItems.OAKHEART_DOOR.get());
                                output.accept(ModBlocks.OAKHEART_TRAPDOOR.get());
                                output.accept(ModItems.OAKHEART_SIGN.get());
                                output.accept(ModItems.OAKHEART_HANGING_SIGN.get());
                                output.accept(ModBlocks.OAKHEART_PRESSURE_PLATE.get());
                                output.accept(ModBlocks.OAKHEART_BUTTON.get());
                                output.accept(ModItems.OAKHEART_BOAT.get());
                                output.accept(ModItems.OAKHEART_CHEST_BOAT.get());


                                //oakroot
                                output.accept(ModBlocks.OAKROOT_SAPLING.get());
                                output.accept(ModBlocks.OAKROOT_LEAVES.get());
                                output.accept(ModBlocks.OAKROOT_LOG.get());
                                output.accept(ModBlocks.STRIPPED_OAKROOT_LOG.get());
                                output.accept(ModBlocks.OAKROOT_WOOD.get());
                                output.accept(ModBlocks.STRIPPED_OAKROOT_WOOD.get());
                                output.accept(ModBlocks.OAKROOT_PLANKS.get());
                                output.accept(ModBlocks.OAKROOT_STAIRS.get());
                                output.accept(ModBlocks.OAKROOT_SLAB.get());
                                output.accept(ModBlocks.OAKROOT_FENCE.get());
                                output.accept(ModBlocks.OAKROOT_FENCE_GATE.get());
                                output.accept(ModItems.OAKROOT_DOOR.get());
                                output.accept(ModBlocks.OAKROOT_TRAPDOOR.get());
                                output.accept(ModItems.OAKROOT_SIGN.get());
                                output.accept(ModItems.OAKROOT_HANGING_SIGN.get());
                                output.accept(ModBlocks.OAKROOT_PRESSURE_PLATE.get());
                                output.accept(ModBlocks.OAKROOT_BUTTON.get());
                                output.accept(ModItems.OAKROOT_BOAT.get());
                                output.accept(ModItems.OAKROOT_CHEST_BOAT.get());

                                //asha environment
                                output.accept(ModBlocks.LUNARVEIL.get());
                                output.accept(ModBlocks.ASHA_GRASS.get());
                                output.accept(ModBlocks.ASHA_SHORT_GRASS.get());
                                output.accept(ModBlocks.ASHA_GRASS_BLOCK.get());
                                output.accept(ModBlocks.ASHA_DIRT.get());

                                //riverthorne
                                output.accept(ModBlocks.RIVERTHORNE_THISTLE.get());
                                output.accept(ModBlocks.RIVERTHORNE.get());

                                //sweetlily
                                output.accept(ModBlocks.VIOLET_SWEETLILY.get());
                                output.accept(ModBlocks.PEACH_SWEETLILY.get());
                                output.accept(ModBlocks.NAVY_SWEETLILY.get());
                                output.accept(ModBlocks.MAGENTA_SWEETLILY.get());
                                output.accept(ModBlocks.CHERRY_SWEETLILY.get());

                                //bluetale & redtale
                                output.accept(ModItems.BLUETALE.get());
                                output.accept(ModItems.BLUETALE_SPAWN_EGG.get());
                                output.accept(ModItems.COOKED_BLUETALE.get());
                                output.accept(ModItems.BLUETALE_BUCKET.get());
                                output.accept(ModItems.REDTALE.get());
                                output.accept(ModItems.REDTALE_SPAWN_EGG.get());
                                output.accept(ModItems.COOKED_REDTALE.get());
                                output.accept(ModItems.REDTALE_BUCKET.get());

                                //snuffler
                                output.accept(ModItems.SNUFFLER_SPAWN_EGG.get());
                                output.accept(ModItems.SNUFFLER_CHOP.get());
                                output.accept(ModItems.COOKED_SNUFFLER_CHOP.get());

                                //oakheart berries
                                output.accept(ModItems.NIMBLE_SWEET_TREAT.get());
                                output.accept(ModItems.OAKHEART_BERRIES.get());

                                //jar
                                output.accept(ModItems.OAKHEART_BERRIES_JAM.get());
                                output.accept(ModItems.JAR.get());
                                output.accept(ModItems.STARFLIES_JAR.get());



                            })
                            .build()
            );


    public static final Supplier<CreativeModeTab> LAICAPS_LUNAMAR =
            CREATIVE_MODE_TABS.register(
                    "laicaps_lunamar", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOONSHADE_FRUIT.get()))
                            .title(Component.translatable("creativetab.laicaps.laicaps_lunamar"))
                            .displayItems((itemDisplayParameters, output) ->
                            {
                                output.accept(ModBlocks.LUNAMAR_GLOBE.get());
                                output.accept(ModBlocks.LUNAMAR_TELEPORTER.get());
                                output.accept(ModItems.LUNAMAR_ENTRY.get());


                                //illuma
                                output.accept(ModBlocks.ILLUMA.get());
                                output.accept(ModBlocks.MOONSHADE_KELP.get());
                                output.accept(ModItems.MOONSHADE_FRUIT.get());

                                //bubblemouth
                                output.accept(ModItems.BUBBLEMOUTH.get());
                                output.accept(ModItems.BUBBLEMOUTH_SPAWN_EGG.get());
                                output.accept(ModItems.COOKED_BUBBLEMOUTH.get());
                                output.accept(ModItems.BUBBLEMOUTH_BUCKET.get());

                                //moonray
                                output.accept(ModItems.MOONRAY.get());
                                output.accept(ModItems.MOONRAY_SPAWN_EGG.get());
                                output.accept(ModItems.MOONRAY_BUCKET.get());

                                //glimpuff
                                output.accept(ModItems.GLIMPUFF.get());
                                output.accept(ModItems.GLIMPUFF_SPAWN_EGG.get());
                                output.accept(ModItems.COOKED_GLIMPUFF.get());
                                output.accept(ModItems.GLIMPUFF_BUCKET.get());


                                output.accept(ModItems.SWIBBLE_SPAWN_EGG.get());

                            })
                            .build());

    public static final Supplier<CreativeModeTab> LAICAPS_EMBER =
            CREATIVE_MODE_TABS.register(
                    "laicaps_ember", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.BARRIER))
                            .title(Component.translatable("creativetab.laicaps.laicaps_ember"))
                            .displayItems((itemDisplayParameters, output) ->
                            {

                                output.accept(ModBlocks.EMBER_GLOBE.get());
                                output.accept(ModBlocks.EMBER_TELEPORTER.get());
                                output.accept(ModItems.EMBER_ENTRY.get());



                            }).build());


    public static final Supplier<CreativeModeTab> LAICAPS_PUZZLES =
            CREATIVE_MODE_TABS.register(
                    "laicaps_puzzles", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get()))
                            .title(Component.translatable("creativetab.laicaps.laicaps_puzzles"))
                            .displayItems((itemDisplayParameters, output) ->
                            {
                                output.accept(ModItems.CHISEL.get());

                                //Chisel
                                output.accept(ModItems.CHISEL.get());

                                //treasure chest
                                output.accept(ModBlocks.TREASURE_CHEST.get());

                                //TODO sender receiver
                                //output.accept(ModBlocks.SENDER_PUZZLE_BLOCK.get());
                                //output.accept(ModBlocks.RECEIVER_BLOCK.get());

                                //symbols
                                output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get());
                                output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK.get());
                                output.accept(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get());

                                //chase
                                output.accept(ModBlocks.CHASE_CONTROLLER_BLOCK.get());

                                //hidden
                                output.accept(ModBlocks.HIDDEN_CONTROLLER_BLOCK.get());

                                //TODO rotating
                                output.accept(ModBlocks.ROTATING_CONTROLLER_BLOCK.get());
                                output.accept(ModBlocks.ROTATING_PUZZLE_BLOCK.get());

                                //combat
                                output.accept(ModBlocks.COMBAT_CONTROLLER_BLOCK.get());

                                //notes
                                output.accept(ModBlocks.NOTES_PUZZLE_BLOCK.get());
                                output.accept(ModBlocks.NOTES_CONTROLLER_BLOCK.get());

                                //floating water structure
                                output.accept(ModBlocks.WATER_CONTAINER.get());
                                output.accept(ModBlocks.WATER_CONTAINER_HELPER.get());


                            }).build());


}
