package com.wdiscute.laicaps.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class AstrologyNotebookItem extends Item
{

    public AstrologyNotebookItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {

        pContext.getItemInHand().set(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_LUNAMAR, 1);
        pContext.getItemInHand().set(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_EMBER, 1);
        pContext.getItemInHand().set(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_ASHA, 1);
        pContext.getPlayer().displayClientMessage(Component.translatable("item.laicaps.astrology_notebook.use"), true);
        return InteractionResult.SUCCESS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> tooltipComponents, TooltipFlag pTooltipFlag)
    {
        if (pStack.get(ModDataComponentTypes.COORDINATES.get()) != null)
        {
            tooltipComponents.add(Component.literal("Last Block Clicked at " + pStack.get(ModDataComponentTypes.COORDINATES.get())));
        }
        super.appendHoverText(pStack, pContext, tooltipComponents, pTooltipFlag);
    }
}
