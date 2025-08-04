package com.wdiscute.laicaps.entity.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.bluetale.BluetaleModel;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import com.wdiscute.laicaps.entity.rocket.RocketModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FishingBobRenderer extends EntityRenderer<FishingBobEntity>
{
    protected FishingBobModel<FishingBobEntity> model;

    public FishingBobRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new FishingBobModel<>(context.bakeLayer(FishingBobModel.LAYER_LOCATION));
    }


    @Override
    public ResourceLocation getTextureLocation(FishingBobEntity fishingBobEntity)
    {
        return Laicaps.rl("textures/entity/fishing/bob.png");
    }

    @Override
    public void render(FishingBobEntity fishingBobEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        poseStack.pushPose();

        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.model.setupAnim(fishingBobEntity, 0.0F, 0.0F, fishingBobEntity.tickCount + partialTicks, 0.0F, 0.0F);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(fishingBobEntity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    public void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, int z, float u, float v, int normalX, int normalY, int normalZ, int packedLight) {
        consumer.addVertex(pose, (float)x, (float)y, (float)z).setColor(-1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, (float)normalX, (float)normalZ, (float)normalY);
    }

}
