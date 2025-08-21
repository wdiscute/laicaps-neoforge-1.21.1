package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.astronomytable.NotebookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EntryUnlockableItem extends Item
{

    private final String adv;
    private final String criteria;
    private final String planetForToast;

    public EntryUnlockableItem(Properties properties, String adv, String criteria, String planetForToast)
    {
        super(properties);
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
