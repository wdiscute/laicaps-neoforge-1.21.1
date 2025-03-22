package com.wdiscute.laicaps;


import com.wdiscute.laicaps.item.ModFoodProperties;
import com.wdiscute.laicaps.item.custom.ChiselItem;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);


    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(new Item.Properties()
                    .durability(32)));



    public static final DeferredItem<Item> OAKROOT_SIGN =
            ITEMS.register("oakroot_sign",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.OAKROOT_SIGN.get(),
                            ModBlocks.OAKROOT_WALL_SIGN.get()
                    ));

    public static final DeferredItem<Item> OAKROOT_HANGING_SIGN =
            ITEMS.register("oakroot_hanging_sign",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.OAKROOT_HANGING_SIGN.get(),
                            ModBlocks.OAKROOT_WALL_HANGING_SIGN.get()
                    ));

    public static final DeferredItem<Item> OAKHEART_SIGN =
            ITEMS.register("oakheart_sign",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.OAKHEART_SIGN.get(),
                            ModBlocks.OAKHEART_WALL_SIGN.get()
                    ));

    public static final DeferredItem<Item> OAKHEART_HANGING_SIGN =
            ITEMS.register("oakheart_hanging_sign",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.OAKHEART_HANGING_SIGN.get(),
                            ModBlocks.OAKHEART_WALL_HANGING_SIGN.get()
                    ));


    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register("oakheart_berries",
            () -> new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


}
