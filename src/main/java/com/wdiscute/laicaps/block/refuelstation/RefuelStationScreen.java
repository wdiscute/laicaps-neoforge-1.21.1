package com.wdiscute.laicaps.block.refuelstation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class RefuelStationScreen extends AbstractContainerScreen<RefuelStationMenu>
{
    private static final Random r = new Random();

    private static final ResourceLocation BACKGROUND = Laicaps.rl("textures/gui/refuel_station/background.png");

    private static final Logger log = LoggerFactory.getLogger(RefuelStationScreen.class);

    private static RefuelStationMenu menu;

    int uiX;
    int uiY;

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);


    }

    public RefuelStationScreen(RefuelStationMenu refuelStationMenu, Inventory playerInventory, Component title)
    {
        super(refuelStationMenu, playerInventory, title);
        menu = refuelStationMenu;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
