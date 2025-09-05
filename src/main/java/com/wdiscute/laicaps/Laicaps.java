package com.wdiscute.laicaps;

import com.mojang.logging.LogUtils;
import com.wdiscute.laicaps.block.astronomytable.NotebookScreen;
import com.wdiscute.laicaps.block.telescope.TelescopeScreen;
import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.BluetaleModel;
import com.wdiscute.laicaps.entity.bluetale.BluetaleRenderer;
import com.wdiscute.laicaps.entity.bluetale.RedtaleRenderer;
import com.wdiscute.laicaps.entity.boat.ModModelLayers;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthEntity;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthModel;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthRenderer;
import com.wdiscute.laicaps.entity.fishing.FishingBobModel;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffEntity;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffGlowLayer;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffModel;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffRenderer;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaModel;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaRenderer;
import com.wdiscute.laicaps.entity.magmaboss.rock.RockModel;
import com.wdiscute.laicaps.entity.magmaboss.rock.RockRenderer;
import com.wdiscute.laicaps.entity.magmaboss.shield.ShieldModel;
import com.wdiscute.laicaps.entity.magmaboss.shield.ShieldRenderer;
import com.wdiscute.laicaps.entity.moonray.MoonrayEntity;
import com.wdiscute.laicaps.entity.moonray.MoonrayModel;
import com.wdiscute.laicaps.entity.moonray.MoonrayRenderer;
import com.wdiscute.laicaps.entity.nimble.NimbleEntity;
import com.wdiscute.laicaps.entity.nimble.NimbleModel;
import com.wdiscute.laicaps.entity.nimble.NimbleRenderer;
import com.wdiscute.laicaps.entity.rocket.RefuelScreen;
import com.wdiscute.laicaps.entity.rocket.RocketModel;
import com.wdiscute.laicaps.entity.rocket.RocketRenderer;
import com.wdiscute.laicaps.entity.snuffler.SnufflerEntity;
import com.wdiscute.laicaps.entity.snuffler.SnufflerModel;
import com.wdiscute.laicaps.entity.snuffler.SnufflerRenderer;
import com.wdiscute.laicaps.entity.swibble.SwibbleEntity;
import com.wdiscute.laicaps.entity.swibble.SwibbleModel;
import com.wdiscute.laicaps.entity.swibble.SwibbleRenderer;
import com.wdiscute.laicaps.entity.fishing.FishingBobRenderer;
import com.wdiscute.laicaps.fishing.FishingRodScreen;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.entity.boat.ModBoatRenderer;
import com.wdiscute.laicaps.item.ModItemProperties;
import com.wdiscute.laicaps.network.PayloadReceiver;
import com.wdiscute.laicaps.network.Payloads;
import com.wdiscute.laicaps.particle.*;
import com.wdiscute.laicaps.notebook.EntryUnlockedToast;
import com.wdiscute.laicaps.util.Tooltips;
import com.wdiscute.laicaps.worldgen.ModFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(Laicaps.MOD_ID)
public class Laicaps
{
    public static final String MOD_ID = "laicaps";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int MENU_ENTRIES = 5;
    public static final int EMBER_ENTRIES = 4;
    public static final int ASHA_ENTRIES = 4;
    public static final int OVERWORLD_ENTRIES = 4;
    public static final int LUNAMAR_ENTRIES = 4;

    public static float hue;


