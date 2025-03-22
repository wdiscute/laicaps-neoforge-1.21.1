package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.block.ModBlockEntity;
import com.wdiscute.laicaps.block.ModWoodTypes;
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
