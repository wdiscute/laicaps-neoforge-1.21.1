package com.wdiscute.laicaps.block.sign;

import com.wdiscute.laicaps.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignBlockEntity extends SignBlockEntity
{


    public ModHangingSignBlockEntity(BlockPos pos, BlockState blockState)
    {
        super(ModBlockEntity.MOD_HANGING_SIGN.get() ,pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return ModBlockEntity.MOD_HANGING_SIGN.get();
    }
}
