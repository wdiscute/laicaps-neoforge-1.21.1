package com.wdiscute.laicaps.entity.magmaboss.rock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RockRenderer extends EntityRenderer<RockEntity>
{
    protected RockModel<RockEntity> model;

    public RockRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.model = new RockModel<>(context.bakeLayer(RockModel.LAYER_LOCATION));
    }

    public void render(RockEntity rockEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        super.render(rockEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();

        rockEntity.xRot += 0.3f;
        rockEntity.yRot += 1.5f;
        rockEntity.zRot += 0.6f;

        poseStack.mulPose(Axis.XP.rotationDegrees(rockEntity.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(rockEntity.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(rockEntity.zRot));

        poseStack.translate(0, -1, 0);



        this.model.setupAnim(rockEntity, 0.0F, 0.0F, rockEntity.tickCount + partialTicks, 0.0F, 0.0F);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(rockEntity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(RockEntity rocketEntity)
    {
        return Laicaps.rl("textures/entity/magma/rock.png");
    }


}
