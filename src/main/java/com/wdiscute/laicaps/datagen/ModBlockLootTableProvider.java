package com.wdiscute.laicaps.datagen;

import com.wdiscute.laicaps.block.ModBlocks;
import com.wdiscute.laicaps.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider
{
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {

        dropSelf(ModBlocks.RAW_ALEXENDRITE_BLOCK.get());

        dropSelf(ModBlocks.MAGIC_BLOCK.get());
        dropSelf(ModBlocks.SENDER_PUZZLE_BLOCK.get());
        dropSelf(ModBlocks.RECEIVER_BLOCK.get());
        dropSelf(ModBlocks.SYMBOL_PUZZLE_BLOCK.get());
        dropSelf(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE.get());
        dropSelf(ModBlocks.SYMBOL_CONTROLLER_BLOCK.get());

        dropSelf(ModBlocks.ALEXENDRITE_BLOCK.get());
        dropSelf(ModBlocks.ALEXANDRITE_TRAPDOOR.get());
        dropSelf(ModBlocks.ALEXANDRITE_FENCE.get());
        dropSelf(ModBlocks.ALEXANDRITE_FENCE_GATE.get());
        dropSelf(ModBlocks.ALEXANDRITE_WALL.get());
        dropSelf(ModBlocks.ALEXANDRITE_BUTTON.get());
        dropSelf(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ALEXANDRITE_STAIRS.get());

        dropSelf(ModBlocks.ALEXANDRITE_LAMP.get());
        dropSelf(ModBlocks.WALNUT_SAPLING.get());

        //oakroot
        dropSelf(ModBlocks.OAKROOT_LOG.get());
        dropSelf(ModBlocks.OAKROOT_SAPLING.get());
        this.add(ModBlocks.OAKROOT_LEAVES.get(),
                block -> createLeavesDrops(ModBlocks.OAKROOT_LEAVES.get(), ModBlocks.OAKROOT_SAPLING.get()));

        dropSelf(ModBlocks.OAKHEART_SAPLING.get());


        this.add(ModBlocks.ALEXANDRITE_DOOR.get(),
                block -> createDoorTable(ModBlocks.ALEXANDRITE_DOOR.get()));

        this.add(ModBlocks.ALEXANDRITE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ALEXANDRITE_SLAB.get()));

        this.add(ModBlocks.ALEXENDRITE_ORE.get(),
                block -> createOreDrop(ModBlocks.ALEXENDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));

        this.add(ModBlocks.ALEXENDRITE_DEEPSLATE_ORE.get(),
                block -> createMultipleDrops(ModBlocks.ALEXENDRITE_DEEPSLATE_ORE.get(), ModItems.RAW_ALEXANDRITE.get(), 1,3));







    }






    protected LootTable.Builder createMultipleDrops(Block pBlock, Item itemToDrop, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem
                                .lootTableItem(itemToDrop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
