package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.entity.rocket.RE;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RPDoor extends RP
{
    public RPDoor(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, RE parentRocket, RE.interact interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, false, parentRocket, interaction);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !parentRocket.getEntityData().get(RE.DOOR);
    }
}
