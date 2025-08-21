package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModTags;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class RocketLayers
{


    public static void renderCarpetLayer(RocketModel<RE> model, RE rocketEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        if (rocketEntity.getEntityData().get(RE.CARPET_FIRST_SEAT).is(ItemTags.WOOL_CARPETS))
        {
            Item item = rocketEntity.getEntityData().get(RE.CARPET_FIRST_SEAT).getItem();

            String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
            String path      = BuiltInRegistries.ITEM.getKey(item).getPath();

            VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutoutNoCull(Laicaps.rl("textures/entity/rocket/carpet_first_seat/" + namespace + "_" + path + ".png")));
            model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (rocketEntity.getEntityData().get(RE.CARPET_SECOND_SEAT).is(ItemTags.WOOL_CARPETS))
        {
            Item item = rocketEntity.getEntityData().get(RE.CARPET_SECOND_SEAT).getItem();

            String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
            String path      = BuiltInRegistries.ITEM.getKey(item).getPath();

            VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutoutNoCull(Laicaps.rl("textures/entity/rocket/carpet_second_seat/" + namespace + "_" + path + ".png")));
            model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (rocketEntity.getEntityData().get(RE.CARPET_THIRD_SEAT).is(ItemTags.WOOL_CARPETS))
        {
            Item item = rocketEntity.getEntityData().get(RE.CARPET_THIRD_SEAT).getItem();

            String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
            String path      = BuiltInRegistries.ITEM.getKey(item).getPath();

            VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutoutNoCull(Laicaps.rl("textures/entity/rocket/carpet_third_seat/" + namespace + "_" + path + ".png")));
            model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }

    }


    public static void renderGlobeLayer(RocketModel<RE> model, RE rocketEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {

        if (rocketEntity.getEntityData().get(RE.GLOBE).is(ModTags.Items.GLOBES))
        {
            Item item = rocketEntity.getEntityData().get(RE.GLOBE).getItem();

            String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
            String path      = BuiltInRegistries.ITEM.getKey(item).getPath();

            VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutout(Laicaps.rl("textures/entity/rocket/globe/" + namespace + "_" + path + ".png")));
            model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }

    }


    public static void renderScreenLayer(RocketModel<RE> model, RE rocketEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        int jumping = rocketEntity.getEntityData().get(RE.JUMPING) / 20;
        int state = rocketEntity.getEntityData().get(RE.STATE);
        int countdown = 11 - jumping;
        if (countdown < 0) countdown = 0;

        //idle
        if (state == 0)
        {

            //if missing knowledge
            if (rocketEntity.getEntityData().get(RE.MISSING_KNOWLEDGE))
            {
                VertexConsumer vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/knowledge.png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }

            //if missing fuel
            if (rocketEntity.getEntityData().get(RE.MISSING_FUEL))
            {
                VertexConsumer vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/fuel.png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }

            //if jumping aka starting countdown
            if (jumping > 0)
            {
                VertexConsumer vertexconsumer = buffer.getBuffer(
                        RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/rocket_countdown_" + countdown + ".png")));
                model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                return;
            }


            //if all else fails, it's idle
            VertexConsumer vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/idle.png")));
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            return;




        }


        //taking off
        if (state == 1)
        {
            VertexConsumer vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/takeoff.png")));
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            return;
        }

        //landing
        if (state == 3)
        {
            VertexConsumer vertexconsumer = buffer.getBuffer(
                    RenderType.breezeEyes(Laicaps.rl("textures/entity/rocket/main_screen/takeoff.png")));
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            return;
        }

    }





}
