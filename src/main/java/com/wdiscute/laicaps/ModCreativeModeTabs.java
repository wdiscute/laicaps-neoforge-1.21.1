package com.wdiscute.laicaps;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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

    public static final Supplier<CreativeModeTab> LAICAPS_TAP =
            CREATIVE_MODE_TABS.register("laicaps_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.LUNARVEIL.get()))
                    .title(Component.translatable("creativetab.laicaps.laicaps"))
                    .displayItems( (itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHISEL.get());

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
                        output.accept(ModBlocks.OAKHEART_DOOR.get());
                        output.accept(ModBlocks.OAKHEART_TRAPDOOR.get());
                        output.accept(ModBlocks.OAKHEART_SIGN.get());
                        output.accept(ModBlocks.OAKHEART_HANGING_SIGN.get());
                        output.accept(ModBlocks.OAKHEART_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.OAKHEART_BUTTON.get());
                        //output.accept(ModBlocks.OAKHEART_BOAT.get());
                        //output.accept(ModBlocks.OAKHEART_CHEST_BOAT.get());



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
                        output.accept(ModBlocks.OAKROOT_DOOR.get());
                        output.accept(ModBlocks.OAKROOT_TRAPDOOR.get());
                        output.accept(ModBlocks.OAKROOT_SIGN.get());
                        output.accept(ModBlocks.OAKROOT_HANGING_SIGN.get());
                        output.accept(ModBlocks.OAKROOT_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.OAKROOT_BUTTON.get());
                        //output.accept(ModBlocks.OAKROOT_BOAT.get());
                        //output.accept(ModBlocks.OAKROOT_CHEST_BOAT.get());




                        //puzzle blocks sender receiver
                        output.accept(ModBlocks.SENDER_PUZZLE_BLOCK.get());
                        output.accept(ModBlocks.RECEIVER_BLOCK.get());
                        //puzzle blocks symbols
                        output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get());
                        output.accept(ModBlocks.SYMBOL_PUZZLE_BLOCK.get());
                        output.accept(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get());
                        //asha environment
                        output.accept(ModBlocks.LUNARVEIL.get());
                        output.accept(ModBlocks.ASHA_GRASS.get());
                        output.accept(ModBlocks.ASHA_SHORT_GRASS.get());
                        output.accept(ModBlocks.ASHA_GRASS_BLOCK.get());
                        output.accept(ModBlocks.ASHA_DIRT.get());
                        //riverthorne
                        output.accept(ModBlocks.RIVERTHORNE_THISTLE.get());
                        output.accept(ModBlocks.RIVERTHORNE.get());

                    })
                    .build()
            );
}
