package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.ModDataAttachments;
import com.wdiscute.laicaps.entity.fishing.FishingBobEntity;
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
import net.minecraft.world.item.TooltipFlag;
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


        if (player.getData(ModDataAttachments.FISHING.get()).isEmpty())
        {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (level instanceof ServerLevel)
            {
                level.addFreshEntity(new FishingBobEntity(level, player));
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

        ItemStack itemstack = player.getItemInHand(hand);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

