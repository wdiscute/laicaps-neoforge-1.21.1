package com.wdiscute.laicaps.block.researchstation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.astronomytable.AstronomyTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class ResearchStationScreen extends AbstractContainerScreen<ResearchStationMenu>
{

    private static final Logger log = LoggerFactory.getLogger(ResearchStationScreen.class);
    private static final ResourceLocation INV_BOOK_BACKGROUND = Laicaps.rl("textures/gui/research_station/background.png");

    Random r = new Random();

    int uiX;
    int uiY;


    @Override
    protected void init()
    {
        super.init();

        imageWidth = 512;
        imageHeight = 256;
        uiX = (width - imageWidth) / 2;
        uiY = (height - imageHeight) / 2;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //previous arrow
        if (x > 68 && x < 105 && y > 230 && y < 240)
        {

        }

        return super.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        //initial setup every frame
        double x = mouseX - uiX;
        double y = mouseY - uiY;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, INV_BOOK_BACKGROUND);


        renderImage(guiGraphics, INV_BOOK_BACKGROUND);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public ResearchStationScreen(ResearchStationMenu researchStationMenu, Inventory playerInventory, Component title)
    {
        super(researchStationMenu, playerInventory, title);
    }


    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl)
    {
        guiGraphics.blit(rl, uiX, uiY, 0, 0, 512, 256, 512, 256);
    }

    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl, int yOffset)
    {
        guiGraphics.blit(rl, uiX, uiY + yOffset, 0, 0, 512, 256, 512, 256);
    }


    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
