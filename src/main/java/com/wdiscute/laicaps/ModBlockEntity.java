package com.wdiscute.laicaps;

import com.wdiscute.laicaps.block.astronomytable.AstronomyTableBlockEntity;
import com.wdiscute.laicaps.block.chase.ChaseControllerBlockEntity;
import com.wdiscute.laicaps.block.combat.CombatControllerBlockEntity;
import com.wdiscute.laicaps.block.rotating.RotatingControllerBlockentity;
import com.wdiscute.laicaps.block.rotating.RotatingPuzzleBlock;
import com.wdiscute.laicaps.block.rotating.RotatingPuzzleBlockentity;
import com.wdiscute.laicaps.block.sign.ModHangingSignBlockEntity;
import com.wdiscute.laicaps.block.sign.ModSignBlockEntity;
import com.wdiscute.laicaps.block.hidden.HiddenControllerBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesControllerBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesPuzzleBlockEntity;
import com.wdiscute.laicaps.block.refuelstation.RefuelStationBlockEntity;
import com.wdiscute.laicaps.block.researchstation.ResearchStationBlockEntity;
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

    //TODO REWORK THIS
//    public static final Supplier<BlockEntityType<ReceiverBlockEntity>> RECEIVER_BLOCK = BLOCK_ENTITIES.register("receiver_block",
//            () -> BlockEntityType.Builder.of(ReceiverBlockEntity::new, ModBlocks.RECEIVER_BLOCK.get())
//                    .build(null));

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

    public static final Supplier<BlockEntityType<CombatControllerBlockEntity>> COMBAT_CONTROLLER_BLOCK = BLOCK_ENTITIES.register("combat_controller_block",
            () -> BlockEntityType.Builder.of(CombatControllerBlockEntity::new, ModBlocks.COMBAT_CONTROLLER_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<WaterContainerHelperBlockEntity>> WATER_CONTAINER_HELPER_BLOCK = BLOCK_ENTITIES.register("water_container_helper_block",
            () -> BlockEntityType.Builder.of(WaterContainerHelperBlockEntity::new, ModBlocks.WATER_CONTAINER_HELPER.get())
                    .build(null));

    public static final Supplier<BlockEntityType<TelescopeBlockEntity>> TELESCOPE = BLOCK_ENTITIES.register("telescope",
            () -> BlockEntityType.Builder.of(TelescopeBlockEntity::new, ModBlocks.TELESCOPE.get())
                    .build(null));

    public static final Supplier<BlockEntityType<AstronomyTableBlockEntity>> ASTRONOMY_TABLE = BLOCK_ENTITIES.register("astronomy_table",
            () -> BlockEntityType.Builder.of(AstronomyTableBlockEntity::new, ModBlocks.ASTRONOMY_RESEARCH_TABLE.get())
                    .build(null));

    public static final Supplier<BlockEntityType<RefuelStationBlockEntity>> REFUEL_STATION = BLOCK_ENTITIES.register("refuel_station",
            () -> BlockEntityType.Builder.of(RefuelStationBlockEntity::new, ModBlocks.REFUEL_STATION.get())
                    .build(null));

    public static final Supplier<BlockEntityType<ResearchStationBlockEntity>> RESEARCH_STATION = BLOCK_ENTITIES.register("research_station",
            () -> BlockEntityType.Builder.of(ResearchStationBlockEntity::new, ModBlocks.RESEARCH_STATION.get())
                    .build(null));

    public static final Supplier<BlockEntityType<RotatingPuzzleBlockentity>> ROTATING_PUZZLE = BLOCK_ENTITIES.register("rotating_puzzle",
            () -> BlockEntityType.Builder.of(RotatingPuzzleBlockentity::new, ModBlocks.ROTATING_PUZZLE_BLOCK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<RotatingControllerBlockentity>> ROTATING_CONTROLLER = BLOCK_ENTITIES.register("rotating_controller",
            () -> BlockEntityType.Builder.of(RotatingControllerBlockentity::new, ModBlocks.ROTATING_CONTROLLER_BLOCK.get())
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
