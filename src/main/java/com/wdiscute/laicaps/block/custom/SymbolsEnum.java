package com.wdiscute.laicaps.block.custom;

import net.minecraft.util.StringRepresentable;

public enum SymbolsEnum implements StringRepresentable
{
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

    private SymbolsEnum(final String pName) {
        this.name = pName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
