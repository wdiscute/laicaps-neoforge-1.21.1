package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.ModItems;
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

    private int counter;
    private RE re;



    public RefuelMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(inv, containerId, null);
    }

    public RefuelMenu(Inventory inv, int containerId, RE re)
    {
        super(ModMenuTypes.REFUEL_MENU.get(), containerId);

        this.re = re;

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


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 1;  // must be the number of slots you have!


    public ItemStack quickMoveStack(Player playerIn, int pIndex)
    {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
        {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(
                    sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                            + TE_INVENTORY_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
        {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;
            }
        } else
        {
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
        counter++;

        if(counter % 2 == 0)
        {
            int fuel = re.getEntityData().get(RE.FUEL);
            if(fuel < 1300 && inventory.getStackInSlot(0).is(ModItems.ENDERBLAZE_FUEL))
            {
                re.getEntityData().set(RE.FUEL, fuel + 11);
                inventory.getStackInSlot(0).shrink(1);
            }
        }

        return true;
    }
}
