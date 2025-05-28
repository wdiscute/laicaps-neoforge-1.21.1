package com.wdiscute.laicaps.entity.bubblemouth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.BluetaleModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BubblemouthRenderer extends MobRenderer<BubblemouthEntity, BubblemouthModel<BubblemouthEntity>>
{

    public BubblemouthRenderer(EntityRendererProvider.Context context)
    {
        super(context, new BubblemouthModel<>(context.bakeLayer(BubblemouthModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    protected void setupRotations(BubblemouthEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale)
    {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        if (!entity.isInWater())
        {
            float f = 1.3F;
            float f1 = 1.7F;
            float f2 = f * 4.3F * Mth.sin(f1 * 0.6F * bob);
            poseStack.mulPose(Axis.YP.rotationDegrees(f2));
            poseStack.translate(0.2F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BubblemouthEntity bubblemouthEntity)
    {
        return Laicaps.rl("textures/entity/bubblemouth/bubblemouth.png");
    }

    @Override
    public void render(BubblemouthEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
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
