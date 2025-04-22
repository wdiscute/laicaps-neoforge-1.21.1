package com.wdiscute.laicaps.block.telescope;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = Laicaps.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Shaders {

    public static ShaderInstance REVEAL_SHADER = null;
    public static RenderStateShard.ShaderStateShard REVEAL_SHADER_SHARD = new RenderStateShard.ShaderStateShard(()->REVEAL_SHADER);

    @SubscribeEvent
    public static void register(RegisterShadersEvent event) throws IOException
    {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.tryBuild(Laicaps.MOD_ID,"reveal_panel"), DefaultVertexFormat.POSITION_TEX),
                (inst)->{
                    REVEAL_SHADER = inst;
                });
    }

}
