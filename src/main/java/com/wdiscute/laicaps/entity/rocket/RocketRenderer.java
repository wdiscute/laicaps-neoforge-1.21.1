package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class RocketRenderer extends LivingEntityRenderer<RocketEntity, RocketModel<RocketEntity>>
{


    public RocketRenderer(EntityRendererProvider.Context context)
    {
        super(context, new RocketModel<>(context.bakeLayer(RocketModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntity rocketEntity)
    {
        return ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "textures/entity/rocket/rocket.png");
    }

    @Override
    public void render(RocketEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
