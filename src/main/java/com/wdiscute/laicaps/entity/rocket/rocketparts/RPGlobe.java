package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RE;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RPGlobe extends RP
{
    public RPGlobe(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RE parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if(hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (player.getItemInHand(hand).is(ModTags.Items.GLOBES) && !parentRocket.getEntityData().get(RE.GLOBE).is(player.getItemInHand(hand).getItem()))
        {
            player.addItem(parentRocket.getEntityData().get(RE.GLOBE));
            parentRocket.getEntityData().set(RE.GLOBE, new ItemStack(player.getItemInHand(hand).getItem()));
            player.getItemInHand(hand).shrink(1);
        }
        else
        {
            if (parentRocket.globeSpinCounter < 150) parentRocket.globeSpinCounter += 10;
        }
        return InteractionResult.SUCCESS;
    }
}
