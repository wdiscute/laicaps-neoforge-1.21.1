package com.wdiscute.laicaps.block.astronomytable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
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

    private static final ResourceLocation EMBER_SELECTED = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/ember_selected.png");
    private static final ResourceLocation ASHA_SELECTED = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/asha_selected.png");
    private static final ResourceLocation OVERWORLD_SELECTED = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/overworld_selected.png");
    private static final ResourceLocation LUNAMAR_SELECTED = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/lunamar_selected.png");

    private static final ResourceLocation EMBER_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/ember_highlight.png");
    private static final ResourceLocation ASHA_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/asha_highlight.png");
    private static final ResourceLocation OVERWORLD_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/overworld_highlight.png");
    private static final ResourceLocation LUNAMAR_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/gui/astronomy_table/lunamar_highlight.png");


    Random r = new Random();

    int uiX;
    int uiY;

    int currentEntry = 0;
    int currentPlanet = 0;

    List<String> obfuscatedLeft = new ArrayList<>();
    List<String> obfuscatedRight = new ArrayList<>();


    private void reObfuscate()
    {
        obfuscatedLeft.clear();
        obfuscatedRight.clear();
        for (int i = 0; i < 16; i++)
        {
            if (r.nextFloat(1f) < 0.8)
            {
                String s = "";
                for (int j = 0; j < r.nextInt(30, 55); j++)
                    s = r.nextFloat() < 0.85 ? s.concat("|") : s.concat(" ");

                obfuscatedLeft.add(s);
            } else
            {
                obfuscatedLeft.add("");
            }
        }

        for (int i = 0; i < 19; i++)
        {
            if (r.nextFloat(1f) < 0.8)
            {
                String s = "";
                for (int j = 0; j < r.nextInt(30, 55); j++)
                    s = r.nextFloat() < 0.85 ? s.concat("|") : s.concat(" ");

                obfuscatedRight.add(s);
            } else
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
            if (currentEntry > 0)
            {
                currentEntry--;
                reObfuscate();
            }

            if (currentEntry == 0)
            {
                if (currentPlanet == 0) return false;
                if (currentPlanet == 1)
                {
                    currentPlanet--;
                    reObfuscate();
                }

                if (currentPlanet == 2)
                {
                    currentPlanet--;
                    currentEntry = Laicaps.EMBER_ENTRIES;
                    reObfuscate();
                }

                if (currentPlanet == 3)
                {
                    currentPlanet--;
                    currentEntry = Laicaps.ASHA_ENTRIES;
                    reObfuscate();
                }

                if (currentPlanet == 4)
                {
                    currentPlanet--;
                    currentEntry = Laicaps.OVERWORLD_ENTRIES;
                    reObfuscate();
                }

            }
        }

        //next arrow
        if (x > 420 && x < 440 && y > 230 && y < 240)
        {
            //menu
            if (currentPlanet == 0)
            {
                currentEntry = 0;
                currentPlanet++;
                reObfuscate();
            }


            //ember
            if (currentPlanet == 1)
                if (currentEntry < Laicaps.EMBER_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                } else
                {
                    currentEntry = 0;
                    currentPlanet++;
                    reObfuscate();
                }

            //asha
            if (currentPlanet == 2)
                if (currentEntry < Laicaps.ASHA_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                } else
                {
                    currentEntry = 0;
                    currentPlanet++;
                    reObfuscate();
                }

            //overworld
            if (currentPlanet == 3)
                if (currentEntry < Laicaps.OVERWORLD_ENTRIES)
                {
                    currentEntry++;
                    reObfuscate();
                } else
                {
                    currentEntry = 0;
                    currentPlanet++;
                    reObfuscate();
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
        if (!(currentPlanet == 0)) guiGraphics.blit(ARROW_PREVIOUS, uiX + 65, uiY + 227, 0, 0, 23, 13, 23, 13);
        if (!(currentPlanet == 4 && currentEntry == Laicaps.LUNAMAR_ENTRIES))
            guiGraphics.blit(ARROW_NEXT, uiX + 420, uiY + 227, 0, 0, 23, 13, 23, 13);


        //render page index at the bottom
        {
            if (entryUnlocked && currentPlanet != 0)
            {
                guiGraphics.drawString(
                        this.font, Component.translatable("gui.astronomy_research_table." + currentPlanetString + ".entry" + currentEntry + ".name"),
                        uiX + 100, uiY + 230, 0, false);
            } else
            {
                String s = "";
                if (currentPlanet == 1) s = "      §6Ember§r - §c§k!!!!!!!!!";
                if (currentPlanet == 2) s = "       §dAsha§r - §c§k!!!!!!!!!";
                if (currentPlanet == 3) s = "  §2Overworld§r - §c§k!!!!!!!!!";
                if (currentPlanet == 4) s = "    §9Lunamar§r - §c§k!!!!!!!!!";

                guiGraphics.drawString(this.font, Component.literal(s), uiX + 100, uiY + 230, 0, false);
            }


        }


        //if entry not unlocked, displays obfuscated text on both pages
        if (!entryUnlocked)
        {
            Random r = new Random();
            guiGraphics.drawString(this.font, Component.literal("            §c§k§l!!!!!!!!!!!!!!!!!!!!!!!!"), uiX + 65, uiY + 20, 0, true);

            for (int i = 0; i < 16; i++)
            {
                guiGraphics.drawString(this.font, Component.literal("§c§k§l" + obfuscatedLeft.get(i)),
                        uiX + 65, uiY + 60 + (i * 10), 0, true);
            }

            for (int i = 0; i < 19; i++)
            {
                guiGraphics.drawString(this.font, Component.literal("§c§k§l" + obfuscatedRight.get(i)),
                        uiX + 268, uiY + 30 + (i * 10), 0, true);
            }


            return;
        }


        for (int i = 0; i < 22; i++)
        {
            String key = "gui.astronomy_research_table.asha.entry" + currentEntry + ".left." + i;
            if (I18n.exists(key))
            {
                if (I18n.get(key).contains("image%"))
                {
                    renderIllustration(
                            guiGraphics, uiX + 65, uiY + 10 + (i * 10),
                            Laicaps.rl("textures/gui/astronomy_table/" + I18n.get(key).substring(I18n.get(key).lastIndexOf("%") + 1) + ".png"));
                } else
                {
                    guiGraphics.drawString(this.font, Component.translatable(key), uiX + 65, uiY + 10 + (i * 10), 0, false);
                }
            }
        }


        for (int i = 0; i < 21; i++)
        {
            String key = "gui.astronomy_research_table.asha.entry" + currentEntry + ".right." + i;
            if (I18n.exists(key))
            {
                if (I18n.get(key).contains("image%"))
                {
                    renderIllustration(guiGraphics, uiX + 268, uiY + 10 + (i * 10), Laicaps.rl("textures/gui/astronomy_table/" + I18n.get(key).substring(I18n.get(key).lastIndexOf("%") + 1) + ".png"));
                } else
                {
                    guiGraphics.drawString(this.font, Component.translatable(key), uiX + 268, uiY + 10 + (i * 10), 0, false);

                }

            }
        }

    }


    private void renderIllustration(GuiGraphics guiGraphics, int x, int y, ResourceLocation rl)
    {
        guiGraphics.blit(rl, x, y, 0, 0, 180, 230, 180, 230);
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

    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
