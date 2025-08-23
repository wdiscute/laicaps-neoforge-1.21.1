package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        ItemProperties.register(
                ModItems.STARCATCHER_FISHING_ROD.get(),
                Laicaps.rl("cast"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.CAST.get(), Boolean.FALSE) ? 1.0F : 0.0F
        );
    }


}
