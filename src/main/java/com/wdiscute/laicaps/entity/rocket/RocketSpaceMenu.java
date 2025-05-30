package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RocketSpaceMenu extends AbstractContainerMenu
{
    public final Container container;

    public RocketSpaceMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, new SimpleContainer(5));
    }

    public RocketSpaceMenu(int containerId, Inventory playerInventory, Container container)
    {
        super(ModMenuTypes.ROCKET_SPACE_MENU.get(), containerId);
        this.container = container;

        container.startOpen(playerInventory.player);


        //player inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, (8 + l * 18) - 168, 84 + i * 18));
            }
        }

        //player hotbar
        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(playerInventory, i, (8 + i * 18) - 168, 142));
        }

        //book
        this.addSlot(new Slot(this.container, 0, -24, 2220)
                     {
                         @Override
                         public boolean mayPlace(ItemStack stack)
                         {
                             return false;
                         }

                         @Override
                         public boolean mayPickup(Player player)
                         {
                             return false;
                         }
                     }
        );

        //TODO expansion item and system
        this.addSlot(new Slot(this.container, 1, -158, 2220)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player player)
            {
                return false;
            }
        });

        //tank
        this.addSlot(new Slot(this.container, 2, -158, 52)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return !container.getItem(3).is(Items.DIRT) ||
                        stack.is(ModItems.TANK) || stack.is(ModItems.MEDIUM_TANK) || stack.is(ModItems.LARGE_TANK);
            }

            @Override
            public boolean mayPickup(Player player)
            {
                if (!container.getItem(3).is(Items.DIRT)) return false;
                return super.mayPickup(player);
            }
        });

        //rocket state
        this.addSlot(new Slot(this.container, 3, -158, 999)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player player)
            {
                return false;
            }
        });

        //planet selected
        this.addSlot(new Slot(this.container, 4, -158, -99920)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player player)
            {
                return false;
            }
        });
    }

    @Override
    public boolean clickMenuButton(Player player, int id)
    {
        if (id == 1) container.setItem(4, new ItemStack(ModItems.EMBER.get()));
        if (id == 2) container.setItem(4, new ItemStack(ModItems.ASHA.get()));
        if (id == 3) container.setItem(4, new ItemStack(ModItems.OVERWORLD.get()));
        if (id == 4) container.setItem(4, new ItemStack(ModItems.LUNAMAR.get()));


        return super.clickMenuButton(player, id);
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
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!


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
        return true;
    }
}
