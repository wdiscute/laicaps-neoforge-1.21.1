package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RPCarpet extends RP
{
    public RPCarpet(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RocketEntity parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if(hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (player.getItemInHand(hand).is(ItemTags.WOOL_CARPETS) && !parentRocket.getEntityData().get(RocketEntity.CARPET).is(player.getItemInHand(hand).getItem()))
        {
            player.addItem(parentRocket.getEntityData().get(RocketEntity.CARPET));
            parentRocket.getEntityData().set(RocketEntity.CARPET, new ItemStack(player.getItemInHand(hand).getItem()));
            player.getItemInHand(hand).shrink(1);
            return InteractionResult.SUCCESS;
        }
        else
        {
            return parentRocket.interactWithPart(player, interaction);
        }
    }
}
