package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RPDoorStairs extends RP
{
    public RPDoorStairs(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, RocketEntity parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, false, parentRocket, interaction);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return parentRocket.getEntityData().get(RocketEntity.DOOR);
    }
}
