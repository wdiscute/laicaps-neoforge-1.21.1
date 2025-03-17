package com.wdiscute.laicaps.block.custom;

import net.minecraft.util.StringRepresentable;

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

    public static SymbolsEnum GetNextSymbol(SymbolsEnum sym)
    {

        //cycles through the list of SymbolsEnum - 1
        for (int i = 0; i < Arrays.stream(SymbolsEnum.values()).count() - 1; i++)
        {
            if(sym == SymbolsEnum.values()[i])
            {
                //if the symbol matches then returns the next in the list
                return SymbolsEnum.values()[i + 1];
            }
        }

        //if no matching symbol is found that means it's the last one so returns the first on the list
        return SymbolsEnum.values()[1];

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
