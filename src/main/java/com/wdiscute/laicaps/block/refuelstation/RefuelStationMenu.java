package com.wdiscute.laicaps.block.refuelstation;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.types.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class RefuelStationMenu extends AbstractContainerMenu
{
    public final RefuelStationBlockEntity blockEntity;
    public final Level level;

    public RefuelStationMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public RefuelStationMenu(int containerId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModMenuTypes.REFUEL_STATION_MENU.get(), containerId);
        this.blockEntity = ((RefuelStationBlockEntity) blockEntity);
        this.level = inv.player.level();

        //player inventory
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }

        //player hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }


        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 53, 35)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.is(ModItems.ENDERBLAZE_FUEL);
            }
        });
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 1, 107, 35)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.is(ModItems.TANK) || stack.is(ModItems.MEDIUM_TANK) || stack.is(ModItems.LARGE_TANK);
            }
        });
    }


    //Made by diesieben07 | https://github.com/diesieben07/SevenCommons
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex)
    {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < 0 + 36)
        {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, 36, 36
                    + 1, false))
            {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < 36 + 1)
        {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, 0, 0 + 36, false))
            {
                return ItemStack.EMPTY;
            }
        } else
        {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0)
        {
            sourceSlot.set(ItemStack.EMPTY);
        } else
        {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.REFUEL_STATION.get());
    }
}
