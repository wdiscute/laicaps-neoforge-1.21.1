package com.wdiscute.laicaps.block.astronomytable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
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

import java.util.*;


public class AstronomyTableScreen extends AbstractContainerScreen<AstronomyTableMenu>
{

    private static final Logger log = LoggerFactory.getLogger(AstronomyTableScreen.class);
    private static final ResourceLocation INV_BOOK_BACKGROUND = Laicaps.rl("textures/gui/astronomy_table/book_background.png");
    private static final ResourceLocation BOOKMARK_BACKGROUND = Laicaps.rl("textures/gui/astronomy_table/bookmark_background.png");
    private static final ResourceLocation BOOKMARK = Laicaps.rl("textures/gui/astronomy_table/bookmark.png");
    private static final ResourceLocation ARROW_PREVIOUS = Laicaps.rl("textures/gui/astronomy_table/arrow_previous.png");
    private static final ResourceLocation ARROW_NEXT = Laicaps.rl("textures/gui/astronomy_table/arrow_next.png");

    private static final ResourceLocation EMBER_SELECTED = Laicaps.rl("textures/gui/astronomy_table/ember_selected.png");
    private static final ResourceLocation ASHA_SELECTED = Laicaps.rl("textures/gui/astronomy_table/asha_selected.png");
    private static final ResourceLocation OVERWORLD_SELECTED = Laicaps.rl("textures/gui/astronomy_table/overworld_selected.png");
    private static final ResourceLocation LUNAMAR_SELECTED = Laicaps.rl("textures/gui/astronomy_table/lunamar_selected.png");

    private static final ResourceLocation EMBER_HIGHLIGHT = Laicaps.rl("textures/gui/astronomy_table/ember_highlight.png");
    private static final ResourceLocation ASHA_HIGHLIGHT = Laicaps.rl("textures/gui/astronomy_table/asha_highlight.png");
    private static final ResourceLocation OVERWORLD_HIGHLIGHT = Laicaps.rl("textures/gui/astronomy_table/overworld_highlight.png");
    private static final ResourceLocation LUNAMAR_HIGHLIGHT = Laicaps.rl("textures/gui/astronomy_table/lunamar_highlight.png");


    List<String> bookmarks = new ArrayList<>();

    Random r = new Random();

    int uiX;
    int uiY;

    int currentEntry = 1;
    int currentPlanet = 0;

    List<String> obfuscatedLeft = new ArrayList<>();
    List<String> obfuscatedRight = new ArrayList<>();

    private void reObfuscate()
    {
        //generate list of strings with random amounts of characters for the obfuscated entries
        minecraft.player.playSound(SoundEvents.BOOK_PAGE_TURN);
        obfuscatedLeft.clear();
        obfuscatedRight.clear();
        //left
        for (int i = 0; i < 19; i++)
        {
            if (r.nextFloat(1f) < 0.8)
            {
                String s = "";
                for (int j = 0; j < r.nextInt(30, 55); j++)
                    s = r.nextFloat() < 0.85 ? s.concat("|") : s.concat(" ");

                obfuscatedLeft.add(s);
            }
            else
            {
                obfuscatedLeft.add("");
            }
        }
        //right
        for (int i = 0; i < 19; i++)
        {
            if (r.nextFloat(1f) < 0.8)
            {
                String s = "";
                for (int j = 0; j < r.nextInt(30, 55); j++)
                    s = r.nextFloat() < 0.85 ? s.concat("|") : s.concat(" ");

                obfuscatedRight.add(s);
            }
            else
            {
                obfuscatedRight.add("");
            }
        }


    }

