package com.wdiscute.laicaps;


import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes
{

    public static final WoodType OAKHEART = WoodType.register(
            new WoodType(Laicaps.MOD_ID + ":oakheart", BlockSetType.OAK)
    );

    public static final WoodType OAKROOT = WoodType.register(
            new WoodType(Laicaps.MOD_ID + ":oakroot", BlockSetType.OAK)
    );

}
