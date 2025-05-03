package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.types.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class RocketSpaceMenu extends AbstractContainerMenu
{
    public final RocketEntity rocketEntity;

    public RocketSpaceMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, ((RocketEntity) inv.player.getVehicle()));
    }

    public RocketSpaceMenu(int containerId, Inventory inv, RocketEntity entity)
    {
        super(ModMenuTypes.ROCKET_SPACE_MENU.get(), containerId);
        this.rocketEntity = entity;

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


        //bandaid fix if player logs out while in the rocket menu so it doesnt crash because entity doesnt exist yet on menu load
        if (rocketEntity == null)
        {
            this.addSlot(new Slot(inv, 10, (8 + 10 * 18) - 168, 142));
            return;
        }


        this.addSlot(new SlotItemHandler(this.rocketEntity.inventory, 0, -88, 35));
    }

    @Override
    public boolean clickMenuButton(Player player, int id)
    {
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha"));

        Vec3 spawnPos = new Vec3(0, 150, 0);

        if (id == 1)
        {
            key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "ember"));
        }

        if (id == 2)
        {
            key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha"));
        }

        if (id == 3)
        {
            key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
            BlockPos b = ((ServerPlayer) player).getRespawnPosition();
            spawnPos = new Vec3(b.getX(), 150, b.getZ());
        }

        if (id == 4)
        {
            key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "lunamar"));
        }

        DimensionTransition dt = new DimensionTransition(
                player.level().getServer().getLevel(key),
                spawnPos,
                new Vec3(0, 0, 0),
                0.0f,
                0.0f,
                entity ->
                {
                }
        );



        this.rocketEntity.changeDimension(dt);

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
        return true;
    }
}
