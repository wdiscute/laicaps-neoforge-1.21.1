package com.wdiscute.laicaps.item.custom;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import com.wdiscute.laicaps.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
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
        Laicaps.appendHoverText(pStack, pContext, tooltipComponents);

        if (pStack.get(ModDataComponentTypes.COORDINATES.get()) != null)
        {
            tooltipComponents.add(Component.literal("Last Block Clicked at " + pStack.get(ModDataComponentTypes.COORDINATES.get())));
        }
        super.appendHoverText(pStack, pContext, tooltipComponents, pTooltipFlag);
    }
}
