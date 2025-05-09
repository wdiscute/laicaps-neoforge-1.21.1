package com.wdiscute.laicaps.block.telescope;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.item.ModDataComponentTypes;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.SlotItemHandler;

public class TelescopeMenu extends AbstractContainerMenu
{
    public final TelescopeBlockEntity blockEntity;
    public final Level level;
    public final BlockState blockstate;

    public TelescopeMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public TelescopeMenu(int containerId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModMenuTypes.TELESCOPE_MENU.get(), containerId);
        this.blockEntity = ((TelescopeBlockEntity) blockEntity);
        this.level = inv.player.level();
        this.blockstate = level.getBlockState(blockEntity.getBlockPos());

        //player inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(inv, l + i * 9 + 9, (8 + l * 18) - 168, 84 + i * 18));
            }
        }

        //player hotbar
        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(inv, i, (8 + i * 18)  - 168, 142));
        }


        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, -88, 35));
    }

    @Override
    public boolean clickMenuButton(Player player, int id)
    {
        ItemStack book = this.blockEntity.inventory.getStackInSlot(0);

        if(!book.is(ModItems.ASTRONOMY_NOTEBOOK)) return false;

        if(id == 1)
        {
            book.set(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER, book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER) + 1);
            this.blockEntity.inventory.setStackInSlot(0, book);
        }

        if(id == 2)
        {
            book.set(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_ASHA, book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_ASHA) + 1);
            this.blockEntity.inventory.setStackInSlot(0, book);
        }

        if(id == 4)
        {
            book.set(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_LUNAMAR, book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_LUNAMAR) + 1);
            this.blockEntity.inventory.setStackInSlot(0, book);
        }

        return super.clickMenuButton(player, id);
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
                player, ModBlocks.TELESCOPE.get()) ||
                stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                        player, ModBlocks.ADVANCED_TELESCOPE.get())
                ;
    }
}
