package com.wdiscute.laicaps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags
{
    public static class Blocks {

        public static final TagKey<Block> RIVERTHORNE_CAN_SURVIVE = createTag("riverthorne_can_survive");


        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> MAGIC_BLOCK_EGGS = createTag("magic_block_eggs");

        private static TagKey<Item> createTag(String name)
        {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, name));
        }
    }

}
