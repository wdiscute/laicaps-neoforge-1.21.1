package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RocketRenderer extends EntityRenderer<RocketEntity>
{
    protected RocketModel<RocketEntity> model;

    public RocketRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new RocketModel<>(context.bakeLayer(RocketModel.LAYER_LOCATION));
    }

    public void render(RocketEntity rocketEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        super.render(rocketEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();

        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.model.setupAnim(rocketEntity, 0.0F, 0.0F, rocketEntity.tickCount + partialTicks, 0.0F, 0.0F);

        RocketMainScreenLayer.renderLayer(model, rocketEntity, poseStack, buffer, packedLight);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(rocketEntity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(RocketEntity rocketEntity)
    {
        return Laicaps.rl("textures/entity/rocket/rocket.png");
    }


}
