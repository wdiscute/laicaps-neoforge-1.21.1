package com.wdiscute.laicaps.entity.glimpuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class GlimpuffGlowLayer extends RenderLayer<GlimpuffEntity, GlimpuffModel<GlimpuffEntity>>
{

    private static final RenderType GLOW = RenderType.breezeEyes(Laicaps.rl("textures/entity/glimpuff/glimpuff_glow.png"));

    public GlimpuffGlowLayer(RenderLayerParent<GlimpuffEntity, GlimpuffModel<GlimpuffEntity>> p_117507_) {
        super(p_117507_);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int i, GlimpuffEntity glimpuffEntity, float v, float v1, float v2, float v3, float v4, float v5)
    {
        if(glimpuffEntity.getEntityData().get(GlimpuffEntity.FULL))
        {
            VertexConsumer vertexconsumer = buffer.getBuffer(this.renderType());
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 3355443, OverlayTexture.NO_OVERLAY);
        }

    }

    public RenderType renderType() {
        return GLOW;
    }
}
