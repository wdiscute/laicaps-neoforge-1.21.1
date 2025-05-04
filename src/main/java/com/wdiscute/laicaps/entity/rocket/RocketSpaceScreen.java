package com.wdiscute.laicaps.entity.rocket;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.telescope.RevealRenderUtil;
import com.wdiscute.laicaps.block.telescope.TelescopeMenu;
import com.wdiscute.laicaps.item.ModDataComponentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector2f;
import org.joml.Vector2i;
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

    private static final ResourceLocation EMBER_TO_EMBER = Laicaps.rl("textures/gui/telescope/ember_to_ember.png");
    private static final ResourceLocation EMBER_TO_ASHA = Laicaps.rl("textures/gui/telescope/ember_to_asha.png");
    private static final ResourceLocation EMBER_TO_OVERWORLD = Laicaps.rl("textures/gui/telescope/ember_overworld.png");
    private static final ResourceLocation EMBER_TO_LUNAMAR = Laicaps.rl("textures/gui/telescope/ember_to_lunamar.png");

    private static final ResourceLocation ASHA_TO_EMBER = Laicaps.rl("textures/gui/telescope/asha_to_ember.png");
    private static final ResourceLocation ASHA_TO_ASHA = Laicaps.rl("textures/gui/telescope/asha_to_asha.png");
    private static final ResourceLocation ASHA_TO_OVERWORLD = Laicaps.rl("textures/gui/telescope/asha_to_overworld.png");
    private static final ResourceLocation ASHA_TO_LUNAMAR = Laicaps.rl("textures/gui/telescope/asha_to_lunamar.png");

    private static final ResourceLocation OVERWORLD_TO_EMBER = Laicaps.rl("textures/gui/telescope/overworld_to_ember.png");
    private static final ResourceLocation OVERWORLD_TO_ASHA = Laicaps.rl("textures/gui/telescope/overworld_to_asha.png");
    private static final ResourceLocation OVERWORLD_TO_OVERWORLD = Laicaps.rl("textures/gui/telescope/overworld_to_overworld.png");
    private static final ResourceLocation OVERWORLD_TO_LUNAMAR = Laicaps.rl("textures/gui/telescope/overworld_to_lunamar.png");

    private static final ResourceLocation LUNAMAR_TO_EMBER = Laicaps.rl("textures/gui/telescope/lunamar_to_ember.png");
    private static final ResourceLocation LUNAMAR_TO_ASHA = Laicaps.rl("textures/gui/telescope/lunamar_to_asha.png");
    private static final ResourceLocation LUNAMAR_TO_OVERWORLD = Laicaps.rl("textures/gui/telescope/lunamar_to_overworld.png");
    private static final ResourceLocation LUNAMAR_TO_LUNAMAR = Laicaps.rl("textures/gui/telescope/lunamar_to_lunamar.png");


    int state = 0;

    int uiX;
    int uiY;

    int canvasX;
    int canvasY;

    private static RocketSpaceMenu menu;

    ItemStack book;

    int planetSelected = -1;

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
        if (menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTROLOGY_NOTEBOOK.get()))
        {
            book = menu.rocketEntity.inventory.getStackInSlot(0);
        }

        if (!menu.rocketEntity.inventory.getStackInSlot(0).equals(book))
        {
            state = 1;
            book = menu.rocketEntity.inventory.getStackInSlot(0);
        }

        if (state == 0 && menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTROLOGY_NOTEBOOK.get()))
        {
            state = 1;
            book = menu.rocketEntity.inventory.getStackInSlot(0);
            return;
        }

        if (!menu.rocketEntity.inventory.getStackInSlot(0).is(ModItems.ASTROLOGY_NOTEBOOK))
        {
            state = 0;
            return;
        }

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200 && book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_EMBER) > 0)
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 1);
        }

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111 && book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_ASHA) > 0)
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 2);
        }

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 3);
        }

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65 && book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_LUNAMAR) > 0)
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 4);
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
        if (state == 1 && book.is(ModItems.ASTROLOGY_NOTEBOOK))
        {
            //renders background
            renderImage(guiGraphics, PLANET_SCREEN_BACKGROUND);

            double x = mouseX - uiX;
            double y = mouseY - uiY;

            //render planets with blurs & highlights
            {
                ResourceLocation rl;
                RenderSystem.enableBlend();

                //ember
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_EMBER) < 4)
                    rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_BLUR_HIGHLIGHTED : EMBER_BLUR;
                else
                {
                    rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_HIGHLIGHTED : EMBER;
                    renderPlanetArrows(guiGraphics, x, y);
                }

                renderImage(guiGraphics, rl);


                //asha
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_ASHA) < 4)
                    rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_BLUR_HIGHLIGHTED : ASHA_BLUR;
                else
                    rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_HIGHLIGHTED : ASHA;
                renderImage(guiGraphics, rl);

                //overworld
                rl = (x > 392 && x < 429 && y > 121 && y < 160) ? OVERWORLD_HIGHLIGHTED : OVERWORLD;
                renderImage(guiGraphics, rl);


                //lunamar
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_LUNAMAR) < 4)
                    rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_BLUR_HIGHLIGHTED : LUNAMAR_BLUR;
                else
                    rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_HIGHLIGHTED : LUNAMAR;
                renderImage(guiGraphics, rl);

                RenderSystem.disableBlend();
            }

            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

            //render tooltip
            {
                List<Component> tooltips;
                //ember
                if (x > 250 && x < 283 && y > 170 && y < 200)
                {
                    tooltips = book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_EMBER).intValue() < 4 ? tooltipHelper("ember_blur") : tooltipHelper("ember");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //asha
                if (x > 310 && x < 335 && y > 86 && y < 111)
                {
                    tooltips = book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_ASHA).intValue() < 4 ? tooltipHelper("asha_blur") : tooltipHelper("asha");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //overworld
                if (x > 392 && x < 429 && y > 121 && y < 160)
                {
                    tooltips = tooltipHelper("overworld");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //lunamar
                if (x > 444 && x < 589 && y > 16 && y < 65)
                {
                    tooltips = book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_LUNAMAR).intValue() < 4 ? tooltipHelper("lunamar_blur") : tooltipHelper("lunamar");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }


            }
        }
    }

    private void renderPlanetArrows(GuiGraphics guiGraphics, double x, double y)
    {
        String planet = "";

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
            planet = "ember";
        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            planet = "asha";

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            planet = "overworld";

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            planet = "lunamar";


        if (menu.rocketEntity.level().dimension() == EMBER_KEY)
        {
            if (planet.equals("ember")) renderImage(guiGraphics, EMBER_TO_EMBER);
            if (planet.equals("asha")) renderImage(guiGraphics, EMBER_TO_ASHA);
            if (planet.equals("overworld")) renderImage(guiGraphics, EMBER_TO_OVERWORLD);
            if (planet.equals("lunamar")) renderImage(guiGraphics, EMBER_TO_LUNAMAR);
        }

        if (menu.rocketEntity.level().dimension() == ASHA_KEY)
        {
            if (planet.equals("ember")) renderImage(guiGraphics, ASHA_TO_EMBER);
            if (planet.equals("asha")) renderImage(guiGraphics, ASHA_TO_ASHA);
            if (planet.equals("overworld")) renderImage(guiGraphics, ASHA_TO_OVERWORLD);
            if (planet.equals("lunamar")) renderImage(guiGraphics, ASHA_TO_LUNAMAR);
        }

        if (menu.rocketEntity.level().dimension() == OVERWORLD_KEY)
        {
            if (planet.equals("ember")) renderImage(guiGraphics, OVERWORLD_TO_EMBER);
            if (planet.equals("asha")) renderImage(guiGraphics, OVERWORLD_TO_ASHA);
            if (planet.equals("overworld")) renderImage(guiGraphics, OVERWORLD_TO_OVERWORLD);
            if (planet.equals("lunamar")) renderImage(guiGraphics, OVERWORLD_TO_LUNAMAR);
        }

        if (menu.rocketEntity.level().dimension() == LUNAMAR_KEY)
        {
            if (planet.equals("ember")) renderImage(guiGraphics, LUNAMAR_TO_EMBER);
            if (planet.equals("asha")) renderImage(guiGraphics, LUNAMAR_TO_ASHA);
            if (planet.equals("overworld")) renderImage(guiGraphics, LUNAMAR_TO_OVERWORLD);
            if (planet.equals("lunamar")) renderImage(guiGraphics, LUNAMAR_TO_LUNAMAR);
        }


    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

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

    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }

    private List<Component> tooltipHelper(String input)
    {
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (I18n.exists("gui.laicaps.telescope.tooltip." + input + "." + i))
            {
                list.add(Component.translatable("gui.laicaps.telescope.tooltip." + input + "." + i));
            } else
            {
                if (i == 0)
                    list.add(Component.literal("translation key missing! gui.laicaps.telescope.tooltip." + input + "." + i));
                if (i == 0) list.add(Component.literal("Report to @wdiscute on discord"));
                if (i == 0) list.add(Component.literal("or whoever is translating the mod! :3"));
                break;
            }
        }


        //add "there's more to research" and "travel possible"
        {
            if (Objects.equals(input, "ember"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.travel"));
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_EMBER) < Laicaps.MAX_EMBER_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(input, "asha"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.travel"));
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_ASHA) < Laicaps.MAX_ASHA_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(input, "overworld"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.travel"));
            }

            if (Objects.equals(input, "lunamar"))
            {
                list.add(Component.translatable("gui.laicaps.rocket.tooltip.generic.travel"));
                if (book.get(ModDataComponentTypes.ASTROLOGY_KNOWLEDGE_LUNAMAR) < Laicaps.MAX_LUNAMAR_KNOWLEDGE)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }
        }


        //add .stargaze translation key at the end for blurred planets
        if (Objects.equals(input, "ember_blur") || Objects.equals(input, "asha_blur") || Objects.equals(input, "lunamar_blur"))
            list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.stargaze"));


        return list;
    }

}
