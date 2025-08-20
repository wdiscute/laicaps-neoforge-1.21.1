package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class InventoryTickBlockItem extends BlockItem
{

    private final String adv;
    private final String criteria;
    private final String planetForToast;

    public InventoryTickBlockItem(Block block, Properties properties, String adv, String criteria, String planetForToast)
    {
        super(block, properties);
        this.adv = adv;
        this.criteria = criteria;
        this.planetForToast = planetForToast;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        if(entity instanceof ServerPlayer sp)
        {
            if(!AdvHelper.hasAdvancementCriteria(sp, adv, criteria))
            {
                AdvHelper.awardAdvancementCriteria(sp, adv, criteria);
                Laicaps.sendToast(planetForToast, criteria);
            }

        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
