package com.wdiscute.laicaps.block.timer;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TimerControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private BlockPos blockLinkedOffset = new BlockPos(0, 0, 0);
    private String symbols = "all";
    //private String symbols = "mushroom, creeper, flower, cat, heart, whale, moon, hourglass, pickaxe, bow, frog";

    public BlockPos getBlockLinkedOffset()
    {
        setChanged();
        return blockLinkedOffset;
    }

    public String getSymbols()
    {
        setChanged();
        return symbols;
    }


    public void setX(int dawd)
    {
        setChanged();
        blockLinkedOffset = new BlockPos(dawd, blockLinkedOffset.getY(), blockLinkedOffset.getZ());
    }

    public int getX()
    {
        setChanged();
        return blockLinkedOffset.getX();
    }


    public void setZ(int dawd)
    {
        setChanged();
        blockLinkedOffset = new BlockPos(blockLinkedOffset.getX(), blockLinkedOffset.getY(), dawd);
    }

    public int getZ()
    {
        setChanged();
        return blockLinkedOffset.getZ();
    }


    public void setY(int dawd)
    {
        setChanged();
        blockLinkedOffset = new BlockPos(blockLinkedOffset.getX(), dawd, blockLinkedOffset.getZ());
    }

    public int getY()
    {
        setChanged();
        return blockLinkedOffset.getY();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        blockLinkedOffset = new BlockPos(tag.getIntArray("linkedBlock")[0], tag.getIntArray("linkedBlock")[1], tag.getIntArray("linkedBlock")[2]);
        symbols = tag.getString("symbols");

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putIntArray("linkedBlock", new int[]{blockLinkedOffset.getX(), blockLinkedOffset.getY(), blockLinkedOffset.getZ()});
        tag.putString("symbols", symbols);

    }

    public TimerControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get(), pPos, pBlockState);
    }

    @Override
    public void tick()
    {

    }
}
