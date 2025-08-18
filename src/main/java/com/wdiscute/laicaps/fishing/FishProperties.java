package com.wdiscute.laicaps.fishing;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FishProperties
{

    final public Item fish;
    public Item bucket_fish;
    final private List<ResourceKey<Biome>> biome;
    final private List<ResourceKey<Level>> dim;
    final private int baseChance;

    private List<Item> incorrectBaits = List.of();

    private List<ResourceKey<Biome>> biomeBlacklist = new ArrayList<>();
    private List<ResourceKey<Dimension>> dimensionBlackList = new ArrayList<>();
    private boolean mustBeClear = false;
    private boolean mustBeRaining = false;
    private boolean mustBeThundering = false;
    private int mustBeCaughtBellowY = Integer.MAX_VALUE;
    private int mustBeCaughtAboveY = Integer.MIN_VALUE;
    private boolean mustHaveCorrectBait = false;
    private boolean mustHaveCorrectBobber = false;
    private Item correctBait = ItemStack.EMPTY.getItem();
    private Item correctBobber = ItemStack.EMPTY.getItem();
    private int correctBaitChanceAdded = 0;
    private daytime timeRestriction = daytime.ALL;

    public boolean consumesBait = true;

    public boolean shouldSkipMinigame = false;


    public enum daytime
    {
        ALL,
        DAY,
        NOON,
        NIGHT,
        MIDNIGHT
    }


    public FishProperties(Item fish, List<ResourceKey<Level>> dimensionList, List<ResourceKey<Biome>> biomeList, int baseChance)
    {
        this.fish = fish;
        this.dim = dimensionList;
        this.biome = biomeList;
        this.baseChance = baseChance;
    }

    public FishProperties skipsMinigame()
    {
        this.shouldSkipMinigame = true;
        return this;
    }

    public FishProperties mustHaveCorrectBobber(Item bobber)
    {
        this.mustHaveCorrectBobber = true;
        this.correctBobber = bobber;
        return this;
    }

    public FishProperties doesNotConsumeBait()
    {
        this.consumesBait = false;
        return this;
    }


    public FishProperties incorrectBaits(List<Item> blacklist)
    {
        this.incorrectBaits = blacklist;
        return this;
    }

    public FishProperties mustBeThundering()
    {
        this.mustBeThundering = true;
        return this;
    }

    public FishProperties mustBeRaining()
    {
        this.mustBeRaining = true;
        return this;
    }

    public FishProperties mustBeClear()
    {
        this.mustBeClear = true;
        return this;
    }

    public FishProperties timeRestrictions(daytime time)
    {
        timeRestriction = time;
        return this;
    }


    public FishProperties mustBeCaughtBellowY(int i)
    {
        this.mustBeCaughtBellowY = i;
        return this;
    }

    public FishProperties mustBeCaughtAboveY(int i)
    {
        this.mustBeCaughtAboveY = i;
        return this;
    }

    public FishProperties mustHaveCorrectBait()
    {
        this.mustHaveCorrectBait = true;
        return this;
    }

    public FishProperties correctBaitChanceAdded(Item item, int addedChance)
    {
        this.correctBait = item;
        this.correctBaitChanceAdded = addedChance;
        return this;
    }

    public FishProperties canBeBucketed(Item bucket_of_fish)
    {
        this.bucket_fish = bucket_of_fish;
        return this;
    }


    public FishProperties biomeBlacklist(List<ResourceKey<Biome>> biome)
    {
        biomeBlacklist = biome;
        return this;
    }

    public FishProperties biomeBlacklist(ResourceKey<Biome> biome)
    {
        biomeBlacklist.add(biome);
        return this;
    }

    public FishProperties dimensionBlacklist(List<ResourceKey<Dimension>> dim)
    {
        dimensionBlackList = dim;
        return this;
    }

    public FishProperties dimensionBlacklist(ResourceKey<Dimension> dim)
    {
        dimensionBlackList.add(dim);
        return this;
    }


    public int getChance(Level level, BlockPos pos, ItemStack bobber, ItemStack bait)
    {

        int chance = baseChance;

        //dimension  check
        if (dim != null)
        {
            if (!this.dim.contains(level.dimension()))
            {
                return 0;
            }
        }

        //biome check
        if (biome != null)
        {
            if (!this.biome.contains(level.getBiome(pos).getKey()))
            {
                return 0;
            }
        }

        if (!biomeBlacklist.isEmpty())
        {
            if (this.biomeBlacklist.contains(level.getBiome(pos).getKey()))
            {
                return 0;
            }
        }

        if (!dimensionBlackList.isEmpty())
        {
            if (this.dimensionBlackList.contains(level.dimension()))
            {
                return 0;
            }
        }

        //blacklisted baits
        if (incorrectBaits.contains(bait.getItem()))
        {
            return 0;
        }

        //y level check
        if (pos.getY() > mustBeCaughtBellowY)
        {
            return 0;
        }

        //y level check
        if (pos.getY() < mustBeCaughtAboveY)
        {
            return 0;
        }

        //time check
        if (timeRestriction != daytime.ALL)
        {
            long time = level.getDayTime() % 24000;

            switch (timeRestriction)
            {
                case daytime.DAY:
                    if (!(time > 23000 || time < 12700)) return 0;
                    break;

                case daytime.NOON:
                    if (!(time > 3500 && time < 8500)) return 0;
                    break;

                case daytime.NIGHT:
                    if (!(time < 23000 && time > 12700)) return 0;
                    break;

                case daytime.MIDNIGHT:
                    if (!(time > 16500 && time < 19500)) return 0;
                    break;
            }
        }

        //clear check
        if (mustBeClear && (level.getRainLevel(0) > 0.5 || level.getThunderLevel(0) > 0.5))
        {
            return 0;
        }

        //rain check
        if (mustBeRaining && level.getRainLevel(0) < 0.5)
        {
            return 0;
        }

        //thunder check
        if (mustBeThundering && level.getThunderLevel(0) < 0.5)
        {
            return 0;
        }

        //correct bait check
        if (mustHaveCorrectBait)
        {
            if (bait.is(correctBait))
            {
                chance += correctBaitChanceAdded;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            if (correctBaitChanceAdded > 0)
            {
                if (bait.is(correctBait))
                {
                    chance += correctBaitChanceAdded;
                }
            }
        }

        //correct bobber check
        if (mustHaveCorrectBobber)
        {
            if (!bobber.is(correctBobber))
            {
                return 0;
            }
        }

        return chance;
    }


}
