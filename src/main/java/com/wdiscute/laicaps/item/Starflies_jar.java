package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class Starflies_jar extends Item
{
    public Starflies_jar(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        if (context.getLevel().getBlockState(context.getClickedPos().above()).isAir())
        {
            context.getLevel().setBlockAndUpdate(context.getClickedPos().above(), ModBlocks.STARFLIES_BLOCK.get().defaultBlockState());
            context.getItemInHand().shrink(1);
            context.getPlayer().addItem(new ItemStack(ModItems.JAR.get()));
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
