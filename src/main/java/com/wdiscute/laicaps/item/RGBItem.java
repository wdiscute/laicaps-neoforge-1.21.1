package com.wdiscute.laicaps.item;

import com.sun.jna.platform.win32.OaIdl;
import com.wdiscute.laicaps.util.RGBTooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.awt.*;
import java.util.List;

public class RGBItem extends Item
{
    public RGBItem(Properties properties)
    {
        super(properties);
    }


    float hue;

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {

        System.out.println(context.level());

        hue += 0.001f;

        tooltipComponents.add(RGBTooltipHelper.RGBEachLetter(hue, "test tooltip thats very cool and long", 0.01f));
        tooltipComponents.add(RGBTooltipHelper.RGBEachLetter(hue, "and also has 2 lines lol", 0.1f));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
