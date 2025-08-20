package com.wdiscute.laicaps.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TankItem extends Item
{
    public TankItem(String size, int damage, Properties properties)
    {
        super(properties);
        this.size = size;
        this.damage = damage;
    }

    private final String size;
    private final int damage;

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        int maxFuel = stack.getMaxDamage();
        if (Screen.hasShiftDown())
        {
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));
            for (int i = 0; i < 100; i++)
            {
                if (!I18n.exists("tooltip.laicaps.tank." + size + "." + i)) break;
                tooltipComponents.add(Component.translatable("tooltip.laicaps.tank." + size + "." + i));
            }


            String color = "§4";

            if (stack.get(ModDataComponents.FUEL) > maxFuel / 2) color = "§6";
            if (stack.get(ModDataComponents.FUEL) > maxFuel / 1.5) color = "§a";

            tooltipComponents.add(Component.literal(color + I18n.get("tooltip.laicaps.tank.fuel") + ": §l[" + stack.get(ModDataComponents.FUEL) + " / " + maxFuel + "]"));
        }
        else tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
    }



    @Override
    public int getDamage(ItemStack stack)
    {
        return (damage - stack.get(ModDataComponents.FUEL) == 0) ? 1 : damage - stack.get(ModDataComponents.FUEL);
    }






}

