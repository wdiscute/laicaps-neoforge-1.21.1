package com.wdiscute.laicaps.block;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.blockentity.ReceiverBlockEntity;
import com.wdiscute.laicaps.blockentity.SymbolControllerBlockEntity;
import com.wdiscute.laicaps.blockentity.SymbolPuzzleBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntity
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Laicaps.MOD_ID);

    public static final Supplier<BlockEntityType<ReceiverBlockEntity>> RECEIVER_BLOCK = BLOCK_ENTITIES.register("receiver_block",
            () -> BlockEntityType.Builder.of(ReceiverBlockEntity::new, ModBlocks.RECEIVER_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<SymbolPuzzleBlockEntity>> SYMBOL_PUZZLE_BLOCK = BLOCK_ENTITIES.register("symbol_puzzle_block",
            () -> BlockEntityType.Builder.of(SymbolPuzzleBlockEntity::new, ModBlocks.SYMBOL_PUZZLE_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<SymbolControllerBlockEntity>> SYMBOL_CONTROLLER_BLOCK = BLOCK_ENTITIES.register("symbol_controller_block",
            () -> BlockEntityType.Builder.of(SymbolControllerBlockEntity::new, ModBlocks.SYMBOL_CONTROLLER_BLOCK.get())
                    .build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
