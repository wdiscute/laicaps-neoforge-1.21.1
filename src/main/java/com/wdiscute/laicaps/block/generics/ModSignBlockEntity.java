package com.wdiscute.laicaps.block.generics;

import com.wdiscute.laicaps.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity
{


    public ModSignBlockEntity(BlockPos pos, BlockState blockState)
    {
        super(ModBlockEntity.MOD_SIGN.get(), pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return ModBlockEntity.MOD_SIGN.get();
    }
}
