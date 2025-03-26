package com.wdiscute.laicaps.block.notes;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NotesPuzzleBlockEntity extends BlockEntity implements TickableBlockEntity
{

    BlockPos linkedBlock = new BlockPos(0,0,0);
    private int activeTime = -1;

    public BlockPos getLinkedBlock()
    {
        setChanged();
        return linkedBlock;
    }

    public void setLinkedBlock(BlockPos blockPos, Player player)
    {
        linkedBlock = blockPos;
        player.displayClientMessage(Component.literal("Linked  " + blockPos + ". Hopefully its a controller because im not checking"), true);
    }

    public void playNote(Integer activeTime)
    {
        this.activeTime = activeTime;
        BlockState state = level.getBlockState(getBlockPos());
        level.playSound(null, getBlockPos(), state.getValue(NotesPuzzleBlock.NOTE).getSound(), SoundSource.BLOCKS, 1f, state.getValue(NotesPuzzleBlock.NOTE).getPitch());
        level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(NotesPuzzleBlock.ACTIVE, true));
    }

    @Override
    public void tick()
    {
        if(activeTime == -1) return;
        if(activeTime > -1) activeTime--;
        if(activeTime == -1) level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(NotesPuzzleBlock.ACTIVE, false));
    }




    public NotesPuzzleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.NOTES_PUZZLE_BLOCK.get(), pPos, pBlockState);
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
