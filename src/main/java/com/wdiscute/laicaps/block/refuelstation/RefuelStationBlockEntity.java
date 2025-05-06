package com.wdiscute.laicaps.block.refuelstation;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.item.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class RefuelStationBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity
{

    int counter = 0;

    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 64;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public void drops()
    {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(level, this.worldPosition, inv);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Refuel Station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new RefuelStationMenu(i, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));

    }

    public RefuelStationBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.REFUEL_STATION.get(), pPos, pBlockState);
    }


    @Override
    public void tick()
    {
        counter++;
        if(counter >= 8)
        {
            counter = 0;

            ItemStack fuel = inventory.getStackInSlot(0);
            ItemStack tank = inventory.getStackInSlot(1);
            if(fuel.isEmpty()) return;
            if(tank.isEmpty()) return;

            if (tank.is(ModItems.TANK.get()) && fuel.is(ModItems.ENDERBLAZE_FUEL.get()))
            {
                int fuelAvailable = tank.get(ModDataComponentTypes.FUEL);
                if (fuelAvailable <= 390)
                {
                    tank.set(ModDataComponentTypes.FUEL, fuelAvailable + 10);
                    fuel.shrink(1);
                    inventory.setStackInSlot(0, fuel);
                    inventory.setStackInSlot(1, tank);
                }
            }

            if (tank.is(ModItems.MEDIUM_TANK.get()) && fuel.is(ModItems.ENDERBLAZE_FUEL.get()))
            {
                int fuelAvailable = tank.get(ModDataComponentTypes.FUEL);
                if (fuelAvailable <= 790)
                {
                    tank.set(ModDataComponentTypes.FUEL, fuelAvailable + 10);
                    fuel.shrink(1);
                    inventory.setStackInSlot(0, fuel);
                    inventory.setStackInSlot(1, tank);
                }
            }

            if (tank.is(ModItems.LARGE_TANK.get()) && fuel.is(ModItems.ENDERBLAZE_FUEL.get()))
            {
                int fuelAvailable = tank.get(ModDataComponentTypes.FUEL);
                if (fuelAvailable <= 1490)
                {
                    tank.set(ModDataComponentTypes.FUEL, fuelAvailable + 10);
                    fuel.shrink(1);
                    inventory.setStackInSlot(0, fuel);
                    inventory.setStackInSlot(1, tank);
                }
            }
        }

    }
}
