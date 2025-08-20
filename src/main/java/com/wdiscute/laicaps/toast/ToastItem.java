package com.wdiscute.laicaps.toast;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToastItem extends Item
{
    public ToastItem(Properties properties)
    {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {

        if (level.isClientSide)
        {
            Laicaps.sendToast("asha", "entry1");
        }


        return super.use(level, player, usedHand);
    }
}

