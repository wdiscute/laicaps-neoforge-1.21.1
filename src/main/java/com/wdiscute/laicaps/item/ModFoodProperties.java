package com.wdiscute.laicaps.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ModFoodProperties
{
    public static final FoodProperties KOHLRABI = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(4)
            .effect(new MobEffectInstance(
                    MobEffects.ABSORPTION, 2000, 4), 0.42f)
            .alwaysEdible()
            .build();


    public static final FoodProperties OAKHEART_BERRIES = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(4)
            .usingConvertsTo(Items.STICK)
            .build();


    public static final FoodProperties COOKED_BLUETALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .build();

    public static final FoodProperties RAW_BLUETALE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build();
}

