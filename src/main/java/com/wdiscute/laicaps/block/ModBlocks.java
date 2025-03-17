package com.wdiscute.laicaps.block;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.custom.*;
import com.wdiscute.laicaps.item.ModItems;
import com.wdiscute.laicaps.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Laicaps.MOD_ID);

    public static final DeferredBlock<Block> ALEXENDRITE_BLOCK =
            registerBlock("alexandrite_block", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(4f, 4f)
                            .noCollission()
                            .replaceable()
                            .sound(SoundType.TUFF)
                    )
            );

    public static final DeferredBlock<Block> RAW_ALEXENDRITE_BLOCK =
            registerBlock("raw_alexandrite_block", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(4f, 4f)
                            .sound(SoundType.BONE_BLOCK)
                    )
            );

    public static final DeferredBlock<Block> ALEXENDRITE_ORE =
            registerBlock("alexandrite_ore", () ->
                    new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(4f, 6f)
                    )
            );

    public static final DeferredBlock<Block> ALEXENDRITE_DEEPSLATE_ORE =
            registerBlock("alexandrite_deepslate_ore", () ->
                    new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(5f, 7f)
                    )
            );

    public static final DeferredBlock<Block> MAGIC_BLOCK =
            registerBlock("magic_block", () ->
                    new MagicBlock(BlockBehaviour.Properties.of()
                            .strength(5f, 7f)
                            .sound(SoundType.AMETHYST)
                    )
            );

    public static final DeferredBlock<StairBlock> ALEXANDRITE_STAIRS =
            registerBlock("alexandrite_stairs", () ->
                    new StairBlock(ModBlocks.ALEXENDRITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<SlabBlock> ALEXANDRITE_SLAB =
            registerBlock("alexandrite_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<PressurePlateBlock> ALEXANDRITE_PRESSURE_PLATE =
            registerBlock("alexandrite_pressure_plate", () ->
                    new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<ButtonBlock> ALEXANDRITE_BUTTON =
            registerBlock("alexandrite_button", () ->
                    new ButtonBlock(BlockSetType.IRON, 20, BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                            .noCollission()
                    )
            );

    public static final DeferredBlock<FenceBlock> ALEXANDRITE_FENCE =
            registerBlock("alexandrite_fence", () ->
                    new FenceBlock(BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<FenceGateBlock> ALEXANDRITE_FENCE_GATE =
            registerBlock("alexandrite_fence_gate", () ->
                    new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<WallBlock> ALEXANDRITE_WALL =
            registerBlock("alexandrite_wall", () ->
                    new WallBlock(BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                    )
            );

    public static final DeferredBlock<DoorBlock> ALEXANDRITE_DOOR =
            registerBlock("alexandrite_door", () ->
                    new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                            .noOcclusion() //noOcclusion needs to be set otherwise transparent pixels will make the world see through like xray
                    )
            );

    public static final DeferredBlock<TrapDoorBlock> ALEXANDRITE_TRAPDOOR =
            registerBlock("alexandrite_trapdoor", () ->
                    new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                            .noOcclusion() //noOcclusion needs to be set otherwise transparent pixels will make the world see through like xray
                    )
            );

    public static final DeferredBlock<Block> ALEXANDRITE_LAMP =
            registerBlock("alexandrite_lamp", () ->
                    new AlexandriteLampBlock(BlockBehaviour.Properties.of()
                            .strength(3)
                            .requiresCorrectToolForDrops()
                            .lightLevel(state -> state.getValue(AlexandriteLampBlock.CLICKED) ? 15 : 0)
                    )
            );

    public static final DeferredBlock<Block> SENDER_PUZZLE_BLOCK =
            registerBlock("sender_puzzle_block", () ->
                    new SenderPuzzleBLock(BlockBehaviour.Properties.of()
                            .strength(30)
                            .sound(SoundType.AMETHYST)
                    )
            );

    public static final DeferredBlock<ReceiverBlock> RECEIVER_BLOCK =
            registerBlock("receiver_block", () ->
                    new ReceiverBlock(BlockBehaviour.Properties.of()
                            .strength(30)
                            .sound(SoundType.AMETHYST)
                    )
            );

    public static final DeferredBlock<Block> SYMBOL_PUZZLE_BLOCK =
            registerBlock("symbol_puzzle_block", () ->
                    new SymbolPuzzleBlock(BlockBehaviour.Properties.of()
                            .strength(30)
                            .sound(SoundType.STONE)
                    )
            );

    public static final DeferredBlock<Block> SYMBOL_CONTROLLER_BLOCK =
            registerBlock("symbol_controller_block", () ->
                    new SymbolControllerBlock(BlockBehaviour.Properties.of()
                            .strength(30)
                            .sound(SoundType.STONE)
                    )
            );


    public static final DeferredBlock<Block> SYMBOL_PUZZLE_BLOCK_INACTIVE =
            registerBlock("symbol_puzzle_block_inactive", () ->
                    new SymbolPuzzleBlockInactive(BlockBehaviour.Properties.of()
                            .strength(30)
                            .sound(SoundType.STONE)
                    )
            );


    //sapling requires tree grower which is being provided on ModTreeGrowers
    //we use ModSaplingBlock so we can change what block it can be placed/grown on etc
    public static final DeferredBlock<Block> WALNUT_SAPLING =
            registerBlock("walnut_sapling", () ->
                    new ModSaplingBlock(ModTreeGrowers.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> Blocks.GRASS_BLOCK));


    public static final DeferredBlock<Block> OAKROOT_SAPLING =
            registerBlock("oakroot_sapling", () ->
                    new ModSaplingBlock(ModTreeGrowers.OAKROOT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> ModBlocks.ASHA_GRASS_BLOCK.get()));

    public static final DeferredBlock<Block> OAKROOT_LEAVES =
            registerBlock("oakroot_leaves", () ->
                    new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LEAVES)));

    public static final DeferredBlock<Block> OAKROOT_LOG =
            registerBlock("oakroot_log", () ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.of()
                            .sound(SoundType.WOOD)
                            .strength(2.0F)
                            .ignitedByLava()
                    ));


    public static final DeferredBlock<Block> OAKHEART_SAPLING =
            registerBlock("oakheart_sapling", () ->
                    new ModSaplingBlock(ModTreeGrowers.OAKHEART, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> ModBlocks.ASHA_GRASS_BLOCK.get()));

    public static final DeferredBlock<Block> OAKHEART_LOG =
            registerBlock("oakheart_log", () ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.of()
                            .sound(SoundType.WOOD)
                            .strength(2.0F)
                            .ignitedByLava()
                    ));

    public static final DeferredBlock<Block> OAKHEART_LEAVES =
            registerBlock("oakheart_leaves", () ->
                    new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LEAVES)));

    public static final DeferredBlock<Block> FLOWERING_OAKHEART_LEAVES =
            registerBlock("flowering_oakheart_leaves", () ->
                    new FloweringOakheartLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LEAVES).
                            randomTicks()
                    ));


    public static final DeferredBlock<Block> ASHA_GRASS_BLOCK =
            registerBlock("asha_grass_block", () ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.of()
                            .sound(SoundType.ROOTED_DIRT)
                            .strength(0.6F)
                    ));

    public static final DeferredBlock<Block> ASHA_DIRT =
            registerBlock("asha_dirt", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .sound(SoundType.ROOTED_DIRT)
                            .strength(0.6F)
                    ));


    public static final DeferredBlock<Block> LUNARVEIL =
            registerBlock("lunarveil", () ->
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
            registerBlock("riverthorne", () ->
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
            registerBlock("riverthorne_thistle", () ->
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
            registerBlock("asha_short_grass", () ->
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
                        protected MapCodec<? extends BushBlock> codec()
                        {
                            return null;
                        }

                        @Override
                        protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                        {
                            if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState())
                                return true;

                            if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_DIRT.get().defaultBlockState())
                                return true;

                            return false;
                        }
                    }
                    );

    public static final DeferredBlock<Block> ASHA_GRASS =
            registerBlock("asha_grass", () ->
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
                        protected MapCodec<? extends BushBlock> codec()
                        {
                            return null;
                        }

                        @Override
                        protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
                        {
                            if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_GRASS_BLOCK.get().defaultBlockState())
                                return true;

                            if (state.getBlock().defaultBlockState() == ModBlocks.ASHA_DIRT.get().defaultBlockState())
                                return true;

                            return false;
                        }
                    }
                    );

    public static final DeferredBlock<Block> BRICKS =
            registerBlock("bricks", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .sound(SoundType.STONE)
                            .strength(4f, 4f)
                            .instabreak()
                    )
            );
















    

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

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
