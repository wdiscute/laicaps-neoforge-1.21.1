package com.wdiscute.laicaps;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.block.astronomytable.AstronomyTableBlock;
import com.wdiscute.laicaps.block.chase.ChaseControllerBlock;
import com.wdiscute.laicaps.block.generics.*;
import com.wdiscute.laicaps.block.hidden.HiddenControllerBlock;
import com.wdiscute.laicaps.block.receiversender.ReceiverBlock;
import com.wdiscute.laicaps.block.receiversender.SenderPuzzleBLock;
import com.wdiscute.laicaps.block.refuelstation.RefuelStationBlock;
import com.wdiscute.laicaps.block.rotating.RotatingControllerBlock;
import com.wdiscute.laicaps.block.rotating.RotatingPuzzleBlock;
import com.wdiscute.laicaps.block.singleblocks.*;
import com.wdiscute.laicaps.block.notes.NotesControllerBlock;
import com.wdiscute.laicaps.block.notes.NotesPuzzleBlock;
import com.wdiscute.laicaps.block.symbol.SymbolControllerBlock;
import com.wdiscute.laicaps.block.symbol.SymbolPuzzleBlock;
import com.wdiscute.laicaps.block.symbol.SymbolPuzzleBlockInactive;
import com.wdiscute.laicaps.block.telescope.TelescopeBaseBlock;
import com.wdiscute.laicaps.block.telescope.TelescopeBlock;
import com.wdiscute.laicaps.block.watercontainer.WaterContainerBlock;
import com.wdiscute.laicaps.block.watercontainer.WaterContainerHelperBlock;
import com.wdiscute.laicaps.types.ModWoodTypes;
import com.wdiscute.laicaps.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Laicaps.MOD_ID);


    //
    //,--.   ,--. ,--.  ,---.    ,-----.
    //|   `.'   | |  | '   .-'  '  .--./
    //|  |'.'|  | |  | `.  `-.  |  |
    //|  |   |  | |  | .-'    | '  '--'\
    //`--'   `--' `--' `-----'   `-----'
    //

    public static final DeferredBlock<Block> SALT =
            registerBlock(
                    "salt", () ->
                            new SaltBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.SCULK)
                                    .noOcclusion()
                                    .noCollission()
                            )
            );


    public static final DeferredBlock<Block> REFUEL_STATION =
            registerBlock(
                    "refuel_station", () ->
                            new RefuelStationBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.STONE)
                            ));

    public static final DeferredBlock<Block> ASHA_TELEPORTER =
            registerBlock(
                    "asha_teleporter", () ->
                            new SimpleTeleporterBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.SCULK)
                            )
                            {
                                @Override
                                public ResourceKey<Level> getDimensionKey()
                                {
                                    return ResourceKey.create(
                                            Registries.DIMENSION,
                                            ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha"));
                                }
                            }
            );


    public static final DeferredBlock<Block> LUNAMAR_TELEPORTER =
            registerBlock(
                    "lunamar_teleporter", () ->
                            new SimpleTeleporterBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.SCULK)
                            )
                            {
                                @Override
                                public ResourceKey<Level> getDimensionKey()
                                {
                                    return ResourceKey.create(
                                            Registries.DIMENSION,
                                            ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "lunamar"));
                                }
                            }
            );

    public static final DeferredBlock<Block> EMBER_TELEPORTER =
            registerBlock(
                    "ember_teleporter", () ->
                            new SimpleTeleporterBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.SCULK)
                            )
                            {
                                @Override
                                public ResourceKey<Level> getDimensionKey()
                                {
                                    return ResourceKey.create(
                                            Registries.DIMENSION,
                                            ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "ember"));
                                }
                            }
            );

    public static final DeferredBlock<Block> TELESCOPE =
            registerBlock(
                    "telescope", () ->
                            new TelescopeBlock(BlockBehaviour.Properties.of()
                                    .strength(6)
                                    .sound(SoundType.GLASS)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> ADVANCED_TELESCOPE =
            registerBlock(
                    "advanced_telescope", () ->
                            new TelescopeBlock(BlockBehaviour.Properties.of()
                                    .strength(6)
                                    .sound(SoundType.GLASS)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> TELESCOPE_STAND =
            registerBlock(
                    "telescope_stand", () ->
                            new TelescopeBaseBlock(BlockBehaviour.Properties.of()
                                    .strength(6)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> ASTRONOMY_RESEARCH_TABLE =
            registerBlock(
                    "astronomy_research_table", () ->
                            new AstronomyTableBlock(BlockBehaviour.Properties.of()
                                    .strength(6)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                            )
            );


    //
    //,------.  ,--. ,--. ,-------. ,-------. ,--.    ,------.
    //|  .--. ' |  | |  | `--.   /  `--.   /  |  |    |  .---'
    //|  '--' | |  | |  |   /   /     /   /   |  |    |  `--,
    //|  | --'  '  '-'  '  /   `--.  /   `--. |  '--. |  `---.
    //`--'       `-----'  `-------' `-------' `-----' `------'
    //

    public static final DeferredBlock<Block> TREASURE_CHEST =
            registerBlock(
                    "treasure_chest", () ->
                            new TreasureChestBlock(BlockBehaviour.Properties.of()
                                    .strength(5)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> SENDER_PUZZLE_BLOCK =
            registerBlock(
                    "sender_puzzle_block", () ->
                            new SenderPuzzleBLock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.AMETHYST)
                            )
            );

    public static final DeferredBlock<Block> RECEIVER_BLOCK =
            registerBlock(
                    "receiver_block", () ->
                            new ReceiverBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.AMETHYST)
                            )
            );

    public static final DeferredBlock<Block> SYMBOL_PUZZLE_BLOCK =
            registerBlock(
                    "symbol_puzzle_block", () ->
                            new SymbolPuzzleBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.STONE)
                            )
            );

    public static final DeferredBlock<Block> SYMBOL_CONTROLLER_BLOCK =
            registerBlock(
                    "symbol_controller_block", () ->
                            new SymbolControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> SYMBOL_PUZZLE_BLOCK_INACTIVE =
            registerBlock(
                    "symbol_puzzle_block_inactive", () ->
                            new SymbolPuzzleBlockInactive(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.STONE)
                            )
            );

    public static final DeferredBlock<Block> NOTES_PUZZLE_BLOCK =
            registerBlock(
                    "notes_puzzle_block", () ->
                            new NotesPuzzleBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.STONE)
                            )
            );

    public static final DeferredBlock<Block> NOTES_CONTROLLER_BLOCK =
            registerBlock(
                    "notes_controller_block", () ->
                            new NotesControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                            )
            );

    public static final DeferredBlock<Block> ROTATING_PUZZLE_BLOCK =
            registerBlock(
                    "rotating_puzzle_block", () ->
                            new RotatingPuzzleBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.STONE)
                            )
            );

    public static final DeferredBlock<Block> ROTATING_CONTROLLER_BLOCK =
            registerBlock(
                    "rotating_controller_block", () ->
                            new RotatingControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                            )
            );


    public static final DeferredBlock<Block> CHASE_CONTROLLER_BLOCK =
            registerBlock(
                    "chase_controller_block", () ->
                            new ChaseControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                            )
            );

    public static final DeferredBlock<Block> HIDDEN_CONTROLLER_BLOCK =
            registerBlock(
                    "hidden_controller_block", () ->
                            new HiddenControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                                    .noCollission()
                            )
            );

    public static final DeferredBlock<Block> COMBAT_CONTROLLER_BLOCK =
            registerBlock(
                    "combat_controller_block", () ->
                            new HiddenControllerBlock(BlockBehaviour.Properties.of()
                                    .strength(30)
                                    .sound(SoundType.WOOD)
                                    .noCollission()
                            )
            );


    //
    // ,-----.    ,---.   ,--. ,--. ,--.  ,--. ,------.   ,---.   ,------.  ,--------.
    //'  .-.  '  /  O  \  |  .'   / |  '--'  | |  .---'  /  O  \  |  .--. ' '--.  .--'
    //|  | |  | |  .-.  | |  .   '  |  .--.  | |  `--,  |  .-.  | |  '--'.'    |  |
    //'  '-'  ' |  | |  | |  |\   \ |  |  |  | |  `---. |  | |  | |  |\  \     |  |
    // `-----'  `--' `--' `--' '--' `--'  `--' `------' `--' `--' `--' '--'    `--'
    //


    public static final DeferredBlock<Block> OAKHEART_SAPLING =
            registerBlock(
                    "oakheart_sapling", () ->
                            new ModSaplingBlock(
                                    ModTreeGrowers.OAKHEART,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> ModBlocks.ASHA_GRASS_BLOCK.get())
                            {
                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter pLevel, BlockPos pPos)
                                {
                                    if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState())
                                        return true;

                                    if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_DIRT.get().defaultBlockState())
                                        return true;

                                    return false;
                                }
                            });

    public static final DeferredBlock<Block> OAKHEART_LOG =
            registerBlock(
                    "oakheart_log", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.WOOD)
                                    .strength(2.0F)
                                    .ignitedByLava()
                            )
                            {
                                @Override
                                protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
                                {
                                    if (stack.is(ItemTags.AXES))
                                    {
                                        level.setBlockAndUpdate(pos, STRIPPED_OAKHEART_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        return ItemInteractionResult.SUCCESS;
                                    }
                                    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                                }
                            }
            );

    public static final DeferredBlock<Block> STRIPPED_OAKHEART_LOG =
            registerBlock(
                    "stripped_oakheart_log", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
                            {
                                @Override
                                protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
                                {
                                    if (stack.is(ItemTags.AXES))
                                    {
                                        level.setBlockAndUpdate(pos, STRIPPED_OAKHEART_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        return ItemInteractionResult.SUCCESS;
                                    }
                                    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                                }
                            }
            );

    public static final DeferredBlock<Block> OAKHEART_WOOD =
            registerBlock(
                    "oakheart_wood", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
                            {
                                @Override
                                protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
                                {
                                    if (stack.is(ItemTags.AXES))
                                    {
                                        level.setBlockAndUpdate(pos, STRIPPED_OAKHEART_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        return ItemInteractionResult.SUCCESS;
                                    }
                                    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                                }
                            }
            );

    public static final DeferredBlock<Block> STRIPPED_OAKHEART_WOOD =
            registerBlock(
                    "stripped_oakheart_wood", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
            );

    public static final DeferredBlock<Block> OAKHEART_PLANKS =
            registerBlock(
                    "oakheart_planks", () ->
                            new Block(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.WOOD)
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F, 3.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKHEART_SLAB =
            registerBlock(
                    "oakheart_slab", () ->
                            new SlabBlock(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.WOOD)
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F, 3.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKHEART_STAIRS =
            registerBlock(
                    "oakheart_stairs", () ->
                            new StairBlock(
                                    OAKHEART_PLANKS.get().defaultBlockState(),
                                    BlockBehaviour.Properties.ofFullCopy(OAKHEART_PLANKS.get())
                            ));

    public static final DeferredBlock<Block> OAKHEART_FENCE =
            registerBlock(
                    "oakheart_fence", () ->
                            new FenceBlock(
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(2.0F, 3.0F)
                                            .ignitedByLava()
                                            .sound(SoundType.WOOD)
                            ));

    public static final DeferredBlock<Block> OAKHEART_FENCE_GATE =
            registerBlock(
                    "oakheart_fence_gate", () ->
                            new FenceGateBlock(
                                    ModWoodTypes.OAKHEART,
                                    BlockBehaviour.Properties.of()
                                            .forceSolidOn()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(2.0F, 3.0F)
                                            .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKHEART_DOOR =
            registerBlockNoItem(
                    "oakheart_door", () ->
                            new DoorBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(3.0F)
                                            .noOcclusion()
                                            .ignitedByLava()
                                            .pushReaction(PushReaction.DESTROY)
                            ));

    public static final DeferredBlock<Block> OAKHEART_TRAPDOOR =
            registerBlock(
                    "oakheart_trapdoor", () ->
                            new TrapDoorBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(3.0F)
                                            .noOcclusion()
                                            .isValidSpawn(Blocks::never)
                                            .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKHEART_BUTTON =
            registerBlock(
                    "oakheart_button", () ->
                            new ButtonBlock(
                                    BlockSetType.OAK, 30,
                                    BlockBehaviour.Properties.of()
                                            .noCollission()
                                            .strength(0.5F)
                                            .pushReaction(PushReaction.DESTROY)
                            ));

    public static final DeferredBlock<Block> OAKHEART_SIGN =
            registerBlockNoItem(
                    "oakheart_sign", () ->
                            new ModStandingSignBlock(
                                    ModWoodTypes.OAKHEART,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKHEART_WALL_SIGN =
            registerBlockNoItem(
                    "oakheart_wall_sign", () ->
                            new ModWallSignBlock(
                                    ModWoodTypes.OAKHEART,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
                            ));


    public static final DeferredBlock<Block> OAKHEART_HANGING_SIGN =
            registerBlockNoItem(
                    "oakheart_hanging_sign", () ->
                            new ModHangingSignBlock(
                                    ModWoodTypes.OAKHEART,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKHEART_WALL_HANGING_SIGN =
            registerBlockNoItem(
                    "oakheart_wall_hanging_sign", () ->
                            new ModWallHangingSignBlock(
                                    ModWoodTypes.OAKHEART,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKHEART_PRESSURE_PLATE =
            registerBlock(
                    "oakheart_pressure_plate", () ->
                            new PressurePlateBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .forceSolidOn()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .noCollission()
                                            .strength(0.5F)
                                            .ignitedByLava()
                                            .pushReaction(PushReaction.DESTROY)
                            ));

    public static final DeferredBlock<Block> OAKHEART_LEAVES =
            registerBlock(
                    "oakheart_leaves", () ->
                            new LeavesBlock(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.PLANT)
                                    .strength(0.2F)
                                    .randomTicks()
                                    .sound(SoundType.GRASS)
                                    .noOcclusion()
                                    .isValidSpawn(Blocks::ocelotOrParrot)
                                    .isSuffocating(ModBlocks::never)
                                    .isViewBlocking(ModBlocks::never)
                                    .ignitedByLava()
                                    .pushReaction(PushReaction.DESTROY)
                                    .isRedstoneConductor(ModBlocks::never)
                            ));

    public static final DeferredBlock<Block> FLOWERING_OAKHEART_LEAVES =
            registerBlock(
                    "flowering_oakheart_leaves", () ->
                            new FloweringOakheartLeavesBlock(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.PLANT)
                                    .strength(0.2F)
                                    .randomTicks()
                                    .sound(SoundType.GRASS)
                                    .noOcclusion()
                                    .isValidSpawn(Blocks::never)
                                    .isSuffocating(ModBlocks::never)
                                    .isViewBlocking(ModBlocks::never)
                                    .ignitedByLava()
                                    .pushReaction(PushReaction.DESTROY)
                                    .isRedstoneConductor(ModBlocks::never)
                            ));


    //
    // ,-----.    ,---.   ,--. ,--. ,------.   ,-----.   ,-----.  ,--------.
    //'  .-.  '  /  O  \  |  .'   / |  .--. ' '  .-.  ' '  .-.  ' '--.  .--'
    //|  | |  | |  .-.  | |  .   '  |  '--'.' |  | |  | |  | |  |    |  |
    //'  '-'  ' |  | |  | |  |\   \ |  |\  \  '  '-'  ' '  '-'  '    |  |
    // `-----'  `--' `--' `--' '--' `--' '--'  `-----'   `-----'     `--'
    //


    public static final DeferredBlock<Block> OAKROOT_SAPLING =
            registerBlock(
                    "oakroot_sapling", () ->
                            new ModSaplingBlock(ModTreeGrowers.OAKROOT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> ModBlocks.ASHA_GRASS_BLOCK.get())
                            {
                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter pLevel, BlockPos pPos)
                                {
                                    if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState())
                                        return true;

                                    if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_DIRT.get().defaultBlockState())
                                        return true;

                                    return false;
                                }
                            });

    public static final DeferredBlock<Block> OAKROOT_LOG =
            registerBlock(
                    "oakroot_log", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.WOOD)
                                    .strength(2.0F)
                                    .ignitedByLava()
                            )
                            {
                                @Override
                                protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
                                {
                                    if (stack.is(ItemTags.AXES))
                                    {
                                        level.setBlockAndUpdate(pos, STRIPPED_OAKROOT_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        return ItemInteractionResult.SUCCESS;
                                    }
                                    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                                }
                            }
            );

    public static final DeferredBlock<Block> STRIPPED_OAKROOT_LOG =
            registerBlock(
                    "stripped_oakroot_log", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
            );

    public static final DeferredBlock<Block> OAKROOT_WOOD =
            registerBlock(
                    "oakroot_wood", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
                            {
                                @Override
                                protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
                                {
                                    if (stack.is(ItemTags.AXES))
                                    {
                                        level.setBlockAndUpdate(pos, STRIPPED_OAKROOT_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        return ItemInteractionResult.SUCCESS;
                                    }
                                    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                                }
                            }
            );

    public static final DeferredBlock<Block> STRIPPED_OAKROOT_WOOD =
            registerBlock(
                    "stripped_oakroot_wood", () ->
                            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            )
            );

    public static final DeferredBlock<Block> OAKROOT_PLANKS =
            registerBlock(
                    "oakroot_planks", () ->
                            new Block(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.WOOD)
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F, 3.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKROOT_SLAB =
            registerBlock(
                    "oakroot_slab", () ->
                            new SlabBlock(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.WOOD)
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(2.0F, 3.0F)
                                    .sound(SoundType.WOOD)
                                    .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKROOT_STAIRS =
            registerBlock(
                    "oakroot_stairs", () ->
                            new StairBlock(
                                    OAKROOT_PLANKS.get().defaultBlockState(),
                                    BlockBehaviour.Properties.ofFullCopy(OAKROOT_PLANKS.get())
                            ));

    public static final DeferredBlock<Block> OAKROOT_FENCE =
            registerBlock(
                    "oakroot_fence", () ->
                            new FenceBlock(
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(2.0F, 3.0F)
                                            .ignitedByLava()
                                            .sound(SoundType.WOOD)
                            ));

    public static final DeferredBlock<Block> OAKROOT_FENCE_GATE =
            registerBlock(
                    "oakroot_fence_gate", () ->
                            new FenceGateBlock(
                                    ModWoodTypes.OAKROOT,
                                    BlockBehaviour.Properties.of()
                                            .forceSolidOn()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(2.0F, 3.0F)
                                            .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKROOT_DOOR =
            registerBlockNoItem(
                    "oakroot_door", () ->
                            new DoorBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(3.0F)
                                            .noOcclusion()
                                            .ignitedByLava()
                                            .pushReaction(PushReaction.DESTROY)
                            ));

    public static final DeferredBlock<Block> OAKROOT_TRAPDOOR =
            registerBlock(
                    "oakroot_trapdoor", () ->
                            new TrapDoorBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .strength(3.0F)
                                            .noOcclusion()
                                            .isValidSpawn(Blocks::never)
                                            .ignitedByLava()
                            ));

    public static final DeferredBlock<Block> OAKROOT_BUTTON =
            registerBlock(
                    "oakroot_button", () ->
                            new ButtonBlock(
                                    BlockSetType.OAK, 30,
                                    BlockBehaviour.Properties.of()
                                            .noCollission()
                                            .strength(0.5F)
                                            .pushReaction(PushReaction.DESTROY)
                            ));

    public static final DeferredBlock<Block> OAKROOT_SIGN =
            registerBlockNoItem(
                    "oakroot_sign", () ->
                            new ModStandingSignBlock(
                                    ModWoodTypes.OAKROOT,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKROOT_WALL_SIGN =
            registerBlockNoItem(
                    "oakroot_wall_sign", () ->
                            new ModWallSignBlock(
                                    ModWoodTypes.OAKROOT,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKROOT_HANGING_SIGN =
            registerBlockNoItem(
                    "oakroot_hanging_sign", () ->
                            new ModHangingSignBlock(
                                    ModWoodTypes.OAKROOT,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKROOT_WALL_HANGING_SIGN =
            registerBlockNoItem(
                    "oakroot_wall_hanging_sign", () ->
                            new ModWallHangingSignBlock(
                                    ModWoodTypes.OAKROOT,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
                            ));

    public static final DeferredBlock<Block> OAKROOT_PRESSURE_PLATE =
            registerBlock(
                    "oakroot_pressure_plate", () ->
                            new PressurePlateBlock(
                                    BlockSetType.OAK,
                                    BlockBehaviour.Properties.of()
                                            .forceSolidOn()
                                            .instrument(NoteBlockInstrument.BASS)
                                            .noCollission()
                                            .strength(0.5F)
                                            .ignitedByLava()
                                            .pushReaction(PushReaction.DESTROY)
                            ));


    public static final DeferredBlock<Block> OAKROOT_LEAVES =
            registerBlock(
                    "oakroot_leaves", () ->
                            new LeavesBlock(BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.PLANT)
                                    .strength(0.2F)
                                    .randomTicks()
                                    .sound(SoundType.GRASS)
                                    .noOcclusion()
                                    .isValidSpawn(Blocks::ocelotOrParrot)
                                    .isSuffocating(ModBlocks::never)
                                    .isViewBlocking(ModBlocks::never)
                                    .ignitedByLava()
                                    .pushReaction(PushReaction.DESTROY)
                                    .isRedstoneConductor(ModBlocks::never)
                            ));


    //
    //  ,---.    ,---.   ,--.  ,--.   ,---.
    // /  O  \  '   .-'  |  '--'  |  /  O  \
    //|  .-.  | `.  `-.  |  .--.  | |  .-.  |
    //|  | |  | .-'    | |  |  |  | |  | |  |
    //`--' `--' `-----'  `--'  `--' `--' `--'
    //
    //
    //,------. ,--.  ,--. ,--.   ,--. ,--. ,------.   ,-----.  ,--.  ,--. ,--.   ,--. ,------. ,--.  ,--. ,--------.
    //|  .---' |  ,'.|  |  \  `.'  /  |  | |  .--. ' '  .-.  ' |  ,'.|  | |   `.'   | |  .---' |  ,'.|  | '--.  .--'
    //|  `--,  |  |' '  |   \     /   |  | |  '--'.' |  | |  | |  |' '  | |  |'.'|  | |  `--,  |  |' '  |    |  |
    //|  `---. |  | `   |    \   /    |  | |  |\  \  '  '-'  ' |  | `   | |  |   |  | |  `---. |  | `   |    |  |
    //`------' `--'  `--'     `-'     `--' `--' '--'  `-----'  `--'  `--' `--'   `--' `------' `--'  `--'    `--'
    //


    public static final DeferredBlock<Block> ASHA_GRASS_BLOCK =
            registerBlock(
                    "asha_grass_block", () ->
                            new AshaGrassBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.ROOTED_DIRT)
                                    .strength(0.6F)
                                    .mapColor(MapColor.GRASS)
                                    .randomTicks()
                            ));

    public static final DeferredBlock<Block> ASHA_DIRT =
            registerBlock(
                    "asha_dirt", () ->
                            new Block(BlockBehaviour.Properties.of()
                                    .sound(SoundType.ROOTED_DIRT)
                                    .strength(0.6F)
                            ));


    public static final DeferredBlock<Block> LUNARVEIL =
            registerBlock(
                    "lunarveil", () ->
                            new LunarveilBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .strength(0.6F)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                                    .lightLevel(state -> state.getValue(LunarveilBlock.OPEN) ? 11 : 0)
                                    .randomTicks()
                            ));


    public static final DeferredBlock<Block> RIVERTHORNE =
            registerBlock(
                    "riverthorne", () ->
                            new Riverthorne(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .strength(0.6F)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .replaceable()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                                    .randomTicks()
                            ));


    public static final DeferredBlock<Block> RIVERTHORNE_THISTLE =
            registerBlock(
                    "riverthorne_thistle", () ->
                            new RiverthorneThistle(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .strength(0.6F)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                                    .randomTicks()
                                    .replaceable()
                                    .lightLevel(state -> state.getValue(RiverthorneThistle.GROWN) ? 5 : 0)
                            ));


    public static final DeferredBlock<Block> ASHA_SHORT_GRASS =
            registerBlock(
                    "asha_short_grass", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                                    .replaceable()
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box((double) 3.0F, (double) 0.0F, (double) 3.0F, (double) 13.0F, (double) 8.0F, (double) 13.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );

    public static final DeferredBlock<Block> ASHA_GRASS =
            registerBlock(
                    "asha_grass", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                                    .replaceable()
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 13.0F, 14.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );


    public static final DeferredBlock<Block> VIOLET_SWEETLILY =
            registerBlock(
                    "violet_sweetlily", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 13.0F, 11.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );


    public static final DeferredBlock<Block> PEACH_SWEETLILY =
            registerBlock(
                    "peach_sweetlily", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box((double) 5.0F, (double) 0.0F, (double) 5.0F, (double) 11.0F, (double) 13.0F, (double) 11.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );


    public static final DeferredBlock<Block> MAGENTA_SWEETLILY =
            registerBlock(
                    "magenta_sweetlily", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box((double) 5.0F, (double) 0.0F, (double) 5.0F, (double) 11.0F, (double) 13.0F, (double) 11.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );


    public static final DeferredBlock<Block> NAVY_SWEETLILY =
            registerBlock(
                    "navy_sweetlily", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box((double) 5.0F, (double) 0.0F, (double) 5.0F, (double) 11.0F, (double) 13.0F, (double) 11.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    if (state.is(ModBlocks.ASHA_GRASS_BLOCK.get()))
                                        return true;

                                    if (state.is(ModBlocks.ASHA_DIRT.get()))
                                        return true;

                                    return false;
                                }
                            }
            );


    public static final DeferredBlock<Block> CHERRY_SWEETLILY =
            registerBlock(
                    "cherry_sweetlily", () ->
                            new BushBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .noCollission()
                                    .noOcclusion()
                                    .instabreak()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                                    .pushReaction(PushReaction.DESTROY)
                            )
                            {
                                @Override
                                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
                                {
                                    Vec3 vec3 = state.getOffset(level, pos);
                                    VoxelShape shape = Block.box((double) 5.0F, (double) 0.0F, (double) 5.0F, (double) 11.0F, (double) 13.0F, (double) 11.0F);
                                    return shape.move(vec3.x, vec3.y, vec3.z);
                                }

                                @Override
                                protected MapCodec<? extends BushBlock> codec()
                                {
                                    return null;
                                }

                                @Override
                                protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                                {
                                    return state.is(ModBlocks.ASHA_DIRT.get()) || state.is(ModBlocks.ASHA_GRASS_BLOCK.get());
                                }
                            }
            );


    public static final DeferredBlock<Block> POTTED_VIOLET_SWEETLILY =
            registerBlockNoItem("potted_violet_sweetlily",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT,
                            ModBlocks.VIOLET_SWEETLILY,
                                    BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                            )
            );

    public static final DeferredBlock<Block> POTTED_PEACH_SWEETLILY =
            registerBlockNoItem("potted_peach_sweetlily",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.PEACH_SWEETLILY,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                    )
            );

    public static final DeferredBlock<Block> POTTED_MAGENTA_SWEETLILY =
            registerBlockNoItem("potted_magenta_sweetlily",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.MAGENTA_SWEETLILY,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                    )
            );

    public static final DeferredBlock<Block> POTTED_CHERRY_SWEETLILY =
            registerBlockNoItem("potted_cherry_sweetlily",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.CHERRY_SWEETLILY,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                    )
            );

    public static final DeferredBlock<Block> POTTED_NAVY_SWEETLILY =
            registerBlockNoItem("potted_navy_sweetlily",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.NAVY_SWEETLILY,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                    )
            );



    public static final DeferredBlock<Block> POTTED_LUNARVEIL =
            registerBlockNoItem("potted_lunarveil",
                    () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.LUNARVEIL,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA)
                    )
            );




    //
    //,--.   ,--.   ,---.   ,--------. ,------. ,------.
    //|  |   |  |  /  O  \  '--.  .--' |  .---' |  .--. '
    //|  |.'.|  | |  .-.  |    |  |    |  `--,  |  '--'.'
    //|   ,'.   | |  | |  |    |  |    |  `---. |  |\  \
    //'--'   '--' `--' `--'    `--'    `------' `--' '--'
    //


    public static final DeferredBlock<Block> WATER_CONTAINER =
            registerBlock(
                    "water_container", () ->
                            new WaterContainerBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.SCULK)
                                    .strength(1f, 40f)
                                    .noCollission()
                                    .noOcclusion()
                                    .replaceable()
                                    .forceSolidOn()
                                    .noTerrainParticles()
                                    .isValidSpawn(Blocks::never)
                                    .pushReaction(PushReaction.DESTROY)
                            )
            );

    public static final DeferredBlock<Block> WATER_CONTAINER_HELPER =
            registerBlock(
                    "water_container_helper", () ->
                            new WaterContainerHelperBlock(BlockBehaviour.Properties.of()
                                    .sound(SoundType.SCULK)
                                    .strength(1f, 40f)
                                    .isValidSpawn(Blocks::never)
                                    .pushReaction(PushReaction.DESTROY)
                                    .randomTicks()
                                    .replaceable()
                                    .instabreak()
                            )
            );


    public static final DeferredBlock<Block> WATER_FLOWER =
            registerBlock(
                    "water_flower", () ->
                            new WaterFlower(BlockBehaviour.Properties.of()
                                    .sound(SoundType.GRASS)
                                    .isValidSpawn(Blocks::never)
                                    .pushReaction(PushReaction.DESTROY)
                                    .randomTicks()
                                    .instabreak()
                                    .noOcclusion()
                                    .noCollission()
                                    .offsetType(BlockBehaviour.OffsetType.XZ)
                            )
            );


    //
    // ,-----.  ,--------. ,--.  ,--. ,------. ,------.   ,---.
    //'  .-.  ' '--.  .--' |  '--'  | |  .---' |  .--. ' '   .-'
    //|  | |  |    |  |    |  .--.  | |  `--,  |  '--'.' `.  `-.
    //'  '-'  '    |  |    |  |  |  | |  `---. |  |\  \  .-'    |
    // `-----'     `--'    `--'  `--' `------' `--' '--' `-----'
    //


    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return true;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return false;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block)
    {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
