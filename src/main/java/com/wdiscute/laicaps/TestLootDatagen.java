package com.wdiscute.laicaps;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class TestLootDatagen extends BlockLootSubProvider
{

    private static final float[] NORMAL_LEAVES_STICK_CHANCES;

    protected TestLootDatagen(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        //dropSelf(ModBlocks.COAL_OAK_WOOD.get());
        //dropSelf(ModBlocks.COAL_OAK_SAPLING.get());

        //dropSelf(ModBlocks.COAL_OAK_LOG.get());

        //createMystLeafDrop(ModBlocks.COAL_OAK_LEAVES.get(), ModBlocks.COAL_OAK_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES, ModItems.COAL_ACORN.get(), 0.15f);



    }

    protected LootTable.Builder createMystLeafDrop(Block leavesBlock, Block saplingBlock, float[] chances, Item customItem, float customChance) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(leavesBlock, saplingBlock, chances)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        //.when(this.doesNotHaveShearsOrSilkTouch())
                        .add(((LootPoolSingletonContainer.Builder)this
                                .applyExplosionCondition(leavesBlock, LootItem.lootTableItem(customItem)))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup
                                        .getOrThrow(Enchantments.FORTUNE), new float[]
                                        {0.0F, customChance, customChance * 1.1F, customChance * 1.25F, customChance * 2.0F}))));
    }



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }


    static {
        //HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(new ItemLike[]{Items.SHEARS}));
        NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    }
}
