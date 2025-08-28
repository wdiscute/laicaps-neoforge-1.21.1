package com.wdiscute.laicaps.util;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.awt.*;
import java.util.List;

public class RGBTooltipHelper
{

    public static void modifyItemTooltip(ItemTooltipEvent event)
    {
        List<Component> tooltipComponents = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".0"))
        {
            Laicaps.hue += 0.001f;
            if (event.getFlags().hasShiftDown())
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));

                for (int i = 0; i < 100; i++)
                {
                    if (!I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i))
                        break;

                    String s = I18n.get("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i).toString();

                    if(s.contains("<rgb>"))
                    {
                        Component c = Component.literal("")
                                .append(s.substring(0, s.indexOf("<rgb>")))
                                .append(RGBEachLetter(Laicaps.hue, s.substring(s.indexOf("<rgb>") + 5, s.length() - s.indexOf("</rgb>") - 12), 0.01f))
                                .append(s.substring(s.indexOf("</rgb>") + 6));
                        tooltipComponents.add(c);
                    }
                    else
                    {
                        tooltipComponents.add(Component.translatable("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i));
                    }



                }

            } else
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
            }

        }
    }


    public static Component RGBEachLetter(float hue, String text, float speed)
    {

        Component c = Component.literal("");

        hue += 0.001f;

        for (int i = 0; i < text.length(); i++)
        {
            String s = text.substring(i, i+1);

            int color = Color.HSBtoRGB(i * speed + hue, 1, 1);

            Component l = Component.literal(s).withColor(color);

            c = Component.literal("").append(c).append(l);
        }

        return c;


    }

}
