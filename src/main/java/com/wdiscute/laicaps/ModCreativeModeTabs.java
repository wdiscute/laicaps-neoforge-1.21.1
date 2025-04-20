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

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }


    public static final Supplier<CreativeModeTab> LAICAPS =
            CREATIVE_MODE_TABS.register("laicaps", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ELITE_NAVIGATION_CHIP.get()))
                    .title(Component.translatable("creativetab.laicaps.laicaps"))
                    .displayItems( (itemDisplayParameters, output) -> {

                        //teleporters
                        output.accept(ModBlocks.ASHA_TELEPORTER.get());
                        output.accept(ModBlocks.LUNAMAR_TELEPORTER.get());
                        output.accept(ModBlocks.EMBER_TELEPORTER.get());

                        //chips
                        output.accept(ModItems.BASIC_MICROCHIP.get());
                        output.accept(ModItems.BASIC_NAVIGATION_CHIP.get());
                        output.accept(ModItems.REFINED_MICROCHIP.get());
                        output.accept(ModItems.REFINED_NAVIGATION_CHIP.get());
                        output.accept(ModItems.ADVANCED_MICROCHIP.get());
                        output.accept(ModItems.ADVANCED_NAVIGATION_CHIP.get());
                        output.accept(ModItems.ELITE_MICROCHIP.get());
                        output.accept(ModItems.ELITE_NAVIGATION_CHIP.get());




                    })
                    .build()
            );

    public static final Supplier<CreativeModeTab> LAICAPS_ASHA =
            CREATIVE_MODE_TABS.register("laicaps_asha", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.LUNARVEIL.get()))
                    .title(Component.translatable("creativetab.laicaps.laicaps_asha"))
                    .displayItems( (itemDisplayParameters, output) -> {

                        //oakheart
                        output.accept(ModBlocks.OAKHEART_SAPLING.get());
                        output.accept(ModBlocks.OAKHEART_LEAVES.get());
                        output.accept(ModBlocks.FLOWERING_OAKHEART_LEAVES.get());
                        output.accept(ModItems.OAKHEART_BERRIES.get());
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





                        //water flower
                        output.accept(ModBlocks.WATER_FLOWER.get());


                    })
                    .build()
            );

    public static final Supplier<CreativeModeTab> LAICAPS_LUNAMAR =
            CREATIVE_MODE_TABS.register("laicaps_lunamar", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WATER_FLOWER.get()))
                    .title(Component.translatable("creativetab.laicaps.laicaps_lunamar"))
                    .displayItems( (itemDisplayParameters, output) -> {

                        //water flower
                        output.accept(ModBlocks.WATER_FLOWER.get());


                    })
                    .build());

    public static final Supplier<CreativeModeTab> LAICAPS_EMBER =
            CREATIVE_MODE_TABS.register("laicaps_ember", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.BARRIER))
                    .title(Component.translatable("creativetab.laicaps.laicaps_ember"))
                    .displayItems( (itemDisplayParameters, output) -> {

                        //add ember stuff here
                        output.accept(Items.BARRIER);


                    }).build());


    public static final Supplier<CreativeModeTab> LAICAPS_PUZZLES =
            CREATIVE_MODE_TABS.register("laicaps_puzzles", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get()))
                    .title(Component.translatable("creativetab.laicaps.laicaps_puzzles"))
                    .displayItems( (itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHISEL.get());

                        //Chisel
                        output.accept(ModItems.CHISEL.get());

                        //treasure chest
                        output.accept(ModBlocks.TREASURE_CHEST.get());

                        //sender receiver
                        output.accept(ModBlocks.SENDER_PUZZLE_BLOCK.get());
                        output.accept(ModBlocks.RECEIVER_BLOCK.get());

                        //symbols
                        output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get());
                        output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK.get());
                        output.accept(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get());

                        //chase
                        output.accept(ModBlocks.CHASE_CONTROLLER_BLOCK.get());

                        //hidden
                        output.accept(ModBlocks.HIDDEN_CONTROLLER_BLOCK.get());

                        //rotating
                        output.accept(ModBlocks.ROTATING_CONTROLLER_BLOCK.get());
                        output.accept(ModBlocks.ROTATING_PUZZLE_BLOCK.get());

                        //notes
                        output.accept(ModBlocks.NOTES_PUZZLE_BLOCK.get());
                        output.accept(ModBlocks.NOTES_CONTROLLER_BLOCK.get());

                        //floating water structure
                        output.accept(ModBlocks.WATER_CONTAINER.get());
                        output.accept(ModBlocks.WATER_CONTAINER_HELPER.get());



                    }).build());


}
