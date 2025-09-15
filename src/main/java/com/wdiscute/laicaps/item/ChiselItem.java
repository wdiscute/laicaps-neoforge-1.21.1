package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.LaicapsKeys;
import com.wdiscute.laicaps.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChiselItem extends Item
{

    public ChiselItem(Properties pProperties)
    {
        super(pProperties);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        if(context.getLevel().isClientSide);


        Level level = context.getLevel();

        if (!level.isClientSide() && context.getPlayer().isCrouching())
        {
            context.getItemInHand().set(ModDataComponents.COORDINATES.get(), context.getClickedPos());
            level.playSound(null, context.getClickedPos(), ModSounds.CHISEL_USE.get(), SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
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
