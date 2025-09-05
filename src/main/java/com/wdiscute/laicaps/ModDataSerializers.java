package com.wdiscute.laicaps;

import com.wdiscute.laicaps.entity.magmaboss.MagmaState;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataSerializers
{
    private static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(
            NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Laicaps.MOD_ID);

    public static final Supplier<EntityDataSerializer<MagmaState>> MAGMA_STATE = ENTITY_DATA_SERIALIZERS
            .register("magma_state", () -> EntityDataSerializer.forValueType(MagmaState.STREAM_CODEC));


    public static void register(IEventBus eventBus) {
        ENTITY_DATA_SERIALIZERS.register(eventBus);
    }


}
