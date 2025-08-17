package com.wdiscute.laicaps.block.astronomytable;

import com.wdiscute.laicaps.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AstronomyTableBlockEntity extends BlockEntity implements MenuProvider
{

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Astronomy Table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new NotebookMenu(i, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
    }

    public AstronomyTableBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        //super(ModBlockEntity.ASTRONOMY_TABLE.get(), pPos, pBlockState);
        super(null, pPos, pBlockState);
    }

}
