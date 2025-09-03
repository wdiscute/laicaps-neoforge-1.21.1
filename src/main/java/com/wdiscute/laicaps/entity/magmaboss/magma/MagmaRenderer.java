package com.wdiscute.laicaps.entity.magmaboss.magma;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MagmaRenderer extends EntityRenderer<MagmaEntity>
{
    protected MagmaModel<MagmaEntity> model;

    public MagmaRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new MagmaModel<>(context.bakeLayer(MagmaModel.LAYER_LOCATION));
    }

    public void render(MagmaEntity magmaEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        super.render(magmaEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();

        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.model.setupAnim(magmaEntity, 0.0F, 0.0F, magmaEntity.tickCount + partialTicks, 0.0F, 0.0F);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(magmaEntity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(MagmaEntity rocketEntity)
    {
        return Laicaps.rl("textures/entity/rocket/rocket.png");
    }


}
