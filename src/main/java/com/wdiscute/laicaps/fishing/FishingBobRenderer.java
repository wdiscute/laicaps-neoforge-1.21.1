package com.wdiscute.laicaps.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FishingBobRenderer extends EntityRenderer<FishingBobEntity>
{

    public FishingBobRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FishingBobEntity bluetaleEntity)
    {
        return Laicaps.rl("textures/entity/bluetale/bluetale.png");
    }



    @Override
    public void render(FishingBobEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
