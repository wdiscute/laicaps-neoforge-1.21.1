package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.UUID;

public class ReceiverBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private int counter = 0;
    private int tickOffset = 0;
    private final UUID[] arrayuuid = new UUID[15];


    public boolean CanPlayerObtainDrops(UUID uuid)
    {
        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            if (Objects.equals(this.arrayuuid[0], uuid))
            {
                return false;
            }
            if (Objects.equals(this.arrayuuid[0], null))
            {
                this.arrayuuid[i] = uuid;
                return true;
            }

        }
        return false;
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            if (this.arrayuuid[i] == null)
                return;

            tag.putUUID("user" + i, this.arrayuuid[i]);
        }


    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);

        for (int i = 0; i < 16; i++)
        {

            if (tag.contains("user" + i))
                this.arrayuuid[i] = tag.getUUID("user" + i);



        }

    }


    @Override
    public void tick()
    {
        if (this.level == null || this.level.isClientSide()) return;

        if (this.tickOffset == 0)
        {
            this.tickOffset = (int) (Math.random() * 20 + 1);
        }
        counter++;

        if ((counter + this.tickOffset) % 20 == 0)
        {
            this.level.scheduleTick(this.getBlockPos(), ModBlocks.RECEIVER_BLOCK.get(), 1);
        }

        if (counter > 10000000) counter = 0;

    }


    public ReceiverBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.RECEIVER_BLOCK.get(), pPos, pBlockState);
    }
}
