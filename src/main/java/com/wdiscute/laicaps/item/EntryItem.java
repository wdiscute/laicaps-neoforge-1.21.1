package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.util.AdvHelper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntryItem extends Item
{
    public EntryItem(String planet, Properties properties)
    {
        super(properties);
        this.planet = planet;
    }

    private final String planet;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {

        if (awardEntry(player, planet))
        {
            ItemStack is = player.getItemInHand(usedHand);
            is.shrink(1);
            return InteractionResultHolder.consume(is);
        }

        return super.use(level, player, usedHand);
    }


    private static boolean awardEntry(Player player, String planet)
    {
        if (player instanceof ServerPlayer sp)
        {
            Random r = new Random();

            List<String> result = new ArrayList<>();
            AdvHelper.getEntriesRemainingAsIterable(sp, planet + "_entries").forEach(result::add);
            String criteria = result.get(r.nextInt(result.size()));

            if (criteria.equals("none"))
            {
                sp.displayClientMessage(Component.literal("There are no entries left to unlock"), true);
                return false;
            }
            sp.displayClientMessage(Component.translatable("tooltip.laicaps.entry_page.unlock.before").append(Component.translatable("gui.astronomy_research_table." + planet + "." + criteria + ".name")).append(Component.translatable("tooltip.laicaps.entry_page.unlock.after")), true);
            AdvHelper.awardAdvancementCriteria(sp, planet + "_entries", criteria);
            return true;
        }

        return false;
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
