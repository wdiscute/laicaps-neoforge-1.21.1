package com.wdiscute.laicaps;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class test extends BlockLootSubProvider
{
    protected test(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries)
    {
        super(explosionResistant, enabledFeatures, registries);
    }


    protected LootTable.Builder createMystLeafDrop(Block leavesBlock, Block saplingBlock, float[] chances, Item customItem, float customChance) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(leavesBlock, saplingBlock, chances)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(this.doesNottHaveShearsOrSilkTouch())
                        .add(((LootPoolSingletonContainer.Builder)this
                                .applyExplosionCondition(leavesBlock, LootItem.lootTableItem(customItem)))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup
                                        .getOrThrow(Enchantments.FORTUNE), new float[]
                                        {0.0F, customChance, customChance * 1.1F, customChance * 1.25F, customChance * 2.0F}))));
    }

    protected LootItemCondition.Builder doesNottHaveShearsOrSilkTouch() {
        return this.hasShearssOrSilkTouch().invert();
    }
    private LootItemCondition.Builder hasShearssOrSilkTouch() {
        return HAS_SHEARS.or(this.hasSilkTouch());
    }



    @Override
    protected void generate()
    {

    }
}
