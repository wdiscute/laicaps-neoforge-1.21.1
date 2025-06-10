package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RPGlobe extends RP
{
    public RPGlobe(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RocketEntity parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if(hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (player.getItemInHand(hand).is(ModTags.Items.GLOBES) && !parentRocket.getEntityData().get(RocketEntity.GLOBE).is(player.getItemInHand(hand).getItem()))
        {
            player.addItem(parentRocket.getEntityData().get(RocketEntity.GLOBE));
            parentRocket.getEntityData().set(RocketEntity.GLOBE, new ItemStack(player.getItemInHand(hand).getItem()));
            player.getItemInHand(hand).shrink(1);
        }
        else
        {
            if (parentRocket.globeSpinCounter < 150) parentRocket.globeSpinCounter += 10;
        }
        return InteractionResult.SUCCESS;
    }
}
