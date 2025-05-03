package com.wdiscute.laicaps.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(InventoryScreen.class)
public abstract class RecipeBookRender extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener
{


    @Shadow
    private boolean widthTooNarrow;

    @Shadow
    private float xMouse;
    @Shadow
    private float yMouse;


    public RecipeBookRender(InventoryMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init()
    {
        if (this.minecraft.gameMode.hasInfiniteItems())
        {
            this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), (Boolean) this.minecraft.options.operatorItemsTab().get()));
        } else
        {
            super.init();
            this.widthTooNarrow = this.width < 379;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        if (this.widthTooNarrow)
        {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        } else
        {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    public void containerTick()
    {
        if (this.minecraft.gameMode.hasInfiniteItems())
        {
            this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), (Boolean) this.minecraft.options.operatorItemsTab().get()));
        }
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers)
    {
        return false;
    }

    @Override
    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY)
    {
        return (!this.widthTooNarrow) && super.isHovering(x, y, width, height, mouseX, mouseY);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        return this.widthTooNarrow ? false : super.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean flag = mouseX < (double)guiLeft || mouseY < (double)guiTop || mouseX >= (double)(guiLeft + this.imageWidth) || mouseY >= (double)(guiTop + this.imageHeight);
        return flag;
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
    }

    @Override
    public void recipesUpdated() {
        return;
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return null;
    }




}


