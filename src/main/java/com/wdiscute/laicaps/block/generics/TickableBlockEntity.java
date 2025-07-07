package com.wdiscute.laicaps.block.generics;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity
{
    void tick();

    static <T extends BlockEntity>BlockEntityTicker<T> getTicketHelper(Level level) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((TickableBlockEntity)blockEntity).tick();
    }
}
