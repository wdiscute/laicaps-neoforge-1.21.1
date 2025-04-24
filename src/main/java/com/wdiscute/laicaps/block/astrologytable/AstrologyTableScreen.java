package com.wdiscute.laicaps.block.astrologytable;

import com.google.common.collect.Lists;
import com.wdiscute.laicaps.block.telescope.TelescopeStarButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class AstrologyTableScreen extends AbstractContainerScreen<AstrologyTableMenu>
{


    private static final Logger log = LoggerFactory.getLogger(AstrologyTableScreen.class);


    private static AstrologyTableMenu menu;

    private int counter = 0;
    private int scrollOffset = 0;

    Random r = new Random();

    int uiX = (width - imageWidth) / 2;
    int uiY = (height - imageHeight) / 2;

    int currentPage = 0;


    @Override
    protected void init()
    {

    }

    @Override
    protected void containerTick()
    {
        System.out.println("works");
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {

    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawString(this.font, Component.literal("test string that's very cool"), 500, 500, 16250871);
        //guiGraphics.renderItem(ModBlocks.LUNARVEIL.toStack(), 100, 100);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }

    public AstrologyTableScreen(AstrologyTableMenu astrologyNotebookMenu, Inventory playerInventory, Component title)
    {
        super(astrologyNotebookMenu, playerInventory, title);
        menu = astrologyNotebookMenu;
    }


    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
