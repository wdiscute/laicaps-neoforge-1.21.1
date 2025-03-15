package com.wdiscute.laicaps.block.custom;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

public enum SymbolsEnum implements StringRepresentable
{
    RANDOM("random"),
    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five"),
    SIX("six"),
    SEVEN("seven"),
    EIGHT("eight"),
    NINE("nine"),
    TEN("ten");

    private final String name;

    public static SymbolsEnum getRandom()
    {

        Random r = new Random();

        int i = r.nextInt(((int) Arrays.stream(SymbolsEnum.values()).count() - 1));

        return SymbolsEnum.values()[i];
    }

    public static SymbolsEnum GetNextSymbolInCycle(String s)
    {

        System.out.println(SymbolsEnum.valueOf(SymbolsEnum.class, s));


        return SymbolsEnum.valueOf("three");
    }


    SymbolsEnum(final String pName)
    {
        this.name = pName;
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
