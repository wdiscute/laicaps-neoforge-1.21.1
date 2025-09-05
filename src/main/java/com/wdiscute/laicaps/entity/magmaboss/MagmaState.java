package com.wdiscute.laicaps.entity.magmaboss;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum MagmaState
{
    IDLE(0),
    WAKING(1),
    FIRST_PHASE(2),
    INVULNERABLE(3),
    SECOND_PHASE(4),
    DYING(5);


    private static final IntFunction<MagmaState> BY_ID = ByIdMap.continuous(
            MagmaState::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    public static final StreamCodec<ByteBuf, MagmaState> STREAM_CODEC = ByteBufCodecs
            .idMapper(BY_ID, MagmaState::id);

    private final int id;

    MagmaState(int id)
    {
        this.id = id;
    }

    private int id()
    {
        return this.id;
    }

}
