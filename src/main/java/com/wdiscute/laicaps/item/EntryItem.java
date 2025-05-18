package com.wdiscute.laicaps.item;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

    @Override
    public Component getName(ItemStack stack)
    {
        return stack.get(ModDataComponents.ENTRY_NAME) == null ?
                Component.literal("Entry Page") :
                Component.literal(stack.get(ModDataComponents.ENTRY_NAME));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        if (stack.get(ModDataComponents.ENTRY_NAME) == null) stack.set(ModDataComponents.ENTRY_NAME, "Entry Page");
        if (stack.get(ModDataComponents.ENTRY_NAME).contains("Entry Page"))
        {
            Random r = new Random();

            String[] dusty = I18n.get("item.laicaps.entry.dusty").split(",");
            List<String> dustylist;
            dustylist = Arrays.stream(dusty).toList();

            String[] research = I18n.get("item.laicaps.entry.research").split(",");
            List<String> researchlist;
            researchlist = Arrays.stream(research).toList();

            String[] paper = I18n.get("item.laicaps.entry.paper").split(",");
            List<String> paperlist;
            paperlist = Arrays.stream(paper).toList();


            String s = dustylist.get(r.nextInt(dustylist.size())) + " " + researchlist.get(r.nextInt(researchlist.size())) + " " + paperlist.get(r.nextInt(paperlist.size()));

            stack.set(
                    ModDataComponents.ENTRY_NAME, s);

        }
    }


}
