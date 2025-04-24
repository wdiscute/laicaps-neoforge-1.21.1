package com.wdiscute.laicaps;

import com.wdiscute.laicaps.block.astrologytable.AstrologyTableBlockEntity;
import com.wdiscute.laicaps.block.chase.ChaseControllerBlockEntity;
import com.wdiscute.laicaps.block.generics.ModHangingSignBlockEntity;
import com.wdiscute.laicaps.block.generics.ModSignBlockEntity;
import com.wdiscute.laicaps.block.hidden.HiddenControllerBlockEntity;
import com.wdiscute.laicaps.block.receiversender.ReceiverBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesControllerBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesPuzzleBlockEntity;
import com.wdiscute.laicaps.block.symbol.SymbolControllerBlockEntity;
import com.wdiscute.laicaps.block.symbol.SymbolPuzzleBlockEntity;
import com.wdiscute.laicaps.block.telescope.TelescopeBlockEntity;
import com.wdiscute.laicaps.block.watercontainer.WaterContainerHelperBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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

    public static final Supplier<BlockEntityType<NotesControllerBlockEntity>> NOTES_CONTROLLER_BLOCK = BLOCK_ENTITIES.register("notes_controller_block",
            () -> BlockEntityType.Builder.of(NotesControllerBlockEntity::new, ModBlocks.NOTES_CONTROLLER_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<NotesPuzzleBlockEntity>> NOTES_PUZZLE_BLOCK = BLOCK_ENTITIES.register("notes_puzzle_block",
            () -> BlockEntityType.Builder.of(NotesPuzzleBlockEntity::new, ModBlocks.NOTES_PUZZLE_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<ChaseControllerBlockEntity>> CHASE_CONTROLLER_BLOCK = BLOCK_ENTITIES.register("chase_controller_block",
            () -> BlockEntityType.Builder.of(ChaseControllerBlockEntity::new, ModBlocks.CHASE_CONTROLLER_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<HiddenControllerBlockEntity>> HIDDEN_CONTROLLER_BLOCK = BLOCK_ENTITIES.register("hidden_controller_block",
            () -> BlockEntityType.Builder.of(HiddenControllerBlockEntity::new, ModBlocks.HIDDEN_CONTROLLER_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<WaterContainerHelperBlockEntity>> WATER_CONTAINER_HELPER_BLOCK = BLOCK_ENTITIES.register("water_container_helper_block",
            () -> BlockEntityType.Builder.of(WaterContainerHelperBlockEntity::new, ModBlocks.WATER_CONTAINER_HELPER.get())
                    .build(null));

    public static final Supplier<BlockEntityType<TelescopeBlockEntity>> TELESCOPE = BLOCK_ENTITIES.register("telescope",
            () -> BlockEntityType.Builder.of(TelescopeBlockEntity::new, ModBlocks.TELESCOPE.get())
                    .build(null));

    public static final Supplier<BlockEntityType<AstrologyTableBlockEntity>> ASTROLOGY_TABLE = BLOCK_ENTITIES.register("astrology_table",
            () -> BlockEntityType.Builder.of(AstrologyTableBlockEntity::new, ModBlocks.ASTROLOGY_RESEARCH_TABLE.get())
                    .build(null));







    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Laicaps.MOD_ID);

    public static final Supplier<BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITY_TYPES.register(
            "mod_sign",
            () -> BlockEntityType.Builder.of(
                            ModSignBlockEntity::new,
                            ModBlocks.OAKROOT_SIGN.get(),
                            ModBlocks.OAKROOT_WALL_SIGN.get(),
                            ModBlocks.OAKHEART_SIGN.get(),
                            ModBlocks.OAKHEART_WALL_SIGN.get()
                    )
                    .build(null)
    );

    public static final Supplier<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN = BLOCK_ENTITY_TYPES.register(
            "mod_hanging_sign",
            () -> BlockEntityType.Builder.of(
                            ModHangingSignBlockEntity::new,
                            ModBlocks.OAKROOT_HANGING_SIGN.get(),
                            ModBlocks.OAKROOT_WALL_HANGING_SIGN.get(),                            ModBlocks.OAKROOT_HANGING_SIGN.get(),
                            ModBlocks.OAKHEART_HANGING_SIGN.get(),
                            ModBlocks.OAKHEART_WALL_HANGING_SIGN.get()
                            )
                    .build(null)
    );


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
    }

}