    @Override
    protected void init()
    {
        super.init();

        imageWidth = 512;
        imageHeight = 256;
        uiX = (width - imageWidth) / 2;
        uiY = (height - imageHeight) / 2;
        reObfuscate();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //previous arrow
        if (x > 68 && x < 105 && y > 230 && y < 240)
        {

            if(currentEntry == 1 && currentPlanet == 0)
            {
                return false;
            }

            if (currentEntry > 0)
            {
                currentEntry--;
                reObfuscate();

                if(currentEntry == 0)
                {
                    if (currentPlanet == 1)
                    {
                        currentPlanet--;
                        currentEntry = Laicaps.MENU_ENTRIES;
                        reObfuscate();
                        return false;
                    }

                    if (currentPlanet == 2)
                    {
                        currentPlanet--;
                        currentEntry = Laicaps.EMBER_ENTRIES;
                        reObfuscate();
                        return false;
                    }

                    if (currentPlanet == 3)
                    {
                        currentPlanet--;
                        currentEntry = Laicaps.ASHA_ENTRIES;
                        reObfuscate();
                        return false;
                    }

                    if (currentPlanet == 4)
                    {
                        currentPlanet--;
                        currentEntry = Laicaps.OVERWORLD_ENTRIES;
                        reObfuscate();
                        return false;
                    }
                }
                return false;
            }




        }

        //next arrow
        if (x > 420 && x < 440 && y > 230 && y < 240)
        {
            //menu
            if (currentPlanet == 0)
                if (currentEntry < Laicaps.MENU_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                }
                else
                {
                    currentEntry = 1;
                    currentPlanet++;
                    reObfuscate();
                    return false;
                }

            //ember
            if (currentPlanet == 1)
                if (currentEntry < Laicaps.EMBER_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                }
                else
                {
                    currentEntry = 1;
                    currentPlanet++;
                    reObfuscate();
                    return false;
                }

            //asha
            if (currentPlanet == 2)
                if (currentEntry < Laicaps.ASHA_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                }
                else
                {
                    currentEntry = 1;
                    currentPlanet++;
                    reObfuscate();
                    return false;
                }

            //overworld
            if (currentPlanet == 3)
                if (currentEntry < Laicaps.OVERWORLD_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                }
                else
                {
                    currentEntry = 1;
                    currentPlanet++;
                    reObfuscate();
                    return false;
                }

            //lunamar
            if (currentPlanet == 4)
                if (currentEntry < Laicaps.LUNAMAR_ENTRIES)
                {
                    reObfuscate();
                    currentEntry++;
                }


        }

        //ember lil planet
        if (x > 308 && x < 323 && y > 226 && y < 241)
        {
            reObfuscate();
            currentPlanet = 1;
            currentEntry = 1;
        }
        //asha lil planet
        if (x > 335 && x < 350 && y > 226 && y < 241)
        {
            reObfuscate();
            currentPlanet = 2;
            currentEntry = 1;
        }
        //overworld lil planet
        if (x > 362 && x < 377 && y > 226 && y < 241)
        {
            reObfuscate();
            currentPlanet = 3;
            currentEntry = 1;
        }
        //lunamar lil planet
        if (x > 389 && x < 404 && y > 226 && y < 241)
        {
            reObfuscate();
            currentPlanet = 4;
            currentEntry = 1;
        }


        //save bookmark
        if (x > 277 && x < 298 && y > 3 && y < 29)
        {
            int entry = currentEntry;
            if (currentPlanet == 1) entry = Laicaps.MENU_ENTRIES + currentEntry;
            if (currentPlanet == 2) entry = Laicaps.MENU_ENTRIES + Laicaps.EMBER_ENTRIES + currentEntry;
            if (currentPlanet == 3)
                entry = Laicaps.MENU_ENTRIES + Laicaps.EMBER_ENTRIES + Laicaps.ASHA_ENTRIES + currentEntry;
            if (currentPlanet == 4)
                entry = Laicaps.MENU_ENTRIES + Laicaps.EMBER_ENTRIES + Laicaps.ASHA_ENTRIES + Laicaps.OVERWORLD_ENTRIES + currentEntry;

            minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, entry);

        }

