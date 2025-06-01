package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RocketMainScreenLayer
{

    public static void renderLayer(RocketModel<RocketEntity> model, RocketEntity rocketEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        int jumping = rocketEntity.getEntityData().get(RocketEntity.JUMPING) / 20;
        int state = rocketEntity.getEntityData().get(RocketEntity.STATE);
        int countdown = 11 - jumping;
        if (countdown < 0) countdown = 0;

        VertexConsumer vertexconsumer = buffer.getBuffer(
                RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/base.png")));


        if (state == 0)
        {

            //if missing fuel
            if (rocketEntity.getEntityData().get(RocketEntity.MISSING_FUEL))
            {
                vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/fuel.png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }

            //if missing knowledge
            if (rocketEntity.getEntityData().get(RocketEntity.MISSING_KNOWLEDGE))
            {
                vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/knowledge.png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }


            //if jumping aka starting countdown
            if (jumping != 0)
            {
                vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/rocket_countdown_" + countdown + ".png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }


            //if all else fails, it's idle
            vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/idle.png")));
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            return;

        }


        if (state == 1)
        {
            vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/takeoff.png")));
        }

        if (state == 3)
        {
            vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/takeoff.png")));
        }


        model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);


    }

}
