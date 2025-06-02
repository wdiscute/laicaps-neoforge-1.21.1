package com.wdiscute.laicaps.entity.rocket;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.telescope.RevealRenderUtil;
import com.wdiscute.laicaps.item.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.joml.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.*;


public class RocketSpaceScreen extends AbstractContainerScreen<RocketSpaceMenu>
{

    private static final Logger log = LoggerFactory.getLogger(RocketSpaceScreen.class);

    private static final ResourceLocation INV_AND_BORDER_BACKGROUND = Laicaps.rl("textures/gui/rocket/inventory_overlay.png");
    private static final ResourceLocation PLANET_SCREEN_BACKGROUND = Laicaps.rl("textures/gui/rocket/planet_screen_background.png");
    private static final ResourceLocation BLACK_OVERLAY = Laicaps.rl("textures/gui/telescope/black.png");
    private static final ResourceLocation FUEL = Laicaps.rl("textures/gui/rocket/fuel.png");

    private static final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
    private static final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl( "asha"));
    private static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    private static final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl( "lunamar"));

    private static final ResourceLocation EMBER = Laicaps.rl("textures/gui/telescope/ember.png");
    private static final ResourceLocation EMBER_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/ember_highlighted.png");
    private static final ResourceLocation EMBER_BLUR = Laicaps.rl("textures/gui/telescope/ember_blur.png");
    private static final ResourceLocation EMBER_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/ember_blur_highlighted.png");
    private static final ResourceLocation ASHA = Laicaps.rl("textures/gui/telescope/asha.png");
    private static final ResourceLocation ASHA_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/asha_highlighted.png");
    private static final ResourceLocation ASHA_BLUR = Laicaps.rl("textures/gui/telescope/asha_blur.png");
    private static final ResourceLocation ASHA_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/asha_blur_highlighted.png");
    private static final ResourceLocation OVERWORLD = Laicaps.rl("textures/gui/telescope/overworld.png");
    private static final ResourceLocation OVERWORLD_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/overworld_highlighted.png");
    private static final ResourceLocation LUNAMAR = Laicaps.rl("textures/gui/telescope/lunamar.png");
    private static final ResourceLocation LUNAMAR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/lunamar_highlighted.png");
    private static final ResourceLocation LUNAMAR_BLUR = Laicaps.rl("textures/gui/telescope/lunamar_blur.png");
    private static final ResourceLocation LUNAMAR_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/lunamar_blur_highlighted.png");

    private static final ResourceLocation EMBER_TO_EMBER = Laicaps.rl("textures/gui/rocket/ember_to_ember.png");
    private static final ResourceLocation EMBER_TO_ASHA = Laicaps.rl("textures/gui/rocket/ember_to_asha.png");
    private static final ResourceLocation EMBER_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/ember_to_overworld.png");
    private static final ResourceLocation EMBER_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/ember_to_lunamar.png");

    private static final ResourceLocation ASHA_TO_EMBER = Laicaps.rl("textures/gui/rocket/asha_to_ember.png");
    private static final ResourceLocation ASHA_TO_ASHA = Laicaps.rl("textures/gui/rocket/asha_to_asha.png");
    private static final ResourceLocation ASHA_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/asha_to_overworld.png");
    private static final ResourceLocation ASHA_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/asha_to_lunamar.png");

    private static final ResourceLocation OVERWORLD_TO_EMBER = Laicaps.rl("textures/gui/rocket/overworld_to_ember.png");
    private static final ResourceLocation OVERWORLD_TO_ASHA = Laicaps.rl("textures/gui/rocket/overworld_to_asha.png");
    private static final ResourceLocation OVERWORLD_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/overworld_to_overworld.png");
    private static final ResourceLocation OVERWORLD_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/overworld_to_lunamar.png");

    private static final ResourceLocation LUNAMAR_TO_EMBER = Laicaps.rl("textures/gui/rocket/lunamar_to_ember.png");
    private static final ResourceLocation LUNAMAR_TO_ASHA = Laicaps.rl("textures/gui/rocket/lunamar_to_asha.png");
    private static final ResourceLocation LUNAMAR_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/lunamar_to_overworld.png");
    private static final ResourceLocation LUNAMAR_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/lunamar_to_lunamar.png");

    private static final ResourceLocation UNKNOWN_TO_EMBER = Laicaps.rl("textures/gui/rocket/unknown_to_ember.png");
    private static final ResourceLocation UNKNOWN_TO_ASHA = Laicaps.rl("textures/gui/rocket/unknown_to_asha.png");
    private static final ResourceLocation UNKNOWN_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/unknown_to_overworld.png");
    private static final ResourceLocation UNKNOWN_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/unknown_to_lunamar.png");

    private static RocketSpaceMenu menu;

    int uiX;
    int uiY;

    int canvasX;
    int canvasY;

    ItemStack tank;
    float fuelAvailable = 0;

    boolean emberDiscovered;
    int emberEntries;
    boolean ashaDiscovered;
    int ashaEntries;
    boolean lunamarDiscovered;
    int lunamarEntries;

    int rocketState = 0;
    int counter = 0;

    @Override
    protected void init()
    {
        super.init();
        this.imageWidth = 512;
        this.imageHeight = 256;

        uiX = (width - imageWidth) / 2;
        uiY = (height - imageHeight) / 2;
        canvasX = uiX + 178;
        canvasY = uiY + 3;
    }

    @Override
    protected void containerTick()
    {
        counter++;

        if (menu.container == null) return;

        if (menu.container.getItem(3).is(Items.DIRT)) rocketState = 0;
        if (menu.container.getItem(3).is(Items.STONE)) rocketState = 1;
        if (menu.container.getItem(3).is(Items.ANDESITE)) rocketState = 2;
        if (menu.container.getItem(3).is(Items.GRANITE)) rocketState = 3;
        if (menu.container.getItem(3).is(Items.DIORITE)) rocketState = 4;

        ClientAdvancements adv = Minecraft.getInstance().getConnection().getAdvancements();

        emberDiscovered = AdvHelper.hasAdvancement(adv, "ember_discovered");
        ashaDiscovered = AdvHelper.hasAdvancement(adv, "asha_discovered");
        lunamarDiscovered = AdvHelper.hasAdvancement(adv, "lunamar_discovered");

        emberEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "ember_entries");
        ashaEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "asha_entries");
        lunamarEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "lunamar_entries");


        if (menu.container.getItem(2).is(ModItems.TANK.get()) || menu.container.getItem(2).is(ModItems.MEDIUM_TANK.get()) || menu.container.getItem(2).is(ModItems.LARGE_TANK.get()))
        {
            tank = menu.container.getItem(2);
            fuelAvailable = tank.get(ModDataComponents.FUEL);

        } else
        {
            fuelAvailable = 0;
        }


    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        if(rocketState != 0) return false;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
            getMinecraft().gameMode.handleInventoryButtonClick(menu.containerId, 1);

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            getMinecraft().gameMode.handleInventoryButtonClick(menu.containerId, 2);

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            getMinecraft().gameMode.handleInventoryButtonClick(menu.containerId, 3);

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            getMinecraft().gameMode.handleInventoryButtonClick(menu.containerId, 4);

        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        checkNulls();
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

        //renders background
        renderImage(guiGraphics, PLANET_SCREEN_BACKGROUND);


        checkMissingItemsMessage(guiGraphics);

        //render fuel
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        RenderSystem.setShaderTexture(0, FUEL);


        float fuelPercentage = ((fuelAvailable * 100 / 1300));
        float fuelPercentageOfBar = fuelPercentage * 128 / 100;
        fuelPercentageOfBar -= 3;

        RevealRenderUtil.renderWithOcclusion(
                poseStack, uiX + 34, uiY + 105, 129, 8,
                Lists.newArrayList(
                        new Vector2f(uiX + 44 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 60 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 75 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 90 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 105 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 120 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 135 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 150 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 165 + fuelPercentageOfBar, uiY + 109),
                        new Vector2f(uiX + 180 + fuelPercentageOfBar, uiY + 109)
                ),
                Lists.newArrayList(
                        0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f
                ));

        poseStack.popPose();

        //render inventory overlay
        renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);


        //render planet selected arrow
        renderPlanetArrows(guiGraphics);

        //render planets with blurs & highlights
        {
            ResourceLocation rl;
            RenderSystem.enableBlend();

            //ember
            if (emberDiscovered)
                rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_HIGHLIGHTED : EMBER;
            else
                rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_BLUR_HIGHLIGHTED : EMBER_BLUR;

            renderImage(guiGraphics, rl);


            //asha
            if (ashaDiscovered)
                rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_HIGHLIGHTED : ASHA;
            else
                rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_BLUR_HIGHLIGHTED : ASHA_BLUR;

            renderImage(guiGraphics, rl);

            //overworld
            rl = (x > 392 && x < 429 && y > 121 && y < 160) ? OVERWORLD_HIGHLIGHTED : OVERWORLD;
            renderImage(guiGraphics, rl);

            //lunamar
            if (lunamarDiscovered)
                rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_HIGHLIGHTED : LUNAMAR;
            else
                rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_BLUR_HIGHLIGHTED : LUNAMAR_BLUR;

            renderImage(guiGraphics, rl);

            RenderSystem.disableBlend();
        }

        //render tooltip
        renderPlanetTooltip(guiGraphics, mouseX, mouseY);


    }

    private void checkMissingItemsMessage(@Nullable GuiGraphics guiGraphics)
    {


        //check knowledge
        boolean canTravel = false;
        if(menu.container.getItem(4).is(ModItems.EMBER) && emberDiscovered) canTravel = true;
        if(menu.container.getItem(4).is(ModItems.ASHA) && ashaDiscovered) canTravel = true;
        if(menu.container.getItem(4).is(ModItems.OVERWORLD)) canTravel = true;
        if(menu.container.getItem(4).is(ModItems.LUNAMAR) && lunamarDiscovered) canTravel = true;

        if (!canTravel)
        {
            guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.rocket.missing_knowledge"), uiX + 30, uiY + 70, 13186614, true);
            return;
        }


        if (tank.isEmpty())
        {
            if (!(guiGraphics == null))
                guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.rocket.missing_tank"), uiX + 30, uiY + 70, 13186614, true);
            return;
        }

        //check fuel
        if (!setFuelRequired())
        {
            guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.rocket.missing_fuel"), uiX + 30, uiY + 70, 13186614, true);
            return;
        }



        if (rocketState == 0)
        {
            if (!(guiGraphics == null))
                guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.rocket.missing_nothing"), uiX + 30, uiY + 70, 13186614, true);
        }

    }

    private boolean setFuelRequired()
    {
        float fuelRequired = 0;
        boolean flag = false;

        if (Minecraft.getInstance().player.level().dimension() == EMBER_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.ASHA)) fuelRequired = 490;
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) fuelRequired = 700;
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) fuelRequired = 1240;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == ASHA_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) fuelRequired = 490;
            if (menu.container.getItem(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) fuelRequired = 330;
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) fuelRequired = 870;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == OVERWORLD_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) fuelRequired = 790;
            if (menu.container.getItem(4).is(ModItems.ASHA)) fuelRequired = 330;
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) fuelRequired = 660;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == LUNAMAR_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) fuelRequired = 1240;
            if (menu.container.getItem(4).is(ModItems.ASHA)) fuelRequired = 870;
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) fuelRequired = 660;
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
            flag = true;
        }

        if (!flag)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
        }

        return fuelAvailable > fuelRequired;

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    private void renderPlanetArrows(GuiGraphics guiGraphics)
    {

        if (Minecraft.getInstance().player.level().dimension() == EMBER_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) renderImage(guiGraphics, EMBER_TO_EMBER);
            if (menu.container.getItem(4).is(ModItems.ASHA)) renderImage(guiGraphics, EMBER_TO_ASHA);
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) renderImage(guiGraphics, EMBER_TO_OVERWORLD);
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) renderImage(guiGraphics, EMBER_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == ASHA_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) renderImage(guiGraphics, ASHA_TO_EMBER);
            if (menu.container.getItem(4).is(ModItems.ASHA)) renderImage(guiGraphics, ASHA_TO_ASHA);
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) renderImage(guiGraphics, ASHA_TO_OVERWORLD);
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) renderImage(guiGraphics, ASHA_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == OVERWORLD_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) renderImage(guiGraphics, OVERWORLD_TO_EMBER);
            if (menu.container.getItem(4).is(ModItems.ASHA)) renderImage(guiGraphics, OVERWORLD_TO_ASHA);
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) renderImage(guiGraphics, OVERWORLD_TO_OVERWORLD);
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) renderImage(guiGraphics, OVERWORLD_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == LUNAMAR_KEY)
        {
            if (menu.container.getItem(4).is(ModItems.EMBER)) renderImage(guiGraphics, LUNAMAR_TO_EMBER);
            if (menu.container.getItem(4).is(ModItems.ASHA)) renderImage(guiGraphics, LUNAMAR_TO_ASHA);
            if (menu.container.getItem(4).is(ModItems.OVERWORLD)) renderImage(guiGraphics, LUNAMAR_TO_OVERWORLD);
            if (menu.container.getItem(4).is(ModItems.LUNAMAR)) renderImage(guiGraphics, LUNAMAR_TO_LUNAMAR);
            return;
        }

        if (menu.container.getItem(4).is(ModItems.EMBER)) renderImage(guiGraphics, UNKNOWN_TO_EMBER);
        if (menu.container.getItem(4).is(ModItems.UNKNOWN)) renderImage(guiGraphics, UNKNOWN_TO_ASHA);
        if (menu.container.getItem(4).is(ModItems.UNKNOWN)) renderImage(guiGraphics, UNKNOWN_TO_OVERWORLD);
        if (menu.container.getItem(4).is(ModItems.UNKNOWN)) renderImage(guiGraphics, UNKNOWN_TO_LUNAMAR);

    }


    private void renderPlanetTooltip(GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        String planet = "";

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
            planet = emberDiscovered ? "ember" : "ember_blur";

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            planet = ashaDiscovered ? "asha" : "asha_blur";

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            planet = "overworld";

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            planet = lunamarDiscovered ? "lunamar" : "lunamar_blur";

        //return if mouse is not hovering a planet
        if (planet.isEmpty()) return;

        //add base tooltips for each planet
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (I18n.exists("gui.laicaps.rocket.tooltip." + planet + "." + i))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip." + planet + "." + i));
            } else
            {
                if (i == 0)
                    list.add(Component.literal("translation key missing! gui.laicaps.rocket.tooltip." + planet + "." + i));
                if (i == 0) list.add(Component.literal("Report to @wdiscute on discord"));
                if (i == 0) list.add(Component.literal("or whoever is translating the mod! :3"));
                break;
            }
        }


        //add "there's more to research" and "Click to Select" for non-blurred
        if(false)
        {
            if (Objects.equals(planet, "ember"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (emberEntries < Laicaps.EMBER_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "asha"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (ashaEntries < Laicaps.ASHA_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "overworld"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
            }

            if (Objects.equals(planet, "lunamar"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (lunamarEntries < Laicaps.LUNAMAR_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }
        }


        //add "more knowledge required" for blurred planets
        if (Objects.equals(planet, "ember_blur") || Objects.equals(planet, "asha_blur") || Objects.equals(planet, "lunamar_blur"))
        {
            //list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.knowledge"));
            //list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.knowledge.2"));
        }

        guiGraphics.renderComponentTooltip(this.font, list, ((int) mouseX), ((int) mouseY));
    }


    //
    //                       ,--.                           ,--.
    //,--,--,   ,---.      ,-'  '-.  ,---.  ,--.,--.  ,---. |  ,---.  ,--. ,--.
    //|      \ | .-. |     '-.  .-' | .-. | |  ||  | | .--' |  .-.  |  \  '  /
    //|  ||  | ' '-' '       |  |   ' '-' ' '  ''  ' \ `--. |  | |  |   \   '
    //`--''--'  `---'        `--'    `---'   `----'   `---' `--' `--' .-'  /
    //                                                                `---'


    private void checkNulls()
    {
        if (tank == null) tank = ItemStack.EMPTY;
    }

    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }

    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl)
    {
        guiGraphics.blit(rl, uiX, uiY, 0, 0, 512, 256, 512, 256);
    }


    public RocketSpaceScreen(RocketSpaceMenu rocketSpaceMenu, Inventory playerInventory, Component title)
    {
        super(rocketSpaceMenu, playerInventory, title);
        menu = rocketSpaceMenu;
    }


}
