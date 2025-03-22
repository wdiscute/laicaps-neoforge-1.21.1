package com.wdiscute.laicaps.item.custom;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item
{

    public ChiselItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();

        if (!level.isClientSide() && pContext.getPlayer().isCrouching())
        {
            pContext.getItemInHand().set(ModDataComponentTypes.COORDINATES.get(), pContext.getClickedPos());
            level.playSound(null, pContext.getClickedPos(), ModSounds.CHISEL_USE.get(), SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> tooltipComponents, TooltipFlag pTooltipFlag)
    {
        if (Laicaps.HasExtraInfoKeyDown())
        {
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));
            tooltipComponents.add(Component.translatable("tooltip.laicaps.chisel.shift_down"));
        }
        else
        {
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
        }

        if (pStack.get(ModDataComponentTypes.COORDINATES.get()) != null)
        {
            tooltipComponents.add(Component.literal("Last Block Clicked at " + pStack.get(ModDataComponentTypes.COORDINATES.get())));
        }
        super.appendHoverText(pStack, pContext, tooltipComponents, pTooltipFlag);
    }
}
