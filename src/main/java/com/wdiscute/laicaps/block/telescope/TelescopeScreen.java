package com.wdiscute.laicaps.block.telescope;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
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
    private static final ResourceLocation SKY_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/sky_background.png");
    private static final ResourceLocation BLACK_OVERLAY = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/black.png");
    private static final ResourceLocation LENSES = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/lenses.png");

    private static final ResourceLocation SUN = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/sun.png");
    private static final ResourceLocation EMBER = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/ember.png");
    private static final ResourceLocation OVERWORLD = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/overworld.png");
    private static final ResourceLocation ASHA = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/asha.png");
    private static final ResourceLocation LUNAMAR = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/telescope/lunamar.png");


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

    int state = 0;

    List<Vector2f> borderOfOcclusion = Lists.newArrayList();

    List<Map.Entry<ResourceLocation, Vector2i>> planetsList = new ArrayList<>();

    List<Map.Entry<ResourceLocation, Vector2i>> starsBGList = new ArrayList<>();
    List<Map.Entry<ResourceLocation, Vector2i>> starsFGList = new ArrayList<>();

    List<ResourceLocation> starsRLBGList = new ArrayList<>();
    List<ResourceLocation> starsRLFGList = new ArrayList<>();

    TelescopeStarButton starButtonLeft;
    TelescopeStarButton starButtonRight;

    @Override
    protected void init()
    {
        super.init();

        //size of "canvas"
        this.imageWidth = 512;
        this.imageHeight = 256;

        uiStartX = (width - imageWidth) / 2;
        uiStartY = (height - imageHeight) / 2;
        blackScreenX = uiStartX + 178;
        blackScreenY = uiStartY + 3;

        //set borderOfOcclusion
        {
            //north
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiStartX + 180 + (i * 20), uiStartY - 43));
            //south
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiStartX + 180 + (i * 20), uiStartY + 299));
            //west
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiStartX + 132, uiStartY - 40 + (i * 20)));
            //east
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiStartX + 555, uiStartY - 40 + (i * 20)));
        }

        //add zoom in and zoom out button
        {
            starButtonLeft = this.addRenderableWidget(TelescopeStarButton.builder(button ->
                            scrollBuffer += (scrollOffset < -900 || scrollOffset > 200) ? 20 : 39)
                    .bounds(uiStartX + 14, uiStartY + 13, 20, 20).build());

            starButtonRight = this.addRenderableWidget(TelescopeStarButton.builder(button ->
                            scrollBuffer -= (scrollOffset < -900 || scrollOffset > 200) ? 20 : 39)
                    .bounds(uiStartX + 136, uiStartY + 13, 20, 20).build());
        }

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

        //create list of background and foreground stars
        {
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

            for (int i = 0; i < 1000; i++)
            {
                i += r.nextInt(40, 220);
                starsFGList.add(new AbstractMap.SimpleEntry<>(starsRLFGList.get(r.nextInt(starsRLFGList.size())), new Vector2i(i, r.nextInt(236))));
            }

            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG, new Vector2i(-300, 105)));
            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG_2, new Vector2i(-260, 75)));
        }

        //create list of planets
        {
            planetsList.add(new AbstractMap.SimpleEntry<>(EMBER, new Vector2i(120, 95)));
            planetsList.add(new AbstractMap.SimpleEntry<>(ASHA, new Vector2i(240, 95)));
            planetsList.add(new AbstractMap.SimpleEntry<>(OVERWORLD, new Vector2i(360, 95)));
            planetsList.add(new AbstractMap.SimpleEntry<>(LUNAMAR, new Vector2i(480, 95)));
        }


    }

    @Override
    protected void containerTick()
    {

        //close if screen resized with a 1 tick delay to fix that one bug that was weird and not cool :(
        if (counter == -1) onClose();
        counter++;

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

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);


        //scroll by hovering the sides
        if (mouseX - uiStartX > 175 && mouseX - uiStartX < 200 && mouseY - uiStartY > 3 && mouseY - uiStartY < 252 && scrollOffset < 0)
            scrollBuffer = 25;
        if (mouseX - uiStartX > 487 && mouseX - uiStartX < 517 && mouseY - uiStartY > 3 && mouseY - uiStartY < 252 && scrollOffset > -700)
            scrollBuffer = -25;

        if (state == 0)
        {
            //renders purple flat sky background
            guiGraphics.blit(SKY_BACKGROUND, uiStartX, uiStartY, 1, 1, 512, 256, 512, 256);

            //renders stars background from list
            for (Map.Entry<ResourceLocation, Vector2i> entry : starsBGList)
            {
                //if entry is inside the borders of the black screen
                if (entry.getValue().x + scrollOffset < 285 && entry.getValue().x + scrollOffset > -10)
                    guiGraphics.blit(
                            entry.getKey(),
                            blackScreenX + entry.getValue().x + scrollOffset,
                            blackScreenY + entry.getValue().y,
                            0, 0, 45, 45, 45, 45);
            }


            //render sun with occlusion
            if (100 + scrollOffset < 326 && 100 + scrollOffset > -10)
            {
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, SUN);

                List<Float> scales = Lists.newArrayList();
                for (int i = 0; i < borderOfOcclusion.size(); i++) scales.add(0.5f);

                RevealRenderUtil.renderWithOcclusion(poseStack, blackScreenX - 50 + (scrollOffset), blackScreenY + 69, 90, 90, borderOfOcclusion, scales);

                poseStack.popPose();
            }


            //renders planets list with occlusion
            for (Map.Entry<ResourceLocation, Vector2i> entry : planetsList)
            {
                if (entry.getValue().x + scrollOffset < 326 && entry.getValue().x + scrollOffset > -120)
                {
                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();

                    RenderSystem.setShaderTexture(0, entry.getKey());

                    List<Float> scales = Lists.newArrayList();
                    for (int i = 0; i < borderOfOcclusion.size(); i++) scales.add(0.5f);

                    RevealRenderUtil.renderWithOcclusion(poseStack, blackScreenX + entry.getValue().x + (scrollOffset), blackScreenY + entry.getValue().y, 90, 90, borderOfOcclusion, scales);

                    poseStack.popPose();
                }

            }

            //render inventory overlay
            guiGraphics.blit(INV_AND_BORDER_BACKGROUND, uiStartX, uiStartY, 0, 0, 512, 256, 514, 257);


        }


        //2 = planet searching
        if (state == 2)
        {
            //renders purple flat sky background
            guiGraphics.blit(SKY_BACKGROUND, uiStartX, uiStartY, 1, 1, 512, 256, 512, 256);

            //renders stars background from list
            for (Map.Entry<ResourceLocation, Vector2i> entry : starsBGList)
            {
                //if entry is inside the borders of the black screen
                if (entry.getValue().x + scrollOffset < 285 && entry.getValue().x + scrollOffset > -10)
                    guiGraphics.blit(
                            entry.getKey(),
                            blackScreenX + entry.getValue().x + scrollOffset,
                            blackScreenY + entry.getValue().y,
                            0, 0, 45, 45, 45, 45);
            }

            //render BLACK_OVERLAY with occlusion around cursor
            {
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(poseStack, uiStartX + 178, uiStartY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(0.08f));

                poseStack.popPose();
            }

            //renders stars foreground from list
            for (Map.Entry<ResourceLocation, Vector2i> entry : starsFGList)
            {
                //if entry is inside the borders of the black screen
                if (entry.getValue().x + scrollOffset < 326 && entry.getValue().x + scrollOffset > -60)
                    guiGraphics.blit(
                            entry.getKey(),
                            blackScreenX + entry.getValue().x + ((int) scrollOffset),
                            blackScreenY + entry.getValue().y,
                            0, 0, 45, 45, 45, 45);
            }

            //render lenses with occlusion outside the correct area
            if (mouseX - uiStartX > 140 && mouseX - uiStartX < 540 && mouseY - uiStartY > -30 && mouseY - uiStartY < 280)
            {
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, LENSES);

                List<Float> scales = Lists.newArrayList();
                for (int i = 0; i < borderOfOcclusion.size(); i++) scales.add(0.5f);

                RevealRenderUtil.renderWithOcclusion(poseStack, mouseX - 45, mouseY - 45, 90, 90, borderOfOcclusion, scales);

                poseStack.popPose();
            }

            //render inventory overlay
            guiGraphics.blit(INV_AND_BORDER_BACKGROUND, uiStartX, uiStartY, 0, 0, 512, 256, 514, 257);

        }


    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        //guiGraphics.drawString(this.font, Component.literal("test string that's very cool"), uiStartX, uiStartY, 16250871);
        //guiGraphics.renderItem(ModBlocks.LUNARVEIL.toStack(), 100, 100);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (Math.abs(scrollBuffer) < 100)
            scrollBuffer += ((int) scrollY) * 9;
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    public TelescopeScreen(TelescopeMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height)
    {
        //TODO FIX BAND-AID FIX SO SCREEN DOESN'T GO OUT OF BOUNDS FOR SOME REASON
        counter = -2;
        super.resize(minecraft, width, height);
    }

    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
