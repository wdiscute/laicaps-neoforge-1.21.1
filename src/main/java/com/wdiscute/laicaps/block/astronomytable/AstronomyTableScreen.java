package com.wdiscute.laicaps.block.astronomytable;

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


public class AstronomyTableScreen extends AbstractContainerScreen<AstronomyTableMenu>
{


    private static final Logger log = LoggerFactory.getLogger(AstronomyTableScreen.class);
    private static final ResourceLocation INV_BOOK_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/book_background.png");
    private static final ResourceLocation ARROW_PREVIOUS = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/arrow_previous.png");
    private static final ResourceLocation ARROW_NEXT = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/arrow_next.png");


    private static AstronomyTableMenu menu;

    Random r = new Random();

    int uiX;
    int uiY;
    int page1X;
    int page1Y;
    int page2X;
    int page2Y;

    int currentPage = 0;


    @Override
    protected void init()
    {
        super.init();

        imageWidth = 512;
        imageHeight = 256;
        uiX = (width - imageWidth) / 2;
        uiY = (height - imageHeight) / 2;
        page1X = uiX + 184;
        page1Y = uiY + 7;
        page2X = uiX + 352;
        page2Y = uiY + 7;
    }

    @Override
    protected void containerTick()
    {

    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, INV_BOOK_BACKGROUND);

        guiGraphics.blit(INV_BOOK_BACKGROUND, uiX, uiY, 0, 0, 512, 256, 512, 256);


        //render arrows above everything else
        if (currentPage != 30) guiGraphics.blit(ARROW_PREVIOUS, page1X, page1Y + 225, 0, 0, 23, 13, 23, 13);
        if (currentPage != 30) guiGraphics.blit(ARROW_NEXT, page2X + 120, page2Y + 225, 0, 0, 23, 13, 23, 13);

    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawString(this.font, Component.literal("test string that's very cool"), page1X, page1Y, 16250871);
        //guiGraphics.renderItem(ModBlocks.LUNARVEIL.toStack(), 100, 100);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }

    public AstronomyTableScreen(AstronomyTableMenu astronomyTableMenu, Inventory playerInventory, Component title)
    {
        super(astronomyTableMenu, playerInventory, title);
        menu = astronomyTableMenu;
    }


    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
