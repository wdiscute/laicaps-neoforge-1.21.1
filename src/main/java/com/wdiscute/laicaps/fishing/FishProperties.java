package com.wdiscute.laicaps.fishing;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class FishProperties
{

    final public Item fish;
    public Item bucket_fish;
    final private List<ResourceKey<Biome>> biome;
    final private List<ResourceKey<Level>> dim;
    final private int baseChance;

    private List<Item> incorrectBaits = List.of();

    private boolean mustBeRaining = false;
    private boolean mustBeThundering = false;
    private int mustBeCaughtBellowY = Integer.MAX_VALUE;
    private int mustBeCaughtAboveY = Integer.MIN_VALUE;
    private boolean mustHaveCorrectBait = false;
    private boolean mustHaveCorrectBobber = false;
    private Item correctBait = ItemStack.EMPTY.getItem();
    private Item correctBobber = ItemStack.EMPTY.getItem();
    private int correctBaitChanceAdded = 0;

    public boolean consumesBait = true;

    public boolean shouldSkipMinigame = false;


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