        for (int i = 0; i < 10; i++)
        {
            if (bookmarks.size() < i) break;

            if (x > 31 && x < 59 && y > 7 + (i * 26) && y < 31 + (i * 26))
            {

                String s = bookmarks.get(i);

                if (s.contains("menu")) currentPlanet = 0;
                if (s.contains("ember")) currentPlanet = 1;
                if (s.contains("asha")) currentPlanet = 2;
                if (s.contains("overworld")) currentPlanet = 3;
                if (s.contains("lunamar")) currentPlanet = 4;


                currentEntry = Integer.parseInt(bookmarks.get(i).substring(bookmarks.get(i).indexOf("_") + 1));
            }
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
        ClientAdvancements adv = Minecraft.getInstance().getConnection().getAdvancements();
        boolean entryUnlocked = false;
        String currentPlanetString = "menu";
        if (currentPlanet == 0) entryUnlocked = true;
        if (currentPlanet == 1) currentPlanetString = "ember";
        if (currentPlanet == 2) currentPlanetString = "asha";
        if (currentPlanet == 3) currentPlanetString = "overworld";
        if (currentPlanet == 4) currentPlanetString = "lunamar";

        //check if entry is unlocked
        for (String s : AdvHelper.getEntriesCompletedAsIterable(adv, currentPlanetString + "_entries"))
            if (s.contains("entry" + currentEntry)) entryUnlocked = true;

        renderImage(guiGraphics, INV_BOOK_BACKGROUND);

        if (currentPlanet == 1) renderImage(guiGraphics, EMBER_SELECTED);
        if (currentPlanet == 2) renderImage(guiGraphics, ASHA_SELECTED);
        if (currentPlanet == 3) renderImage(guiGraphics, OVERWORLD_SELECTED);
        if (currentPlanet == 4) renderImage(guiGraphics, LUNAMAR_SELECTED);


        if (x > 308 && x < 323 && y > 226 && y < 241) renderImage(guiGraphics, EMBER_HIGHLIGHT);
        if (x > 335 && x < 350 && y > 226 && y < 241) renderImage(guiGraphics, ASHA_HIGHLIGHT);
        if (x > 362 && x < 377 && y > 226 && y < 241) renderImage(guiGraphics, OVERWORLD_HIGHLIGHT);
        if (x > 389 && x < 404 && y > 226 && y < 241) renderImage(guiGraphics, LUNAMAR_HIGHLIGHT);


        //render arrows above everything else
        if (!(currentPlanet == 0 && currentEntry == 1))
            guiGraphics.blit(ARROW_PREVIOUS, uiX + 65, uiY + 227, 0, 0, 23, 13, 23, 13);
        if (!(currentPlanet == 4 && currentEntry == Laicaps.LUNAMAR_ENTRIES))
            guiGraphics.blit(ARROW_NEXT, uiX + 420, uiY + 227, 0, 0, 23, 13, 23, 13);


        //render page index at the bottom
        {
            if (entryUnlocked && currentPlanet != 0)
            {
                guiGraphics.drawString(
                        this.font, Component.translatable("gui.astronomy_research_table." + currentPlanetString + ".entry" + currentEntry + ".name"),
                        uiX + 90, uiY + 230, 0, false);
            }
            else
            {
                String s = "";
                if (currentPlanet == 1) s = "      §6Ember§r - §c§k!!!!!!!!!";
                if (currentPlanet == 2) s = "       §dAsha§r - §c§k!!!!!!!!!";
                if (currentPlanet == 3) s = "  §2Overworld§r - §c§k!!!!!!!!!";
                if (currentPlanet == 4) s = "    §9Lunamar§r - §c§k!!!!!!!!!";

                guiGraphics.drawString(this.font, Component.literal(s), uiX + 100, uiY + 230, 0, false);
            }


        }


        //render page number on bottom left
        {
            int entriesMax = 0;
            if (currentPlanet == 0) entriesMax = Laicaps.MENU_ENTRIES;
            if (currentPlanet == 1) entriesMax = Laicaps.EMBER_ENTRIES;
            if (currentPlanet == 2) entriesMax = Laicaps.ASHA_ENTRIES;
            if (currentPlanet == 3) entriesMax = Laicaps.OVERWORLD_ENTRIES;
            if (currentPlanet == 4) entriesMax = Laicaps.LUNAMAR_ENTRIES;

            guiGraphics.drawString(
                    this.font, Component.literal("[" + currentEntry + "/" + entriesMax + "]"),
                    uiX + 213, uiY + 230, 0, false);
        }

        //if entry not unlocked, displays obfuscated text on both pages
        if (!entryUnlocked)
        {
            //left title obfuscated
            guiGraphics.drawString(this.font, Component.literal("            §c§k§l!!!!!!!!!!!!!!!!!!!!!!!!"), uiX + 65, uiY + 20, 0, true);

            //left obfuscated text
            for (int i = 0; i < 16; i++)
                guiGraphics.drawString(this.font, Component.literal("§c§k§l" + obfuscatedLeft.get(i)), uiX + 65, uiY + 60 + (i * 10), 0, true);

            //right obfuscated text
            for (int i = 0; i < 19; i++)
                guiGraphics.drawString(this.font, Component.literal("§c§k§l" + obfuscatedRight.get(i)), uiX + 268, uiY + 30 + (i * 10), 0, true);

        }

        //render text  & image from translation key
        if (entryUnlocked)
        {
            //render image
            String keyImage = "gui.astronomy_research_table." + currentPlanetString + ".entry" + currentEntry + ".image";
            if (I18n.exists(keyImage))
                renderImage(guiGraphics, Laicaps.rl("textures/gui/astronomy_table/" + I18n.get(keyImage) + ".png"));

            //render text left
            for (int i = 0; i < 21; i++)
            {
                String key = "gui.astronomy_research_table." + currentPlanetString + ".entry" + currentEntry + ".left." + i;
                if (I18n.exists(key))
                    guiGraphics.drawString(this.font, Component.translatable(key), uiX + 65, uiY + 10 + (i * 10), 0, false);
            }

            //render text right
            for (int i = 0; i < 21; i++)
            {
                String key = "gui.astronomy_research_table." + currentPlanetString + ".entry" + currentEntry + ".right." + i;
                if (I18n.exists(key))
                    guiGraphics.drawString(this.font, Component.translatable(key), uiX + 272, uiY + 10 + (i * 10), 0, false);
            }
        }


        //render bookmark

        RenderSystem.enableBlend();
        renderImage(guiGraphics, BOOKMARK_BACKGROUND);
        if (AdvHelper.hasAdvancementCriteria(adv, "bookmarks", currentPlanetString + "_" + currentEntry))
        {
            renderImage(guiGraphics, BOOKMARK);
        }

        RenderSystem.disableBlend();

        bookmarks.clear();

        AdvHelper.getEntriesCompletedAsIterable(adv, "bookmarks").forEach(bookmarks::add);

        for (int i = 0; i < bookmarks.size(); i++)
        {
            ResourceLocation bookmarkImage = Laicaps.rl("textures/gui/astronomy_table/bookmark_" + bookmarks.get(i) + ".png");

            if (!(Objects.equals(bookmarks.getFirst(), "none")))
                renderImage(guiGraphics, bookmarkImage, i * 26);
        }


    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public AstronomyTableScreen(AstronomyTableMenu astronomyTableMenu, Inventory playerInventory, Component title)
    {
        super(astronomyTableMenu, playerInventory, title);
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
