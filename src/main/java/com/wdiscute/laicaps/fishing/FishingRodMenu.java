package com.wdiscute.laicaps.fishing;

import com.wdiscute.laicaps.ModDataAttachments;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModMenuTypes;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.List;

public class FishingRodMenu extends AbstractContainerMenu
{
    public final ItemStackHandler inventory = new ItemStackHandler(2)
    {
        @Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            return 64;
        }

    };

    public FishingRodMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, inv.player.getMainHandItem());
    }

    public FishingRodMenu(int containerId, Inventory inv, ItemStack itemStack)
    {
        super(ModMenuTypes.FISHING_ROD_MENU.get(), containerId);

        //player inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(inv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
        //player hotbar
        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }


        inventory.setStackInSlot(0, itemStack.get(ModDataComponents.BOBBER.get()).copyOne());
        inventory.setStackInSlot(1, itemStack.get(ModDataComponents.BAIT.get()).copyOne());

        this.addSlot(new SlotItemHandler(inventory, 0, 53, 35));
        this.addSlot(new SlotItemHandler(inventory, 1, 107, 35));
    }

    @Override
    public void removed(Player player)
    {
        if (!player.level().isClientSide)
        {
            player.getMainHandItem().set(
                    ModDataComponents.BOBBER.get(),
                    ItemContainerContents.fromItems(List.of(inventory.getStackInSlot(0).copy())));

            player.getMainHandItem().set(
                    ModDataComponents.BAIT.get(),
                    ItemContainerContents.fromItems(List.of(inventory.getStackInSlot(1).copy())));

            player.setItemInHand(InteractionHand.MAIN_HAND, player.getMainHandItem());
        }


        super.removed(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i)
    {
        return null;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.getMainHandItem().is(ModItems.STARCATCHER_FISHING_ROD.get());
    }
}
