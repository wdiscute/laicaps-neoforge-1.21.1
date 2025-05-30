package com.wdiscute.laicaps.block.telescope;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
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
    private static final Random r = new Random();

    private static final ResourceLocation INV_AND_BORDER_BACKGROUND = Laicaps.rl("textures/gui/telescope/inv_and_border_background.png");
    private static final ResourceLocation SKY_BACKGROUND = Laicaps.rl("textures/gui/telescope/sky_background.png");
    private static final ResourceLocation BLACK_OVERLAY = Laicaps.rl("textures/gui/telescope/black.png");
    private static final ResourceLocation LENSES = Laicaps.rl("textures/gui/telescope/lenses.png");
    private static final ResourceLocation ZOOM_BACKGROUND = Laicaps.rl("textures/gui/telescope/zoom_background.png");
    private static final ResourceLocation ZOOM_NOTCH = Laicaps.rl("textures/gui/telescope/zoom_notch.png");
    private static final ResourceLocation PLANET_SCREEN_BACKGROUND = Laicaps.rl("textures/gui/telescope/planet_screen_background.png");
    private static final ResourceLocation NO_BOOK_SCREEN_BACKGROUND = Laicaps.rl("textures/gui/telescope/no_book_screen_background.png");

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

    private static final ResourceLocation EMBER_SEARCH = Laicaps.rl("textures/gui/telescope/ember_search.png");
    private static final ResourceLocation ASHA_SEARCH = Laicaps.rl("textures/gui/telescope/asha_search.png");
    private static final ResourceLocation LUNAMAR_SEARCH = Laicaps.rl("textures/gui/telescope/lunamar_search.png");
    private static ResourceLocation planet_being_searched;
    private static int planet_being_searched_x;
    private static int planet_being_searched_y;

    private static final ResourceLocation STAR_1 = Laicaps.rl("textures/gui/telescope/star_1.png");
    private static final ResourceLocation STAR_2 = Laicaps.rl("textures/gui/telescope/star_2.png");
    private static final ResourceLocation STAR_3 = Laicaps.rl("textures/gui/telescope/star_3.png");
    private static final ResourceLocation STAR_4 = Laicaps.rl("textures/gui/telescope/star_4.png");
    private static final ResourceLocation STAR_5 = Laicaps.rl("textures/gui/telescope/star_5.png");
    private static final ResourceLocation STAR_6 = Laicaps.rl("textures/gui/telescope/star_6.png");
    private static final List<ResourceLocation> starsRLBGList = Arrays.asList(STAR_1, STAR_2, STAR_3, STAR_4, STAR_5, STAR_6);

    private static final ResourceLocation STAR_DARK_1 = Laicaps.rl("textures/gui/telescope/star_dark_1.png");
    private static final ResourceLocation STAR_DARK_2 = Laicaps.rl("textures/gui/telescope/star_dark_2.png");
    private static final ResourceLocation STAR_DARK_3 = Laicaps.rl("textures/gui/telescope/star_dark_3.png");
    private static final List<ResourceLocation> starsRLFGList = Arrays.asList(STAR_DARK_1, STAR_DARK_2, STAR_DARK_3);

    private static final ResourceLocation FROG = Laicaps.rl("textures/gui/telescope/frog.png");
    private static final ResourceLocation FROG_2 = Laicaps.rl("textures/gui/telescope/frog_2.png");

    private static final Logger log = LoggerFactory.getLogger(TelescopeScreen.class);


    private static TelescopeMenu menu;

    private int counter = 0;
    private boolean transitionSearch;
    private boolean transitionMenu;
    private int scrollOffset = 0;
    private int max_scrollOffset = 700;

    int uiX;
    int uiY;
    int canvasX;
    int canvasY;

    int scrollBuffer = 0;
    int scrollBufferStrength = 0;

    int state = 0;

    private List<Vector2f> borderOfOcclusion = Lists.newArrayList();

    private int planetSelected = 0;

    private List<Map.Entry<ResourceLocation, Vector2i>> starsBGList = new ArrayList<>();
    private List<Map.Entry<ResourceLocation, Vector2i>> starsFGList = new ArrayList<>();

    boolean emberDiscovered;
    int emberEntries;
    boolean ashaDiscovered;
    int ashaEntries;
    boolean lunamarDiscovered;
    int lunamarEntries;

    @Override
    protected void init()
    {
        state = 1;
        super.init();

        ClientAdvancements adv = Minecraft.getInstance().getConnection().getAdvancements();

        emberDiscovered = AdvHelper.hasAdvancement(adv, "ember_discovered");
        ashaDiscovered = AdvHelper.hasAdvancement(adv, "asha_discovered");
        lunamarDiscovered = AdvHelper.hasAdvancement(adv, "lunamar_discovered");

        emberEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "ember_entries");
        ashaEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "asha_entries");
        lunamarEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "lunamar_entries");

        //set size and x/y's
        {
            this.imageWidth = 512;
            this.imageHeight = 256;

            uiX = (width - imageWidth) / 2;
            uiY = (height - imageHeight) / 2;
            canvasX = uiX + 178;
            canvasY = uiY + 3;
        }

        //set borderOfOcclusion
        {
            //north
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiX + 180 + (i * 20), uiY - 43));
            //south
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiX + 180 + (i * 20), uiY + 299));
            //west
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiX + 132, uiY - 40 + (i * 20)));
            //east
            for (int i = 0; i < 18; i++)
                borderOfOcclusion.add(new Vector2f(uiX + 555, uiY - 40 + (i * 20)));
        }

        //create list of background and foreground stars
        {
            int lastYOffset = r.nextInt(203);
            for (int i = 0; i < max_scrollOffset + 300; i++)
            {
                i += r.nextInt(10, 25);
                int attemptedOffset = r.nextInt(203);
                if (Math.abs(lastYOffset - attemptedOffset) > 40)
                {
                    lastYOffset = attemptedOffset;
                    starsBGList.add(new AbstractMap.SimpleEntry<>(starsRLBGList.get(r.nextInt(starsRLBGList.size())), new Vector2i(i, lastYOffset)));
                }
            }

            for (int i = 0; i < max_scrollOffset + 300; i++)
            {
                i += r.nextInt(40, 220);
                starsFGList.add(new AbstractMap.SimpleEntry<>(starsRLFGList.get(r.nextInt(starsRLFGList.size())), new Vector2i(i, r.nextInt(236))));
            }

            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG, new Vector2i(-300, 105)));
            starsFGList.add(new AbstractMap.SimpleEntry<>(FROG_2, new Vector2i(-260, 75)));
        }
    }

    private void updateAdvancements()
    {
        ClientAdvancements adv = Minecraft.getInstance().getConnection().getAdvancements();

        emberDiscovered = AdvHelper.hasAdvancement(adv, "ember_discovered");
        ashaDiscovered = AdvHelper.hasAdvancement(adv, "asha_discovered");
        lunamarDiscovered = AdvHelper.hasAdvancement(adv, "lunamar_discovered");

        emberEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "ember_entries");
        ashaEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "asha_entries");
        lunamarEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "lunamar_entries");
    }

    @Override
    protected void containerTick()
    {
        updateAdvancements();
        ClientLevel level = Minecraft.getInstance().level;

        int numberOfDays = (int) (level.getDayTime() / 24000f);

        if (!(level.getDayTime() - (numberOfDays * 24000L) > 14000 && level.getDayTime() - (numberOfDays * 24000L) < 23000))
            this.onClose();


        //close if screen resized with a 1 tick delay to fix that one bug that was weird and not cool :(
        if (counter == -1) onClose();
        counter++;

        //scrolling logic
        {
            if (state == 1)
            {
                scrollBuffer = 0;
                scrollBufferStrength = 0;
            }

            if (state == 2)
            {

                if (scrollOffset > 0)
                {
                    scrollBuffer -= 7;
                }

                if (scrollOffset < -max_scrollOffset)
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

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (transitionSearch) return true;
        if (transitionMenu) return true;
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        if (state == 1)
        {
            //ember
            if (x > 250 && x < 283 && y > 170 && y < 200 && !emberDiscovered && menu.blockstate.getValue(TelescopeBlock.ADVANCED))
            {
                counter = 0;
                planetSelected = 1;
                planet_being_searched = EMBER_SEARCH;
                planet_being_searched_x = r.nextInt(max_scrollOffset + 200) + 20;
                planet_being_searched_y = r.nextInt(180) + 17;
                scrollOffset = -max_scrollOffset / 2;
                transitionSearch = true;
            }

            //asha
            if (x > 310 && x < 335 && y > 86 && y < 111 && !ashaDiscovered)
            {
                counter = 0;
                planetSelected = 2;
                planet_being_searched = ASHA_SEARCH;
                planet_being_searched_x = r.nextInt(max_scrollOffset + 200) + 20;
                planet_being_searched_y = r.nextInt(180) + 17;
                scrollOffset = -max_scrollOffset / 2;
                transitionSearch = true;
            }

            //lunamar
            if (x > 444 && x < 589 && y > 16 && y < 65 && !lunamarDiscovered && menu.blockstate.getValue(TelescopeBlock.ADVANCED))
            {
                counter = 0;
                planetSelected = 4;
                planet_being_searched = LUNAMAR_SEARCH;
                planet_being_searched_x = r.nextInt(max_scrollOffset + 200) + 20;
                planet_being_searched_y = r.nextInt(180) + 17;
                scrollOffset = -max_scrollOffset / 2;
                transitionSearch = true;
            }
        }

        if (state == 2)
        {

            if (Math.abs((canvasX + planet_being_searched_x + scrollOffset + 22) - mouseX) < 15 && Math.abs((canvasY + planet_being_searched_y + 22) - mouseY) < 15)
            {
                transitionMenu = true;
                counter = 0;
            }

        }

        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

        //no book in slot
        if (state == 0)
        {
            //renders BLACK OVERLAY sky background
            renderImage(guiGraphics, NO_BOOK_SCREEN_BACKGROUND);

            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

        }

        //planet selection screen
        if (state == 1)
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

            //render transition - black screen closing in
            if (transitionSearch)
            {
                if (counter >= 30)
                {
                    state = 2;
                    counter = 0;
                }

                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, uiX + 178, uiY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(((20 - counter - partialTick) / 1f * 0.08f)));

                poseStack.popPose();
            }

            //render transitionScreen - black screen opening
            if (transitionMenu)
            {
                if (counter >= 30)
                {
                    transitionMenu = false;
                }

                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, uiX + 178, uiY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(((counter + partialTick) / 1f * 0.08f)));

                poseStack.popPose();
            }

            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

            //render tooltip
            {
                List<Component> tooltips;

                //ember with basic telescope
                if (x > 250 && x < 283 && y > 170 && y < 200 && !menu.blockstate.getValue(TelescopeBlock.ADVANCED))
                {
                    tooltips = emberDiscovered ? tooltipHelper("ember") : tooltipHelper("ember_blur", true);
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //ember with advanced telescope
                if (x > 250 && x < 283 && y > 170 && y < 200 && menu.blockstate.getValue(TelescopeBlock.ADVANCED))
                {
                    tooltips = emberDiscovered ? tooltipHelper("ember") : tooltipHelper("ember_blur");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //asha
                if (x > 310 && x < 335 && y > 86 && y < 111)
                {
                    tooltips = ashaDiscovered ? tooltipHelper("asha") : tooltipHelper("asha_blur");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //overworld
                if (x > 392 && x < 429 && y > 121 && y < 160)
                {
                    tooltips = tooltipHelper("overworld");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //lunamar
                if (x > 444 && x < 589 && y > 16 && y < 65 && !menu.blockstate.getValue(TelescopeBlock.ADVANCED))
                {
                    tooltips = lunamarDiscovered ? tooltipHelper("lunamar") : tooltipHelper("lunamar_blur", true);
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }

                //lunamar
                if (x > 444 && x < 589 && y > 16 && y < 65 && menu.blockstate.getValue(TelescopeBlock.ADVANCED))
                {
                    tooltips = lunamarDiscovered ? tooltipHelper("lunamar") : tooltipHelper("lunamar_blur");
                    guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY);
                }


            }
        }

        //2 = planet searching
        if (state == 2)
        {

            //renders purple flat sky background
            renderImage(guiGraphics, SKY_BACKGROUND);

            //renders stars background from list
            for (Map.Entry<ResourceLocation, Vector2i> entry : starsBGList)
            {
                //if entry is inside the borders of the black screen
                if (entry.getValue().x + scrollOffset < 285 && entry.getValue().x + scrollOffset > -10)
                    guiGraphics.blit(
                            entry.getKey(),
                            canvasX + entry.getValue().x + scrollOffset,
                            canvasY + entry.getValue().y,
                            0, 0, 45, 45, 45, 45);
            }

            //render planet
            if (planet_being_searched_x + scrollOffset < 285 && planet_being_searched_x + scrollOffset > -50)
                guiGraphics.blit(
                        planet_being_searched,
                        canvasX + planet_being_searched_x + scrollOffset,
                        canvasY + planet_being_searched_y,
                        0, 0, 45, 45, 45, 45);

            //render BLACK_OVERLAY with occlusion around cursor
            {
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, uiX + 178, uiY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(0.08f));

                poseStack.popPose();
            }

            //renders stars foreground from list
            for (Map.Entry<ResourceLocation, Vector2i> entry : starsFGList)
            {
                //if entry is inside the borders of the black screen
                if (entry.getValue().x + scrollOffset < 326 && entry.getValue().x + scrollOffset > -10)
                    guiGraphics.blit(
                            entry.getKey(),
                            canvasX + entry.getValue().x + ((int) scrollOffset),
                            canvasY + entry.getValue().y,
                            0, 0, 45, 45, 45, 45);
            }

            //render lenses with occlusion outside the correct area
            if (mouseX - uiX > 140 && mouseX - uiX < 540 && mouseY - uiY > -30 && mouseY - uiY < 280)
            {
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, LENSES);

                List<Float> scales = Lists.newArrayList();
                for (int i = 0; i < borderOfOcclusion.size(); i++) scales.add(0.5f);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, mouseX - 45, mouseY - 45, 90, 90,
                        borderOfOcclusion, scales);

                poseStack.popPose();
            }

            //render zoom bar
            renderImage(guiGraphics, ZOOM_BACKGROUND);

            //render notch
            {
                int notchX = ((int) ((scrollOffset + 350) / 6f)) * -1;
                if (notchX < -55) notchX = -55;
                if (notchX > 55) notchX = 55;
                guiGraphics.blit(
                        ZOOM_NOTCH,
                        uiX + notchX,
                        uiY,
                        0, 0, 512, 256, 512, 256);
            }

            //render transitionScreen
            if (transitionSearch)
            {
                if (counter >= 50)
                {
                    transitionSearch = false;
                }
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, uiX + 178, uiY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(((counter + partialTick) / 2 * 0.08f)));

                poseStack.popPose();
            }

            //render transitionMenu
            if (transitionMenu)
            {

                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();

                RenderSystem.setShaderTexture(0, BLACK_OVERLAY);

                RevealRenderUtil.renderWithOcclusion(
                        poseStack, uiX + 178, uiY + 3, 331, 250,
                        Lists.newArrayList(new Vector2f(mouseX, mouseY)),
                        Lists.newArrayList(((20 - counter - partialTick) / 2 * 0.08f)));

                poseStack.popPose();

                if (counter >= 30)
                {
                    state = 1;
                    counter = 0;
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, planetSelected);
                    planetSelected = 0;
                }
            }


            //render inventory overlay
            renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

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

    private List<Component> tooltipHelper(String input)
    {
        return tooltipHelper(input, false);
    }

    private List<Component> tooltipHelper(String input, boolean showNeedsAdvanced)
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
                list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.travel"));
                if (emberEntries < Laicaps.EMBER_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(input, "asha"))
            {
                list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.travel"));
                if (ashaEntries < Laicaps.ASHA_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(input, "overworld"))
            {
                list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.travel"));
            }

            if (Objects.equals(input, "lunamar"))
            {
                list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.travel"));
                if (lunamarEntries < Laicaps.LUNAMAR_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }
        }

        //add .stargaze and .advanced translation key at the end for blurred planets
        if (showNeedsAdvanced)
            for (int i = 0; i < 100; i++)
                if (I18n.exists("gui.laicaps.telescope.tooltip.generic.advanced." + i))
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.advanced." + i));
                else break;
        else if (Objects.equals(input, "ember_blur") || Objects.equals(input, "asha_blur") || Objects.equals(input, "lunamar_blur"))
            list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.stargaze"));

        return list;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (Math.abs(scrollBuffer) < 100)
            scrollBuffer += ((int) scrollY) * 9;
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    public TelescopeScreen(TelescopeMenu telescopeMenu, Inventory playerInventory, Component title)
    {
        super(telescopeMenu, playerInventory, title);
        menu = telescopeMenu;
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
