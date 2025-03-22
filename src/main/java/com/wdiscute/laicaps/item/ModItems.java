package com.wdiscute.laicaps.item;


import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.block.ModWoodTypes;
import com.wdiscute.laicaps.block.custom.ModStandingSignBlock;
import com.wdiscute.laicaps.item.custom.ChiselItem;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);


    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(new Item.Properties()
                    .durability(32)));



    public static final DeferredItem<Item> OAKROOT_SIGN =
            ITEMS.register("oakroot_sign_test",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.OAKROOT_LOG.get(),
                            ModBlocks.OAKROOT_PLANKS.get()
                    ));






    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register("oakheart_berries",
            () -> new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


}
