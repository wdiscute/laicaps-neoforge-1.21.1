package com.wdiscute.laicaps.util;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.apache.commons.codec.binary.Hex;

import java.awt.*;
import java.util.List;

public class Tooltips
{

    public static void modifyItemTooltip(ItemTooltipEvent event)
    {
        List<Component> tooltipComponents = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".0"))
        {
            Laicaps.hue += 0.001f;
            System.out.println(Laicaps.hue);
            if (event.getFlags().hasShiftDown())
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));

                for (int i = 0; i < 100; i++)
                {
                    if (!I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i))
                        break;

                    String s = I18n.get("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i).toString();

                    //s = "<gradient00>A gorgeous Angelfish, one of the last of it's kind.</gradient22>";


                    if (s.contains("<rgb>"))
                    {
                        Component c = Component.literal("")
                                .append(s.substring(0, s.indexOf("<rgb>")))
                                .append(RGBEachLetter(Laicaps.hue, s.substring(s.indexOf("<rgb>") + 5, s.indexOf("</rgb>")), 0.01f))
                                .append(s.substring(s.indexOf("</rgb>") + 6));
                        tooltipComponents.add(c);
                    }
                    else if (s.contains("<gradient"))
                    {
                        float min = Float.parseFloat("0." + s.substring(s.indexOf("<gradient") + 10, s.indexOf("<gradient") + 12));
                        float max = Float.parseFloat("0." + s.substring(s.indexOf("</gradient") + 11, s.indexOf("</gradient") + 13));

                        Component c = Component.literal("")
                                .append(s.substring(0, s.indexOf("<gradient")))
                                .append(Gradient(Laicaps.hue, s.substring(s.indexOf("<gradient") + 13, s.indexOf("</gradient")), min, max))
                                .append(s.substring(s.indexOf("</gradient") + 14));
                        tooltipComponents.add(c);
                    }
                    else
                    {
                        tooltipComponents.add(Component.translatable("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i));
                    }


                }

            }
            else
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
            }

        }
    }

    public static Component Gradient(float hue, String text, float min, float max)
    {
        Component c = Component.literal("");

        for (int i = 0; i < text.length(); i++)
        {
            String s = text.substring(i, i + 1);


            float pingPongedHue = mapHuePingPong(i * 0.01f + hue, min, max);

            int color = Color.HSBtoRGB(pingPongedHue, 1, 1);

            Component l = Component.literal(s).withColor(color);

            c = Component.literal("").append(c).append(l);
        }

        return c;
    }

    public static float mapHuePingPong(float h, float min, float max)
    {
        h = h % 1;

        // Triangle wave between 0 and 1
        float t = Math.abs(2f * h - 1f);

        // Scale to [min,max]
        return min + t * (max - min);
    }


    public static Component RGBEachLetter(float hue, String text, float speed)
    {

        Component c = Component.literal("");

        hue += 0.001f;

        for (int i = 0; i < text.length(); i++)
        {
            String s = text.substring(i, i + 1);

            int color = Color.HSBtoRGB(i * speed + hue, 1, 1);

            Component l = Component.literal(s).withColor(color);

            c = Component.literal("").append(c).append(l);
        }

        return c;


    }

}
