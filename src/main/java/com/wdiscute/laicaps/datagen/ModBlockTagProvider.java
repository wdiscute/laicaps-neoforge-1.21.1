package com.wdiscute.laicaps.datagen;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Laicaps.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALEXENDRITE_BLOCK.get())
                .add(ModBlocks.RAW_ALEXENDRITE_BLOCK.get())

                .add(ModBlocks.ALEXENDRITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.ALEXENDRITE_ORE.get())

                .add(ModBlocks.MAGIC_BLOCK.get())
                .add(ModBlocks.ALEXANDRITE_DOOR.get())
                .add(ModBlocks.ALEXANDRITE_TRAPDOOR.get())
                .add(ModBlocks.ALEXANDRITE_FENCE_GATE.get())
                .add(ModBlocks.ALEXANDRITE_FENCE.get())
                .add(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ALEXANDRITE_BUTTON.get())
                .add(ModBlocks.ALEXANDRITE_SLAB.get())
                .add(ModBlocks.ALEXANDRITE_STAIRS.get())
                .add(ModBlocks.ALEXANDRITE_WALL.get())


        ;



        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ALEXENDRITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.ALEXENDRITE_ORE.get())
                .add(ModBlocks.ALEXENDRITE_BLOCK.get())
                .add(ModBlocks.RAW_ALEXENDRITE_BLOCK.get())
        ;



        //connects to other wooden fences
        //tag(BlockTags.WOODEN_FENCES)
        //        .add(ModBlocks.ALEXANDRITE_FENCE.get());

        //connects to other fences (nether and modded)
        tag(BlockTags.FENCES)
                .add(ModBlocks.ALEXANDRITE_FENCE.get())
        ;

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ALEXANDRITE_FENCE_GATE.get())
        ;

        //adds to forge tags
        //tag(Tags.Blocks.GLASS)
        //        .add(ModBlocks.ALEXANDRITE_GLASS.get())
        //;

        tag(BlockTags.WALLS)
                .add(ModBlocks.ALEXANDRITE_WALL.get())
        ;






    }
}
