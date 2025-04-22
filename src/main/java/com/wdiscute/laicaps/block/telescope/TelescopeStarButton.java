package com.wdiscute.laicaps.block.telescope;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TelescopeStarButton extends AbstractStarButton
{
    protected final OnPress onPress;

    public static TelescopeStarButton.Builder builder(TelescopeStarButton.OnPress onPress) {
        return new TelescopeStarButton.Builder(onPress);
    }

    protected TelescopeStarButton(int x, int y, int width, int height, TelescopeStarButton.OnPress onPress) {
        super(x, y, width, height, Component.literal(""));
        this.onPress = onPress;
    }

    protected TelescopeStarButton(Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.onPress);
        this.setTooltip(builder.tooltip);
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final TelescopeStarButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(TelescopeStarButton.OnPress onPress) {
            this.onPress = onPress;
        }

        public TelescopeStarButton.Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public TelescopeStarButton.Builder width(int width) {
            this.width = width;
            return this;
        }

        public TelescopeStarButton.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public TelescopeStarButton.Builder bounds(int x, int y, int width, int height) {
            return this.pos(x, y).size(width, height);
        }

        public TelescopeStarButton.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public TelescopeStarButton build() {
            return this.build(TelescopeStarButton::new);
        }

        public TelescopeStarButton build(Function<TelescopeStarButton.Builder, TelescopeStarButton> builder) {
            return (TelescopeStarButton)builder.apply(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(TelescopeStarButton var1);
    }
}

