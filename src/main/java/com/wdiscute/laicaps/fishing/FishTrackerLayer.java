package com.wdiscute.laicaps.fishing;

import com.wdiscute.laicaps.LaicapsKeys;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.networkandcodecsandshitomgthissuckssomuchpleasehelp.ModDataAttachments;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.logging.Level;

public class FishTrackerLayer implements LayeredDraw.Layer
{
    private int count;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker)
    {

        boolean spotterCheck = false;

        if(Minecraft.getInstance().level == null) return;
        if(Minecraft.getInstance().player == null) return;

        ClientLevel level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;

        ItemStack rod = new ItemStack(ModItems.STARCATCHER_FISHING_ROD.get());

        if(player.getMainHandItem().is(ModItems.FISH_SPOTTER) || player.getOffhandItem().is(ModItems.FISH_SPOTTER))
        {
            spotterCheck = true;

        }
        else
        {
            ItemContainerContents icc = player.getMainHandItem().get(ModDataComponents.BOBBER);
            if (icc != null)
            {
                ItemStack is = icc.copyOne();
                if (is.is(ModItems.FISH_SPOTTER))
                {
                    spotterCheck = true;
                    rod = player.getMainHandItem();
                }
            }
        }

        if(!spotterCheck) return;


        FishProperties fp = FishProperties.getFishPropertiesFromItem(level.registryAccess(), player.getData(ModDataAttachments.FISH_SPOTTER));

        if(fp == null) return;


        int total = 0;

        for (FishProperties d : level.registryAccess().registryOrThrow(LaicapsKeys.FISH_REGISTRY))
        {
            total += FishProperties.getChance(d, player, rod);
        }

        int specific = FishProperties.getChance(fp, player, rod);

        int chance = ((int) (((float) specific / total) * 100));

        guiGraphics.drawString(Minecraft.getInstance().font, fp.fish().toString(), 100, 100, 0, false);

        guiGraphics.drawString(Minecraft.getInstance().font, chance + "% chance to catch", 100, 120, 0, false);


    }
}
