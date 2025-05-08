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
            return ItemStack.EMPTY;
        }else
        {
            ItemStack brush = new ItemStack(Items.BRUSH);
            brush.setDamageValue(itemStack.getDamageValue() + 1);
            return brush;
        }
    }
}

