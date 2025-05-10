package com.wdiscute.laicaps.entity.bluetale;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BluetaleRenderer extends MobRenderer<BluetaleEntity, BluetaleModel<BluetaleEntity>>
{

    public BluetaleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new BluetaleModel<>(context.bakeLayer(BluetaleModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(BluetaleEntity bluetaleEntity)
    {
        return Laicaps.rl("textures/entity/bluetale/bluetale.png");
    }

    @Override
    public void render(BluetaleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        if(entity.isBaby())
        {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }else
        {
            poseStack.scale(1f,1f,1f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
