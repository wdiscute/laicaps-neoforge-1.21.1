package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.item.ModDataComponentTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class RocketSpaceScreen extends AbstractContainerScreen<RocketSpaceMenu>
{
    private static final Random r = new Random();

    private static final Logger log = LoggerFactory.getLogger(RocketSpaceScreen.class);

    private static final ResourceLocation INV_AND_BORDER_BACKGROUND = Laicaps.rl("textures/gui/rocket/inventory_overlay.png");
    private static final ResourceLocation PLANET_SCREEN_BACKGROUND = Laicaps.rl("textures/gui/telescope/planet_screen_background.png");
    private static final ResourceLocation BLACK_OVERLAY = Laicaps.rl("textures/gui/telescope/black.png");
    private static final ResourceLocation NO_BOOK_SCREEN_BACKGROUND_ROCKET = Laicaps.rl("textures/gui/telescope/no_book_screen_background_rocket.png");

    private static final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "ember"));
    private static final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha"));
    private static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    private static final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "lunamar"));

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

    private static final ResourceLocation NOTHING_TO_EMBER = Laicaps.rl("textures/gui/rocket/lunamar_to_lunamar.png");
    private static final ResourceLocation NOTHING_TO_ASHA = Laicaps.rl("textures/gui/rocket/lunamar_to_lunamar.png");
    private static final ResourceLocation NOTHING_TO_OVERWORLD = Laicaps.rl("textures/gui/rocket/lunamar_to_lunamar.png");
    private static final ResourceLocation NOTHING_TO_LUNAMAR = Laicaps.rl("textures/gui/rocket/lunamar_to_lunamar.png");

    int state = 0;

    int uiX;
    int uiY;

    int canvasX;
    int canvasY;

    private static RocketSpaceMenu menu;

    ItemStack book;

    int planetSelected = -1;

    int emberKnowledge = 0;
    int ashaKnowledge = 0;
    int overworldKnowledge = 0;
    int lunamarKnowledge = 0;

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
        if (menu.rocketEntity == null) return;
        if (menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTRONOMY_NOTEBOOK.get()))
        {
            book = menu.rocketEntity.inventory.getStackInSlot(0);
        }

        if (!menu.rocketEntity.inventory.getStackInSlot(0).equals(book))
        {
            state = 1;
            book = menu.rocketEntity.inventory.getStackInSlot(0);
        }

        if (state == 0 && menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTRONOMY_NOTEBOOK.get()))
        {
            state = 1;
            book = menu.rocketEntity.inventory.getStackInSlot(0);
            return;
        }

        if (!menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTRONOMY_NOTEBOOK))
        {
            state = 0;
            return;
        }


        emberKnowledge = book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER);
        ashaKnowledge = book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER);
        overworldKnowledge = book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER);
        lunamarKnowledge = book.get(ModDataComponentTypes.ASTRONOMY_KNOWLEDGE_EMBER);

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
        {
            planetSelected = 1;
        }

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
        {
            planetSelected = 2;
        }

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
        {
            planetSelected = 3;
        }

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
        {
            planetSelected = 4;
        }


        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

        if (book == null) return;

        guiGraphics.blit(BLACK_OVERLAY, 0, 0, 0, 0, 10000, 10000, 331, 250);

        //no book in slot
        if (state == 0)
        {
            //renders BLACK OVERLAY sky background
            renderImage(guiGraphics, NO_BOOK_SCREEN_BACKGROUND_ROCKET);

            guiGraphics.drawString(this.font, Component.translatable("gui.rocket.telescope.notebook"), uiX + 50, uiY + 70, 13186614, true);

            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

        }


        //planet selection screen
        if (state == 1 && book.is(ModItems.ASTRONOMY_NOTEBOOK))
        {
            //renders background
            renderImage(guiGraphics, PLANET_SCREEN_BACKGROUND);

            double x = mouseX - uiX;
            double y = mouseY - uiY;

            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

            //render planet selected arrow
            renderPlanetArrows(guiGraphics);

            //render planets with blurs & highlights
            {
                ResourceLocation rl;
                RenderSystem.enableBlend();

                //ember
                if (emberKnowledge < 4)
                    rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_BLUR_HIGHLIGHTED : EMBER_BLUR;
                else
                {
                    rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_HIGHLIGHTED : EMBER;
                }

                renderImage(guiGraphics, rl);


                //asha
                if (ashaKnowledge < 4)
                    rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_BLUR_HIGHLIGHTED : ASHA_BLUR;
                else
                    rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_HIGHLIGHTED : ASHA;
                renderImage(guiGraphics, rl);

                //overworld
                rl = (x > 392 && x < 429 && y > 121 && y < 160) ? OVERWORLD_HIGHLIGHTED : OVERWORLD;
                renderImage(guiGraphics, rl);


                //lunamar
                if (lunamarKnowledge < 4)
                    rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_BLUR_HIGHLIGHTED : LUNAMAR_BLUR;
                else
                    rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_HIGHLIGHTED : LUNAMAR;
                renderImage(guiGraphics, rl);

                RenderSystem.disableBlend();
            }

            //render tooltip
            renderPlanetTooltip(guiGraphics, mouseX, mouseY);



        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }


    private void renderPlanetArrows(GuiGraphics guiGraphics)
    {
        if (menu.rocketEntity.level().dimension() == EMBER_KEY)
        {
            if (planetSelected == 1) renderImage(guiGraphics, EMBER_TO_EMBER);
            if (planetSelected == 2) renderImage(guiGraphics, EMBER_TO_ASHA);
            if (planetSelected == 3) renderImage(guiGraphics, EMBER_TO_OVERWORLD);
            if (planetSelected == 4) renderImage(guiGraphics, EMBER_TO_LUNAMAR);
            return;
        }

        if (menu.rocketEntity.level().dimension() == ASHA_KEY)
        {
            if (planetSelected == 1) renderImage(guiGraphics, ASHA_TO_EMBER);
            if (planetSelected == 2) renderImage(guiGraphics, ASHA_TO_ASHA);
            if (planetSelected == 3) renderImage(guiGraphics, ASHA_TO_OVERWORLD);
            if (planetSelected == 4) renderImage(guiGraphics, ASHA_TO_LUNAMAR);
            return;
        }

        if (menu.rocketEntity.level().dimension() == OVERWORLD_KEY)
        {
            if (planetSelected == 1) renderImage(guiGraphics, OVERWORLD_TO_EMBER);
            if (planetSelected == 2) renderImage(guiGraphics, OVERWORLD_TO_ASHA);
            if (planetSelected == 3) renderImage(guiGraphics, OVERWORLD_TO_OVERWORLD);
            if (planetSelected == 4) renderImage(guiGraphics, OVERWORLD_TO_LUNAMAR);
            return;
        }

        if (menu.rocketEntity.level().dimension() == LUNAMAR_KEY)
        {
            if (planetSelected == 1) renderImage(guiGraphics, LUNAMAR_TO_EMBER);
            if (planetSelected == 2) renderImage(guiGraphics, LUNAMAR_TO_ASHA);
            if (planetSelected == 3) renderImage(guiGraphics, LUNAMAR_TO_OVERWORLD);
            if (planetSelected == 4) renderImage(guiGraphics, LUNAMAR_TO_LUNAMAR);
            return;
        }

        if (planetSelected == 1) renderImage(guiGraphics, NOTHING_TO_EMBER);
        if (planetSelected == 2) renderImage(guiGraphics, NOTHING_TO_ASHA);
        if (planetSelected == 3) renderImage(guiGraphics, NOTHING_TO_OVERWORLD);
        if (planetSelected == 4) renderImage(guiGraphics, NOTHING_TO_LUNAMAR);

    }


    private void renderPlanetTooltip(GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        String planet = "";

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
            planet = emberKnowledge > 0 ? "ember" : "ember_blur";

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            planet = ashaKnowledge > 0 ? "asha" : "asha_blur";

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            planet = "overworld";

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            planet = lunamarKnowledge > 0 ? "lunamar" : "lunamar_blur";

        //return if mouse is not hovering a planet
        if (planet.equals("")) return;

        //add base tooltips for each planet
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (I18n.exists("gui.laicaps.telescope.tooltip." + planet + "." + i))
            {
                list.add(Component.translatable("gui.laicaps.telescope.tooltip." + planet + "." + i));
            } else
            {
                if (i == 0)
                    list.add(Component.literal("translation key missing! gui.laicaps.telescope.tooltip." + planet + "." + i));
                if (i == 0) list.add(Component.literal("Report to @wdiscute on discord"));
                if (i == 0) list.add(Component.literal("or whoever is translating the mod! :3"));
                break;
            }
        }


        //add "there's more to research" and "Click to Select" for non-blurred
        {
            if (Objects.equals(planet, "ember"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (emberKnowledge < Laicaps.MAX_EMBER_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "asha"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (ashaKnowledge < Laicaps.MAX_ASHA_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "overworld"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
            }

            if (Objects.equals(planet, "lunamar"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.chart"));
                if (lunamarKnowledge < Laicaps.MAX_LUNAMAR_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }
        }


        //add "more knowledge required" for blurred planets
        if (Objects.equals(planet, "ember_blur") || Objects.equals(planet, "asha_blur") || Objects.equals(planet, "lunamar_blur"))
        {
            list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.knowledge"));
            list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.knowledge.2"));
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
