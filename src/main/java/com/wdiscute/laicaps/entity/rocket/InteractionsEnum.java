package com.wdiscute.laicaps.entity.rocket;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Random;

public enum InteractionsEnum implements StringRepresentable
{
    RIDE("ride"),
    OPEN_MAIN_SCREEN("open_main_screen"),
    OPEN_RESEARCH_SCREEN("open_research_screen"),
    GLOBE_SPIN("globe_spin"),
    TOGGLE_DOOR("toggle_door"),
    NONE("none");

    private final String name;

    InteractionsEnum(final String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}
