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
        public static final TagKey<Block> MOONSHADE_KELP_CAN_SURVIVE = createTag("moonshade_kelp_can_survive");
        public static final TagKey<Block> ILLUMA_CAN_SURVIVE = createTag("illuma_can_survive");


        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(Laicaps.rl(name));
        }
    }

    public static class Items {

        public static final TagKey<Item> MAGIC_BLOCK_EGGS = createTag("magic_block_eggs");
        public static final TagKey<Item> GLOBES = createTag("globes");
        public static final TagKey<Item> BLUEPRINTS = createTag("blueprints");
        public static final TagKey<Item> COMPLETED_BLUEPRINTS = createTag("completed_blueprints");
        public static final TagKey<Item> FISH = createTag("fishing_game_items");

        private static TagKey<Item> createTag(String name)
        {
            return ItemTags.create(Laicaps.rl(name));
        }
    }

}
