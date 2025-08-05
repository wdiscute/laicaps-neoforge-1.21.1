package com.wdiscute.laicaps.item;

import com.sun.jna.platform.win32.OaIdl;
import com.wdiscute.laicaps.ModDataAttachments;
import com.wdiscute.laicaps.entity.fishing.FishingBobEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class StarcatcherFishingRod extends Item
{
    public StarcatcherFishingRod(Properties properties)
    {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {

        ItemStack itemstack = player.getItemInHand(hand).copy();

        if (player.getData(ModDataAttachments.FISHING.get()).isEmpty())
        {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (level instanceof ServerLevel)
            {
                ItemStack bobber;
                ItemStack bait;

                if (itemstack.get(DataComponents.CONTAINER) == null)
                {
                    bobber = ItemStack.EMPTY;
                    bait = ItemStack.EMPTY;
                }
                else
                {
                    bobber = itemstack.get(DataComponents.CONTAINER).getStackInSlot(0);
                    bait = itemstack.get(DataComponents.CONTAINER).getStackInSlot(1);
                }

                level.addFreshEntity(new FishingBobEntity(level, player, bobber, bait));
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }
        else
        {

            List<Entity> entities = level.getEntities(null, new AABB(-25, -65, -25, 25, 65, 25).move(player.position()));

            for (Entity entity : entities)
            {
                if (entity.getUUID().toString().equals(player.getData(ModDataAttachments.FISHING.get())))
                {
                    if (entity instanceof FishingBobEntity fbe)
                    {
                        fbe.kill();
                    }
                }
            }

            player.setData(ModDataAttachments.FISHING.get(), "");
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

