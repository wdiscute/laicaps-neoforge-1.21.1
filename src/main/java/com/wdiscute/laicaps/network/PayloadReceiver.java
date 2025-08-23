package com.wdiscute.laicaps.network;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModDataAttachments;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.entity.fishing.FishingBobEntity;
import com.wdiscute.laicaps.entity.rocket.RE;
import com.wdiscute.laicaps.fishing.FishingMinigameScreen;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PayloadReceiver
{
    private static final Logger log = LoggerFactory.getLogger(PayloadReceiver.class);

    public static void receiveFishingCompletedServer(final Payloads.FishingCompletedPayload data, final IPayloadContext context)
    {

        Player player = context.player();
        ServerLevel level = ((ServerLevel) context.player().level());

        List<Entity> entities = level.getEntities(null, new AABB(-25, -65, -25, 25, 65, 25).move(player.position()));

        for (Entity entity : entities)
        {
            if (entity.getUUID().toString().equals(player.getData(ModDataAttachments.FISHING.get())))
            {
                if (entity instanceof FishingBobEntity fbe)
                {
                    if (data.time() != -1)
                    {
                        if(player.getMainHandItem().is(ModItems.STARCATCHER_FISHING_ROD))
                            player.getMainHandItem().set(ModDataComponents.CAST, false);


                        if(fbe.stack.is(ModItems.THUNDERCHARGED_EEL))
                        {
                            LightningBolt strike = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            strike.setPos(fbe.position());
                            strike.setVisualOnly(true);
                            level.addFreshEntity(strike);
                        }

                        Entity itemFished = new ItemEntity(
                                level,
                                fbe.position().x,
                                fbe.position().y + 1.2f,
                                fbe.position().z,
                                fbe.stack);


                        double x = (player.position().x - fbe.position().x) / 25;
                        double y = (player.position().y - fbe.position().y) / 20;
                        double z = (player.position().z - fbe.position().z) / 25;

                        x = Math.clamp(x, -1, 1);
                        y = Math.clamp(y, -1, 1);
                        z = Math.clamp(z, -1, 1);

                        Vec3 vec3 = new Vec3(x, 0.7 + y, z);

                        itemFished.setDeltaMovement(vec3);

                        level.addFreshEntity(itemFished);

                        Vec3 p = player.position();
                        level.playSound(null, p.x, p.y, p.z, SoundEvents.VILLAGER_CELEBRATE, SoundSource.AMBIENT);
                    }
                    else
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


    public static void receiveToast(final Payloads.ToastPayload data, final IPayloadContext context)
    {
        Laicaps.sendToast(data.menuName(), data.entryName());
    }


    public static void receiveFishingClient(final Payloads.FishingPayload data, final IPayloadContext context)
    {
        Minecraft.getInstance().setScreen(new FishingMinigameScreen(
                Component.literal("Fishing"),
                data.stack(),
                data.bobber(),
                data.bait(),
                data.difficulty()
        ));
    }

    public static void receiveChangePlanetSelected(final Payloads.ChangePlanetSelected data, final IPayloadContext context)
    {

        System.out.println("received " + data.planet());

        List<Entity> entites =  context.player().level().getEntities(null,
                new AABB(-10, -10, -10, 10, 10, 10).move(context.player().position()));

        for(Entity e : entites)
        {
            if(e instanceof RE re && re.getStringUUID().equals(data.entityUUID()))
            {
                ItemStack is = ItemStack.EMPTY;

                if(data.planet().equals("ember")) is = new ItemStack(ModItems.EMBER.get());
                if(data.planet().equals("asha")) is = new ItemStack(ModItems.ASHA.get());
                if(data.planet().equals("overworld")) is = new ItemStack(ModItems.OVERWORLD.get());
                if(data.planet().equals("lunamar")) is = new ItemStack(ModItems.LUNAMAR.get());

                re.getEntityData().set(RE.PLANET_SELECTED, is);
                break;
            }
        }

    }


}
