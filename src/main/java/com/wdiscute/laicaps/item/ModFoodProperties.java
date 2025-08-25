package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

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
            .nutrition(2)
            .saturationModifier(1)
            .usingConvertsTo(Items.STICK)
            .build();

    public static final FoodProperties OAKHEART_BERRIES_JAM = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(3)
            .usingConvertsTo(ModItems.JAR)
            .build();

    public static final FoodProperties SNUFFLER_CHOP = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.3f)
            .build();

    public static final FoodProperties COOKED_SNUFFLER_CHOP = new FoodProperties.Builder()
            .nutrition(8)
            .saturationModifier(0.8f)
            .build();


    public static final FoodProperties COOKED_BASIC_FISH = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .build();

    public static final FoodProperties BASIC_RAW_FISH = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build();

    public static final FoodProperties GHOST_FISH = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .effect(new MobEffectInstance(MobEffects.INVISIBILITY, 1200, 0), 0.6F)
            .build();

    public static final FoodProperties MOONSHADE_FRUIT = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .build();

    public static final FoodProperties MOONRAY = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 0.6F)
            .build();

    public static final FoodProperties THUNDERCHARGED_EEL = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .effect(new MobEffectInstance(MobEffects.POISON, 100, 2), 1F)
            .build();

    public static final FoodProperties EEL = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .effect(new MobEffectInstance(MobEffects.POISON, 100, 1), 1F)
            .build();

    public static final FoodProperties LORD_OF_DEATH_AND_DOOM = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .effect(new MobEffectInstance(MobEffects.WITHER, 19, 0), 1F)
            .build();

    public static final FoodProperties VESANI = new FoodProperties.Builder()
            .nutrition(10)
            .saturationModifier(10)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 2), 1F)
            .build();

    public static final FoodProperties KARPENJOE = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(2)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 3), 1F)
            .build();
}

