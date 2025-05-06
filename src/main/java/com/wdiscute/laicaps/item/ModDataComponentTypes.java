package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes
{
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE ,Laicaps.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> COORDINATES = register("coordinates",
            builder -> builder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ASTRONOMY_KNOWLEDGE_EMBER = register("astronomy_knowledge_ember",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 100)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ASTRONOMY_KNOWLEDGE_ASHA = register("astronomy_knowledge_asha",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 100)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ASTRONOMY_KNOWLEDGE_LUNAMAR = register("astronomy_knowledge_lunamar",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 100)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TANK_FUEL = register("tank_fuel",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 400)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MEDIUM_TANK_FUEL = register("medium_tank_fuel",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 700)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LARGE_TANK_FUEL = register("large_tank_fuel",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 1300)));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }

}
