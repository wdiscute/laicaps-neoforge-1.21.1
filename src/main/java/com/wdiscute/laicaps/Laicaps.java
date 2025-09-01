package com.wdiscute.laicaps;

import com.mojang.logging.LogUtils;
import com.wdiscute.laicaps.block.astronomytable.NotebookScreen;
import com.wdiscute.laicaps.block.telescope.TelescopeScreen;
import com.wdiscute.laicaps.entity.bluetale.BluetaleRenderer;
import com.wdiscute.laicaps.entity.bluetale.RedtaleRenderer;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthRenderer;
import com.wdiscute.laicaps.entity.gecko.GeckoRenderer;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffRenderer;
import com.wdiscute.laicaps.entity.magma.MagmaRenderer;
import com.wdiscute.laicaps.entity.magma.RockRenderer;
import com.wdiscute.laicaps.entity.moonray.MoonrayRenderer;
import com.wdiscute.laicaps.entity.nimble.NimbleRenderer;
import com.wdiscute.laicaps.entity.rocket.RefuelScreen;
import com.wdiscute.laicaps.entity.rocket.RocketRenderer;
import com.wdiscute.laicaps.entity.snuffler.SnufflerRenderer;
import com.wdiscute.laicaps.entity.swibble.SwibbleRenderer;
import com.wdiscute.laicaps.entity.fishing.FishingBobRenderer;
import com.wdiscute.laicaps.fishing.FishingRodScreen;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.entity.boat.ModBoatRenderer;
import com.wdiscute.laicaps.item.ModItemProperties;
import com.wdiscute.laicaps.particle.*;
import com.wdiscute.laicaps.notebook.EntryUnlockedToast;
import com.wdiscute.laicaps.util.Tooltips;
import com.wdiscute.laicaps.worldgen.ModFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.common.NeoForge;
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
            EntityRenderers.register(ModEntities.ROCK.get(), RockRenderer::new);

            EntityRenderers.register(ModEntities.FISHING_BOB.get(), FishingBobRenderer::new);


            //animals
            EntityRenderers.register(ModEntities.GECKO.get(), GeckoRenderer::new);

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

}
