package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.util.AdvHelper;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

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
                PacketDistributor.sendToPlayer(sp, new Payloads.ToastPayload(adv, criteria));
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
