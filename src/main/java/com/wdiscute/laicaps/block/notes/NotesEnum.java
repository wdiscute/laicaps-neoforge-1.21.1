package com.wdiscute.laicaps.block.notes;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Random;

public enum NotesEnum implements StringRepresentable
{
    RANDOM("random", SoundEvents.SKELETON_DEATH, 1f),
    ONE("one", SoundEvents.NOTE_BLOCK_PLING.value(), 0.6f),
    TWO("two", SoundEvents.NOTE_BLOCK_PLING.value(), 0.7f),
    THREE("three", SoundEvents.NOTE_BLOCK_PLING.value(), 0.8f),
    FOUR("four", SoundEvents.NOTE_BLOCK_PLING.value(), 0.9f),
    FIVE("five", SoundEvents.NOTE_BLOCK_PLING.value(), 1f),
    SIX("six", SoundEvents.NOTE_BLOCK_PLING.value(), 1.1f),
    SEVEN("seven", SoundEvents.NOTE_BLOCK_PLING.value(), 1.2f),
    EIGHT("eight", SoundEvents.NOTE_BLOCK_PLING.value(), 1.3f),
    NINE("nine", SoundEvents.NOTE_BLOCK_PLING.value(), 1.4f);

    private final String name;
    private final SoundEvent sound;
    private final Float pitch;

    public SoundEvent getSound()
    {
        return this.sound;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public static NotesEnum getRandom()
    {
        Random r = new Random();
        int i = r.nextInt(((int) Arrays.stream(NotesEnum.values()).count() - 1));
        return NotesEnum.values()[i + 1];
    }

    public static NotesEnum GetNextNote(NotesEnum note)
    {
        //cycles through the list of NotesEnum - 1
        for (int i = 0; i < Arrays.stream(NotesEnum.values()).count() - 1; i++)
        {
            if(note == NotesEnum.values()[i])
            {
                //if the symbol matches then returns the next in the list
                return NotesEnum.values()[i + 1];
            }
        }

        //if no matching symbol is found that means it's the last one so returns the second on the list to skip random
        return NotesEnum.values()[1];

    }


    NotesEnum(final String pName, SoundEvent sound, float pitch)
    {
        this.name = pName;
        this.sound = sound;
        this.pitch = pitch;
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
