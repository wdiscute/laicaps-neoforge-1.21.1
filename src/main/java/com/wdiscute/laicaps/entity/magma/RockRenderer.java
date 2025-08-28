package com.wdiscute.laicaps.entity.magma;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

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

        if(rockEntity.getOwner() == null)
        {
            System.out.println("no owner");
            poseStack.popPose();
            return;
        }


        MagmaEntity magma = ((MagmaEntity) rockEntity.getOwner());


        Vec3 m = rockEntity.position().subtract(magma.position());
        poseStack.translate(-m.x, -m.y, -m.z);

        poseStack.scale(-1.0F, -1.0F, 1.0F);


        double counter = rockEntity.level().getGameTime() + (double)partialTicks;

        double distanceOffset = Math.sin(counter / 20d);
        Vec3 vec3 = new Vec3(4 + distanceOffset, 0, 0);

        double angle = 45 * rockEntity.order + (counter % 360) ;

        double x = (vec3.x * Math.cos(Math.toRadians(angle)) - (vec3.z * Math.sin(Math.toRadians(angle))));
        double z = (vec3.x * Math.sin(Math.toRadians(angle)) + (vec3.z * Math.cos(Math.toRadians(angle))));

        poseStack.translate(-x, -3, z);



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
