package com.wdiscute.laicaps;

import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.item.ModItems;
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
                        output.accept(ModBlocks.OAKHEART_LOG.get());
                        output.accept(ModItems.OAKHEART_BERRIES.get());
                        //oakroot
                        output.accept(ModBlocks.OAKROOT_SAPLING.get());
                        output.accept(ModBlocks.OAKROOT_LEAVES.get());
                        output.accept(ModBlocks.OAKROOT_LOG.get());
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


    public static final Supplier<CreativeModeTab> ALEXANDRITE_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("alexandrite_items_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALEXANDRITE.get()))
                    .title(Component.translatable("creativetab.laicaps.alexandrite_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ALEXANDRITE.get());
                        output.accept(ModBlocks.ALEXANDRITE_STAIRS.get());
                        output.accept(ModBlocks.ALEXANDRITE_SLAB.get());
                        output.accept(ModBlocks.ALEXANDRITE_TRAPDOOR.get());
                        output.accept(ModBlocks.ALEXANDRITE_DOOR.get());
                        output.accept(ModBlocks.ALEXANDRITE_BUTTON.get());
                        output.accept(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.ALEXANDRITE_FENCE.get());
                        output.accept(ModBlocks.ALEXANDRITE_FENCE_GATE.get());
                        output.accept(ModBlocks.ALEXANDRITE_WALL.get());

                        output.accept(ModBlocks.ALEXANDRITE_LAMP.get());

                        output.accept(ModItems.RAW_ALEXANDRITE.get());
                        output.accept(ModBlocks.RAW_ALEXENDRITE_BLOCK.get());
                        output.accept(ModBlocks.ALEXENDRITE_BLOCK.get());
                        output.accept(ModBlocks.ALEXENDRITE_ORE.get());
                        output.accept(ModBlocks.ALEXENDRITE_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.MAGIC_BLOCK.get());
                        output.accept(ModItems.CHISEL.get());
                        output.accept(ModItems.KOHLRABI.get());
                        output.accept(ModItems.AURORA_ASHES.get());

                    })
                    .build()
            );
}
