package com.wdiscute.laicaps;


import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.BluetaleModel;
import com.wdiscute.laicaps.entity.boat.ModModelLayers;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthEntity;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthModel;
import com.wdiscute.laicaps.entity.gecko.GeckoEntity;
import com.wdiscute.laicaps.entity.ModEntities;
import com.wdiscute.laicaps.entity.gecko.GeckoModel;
import com.wdiscute.laicaps.entity.nimble.NimbleEntity;
import com.wdiscute.laicaps.entity.nimble.NimbleModel;
import com.wdiscute.laicaps.entity.rocket.RocketModel;
import com.wdiscute.laicaps.entity.snuffler.SnufflerEntity;
import com.wdiscute.laicaps.entity.snuffler.SnufflerModel;
import com.wdiscute.laicaps.entity.swibble.SwibbleEntity;
import com.wdiscute.laicaps.entity.swibble.SwibbleModel;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Laicaps.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ModModelLayers.OAKHEART_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.OAKROOT_BOAT_LAYER, BoatModel::createBodyModel);

        event.registerLayerDefinition(ModModelLayers.OAKHEART_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.OAKROOT_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);


        event.registerLayerDefinition(GeckoModel.LAYER_LOCATION, GeckoModel::createBodyLayer);
        event.registerLayerDefinition(BluetaleModel.LAYER_LOCATION, BluetaleModel::createBodyLayer);
        //no need for redtale as it uses the same model
        event.registerLayerDefinition(BubblemouthModel.LAYER_LOCATION, BubblemouthModel::createBodyLayer);


        event.registerLayerDefinition(SwibbleModel.LAYER_LOCATION, SwibbleModel::createBodyLayer);

        event.registerLayerDefinition(NimbleModel.LAYER_LOCATION, NimbleModel::createBodyLayer);
        event.registerLayerDefinition(SnufflerModel.LAYER_LOCATION, SnufflerModel::createBodyLayer);

        event.registerLayerDefinition(RocketModel.LAYER_LOCATION, RocketModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributed(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.GECKO.get(), GeckoEntity.createAttributes().build());
        event.put(ModEntities.BLUETALE.get(), BluetaleEntity.createAttributes().build());
        event.put(ModEntities.REDTALE.get(), BluetaleEntity.createAttributes().build());
        event.put(ModEntities.BUBBLEMOUTH.get(), BubblemouthEntity.createAttributes().build());
        event.put(ModEntities.SWIBBLE.get(), SwibbleEntity.createAttributes().build());
        event.put(ModEntities.NIMBLE.get(), NimbleEntity.createAttributes().build());
        event.put(ModEntities.SNUFFLER.get(), SnufflerEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.BLUETALE.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.REDTALE.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.BUBBLEMOUTH.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);


        event.register(ModEntities.SWIBBLE.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.NIMBLE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModEventBusEvents::checkNimbleSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.SNUFFLER.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModEventBusEvents::checkNimbleSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

    }

    public static boolean checkNimbleSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean flag = level.getRawBrightness(pos, 0) > 8;
        return level.getBlockState(pos.below()).is(ModBlocks.ASHA_GRASS_BLOCK) && flag;
    }


    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntity.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntity.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }





}
