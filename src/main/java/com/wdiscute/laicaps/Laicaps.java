package com.wdiscute.laicaps;

import com.google.common.collect.Iterables;
import com.mojang.logging.LogUtils;
import com.wdiscute.laicaps.block.astronomytable.AstronomyTableScreen;
import com.wdiscute.laicaps.block.refuelstation.RefuelStationScreen;
import com.wdiscute.laicaps.block.telescope.TelescopeScreen;
import com.wdiscute.laicaps.entity.bluetale.BluetaleRenderer;
import com.wdiscute.laicaps.entity.bluetale.RedtaleRenderer;
import com.wdiscute.laicaps.entity.gecko.GeckoRenderer;
import com.wdiscute.laicaps.entity.nimble.NimbleRenderer;
import com.wdiscute.laicaps.entity.rocket.RocketRenderer;
import com.wdiscute.laicaps.entity.rocket.RocketSpaceScreen;
import com.wdiscute.laicaps.entity.snuffler.SnufflerRenderer;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.entity.ModEntities;
import com.wdiscute.laicaps.entity.boat.ModBoatRenderer;
import com.wdiscute.laicaps.mixin.AdvancementProgressAcessor;
import com.wdiscute.laicaps.particle.ChasePuzzleParticles;
import com.wdiscute.laicaps.particle.LunarveilParticles;
import com.wdiscute.laicaps.particle.ModParticles;
import com.wdiscute.laicaps.particle.WaterFlowerParticles;
import com.wdiscute.laicaps.sound.ModSounds;
import com.wdiscute.laicaps.types.ModMenuTypes;
import com.wdiscute.laicaps.types.ModWoodTypes;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.slf4j.Logger;

import java.util.List;

@Mod(Laicaps.MOD_ID)
public class Laicaps
{
    public static final String MOD_ID = "laicaps";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int MAX_EMBER_KNOWLEDGE = 4;
    public static final int MAX_ASHA_KNOWLEDGE = 4;
    public static final int MAX_OVERWORLD_KNOWLEDGE = 4;
    public static final int MAX_LUNAMAR_KNOWLEDGE = 4;

    public static ResourceLocation rl(String s)
    {
        return ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, s);
    }

    public static boolean hasAdvancement(ClientAdvancements clientAdvancements, String achievementName)
    {
        return hasAdvancement(clientAdvancements, Laicaps.MOD_ID, achievementName);
    }

    public static boolean hasAdvancement(ClientAdvancements clientAdvancements, String namespace, String advancementName)
    {
        AdvancementHolder adv = clientAdvancements.get(ResourceLocation.fromNamespaceAndPath(namespace, advancementName));

        if(clientAdvancements instanceof AdvancementProgressAcessor acessor && adv != null)
            return Iterables.size(acessor.getProgress().get(adv).getCompletedCriteria()) > 0;
        else
            return false;

    }

    public static int getEntriesCompletedFromAdvancement(ClientAdvancements clientAdvancements, String achievementName)
    {
        return getEntriesCompletedFromAdvancement(clientAdvancements, Laicaps.MOD_ID, achievementName);
    }

    public static int getEntriesCompletedFromAdvancement(ClientAdvancements clientAdvancements, String namespace, String advancementName)
    {
        AdvancementHolder adv = clientAdvancements.get(ResourceLocation.fromNamespaceAndPath(namespace, advancementName));

        if(clientAdvancements instanceof AdvancementProgressAcessor acessor && adv != null)
        {
            return Iterables.size(acessor.getProgress().get(adv).getCompletedCriteria());
        }else
        {
            return 0;
        }
    }


    public static void awardAdvancement(Player player, String achievementName)
    {
        awardAdvancement(player, Laicaps.MOD_ID, achievementName);
    }


    public static void awardAdvancement(Player player, String namespace, String achievementName)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ServerAdvancementManager manager = serverPlayer.server.getAdvancements();
            AdvancementHolder advHolder = manager.get(ResourceLocation.fromNamespaceAndPath(namespace, achievementName));
            PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();

            if (advHolder != null)
            {
                AdvancementProgress progress = playerAdvancements.getOrStartProgress(advHolder);

                if (!progress.isDone())
                {
                    for (String s : progress.getRemainingCriteria()) playerAdvancements.award(advHolder, s);
                }
            }
        }
    }


    public Laicaps(IEventBus modEventBus, ModContainer modContainer)
    {
        NeoForge.EVENT_BUS.addListener(this::ModifyItemTooltip);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlockEntity.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public void ModifyItemTooltip(ItemTooltipEvent event)
    {
        List<Component> tooltipComponents = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".0"))
        {
            if (event.getFlags().hasShiftDown())
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));

                for (int i = 0; i < 100; i++)
                {
                    if (!I18n.exists("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i))
                        break;
                    tooltipComponents.add(Component.translatable("tooltip." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "." + i));
                }

            } else
            {
                tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
            }

        }
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

            event.enqueueWork(() ->
            {
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.VIOLET_SWEETLILY.getId(), ModBlocks.POTTED_VIOLET_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PEACH_SWEETLILY.getId(), ModBlocks.POTTED_PEACH_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHERRY_SWEETLILY.getId(), ModBlocks.POTTED_CHERRY_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MAGENTA_SWEETLILY.getId(), ModBlocks.POTTED_MAGENTA_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.NAVY_SWEETLILY.getId(), ModBlocks.POTTED_NAVY_SWEETLILY);
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.LUNARVEIL.getId(), ModBlocks.POTTED_LUNARVEIL);
            });

            EntityRenderers.register(ModEntities.MOD_BOAT.get(), context -> new ModBoatRenderer(context, false));
            EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));

            EntityRenderers.register(ModEntities.ROCKET.get(), RocketRenderer::new);


            //animals
            EntityRenderers.register(ModEntities.GECKO.get(), GeckoRenderer::new);

            EntityRenderers.register(ModEntities.BLUETALE.get(), BluetaleRenderer::new);
            EntityRenderers.register(ModEntities.REDTALE.get(), RedtaleRenderer::new);
            EntityRenderers.register(ModEntities.NIMBLE.get(), NimbleRenderer::new);
            EntityRenderers.register(ModEntities.SNUFFLER.get(), SnufflerRenderer::new);


        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event)
        {
            event.registerSpriteSet(ModParticles.CHASE_PUZZLE_PARTICLES.get(), ChasePuzzleParticles.Provider::new);
            event.registerSpriteSet(ModParticles.WATER_FLOWER_PARTICLES.get(), WaterFlowerParticles.Provider::new);
            event.registerSpriteSet(ModParticles.LUNARVEIL_PARTICLES.get(), LunarveilParticles.Provider::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event)
        {
            event.register(ModMenuTypes.TELESCOPE_MENU.get(), TelescopeScreen::new);
            event.register(ModMenuTypes.ASTRONOMY_TABLE_MENU.get(), AstronomyTableScreen::new);
            event.register(ModMenuTypes.ROCKET_SPACE_MENU.get(), RocketSpaceScreen::new);
            event.register(ModMenuTypes.REFUEL_STATION_MENU.get(), RefuelStationScreen::new);
        }

    }
}
