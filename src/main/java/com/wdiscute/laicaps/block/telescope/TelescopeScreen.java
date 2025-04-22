package com.wdiscute.laicaps.block.telescope;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class TelescopeScreen extends AbstractContainerScreen<TelescopeMenu>
{

    private static final ResourceLocation INV_AND_BORDER_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/inv_and_border_background.png");
    private static final ResourceLocation BLACK_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/telescope_black_background.png");

    private static final ResourceLocation BLACK_OVERLAY = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/black.png");

    private static final ResourceLocation STAR_1 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_1.png");
    private static final ResourceLocation STAR_2 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_2.png");
    private static final ResourceLocation STAR_3 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_3.png");
    private static final ResourceLocation STAR_4 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_4.png");
    private static final ResourceLocation STAR_5 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_5.png");
    private static final ResourceLocation STAR_6 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_6.png");

    private static final ResourceLocation STAR_DARK_1 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_dark_1.png");
    private static final ResourceLocation STAR_DARK_2 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_dark_2.png");
    private static final ResourceLocation STAR_DARK_3 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/star_dark_3.png");

    private static final ResourceLocation FROG = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/frog.png");
    private static final ResourceLocation FROG_2 = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/frog_2.png");

    private static final Logger log = LoggerFactory.getLogger(TelescopeScreen.class);

    private int counter = 0;
    private int scrollOffset = 0;

    Random r = new Random();

    int uiStartX = (width - imageWidth) / 2;
    int uiStartY = (height - imageHeight) / 2;
    int blackScreenX = uiStartX + 178;
    int blackScreenY = uiStartX + 3;

    int scrollBuffer = 0;
    int scrollBufferStrength = 0;

    List<Map.Entry<ResourceLocation, Vector2i>> starsBGList = new ArrayList<>();

    List<Map.Entry<ResourceLocation, Vector2i>> starsFGList = new ArrayList<>();
    List<ResourceLocation> starsRLBGList = new ArrayList<>();
    List<ResourceLocation> starsRLFGList = new ArrayList<>();

    TelescopeStarButton starButtonLeft;
    TelescopeStarButton starButtonRight;


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (Math.abs(scrollBuffer) < 100)
            scrollBuffer += ((int) scrollY) * 5;
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    protected void containerTick()
    {

        //scrolling logic
        {
            if (scrollOffset > 0)
            {
                scrollBuffer -= 7;
            }

            if (scrollOffset < -700)
            {
                scrollBuffer += 7;
            }

            scrollBufferStrength = 1;
            if (Math.abs(scrollBuffer) > 2) scrollBufferStrength = 2;
            if (Math.abs(scrollBuffer) > 10) scrollBufferStrength = 3;
            if (Math.abs(scrollBuffer) > 15) scrollBufferStrength = 4;
            if (Math.abs(scrollBuffer) > 20) scrollBufferStrength = 6;
            if (Math.abs(scrollBuffer) > 25) scrollBufferStrength = 8;
            if (Math.abs(scrollBuffer) > 30) scrollBufferStrength = 10;
            if (Math.abs(scrollBuffer) > 35) scrollBufferStrength = 13;
            if (Math.abs(scrollBuffer) > 40) scrollBufferStrength = 17;

            if (scrollBuffer != 0)
            {
                scrollOffset += (int) (Math.signum(scrollBuffer) * scrollBufferStrength);
                scrollBuffer += ((int) Math.signum(scrollBuffer)) * -1 * scrollBufferStrength;
            }
        }

        //initial setup and on screen size change
        if (counter == 0)
        {
            this.imageWidth = 512;
            this.imageHeight = 256;

            uiStartX = (width - imageWidth) / 2;
            uiStartY = (height - imageHeight) / 2;

            //add stars ResourceLocations to respective lists
            {
                starsRLBGList.add(STAR_1);
                starsRLBGList.add(STAR_2);
                starsRLBGList.add(STAR_3);
                starsRLBGList.add(STAR_4);
                starsRLBGList.add(STAR_5);
                starsRLBGList.add(STAR_6);
                starsRLFGList.add(STAR_DARK_1);
                starsRLFGList.add(STAR_DARK_2);
                starsRLFGList.add(STAR_DARK_3);
            }

            //add zoom in and zoom out button
            {
                starButtonLeft = this.addRenderableWidget(TelescopeStarButton.builder(button ->
                                scrollBuffer += (scrollOffset < -900 || scrollOffset > 200) ? 20 : 39)
                        .bounds(uiStartX + 14, uiStartY + 13, 20, 20).build());

                starButtonRight = this.addRenderableWidget(TelescopeStarButton.builder(button -> {})
                        .bounds(uiStartX + 136, uiStartY + 13, 20, 20).build());
            }


            if(renderables.get(0) instanceof TelescopeStarButton tsb)
            {
                tsb.setPosition(100, 100);
            }


            //create list of background stars
            int lastYOffset = r.nextInt(203);
            for (int i = 0; i < 1000; i++)
            {
                i += r.nextInt(10, 25);
                int attemptedOffset = r.nextInt(203);

                if (Math.abs(lastYOffset - attemptedOffset) > 40)
                {
                    lastYOffset = attemptedOffset;

                    starsBGList.add(new AbstractMap.SimpleEntry<>(starsRLBGList.get(r.nextInt(starsRLBGList.size())), new Vector2i(i, lastYOffset)));
                }
            }

            //create list of foreground stars
            for (int i = 0; i < 1000; i++)
            {
                i += r.nextInt(5, 100);
                System.out.println(i);
                starsFGList.add(new AbstractMap.SimpleEntry<>(starsRLFGList.get(r.nextInt(starsRLFGList.size())), new Vector2i(i, r.nextInt(236))));
            }

            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG, new Vector2i(-300, 105)));
            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG_2, new Vector2i(-260, 75)));


        }

        counter++;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

        this.imageWidth = 514;
        this.imageHeight = 256;

        uiStartX = (width - imageWidth) / 2;
        uiStartY = (height - imageHeight) / 2;
        blackScreenX = uiStartX + 178;
        blackScreenY = uiStartY + 3;

        guiGraphics.blit(BLACK_BACKGROUND, uiStartX, uiStartY, 1, 1, 512, 256, 512, 256);


        //renders stars background from list
        for (Map.Entry<ResourceLocation, Vector2i> entry : starsBGList)
        {
            //if entry is inside the borders of the black screen
            if (entry.getValue().x + scrollOffset < 285 && entry.getValue().x + scrollOffset > -10) guiGraphics.blit(
                    entry.getKey(),
                    blackScreenX + entry.getValue().x + ((int) scrollOffset),
                    blackScreenY + entry.getValue().y,
                    0, 0, 45, 45, 45, 45);
        }

        //render reveal around cursor
        {
            PoseStack poseStack = guiGraphics.pose();
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            poseStack.pushPose();
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

            List<Vector2f> positions = Lists.newArrayList(new Vector2f(mouseX, mouseY));
            List<Float> scales = Lists.newArrayList(0.09F);
            List<Float> noises = Lists.newArrayList(1F);

            poseStack.translate(0, 0, 0);

            RevealRenderUtil.renderRevealingPanel(poseStack, uiStartX + 178, uiStartY + 3, 331, 250, positions, scales, noises, 0);

            poseStack.popPose();
        }

        //renders stars foreground from list
        for (Map.Entry<ResourceLocation, Vector2i> entry : starsFGList)
        {
            //if entry is inside the borders of the black screen
            if (entry.getValue().x + scrollOffset < 326 && entry.getValue().x + scrollOffset > -60) guiGraphics.blit(
                    entry.getKey(),
                    blackScreenX + entry.getValue().x + ((int) scrollOffset),
                    blackScreenY + entry.getValue().y,
                    0, 0, 45, 45, 45, 45);
        }


        guiGraphics.blit(INV_AND_BORDER_BACKGROUND, uiStartX, uiStartY, 1, 1, 512, 256, 514, 257);


    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, INV_AND_BORDER_BACKGROUND);

        guiGraphics.drawString(this.font, Component.literal("test string that's very cool"), uiStartX, uiStartY, 16250871);
        guiGraphics.renderItem(ModBlocks.LUNARVEIL.toStack(), 100, 100);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }


    public TelescopeScreen(TelescopeMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }


    @Override
    public void resize(Minecraft minecraft, int width, int height)
    {
        counter = 0;
        super.resize(minecraft, width, height);
    }

    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
