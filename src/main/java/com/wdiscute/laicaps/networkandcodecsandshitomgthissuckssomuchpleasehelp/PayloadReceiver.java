package com.wdiscute.laicaps.networkandcodecsandshitomgthissuckssomuchpleasehelp;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.entity.fishing.FishingBobEntity;
import com.wdiscute.laicaps.entity.rocket.RE;
import com.wdiscute.laicaps.fishing.FishingMinigameScreen;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class PayloadReceiver
{
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
                        if (player.getMainHandItem().is(ModItems.STARCATCHER_FISHING_ROD))
                            player.getMainHandItem().set(ModDataComponents.CAST, false);


                        if (fbe.stack.is(ModItems.THUNDERCHARGED_EEL))
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


                        //award fish counter
                        List<FishCaughtCounter> list = player.getData(ModDataAttachments.FISHES_CAUGHT);
                        ResourceLocation rl = BuiltInRegistries.ITEM.getKey(fbe.stack.getItem());

                        List<FishCaughtCounter> newlist = new ArrayList<>();

                        boolean found = false;

                        for (FishCaughtCounter f : list)
                        {
                            newlist.add(f);

                            if (rl.equals(f.getResourceLocation()))
                            {
                                found = true;

                                FishCaughtCounter plusOne = new FishCaughtCounter(rl, f.getCount() + 1);
                                newlist.remove(f);
                                newlist.add(plusOne);
                            }
                        }

                        if(!found)
                        {
                            newlist.add(new FishCaughtCounter(rl, 1));

                            if(player instanceof ServerPlayer sp)
                            {
                                PacketDistributor.sendToPlayer(sp, new Payloads.FishCaughtPayload(new ItemStack(fbe.stack.getItem())));
                            }
                        }

                        player.setData(ModDataAttachments.FISHES_CAUGHT, newlist);



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

    public static void receiveEntryUnlocked(final Payloads.EntryUnlockedPayload data, final IPayloadContext context)
    {
        Laicaps.entryUnlockedToast(data.menuName(), data.entryName());
    }

    public static void receiveFishCaught(final Payloads.FishCaughtPayload data, final IPayloadContext context)
    {
        Laicaps.fishCaughtToast(data.is());
    }


    public static void receiveFishingClient(final Payloads.FishingPayload data, final IPayloadContext context)
    {
        client(data, context);
    }

    @OnlyIn(Dist.CLIENT)
    public static void client(Payloads.FishingPayload data, IPayloadContext context)
    {
        Minecraft.getInstance().setScreen(new FishingMinigameScreen(
                data.stack(),
                data.bobber(),
                data.bait(),
                data.difficulty()
        ));
    }

    public static void receiveChangePlanetSelected(final Payloads.ChangePlanetSelected data, final IPayloadContext context)
    {

        List<Entity> entites = context.player().level().getEntities(
                null,
                new AABB(-10, -10, -10, 10, 10, 10).move(context.player().position()));

        for (Entity e : entites)
        {
            if (e instanceof RE re && re.getStringUUID().equals(data.entityUUID()) && re.getEntityData().get(RE.STATE) == 0)
            {
                ItemStack is = ItemStack.EMPTY;

                if (data.planet().equals("ember")) is = new ItemStack(ModItems.EMBER.get());
                if (data.planet().equals("asha")) is = new ItemStack(ModItems.ASHA.get());
                if (data.planet().equals("overworld")) is = new ItemStack(ModItems.OVERWORLD.get());
                if (data.planet().equals("lunamar")) is = new ItemStack(ModItems.LUNAMAR.get());

                re.getEntityData().set(RE.PLANET_SELECTED, is);
                break;
            }
        }

    }


    public static void receiveBluePrintCompleted(final Payloads.BluePrintCompletedPayload data, final IPayloadContext context)
    {

        if (context.player().getMainHandItem().is(ModItems.SPACESHIP_BLUEPRINT_SKETCH))
        {
            context.player().getMainHandItem().shrink(1);
            context.player().addItem(new ItemStack(ModItems.SPACESHIP_BLUEPRINT.get()));
            return;
        }

        if (context.player().getOffhandItem().is(ModItems.SPACESHIP_BLUEPRINT_SKETCH))
        {
            context.player().getOffhandItem().shrink(1);
            context.player().addItem(new ItemStack(ModItems.SPACESHIP_BLUEPRINT.get()));
        }

    }
}
