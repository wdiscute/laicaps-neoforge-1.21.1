package com.wdiscute.laicaps.block.symbol;

import com.wdiscute.laicaps.ModBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SymbolPuzzleBlockEntity extends BlockEntity
{
    private BlockPos linkedBlock = new BlockPos(0, 0, 0);
    private String symbols = "all";

    public BlockPos getLinkedBLock()
    {
        return linkedBlock;
    }

    public String getSymbols()
    {
        return symbols;
    }

    public void setLinkedBLock(BlockPos bs)
    {
        setChanged();
        linkedBlock = bs;
        Minecraft.getInstance().player.displayClientMessage(Component.literal("linked to block at " + bs), false);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        linkedBlock = new BlockPos(tag.getIntArray("linkedBlock")[0], tag.getIntArray("linkedBlock")[1], tag.getIntArray("linkedBlock")[2]);
        symbols = tag.getString("symbols");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putIntArray("linkedBlock", new int[]{linkedBlock.getX(), linkedBlock.getY(), linkedBlock.getZ()});
        tag.putString("symbols", symbols);
    }

    public SymbolPuzzleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get(), pPos, pBlockState);
    }
}
