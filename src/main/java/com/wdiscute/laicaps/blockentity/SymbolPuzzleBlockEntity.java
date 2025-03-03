package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.block.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SymbolPuzzleBlockEntity extends BlockEntity
{
    private BlockPos blockLinked = getBlockPos();

    public void setBlockLinked(BlockPos blockPos)
    {
        this.blockLinked = blockPos;
    }

    public BlockPos getBlockLinked()
    {
        return this.blockLinked;
    }


    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        this.blockLinked = new BlockPos(pTag.getInt("linkedx"), pTag.getInt("linkedy"), pTag.getInt("linkedz"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        pTag.putInt("linkedx", this.blockLinked.getX());
        pTag.putInt("linkedy", this.blockLinked.getY());
        pTag.putInt("linkedz", this.blockLinked.getZ());
    }

    public SymbolPuzzleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.SYMBOL_PUZZLE_BLOCK.get(), pPos, pBlockState);
    }
}
