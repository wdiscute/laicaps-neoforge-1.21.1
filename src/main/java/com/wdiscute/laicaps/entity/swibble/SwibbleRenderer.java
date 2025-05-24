package com.wdiscute.laicaps.entity.swibble;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SwibbleRenderer extends MobRenderer<SwibbleEntity, SwibbleModel<SwibbleEntity>>
{

    public SwibbleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SwibbleModel<>(context.bakeLayer(SwibbleModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(SwibbleEntity swibbleEntity)
    {
        return Laicaps.rl("textures/entity/swibble/swibble.png");
    }


    @Override
    protected @Nullable RenderType getRenderType(SwibbleEntity livingEntity, boolean bodyVisible, boolean translucent, boolean glowing)
    {
        return RenderType.entityTranslucent(this.getTextureLocation(livingEntity));
    }

    @Override
    public void render(SwibbleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
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
