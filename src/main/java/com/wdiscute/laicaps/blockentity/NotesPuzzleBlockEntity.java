package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.custom.notes.NotesControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Random;

public class NotesPuzzleBlockEntity extends BlockEntity
{

    BlockPos linkedBlock = new BlockPos(0,0,0);

    public BlockPos getLinkedBlock()
    {
        setChanged();
        return linkedBlock;
    }

    public void setLinkedBlock(BlockPos blockPos)
    {
        linkedBlock = blockPos;
    }


    private BlockPos DecodeBlockPosWithOffset(Level plevel, BlockPos pos, BlockPos posOffset)
    {
        //returns the world coords of the linked block based on the offset stored
        BlockState pState = plevel.getBlockState(pos);

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.SOUTH)
            return new BlockPos(pos.getX() + (posOffset.getZ() * -1), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getX());

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.WEST)
            return new BlockPos(pos.getX() + (posOffset.getX() * -1), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getZ() * -1));

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.NORTH)
            return new BlockPos(pos.getX() + posOffset.getZ(), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getX() * -1));

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.EAST)
            return new BlockPos(pos.getX() + posOffset.getX(), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getZ());

        return new BlockPos(0, 0, 0);

    }



    public NotesPuzzleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.NOTES_CONTROLLER_BLOCK.get(), pPos, pBlockState);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.loadAdditional(pTag, pRegistries);
        linkedBlock = new BlockPos(pTag.getIntArray("linkedBlock")[0], pTag.getIntArray("linkedBlock")[1], pTag.getIntArray("linkedBlock")[2]);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.saveAdditional(pTag, pRegistries);
        pTag.putIntArray("linkedBlock", new int[]{linkedBlock.getX(), linkedBlock.getY(), linkedBlock.getZ()});

    }
}
