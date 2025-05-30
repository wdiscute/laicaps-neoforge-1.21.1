package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class RocketItem extends Item {


    public RocketItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        RocketEntity rocketEntity = new RocketEntity(ModEntities.ROCKET.get(), context.getLevel());
        rocketEntity.moveTo(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ(), 0, 0);
        context.getLevel().addFreshEntity(rocketEntity);
        context.getItemInHand().setCount(context.getItemInHand().getCount() - 1);
        return InteractionResult.SUCCESS;

    }
}
