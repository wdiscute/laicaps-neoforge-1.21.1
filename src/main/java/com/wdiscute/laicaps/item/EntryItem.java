package com.wdiscute.laicaps.item;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EntryItem extends Item
{
    public EntryItem(Properties properties)
    {
        super(properties);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getName(ItemStack stack)
    {

        String entryName = stack.get(ModDataComponents.ENTRY_NAME);

        if (entryName.contains("Entry Page")) return Component.literal(entryName);

        String[] numbers = entryName.split(",");

        String[] dusty = I18n.get("item.laicaps.entry.dusty").split(",");
        String[] research = I18n.get("item.laicaps.entry.research").split(",");
        String[] paper = I18n.get("item.laicaps.entry.paper").split(",");

        int dustyIndex = Integer.parseInt(numbers[0]) % dusty.length;
        int researchIndex = Integer.parseInt(numbers[1]) % research.length;
        int paperIndex = Integer.parseInt(numbers[2]) % paper.length;

        String finalstring = dusty[dustyIndex] + " " + research[researchIndex] + " " + paper[paperIndex];

        return Component.literal(finalstring);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        if (stack.get(ModDataComponents.ENTRY_NAME) == null) stack.set(ModDataComponents.ENTRY_NAME, "Entry Page");
        if (stack.get(ModDataComponents.ENTRY_NAME).contains("Entry Page") && !level.isClientSide)
        {
            Random r = new Random();

            stack.set(ModDataComponents.ENTRY_NAME, r.nextInt(100) + "," + r.nextInt(100) + "," + r.nextInt(100));
        }
    }


}
