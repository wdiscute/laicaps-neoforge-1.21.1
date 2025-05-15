package com.wdiscute.laicaps.entity.snuffler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnufflerRenderer extends MobRenderer<SnufflerEntity, SnufflerModel<SnufflerEntity>>
{


    public SnufflerRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SnufflerModel<>(context.bakeLayer(SnufflerModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(SnufflerEntity snufflerEntity)
    {
        return ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/entity/snuffler/snuffler.png");
    }

    @Override
    public void render(SnufflerEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        if(entity.isBaby())
        {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }else
        {
            poseStack.scale(0.9f,0.9f,0.9f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
