package com.wdiscute.laicaps.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class AstronomyNotebookItem extends Item
{

    public AstronomyNotebookItem(Properties pProperties)
    {
        super(pProperties);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        if(stack.get(ModDataComponents.ASTRONOMY_KNOWLEDGE_EMBER) == null)
        {
            stack.set(ModDataComponents.ASTRONOMY_KNOWLEDGE_EMBER, 0);
            stack.set(ModDataComponents.ASTRONOMY_KNOWLEDGE_ASHA, 0);
            stack.set(ModDataComponents.ASTRONOMY_KNOWLEDGE_LUNAMAR, 0);
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {

        pContext.getItemInHand().set(ModDataComponents.ASTRONOMY_KNOWLEDGE_LUNAMAR, 69);
        pContext.getItemInHand().set(ModDataComponents.ASTRONOMY_KNOWLEDGE_EMBER, 69);
        pContext.getItemInHand().set(ModDataComponents.ASTRONOMY_KNOWLEDGE_ASHA, 69);
        pContext.getPlayer().displayClientMessage(Component.translatable("item.laicaps.astronomy_notebook.use"), true);
        return InteractionResult.SUCCESS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> tooltipComponents, TooltipFlag pTooltipFlag)
    {
        if (pStack.get(ModDataComponents.COORDINATES.get()) != null)
        {
            tooltipComponents.add(Component.literal("Last Block Clicked at " + pStack.get(ModDataComponents.COORDINATES.get())));
        }
        super.appendHoverText(pStack, pContext, tooltipComponents, pTooltipFlag);
    }
}
