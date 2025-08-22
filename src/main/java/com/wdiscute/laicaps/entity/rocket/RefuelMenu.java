package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;


public class RefuelMenu extends AbstractContainerMenu
{
    public final ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            return 64;
        }

    };

    public RefuelMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv);
    }

    public RefuelMenu(int containerId, Inventory inv)
    {
        super(ModMenuTypes.REFUEL_MENU.get(), containerId);

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

        this.addSlot(new SlotItemHandler(inventory, 0, 53, 35));
    }

    @Override
    public void removed(Player player)
    {
        if (player instanceof ServerPlayer sp) {
            ItemStack itemstack = inventory.getStackInSlot(0);
            if (!itemstack.isEmpty()) {
                if (player.isAlive() && !(sp.hasDisconnected())) {
                    player.getInventory().placeItemBackInInventory(itemstack);
                } else {
                    player.drop(itemstack, false);
                }
            }
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
        return true;
    }
}
