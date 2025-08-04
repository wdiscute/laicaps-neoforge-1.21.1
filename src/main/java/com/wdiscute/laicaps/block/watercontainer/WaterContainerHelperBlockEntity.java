package com.wdiscute.laicaps.block.watercontainer;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WaterContainerHelperBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private BlockPos blockLinked = new BlockPos(0, 0, 0);
    private int counter = 0;

    @Override
    public void tick()
    {
        counter++;
        if(counter<100) return;

        if(!blockLinked.equals(getBlockPos()))
        {
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    public void linkBlock(BlockPos bp)
    {
        blockLinked = bp;
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        blockLinked = new BlockPos(tag.getIntArray("linkedBlock")[0], tag.getIntArray("linkedBlock")[1], tag.getIntArray("linkedBlock")[2]);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putIntArray("linkedBlock", new int[]{blockLinked.getX(), blockLinked.getY(), blockLinked.getZ()});
    }

    public WaterContainerHelperBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.WATER_CONTAINER_HELPER_BLOCK.get(), pPos, pBlockState);
    }

}
