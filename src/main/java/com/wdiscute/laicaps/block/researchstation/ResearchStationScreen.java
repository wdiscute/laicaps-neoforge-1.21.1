package com.wdiscute.laicaps.block.researchstation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.block.telescope.TelescopeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.ChunkToProtochunkFix;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResearchStationScreen extends AbstractContainerScreen<ResearchStationMenu>
{

    private static ResearchStationMenu menu;

    private static final Logger log = LoggerFactory.getLogger(ResearchStationScreen.class);
    private static final ResourceLocation BACKGROUND = Laicaps.rl("textures/gui/research_station/background.png");

    Random r = new Random();

    ItemStack itemStack = ItemStack.EMPTY;

    int uiX;
    int uiY;

    int jigsawSelected = -1;

    List<Vector2f> jigsaws = new ArrayList<>();

    @Override
    protected void init()
    {
        super.init();

        imageWidth = 512;
        imageHeight = 256;
        uiX = (width - imageWidth) / 2;
        uiY = (height - imageHeight) / 2;
    }

    private void scramble()
    {
        jigsaws.clear();
        if (menu.blockEntity.inventory.getStackInSlot(0).is(ModItems.ROCKET_BLUEPRINT))
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    jigsaws.add(new Vector2f(0, 0));
                }
            }
        }
        else
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 7; j++)
                {
//                    if (i > 3)
//                    {
//                        jigsaws.add(new Vector2f(10, 0));
//                    }
//                    else
//                    {
//                        jigsaws.add(new Vector2f(0, 0));
//                    }
                    jigsaws.add(new Vector2f(r.nextInt(200) - 100, r.nextInt(150) - 75));
                }
            }
        }

        for (int i = 0; i < 35; i++)
        {

            int xoffset = i;

            while (xoffset >= 7)
                xoffset -= 7;

            xoffset = -(xoffset * 35);

            int yoffset = i;
            yoffset = yoffset / 7;
            yoffset = -(yoffset * 35);

            if (!(jigsaws.get(i).x < 252 + xoffset))
                jigsaws.set(i, new Vector2f(252 + xoffset, jigsaws.get(i).y));

            if (!(jigsaws.get(i).x > -42 + xoffset))
                jigsaws.set(i, new Vector2f(-42 + xoffset, jigsaws.get(i).y));

            if (!(jigsaws.get(i).y < 177 + yoffset))
                jigsaws.set(i, new Vector2f(jigsaws.get(i).x, 177 + yoffset));

            if (!(jigsaws.get(i).y > -36 + yoffset))
                jigsaws.set(i, new Vector2f(jigsaws.get(i).x, -36 + yoffset));

        }

    }


    @Override
    protected void containerTick()
    {

        if (itemStack.isEmpty()) return;

        //auto-adjust
        for (int i = 0; i < 35; i++)
        {

            if (Math.abs(jigsaws.get(i).x) < 6 && Math.abs(jigsaws.get(i).y) < 6)
            {
                int x = 0;
                int y = 0;

                if (Math.abs(jigsaws.get(i).x) > 0.9f)
                {
                    x = ((int) (jigsaws.get(i).x + Math.signum(jigsaws.get(i).x) * -1));
                }

                if (Math.abs(jigsaws.get(i).y) > 0.9f)
                {
                    y = ((int) (jigsaws.get(i).y + Math.signum(jigsaws.get(i).y) * -1));
                }

                jigsaws.set(i, new Vector2f(x, y));

            }
        }

        //complete check
        boolean pieceNotInPlace = false;

        for (int i = 0; i < 35; i++)
        {
            if (Math.abs(jigsaws.get(i).x) > 2f || Math.abs(jigsaws.get(i).y) > 2f)
            {
                pieceNotInPlace = true;
                break;
            }
        }

        if (!pieceNotInPlace)
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 1);
        }


        super.containerTick();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {

        if (jigsawSelected != -1)
        {
            jigsaws.set(jigsawSelected, new Vector2f(((float) (jigsaws.get(jigsawSelected).x + dragX)), ((float) (jigsaws.get(jigsawSelected).y + dragY))));

            int xoffset = jigsawSelected;

            while (xoffset >= 7)
                xoffset -= 7;

            xoffset = -(xoffset * 35);

            int yoffset = jigsawSelected;
            yoffset = yoffset / 7;
            yoffset = -(yoffset * 35);

            if (!(jigsaws.get(jigsawSelected).x < 252 + xoffset))
                jigsaws.set(jigsawSelected, new Vector2f(252 + xoffset, jigsaws.get(jigsawSelected).y));

            if (!(jigsaws.get(jigsawSelected).x > -42 + xoffset))
                jigsaws.set(jigsawSelected, new Vector2f(-42 + xoffset, jigsaws.get(jigsawSelected).y));

            if (!(jigsaws.get(jigsawSelected).y < 177 + yoffset))
                jigsaws.set(jigsawSelected, new Vector2f(jigsaws.get(jigsawSelected).x, 177 + yoffset));

            if (!(jigsaws.get(jigsawSelected).y > -36 + yoffset))
                jigsaws.set(jigsawSelected, new Vector2f(jigsaws.get(jigsawSelected).x, -36 + yoffset));

        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);

        if (itemStack.isEmpty() && itemStack.is(ModTags.Items.COMPLETED_BLUEPRINTS)) return false;

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                int index = i * 7 + j;
                //x offset from 0,0 = 222
                //y offset from 0,0 = 41
                if (x > jigsaws.get(index).x + 222 + (j * 35) && x < jigsaws.get(index).x + 222 + 35 + (j * 35) &&
                        y > jigsaws.get(index).y + 41 + (i * 35) && y < jigsaws.get(index).y + 41 + 35 + (i * 35))
                {
                    jigsawSelected = index;
                    return super.mouseClicked(mouseX, mouseY, button);
                }


            }
        }

        return true;
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        //initial setup every frame
        double x = mouseX - uiX;
        double y = mouseY - uiY;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);


        renderImage(guiGraphics, BACKGROUND);


        if (!itemStack.equals(menu.blockEntity.inventory.getStackInSlot(0)))
        {
            itemStack = menu.blockEntity.inventory.getStackInSlot(0);
            scramble();
        }

        if (!menu.blockEntity.inventory.getStackInSlot(0).isEmpty())
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    ResourceLocation rl = Laicaps.rl("textures/gui/research_station/" + getName(i, j) + ".png");

                    int index = i * 7 + j;
                    renderImage(guiGraphics, rl, ((int) jigsaws.get(index).x), ((int) jigsaws.get(index).y));
                }
            }
        }


    }

    private static @NotNull String getName(int i, int j)
    {
        String letter = switch (j)
        {
            case 0:
                yield "a";
            case 1:
                yield "b";
            case 2:
                yield "c";
            case 3:
                yield "d";
            case 4:
                yield "e";
            case 5:
                yield "f";
            default:
                yield "g";
        };

        return letter + (i + 1);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        jigsawSelected = -1;
        return super.mouseReleased(mouseX, mouseY, button);
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
        menu = researchStationMenu;
    }


    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl)
    {
        guiGraphics.blit(rl, uiX, uiY, 0, 0, 512, 256, 512, 256);
    }

    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl, int x, int y)
    {
        guiGraphics.blit(rl, uiX + x, uiY + y, 0, 0, 512, 256, 512, 256);
    }


    //empty so it doesn't render "inventory"  and "telescope" text
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
    }
}
