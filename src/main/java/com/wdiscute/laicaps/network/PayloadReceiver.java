package com.wdiscute.laicaps.network;

import com.wdiscute.laicaps.ModDataAttachments;
import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.fishing.FishingBobEntity;
import com.wdiscute.laicaps.fishing.FishingMinigameScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.UUID;

public class PayloadReceiver
{
    public static void receiveFishingCompletedServer(final Payloads.FishingCompletedPayload data, final IPayloadContext context)
    {
        Player player = context.player();
        ServerLevel level = ((ServerLevel) context.player().level());

        List<Entity> entities = level.getEntities(null, new AABB(-25, -65, -25, 25, 65, 25).move(player.position()));

        for (Entity entity : entities)
        {
            if(entity.getUUID().toString().equals(player.getData(ModDataAttachments.FISHING.get())))
            {
                if(entity instanceof FishingBobEntity fbe)
                {
                    if(data.time() > 1)
                    {
                        level.addFreshEntity(new ItemEntity(level, player.position().x + 0.5f, player.position().y + 1.2f, player.position().z,
                                fbe.stack));

                        Vec3 p = player.position();

                        level.playSound(null, p.x, p.y, p.z, SoundEvents.VILLAGER_CELEBRATE, SoundSource.AMBIENT);
                    }else
                    {
                        Vec3 p = player.position();
                        level.playSound(null, p.x, p.y, p.z, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT);
                    }
                    fbe.kill();
                }
            }
        }

        player.setData(ModDataAttachments.FISHING.get(), "");

    }


    public static void receiveFishingClient(final Payloads.FishingPayload data, final IPayloadContext context)
    {
        Minecraft.getInstance().setScreen(new FishingMinigameScreen(
                Component.literal("Fishing minigame"),
                data.stack(),
                data.difficulty()
        ));
    }


}
