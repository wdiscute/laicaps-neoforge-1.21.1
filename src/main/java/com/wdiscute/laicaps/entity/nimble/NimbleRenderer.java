package com.wdiscute.laicaps.entity.nimble;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NimbleRenderer extends MobRenderer<NimbleEntity, NimbleModel<NimbleEntity>>
{

    public NimbleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new NimbleModel<>(context.bakeLayer(NimbleModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(NimbleEntity nimbleEntity)
    {
        return Laicaps.rl("textures/entity/nimble/nimble.png");
    }

    @Override
    public void render(NimbleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        if(entity.isBaby())
        {
            poseStack.scale(0.9f, 0.9f, 0.9f);
        }else
        {
            poseStack.scale(1.5f,1.5f,1.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
