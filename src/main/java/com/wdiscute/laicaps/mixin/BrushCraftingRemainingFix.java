package com.wdiscute.laicaps.mixin;

import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BrushItem.class)
public class BrushCraftingRemainingFix extends Item
{


    public BrushCraftingRemainingFix(Properties properties)
    {
        super(properties);
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack)
    {
        if(itemStack.getDamageValue() == itemStack.getMaxDamage())
        {
            System.out.println("1");
            return ItemStack.EMPTY;
        }else
        {
            System.out.println("2");
            ItemStack brush = new ItemStack(Items.BRUSH);
            brush.setDamageValue(itemStack.getDamageValue() + 1);
            return brush;
        }
    }
}