    public static ResourceLocation rl(String s)
    {
        return ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, s);
    }


    @OnlyIn(Dist.CLIENT)
    public static void sendToast(String menuName, String entryName)
    {
        Minecraft.getInstance().getToasts().addToast(new EntryUnlockedToast(menuName, entryName));
    }


    public Laicaps(IEventBus modEventBus, ModContainer modContainer)
    {
        NeoForge.EVENT_BUS.addListener(Tooltips::modifyItemTooltip);
        NeoForge.EVENT_BUS.addListener(Laicaps::renderFrame);
        //NeoForge.EVENT_BUS.addListener(EntriesChecks::itemPickupEvent);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlockEntity.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModDataAttachments.register(modEventBus);
        ModDataSerializers.register(modEventBus);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            Sheets.addWoodType(ModWoodTypes.OAKROOT);
            Sheets.addWoodType(ModWoodTypes.OAKHEART);

            ModItemProperties.addCustomItemProperties();

            event.enqueueWork(() ->
            {
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.VIOLET_SWEETLILY.getId(), ModBlocks.POTTED_VIOLET_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PEACH_SWEETLILY.getId(), ModBlocks.POTTED_PEACH_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHERRY_SWEETLILY.getId(), ModBlocks.POTTED_CHERRY_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MAGENTA_SWEETLILY.getId(), ModBlocks.POTTED_MAGENTA_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.NAVY_SWEETLILY.getId(), ModBlocks.POTTED_NAVY_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.LUNARVEIL.getId(), ModBlocks.POTTED_LUNARVEIL);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.OAKHEART_SAPLING.getId(), ModBlocks.POTTED_OAKHEART_SAPLING);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.OAKROOT_SAPLING.getId(), ModBlocks.POTTED_OAKROOT_SAPLING);
            });

            EntityRenderers.register(ModEntities.MOD_BOAT.get(), context -> new ModBoatRenderer(context, false));
            EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));

            EntityRenderers.register(ModEntities.ROCKET.get(), RocketRenderer::new);

            EntityRenderers.register(ModEntities.MAGMA.get(), MagmaRenderer::new);
            EntityRenderers.register(ModEntities.SHIELD.get(), ShieldRenderer::new);
            EntityRenderers.register(ModEntities.ROCK.get(), RockRenderer::new);

            EntityRenderers.register(ModEntities.FISHING_BOB.get(), FishingBobRenderer::new);


            EntityRenderers.register(ModEntities.BLUETALE.get(), BluetaleRenderer::new);
            EntityRenderers.register(ModEntities.REDTALE.get(), RedtaleRenderer::new);
            EntityRenderers.register(ModEntities.BUBBLEMOUTH.get(), BubblemouthRenderer::new);
            EntityRenderers.register(ModEntities.MOONRAY.get(), MoonrayRenderer::new);
            EntityRenderers.register(ModEntities.GLIMPUFF.get(), GlimpuffRenderer::new);
            EntityRenderers.register(ModEntities.SWIBBLE.get(), SwibbleRenderer::new);
            EntityRenderers.register(ModEntities.NIMBLE.get(), NimbleRenderer::new);
            EntityRenderers.register(ModEntities.SNUFFLER.get(), SnufflerRenderer::new);

        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event)
        {
            event.registerSpriteSet(ModParticles.CHASE_PUZZLE_PARTICLES.get(), ChasePuzzleParticles.Provider::new);
            event.registerSpriteSet(ModParticles.WATER_FLOWER_PARTICLES.get(), WaterFlowerParticles.Provider::new);
            event.registerSpriteSet(ModParticles.LUNARVEIL_PARTICLES.get(), LunarveilParticles.Provider::new);
            event.registerSpriteSet(ModParticles.ROCKET_FIRE_PARTICLES.get(), RocketFireParticles.Provider::new);
            event.registerSpriteSet(ModParticles.ROCKET_FIRE_SIMPLE_PARTICLES.get(), RocketFireSimpleParticles.Provider::new);
            event.registerSpriteSet(ModParticles.FISHING_NOTIFICATION.get(), FishingNotificationParticles.Provider::new);
            event.registerSpriteSet(ModParticles.FISHING_BITING.get(), FishingBitingParticles.Provider::new);

            event.registerSpriteSet(ModParticles.ROCK_FALLING.get(), RockParticles.Provider::new);
            event.registerSpriteSet(ModParticles.ROCK_EXPLOSION.get(), RockExplosionParticles.Provider::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event)
        {
            event.register(ModMenuTypes.TELESCOPE_MENU.get(), TelescopeScreen::new);
            event.register(ModMenuTypes.NOTEBOOK_MENU.get(), NotebookScreen::new);
            event.register(ModMenuTypes.FISHING_ROD_MENU.get(), FishingRodScreen::new);
            event.register(ModMenuTypes.REFUEL_MENU.get(), RefuelScreen::new);
        }
    }

    public static void renderFrame(RenderFrameEvent.Post event)
    {
        Laicaps.hue += 0.001f;
    }


    @EventBusSubscriber(modid = Laicaps.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventsBus
    {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(ModModelLayers.OAKHEART_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(ModModelLayers.OAKROOT_BOAT_LAYER, BoatModel::createBodyModel);

            event.registerLayerDefinition(ModModelLayers.OAKHEART_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
            event.registerLayerDefinition(ModModelLayers.OAKROOT_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);


            event.registerLayerDefinition(BluetaleModel.LAYER_LOCATION, BluetaleModel::createBodyLayer);
            //no need for redtale as it uses the same model
            event.registerLayerDefinition(BubblemouthModel.LAYER_LOCATION, BubblemouthModel::createBodyLayer);
            event.registerLayerDefinition(MoonrayModel.LAYER_LOCATION, MoonrayModel::createBodyLayer);
            event.registerLayerDefinition(GlimpuffModel.LAYER_LOCATION, GlimpuffModel::createBodyLayer);


            event.registerLayerDefinition(SwibbleModel.LAYER_LOCATION, SwibbleModel::createBodyLayer);

            event.registerLayerDefinition(NimbleModel.LAYER_LOCATION, NimbleModel::createBodyLayer);
            event.registerLayerDefinition(SnufflerModel.LAYER_LOCATION, SnufflerModel::createBodyLayer);

            event.registerLayerDefinition(RocketModel.LAYER_LOCATION, RocketModel::createBodyLayer);

            event.registerLayerDefinition(MagmaModel.LAYER_LOCATION, MagmaModel::createBodyLayer);
            event.registerLayerDefinition(ShieldModel.LAYER_LOCATION, ShieldModel::createBodyLayer);
            event.registerLayerDefinition(RockModel.LAYER_LOCATION, RockModel::createBodyLayer);

            event.registerLayerDefinition(FishingBobModel.LAYER_LOCATION, FishingBobModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributed(EntityAttributeCreationEvent event)
        {
            event.put(ModEntities.BLUETALE.get(), BluetaleEntity.createAttributes().build());
            event.put(ModEntities.REDTALE.get(), BluetaleEntity.createAttributes().build());
            event.put(ModEntities.BUBBLEMOUTH.get(), BubblemouthEntity.createAttributes().build());
            event.put(ModEntities.MOONRAY.get(), MoonrayEntity.createAttributes().build());
            event.put(ModEntities.GLIMPUFF.get(), GlimpuffEntity.createAttributes().build());
            event.put(ModEntities.SWIBBLE.get(), SwibbleEntity.createAttributes().build());
            event.put(ModEntities.NIMBLE.get(), NimbleEntity.createAttributes().build());
            event.put(ModEntities.SNUFFLER.get(), SnufflerEntity.createAttributes().build());

            event.put(ModEntities.MAGMA.get(), MagmaEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event)
        {
            event.register(
                    ModEntities.BLUETALE.get(), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.REDTALE.get(), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.BUBBLEMOUTH.get(), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.GLIMPUFF.get(), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.SWIBBLE.get(), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.NIMBLE.get(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    ModEventsBus::checkNimbleSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

            event.register(
                    ModEntities.SNUFFLER.get(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    ModEventsBus::checkNimbleSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        }

        public static boolean checkNimbleSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random)
        {
            boolean flag = level.getRawBrightness(pos, 0) > 8;
            return level.getBlockState(pos.below()).is(ModBlocks.ASHA_GRASS_BLOCK) && flag;
        }

        @SubscribeEvent
        public static void registerPayloads(final RegisterPayloadHandlersEvent event)
        {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToClient(
                    Payloads.FishingPayload.TYPE,
                    Payloads.FishingPayload.STREAM_CODEC,
                    PayloadReceiver::receiveFishingClient
            );

            registrar.playToServer(
                    Payloads.FishingCompletedPayload.TYPE,
                    Payloads.FishingCompletedPayload.STREAM_CODEC,
                    PayloadReceiver::receiveFishingCompletedServer
            );

            registrar.playToClient(
                    Payloads.ToastPayload.TYPE,
                    Payloads.ToastPayload.STREAM_CODEC,
                    PayloadReceiver::receiveToast
            );

            registrar.playToServer(
                    Payloads.ChangePlanetSelected.TYPE,
                    Payloads.ChangePlanetSelected.STREAM_CODEC,
                    PayloadReceiver::receiveChangePlanetSelected
            );

            registrar.playToServer(
                    Payloads.BluePrintCompletedPayload.TYPE,
                    Payloads.BluePrintCompletedPayload.STREAM_CODEC,
                    PayloadReceiver::receiveBluePrintCompleted
            );

        }


        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerBlockEntityRenderer(ModBlockEntity.MOD_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntity.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        }

    }

}



