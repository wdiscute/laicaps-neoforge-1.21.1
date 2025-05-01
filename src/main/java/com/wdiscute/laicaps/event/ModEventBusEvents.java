package com.wdiscute.laicaps.event;


import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.gecko.GeckoEntity;
import com.wdiscute.laicaps.entity.ModEntities;
import com.wdiscute.laicaps.entity.gecko.GeckoModel;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import com.wdiscute.laicaps.entity.rocket.RocketModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Laicaps.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(GeckoModel.LAYER_LOCATION, GeckoModel::createBodyLayer);
        event.registerLayerDefinition(RocketModel.LAYER_LOCATION, RocketModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributed(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.GECKO.get(), GeckoEntity.createAttributes().build());
        event.put(ModEntities.ROCKET.get(), RocketEntity.createAttributes().build());
    }

}
