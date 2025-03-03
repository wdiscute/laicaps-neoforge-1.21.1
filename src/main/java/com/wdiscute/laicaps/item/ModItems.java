package com.wdiscute.laicaps.item;

import com.google.common.collect.ImmutableList;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.item.custom.ChiselItem;
import com.wdiscute.laicaps.item.custom.FuelItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);

    public static final DeferredItem<Item> ALEXANDRITE = ITEMS.register("alexandrite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ALEXANDRITE = ITEMS.register("raw_alexandrite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(new Item.Properties()
                    .durability(32)));

    public static final DeferredItem<Item> KOHLRABI = ITEMS.register("kohlrabi",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KOHLRABI)));

    public static final DeferredItem<Item> AURORA_ASHES = ITEMS.register("aurora_ashes",
            () -> new FuelItem(new Item.Properties().food(ModFoodProperties.KOHLRABI), 1200) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.laicaps.aurora_ashes.tooltip"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }
            });


    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register("oakheart_berries",
            () -> new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
