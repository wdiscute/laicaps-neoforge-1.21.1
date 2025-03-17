package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.block.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SymbolPuzzleBlockEntity extends BlockEntity
{
    private BlockPos blockLinkedOffset = new BlockPos(0, 0, 0);
    private String symbols = "mushroom, creeper, flower, cat, heart, whale, moon, hourglass, pickaxe, bow, frog";

    public BlockPos getBlockLinkedOffset()
    {
        return this.blockLinkedOffset;
    }

    public String getSymbols()
    {
        return symbols;
    }


    public void setX(int dawd)
    {
        this.blockLinkedOffset = new BlockPos(dawd, this.blockLinkedOffset.getY(), this.blockLinkedOffset.getZ());
    }

    public int getX()
    {
        return this.blockLinkedOffset.getX();
    }


    public void setZ(int dawd)
    {
        this.blockLinkedOffset = new BlockPos(this.blockLinkedOffset.getX(), this.blockLinkedOffset.getY(), dawd);
    }

    public int getZ()
    {
        return this.blockLinkedOffset.getZ();
    }


    public void setY(int dawd)
    {
        this.blockLinkedOffset = new BlockPos(this.blockLinkedOffset.getX(), dawd, this.blockLinkedOffset.getZ());
    }

    public int getY()
    {
        return this.blockLinkedOffset.getY();
    }


    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        this.blockLinkedOffset = new BlockPos(pTag.getInt("linkedx"), pTag.getInt("linkedy"), pTag.getInt("linkedz"));
        this.symbols = pTag.getString("symbols");

    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        pTag.putInt("linkedx", this.blockLinkedOffset.getX());
        pTag.putInt("linkedy", this.blockLinkedOffset.getY());
        pTag.putInt("linkedz", this.blockLinkedOffset.getZ());
        pTag.putString("symbols", symbols);
    }

    public SymbolPuzzleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get(), pPos, pBlockState);
    }
}
