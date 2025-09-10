package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.util.AdvHelper;
import com.wdiscute.laicaps.entity.rocket.RE;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;
import java.util.UUID;

public class RPCarpet extends RP
{

    private final EntityDataAccessor<ItemStack> carpet;
    private final EntityDataAccessor<Optional<UUID>> seat;

    public RPCarpet(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RE parentRocket, RE.interact interaction, EntityDataAccessor<ItemStack> carpetDataAccessor, EntityDataAccessor<Optional<UUID>> seatDataAccessor)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
        this.carpet = carpetDataAccessor;
        this.seat = seatDataAccessor;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (player.getItemInHand(hand).is(ItemTags.WOOL_CARPETS) && !parentRocket.getEntityData().get(carpet).is(player.getItemInHand(hand).getItem()))
        {
            player.addItem(parentRocket.getEntityData().get(carpet));
            parentRocket.getEntityData().set(carpet, new ItemStack(player.getItemInHand(hand).getItem()));
            player.getItemInHand(hand).shrink(1);
            return InteractionResult.SUCCESS;
        }
        else
        {

            if (player instanceof ServerPlayer sp && !AdvHelper.hasAdvancementCriteria(sp, "menu_entries", "entry5"))
            {
                AdvHelper.awardAdvancementCriteria(sp, "menu_entries", "entry5");
                PacketDistributor.sendToPlayer(sp, new Payloads.EntryUnlockedPayload("menu_entries", "entry5"));
            }


            parentRocket.checkPassengers(RE.FIRST_SEAT);
            parentRocket.checkPassengers(RE.SECOND_SEAT);
            parentRocket.checkPassengers(RE.THIRD_SEAT);

            if (getPassengers().isEmpty())

            {
                parentRocket.getEntityData().set(seat, Optional.of(player.getUUID()));
                return parentRocket.interactWithPart(player, interaction);
            }

            boolean safe = false;

            for (Entity entity : getPassengers())
            {
                if (parentRocket.getEntityData().get(seat).equals(Optional.of(entity.getUUID())))
                {
                    safe = true;
                }
            }

            if (!safe)
            {
                parentRocket.getEntityData().set(seat, Optional.of(player.getUUID()));
                return parentRocket.interactWithPart(player, interaction);
            }


            return parentRocket.interactWithPart(player, interaction);
        }
    }
}
