package com.wdiscute.laicaps.item;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.jna.platform.win32.WinDef;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModTags;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlueprintScreen extends Screen
{
    private static final ResourceLocation BACKGROUND = Laicaps.rl("textures/gui/blueprint/background.png");

    Random r = new Random();

    int uiX;
    int uiY;

    boolean completed = false;

    int jigsawSelected = -1;

    List<Vector2f> jigsaws = new ArrayList<>();

    @Override
    protected void init()
    {
        super.init();

        uiX = (width - 512) / 2;
        uiY = (height - 256) / 2;
    }

    private void scramble()
    {
        jigsaws.clear();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                jigsaws.add(new Vector2f(r.nextInt(200) - 100, r.nextInt(150) - 75));
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
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public void tick()
    {
        if(completed) return;

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
            completed = true;
            PacketDistributor.sendToServer(new Payloads.BluePrintCompletedPayload("done"));

            for (int i = 0; i < 35; i++)
            {
                jigsaws.set(i, new Vector2f(0, 0));
            }
        }
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if(completed) return false;

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

        if(completed) return false;

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //reverse for loop to account for which piece is being rendered at the top
        for (int i = 4; i >= 0; i--)
        {
            for (int j = 6; j >= 0; j--)
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
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        //initial setup every frame
        double x = mouseX - uiX;
        double y = mouseY - uiY;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);


        renderImage(guiGraphics, BACKGROUND);


        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                ResourceLocation rl = Laicaps.rl("textures/gui/blueprint/" + getName(i, j) + ".png");

                int index = i * 7 + j;
                renderImage(guiGraphics, rl, ((int) jigsaws.get(index).x), ((int) jigsaws.get(index).y));
            }
        }

    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
        {
            this.onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
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
    }

    public BlueprintScreen()
    {
        super(Component.empty());
        scramble();
    }


    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl)
    {
        guiGraphics.blit(rl, uiX, uiY, 0, 0, 512, 256, 512, 256);
    }

    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl, int x, int y)
    {
        guiGraphics.blit(rl, uiX + x, uiY + y, 0, 0, 512, 256, 512, 256);
    }
}
