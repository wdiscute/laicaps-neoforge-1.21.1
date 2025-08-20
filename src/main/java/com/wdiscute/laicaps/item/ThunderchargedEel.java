package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ThunderchargedEel extends Item
{
    public ThunderchargedEel(Properties properties)
    {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        if(isSelected && entity instanceof ServerPlayer sp)
        {
            Random r = new Random();
            if(r.nextFloat() > 0.99f)
            {
                var damage = entity.damageSources().damageTypes;
                var holder = damage.getHolderOrThrow(DamageTypes.LIGHTNING_BOLT);
                entity.hurt(new DamageSource(holder), 0.5f);

                if(r.nextFloat() > 0.9f)
                {
                    stack.shrink(1);
                    sp.addItem(new ItemStack(ModItems.EEL.get()));
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }


}
