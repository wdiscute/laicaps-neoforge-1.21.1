package com.wdiscute.laicaps.entity.rocket;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RocketDoorStairs extends RocketPart{
    public RocketDoorStairs(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RocketEntity parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return parentRocket.getEntityData().get(RocketEntity.DOOR);
    }
}
