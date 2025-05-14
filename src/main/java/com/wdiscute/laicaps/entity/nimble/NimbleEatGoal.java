package com.wdiscute.laicaps.entity.nimble;

import net.minecraft.world.entity.ai.goal.Goal;

public class NimbleEatGoal extends Goal
{
    private final NimbleEntity nimbleEntity;

    public NimbleEatGoal(NimbleEntity nimble)
    {
        this.nimbleEntity = nimble;
    }

    @Override
    public boolean canUse()
    {
        return this.nimbleEntity.eatingAS.getAccumulatedTime() > 0;
    }

    @Override
    public boolean isInterruptable()
    {
        return false;
    }
}
