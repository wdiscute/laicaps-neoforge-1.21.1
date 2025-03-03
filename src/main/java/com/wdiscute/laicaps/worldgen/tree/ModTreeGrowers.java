package com.wdiscute.laicaps.worldgen.tree;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers
{
    //make one of these for each type of trees, also accepts big tree varients it seems
    public static final TreeGrower WALNUT = new TreeGrower(Laicaps.MOD_ID + ":walnut",
            Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_KEY), Optional.empty());

    public static final TreeGrower OAKHEART = new TreeGrower(Laicaps.MOD_ID + ":oakheart",
            Optional.empty(), Optional.of(ModConfiguredFeatures.OAKHEART_KEY), Optional.empty());

    public static final TreeGrower OAKROOT = new TreeGrower(Laicaps.MOD_ID + ":oakroot",
            Optional.empty(), Optional.of(ModConfiguredFeatures.OAKROOT_KEY), Optional.empty());


}
