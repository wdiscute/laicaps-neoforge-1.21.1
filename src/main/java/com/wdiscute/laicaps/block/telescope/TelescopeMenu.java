package com.wdiscute.laicaps.block.telescope;

import com.wdiscute.laicaps.util.AdvHelper;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModMenuTypes;
import com.wdiscute.laicaps.networkandcodecsandshitomgthissuckssomuchpleasehelp.Payloads;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

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
            this.addSlot(new Slot(inv, i, (8 + i * 18) - 168, 142));
        }


        //this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, -88, 35));
    }

    @Override
    public boolean clickMenuButton(Player player, int id)
    {
        if (player instanceof ServerPlayer sp)
        {

            switch (id)
            {
                case 1:
                    AdvHelper.awardAdvancement(sp, "ember_discovered");
                    AdvHelper.awardAdvancementCriteria(sp, "ember_entries", "entry1");
                    PacketDistributor.sendToPlayer(sp, new Payloads.EntryUnlockedPayload("ember_entries", "entry1"));
                    break;

                case 2:
                    AdvHelper.awardAdvancement(sp, "asha_discovered");
                    AdvHelper.awardAdvancementCriteria(sp, "asha_entries", "entry1");
                    PacketDistributor.sendToPlayer(sp, new Payloads.EntryUnlockedPayload("asha_entries", "entry1"));
                    break;

                case 3:
                    AdvHelper.awardAdvancement(sp, "lunamar_discovered");
                    AdvHelper.awardAdvancementCriteria(sp, "ember_entries", "entry1");
                    PacketDistributor.sendToPlayer(sp, new Payloads.EntryUnlockedPayload("lunamar_entries", "entry1"));

            }
        }
        return false;
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
            if (!moveItemStackTo(
                    sourceStack, 36, 36
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.TELESCOPE.get());
    }
}
