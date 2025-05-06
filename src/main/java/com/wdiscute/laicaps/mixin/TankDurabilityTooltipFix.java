package com.wdiscute.laicaps.mixin;

import com.wdiscute.laicaps.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Mixin(Screen.class)
public class TankDurabilityTooltipFix
{
    @Inject(at = @At("RETURN"), method = "getTooltipFromItem", cancellable = true)
    private static void tankTooltipFIx(Minecraft minecraft, ItemStack item, CallbackInfoReturnable<List<Component>> cir)
    {
        if (item.is(ModItems.TANK) || item.is(ModItems.MEDIUM_TANK) || item.is(ModItems.LARGE_TANK))
        {
            cir.setReturnValue(item.getTooltipLines(
                    Item.TooltipContext.of(minecraft.level),
                    minecraft.player, TooltipFlag.Default.NORMAL));
        }

    }
}

