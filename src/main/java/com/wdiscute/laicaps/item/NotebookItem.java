package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.block.astronomytable.NotebookMenu;
import com.wdiscute.laicaps.fishing.FishingRodMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NotebookItem extends Item implements MenuProvider
{
    public NotebookItem(Properties properties)
    {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {
        player.openMenu(this);

        return super.use(level, player, usedHand);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("notebook");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new NotebookMenu(i, inventory);
    }
}
