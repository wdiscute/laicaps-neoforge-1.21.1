package com.wdiscute.laicaps.block.researchstation;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.astronomytable.AstronomyTableMenu;
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

public class ResearchStationBlockEntity extends BlockEntity implements MenuProvider
{

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Research Station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new ResearchStationMenu(i, inventory, this);
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

    public ResearchStationBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.RESEARCH_STATION.get(), pPos, pBlockState);
    }

}
