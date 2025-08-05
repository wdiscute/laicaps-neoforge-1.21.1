package com.wdiscute.laicaps.entity.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

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

        this.renderLeash2(fishingBobEntity, partialTicks, poseStack, buffer, fishingBobEntity.getOwner());


    }


    private <E extends Entity> void renderLeash2(FishingBobEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, E leashHolder)
    {
        poseStack.pushPose();
        Vec3 vec3 = leashHolder.getRopeHoldPosition(partialTick); //TODO FIX PLAYER HOLDING LEASH POSITION
        double d0 = (double) (entity.getPreciseBodyRotation(partialTick) * ((float) Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = new Vec3(0, 0.2, 0);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp(partialTick, entity.xo, entity.getX()) + d1;
        double d4 = Mth.lerp(partialTick, entity.yo, entity.getY()) + vec31.y;
        double d5 = Mth.lerp(partialTick, entity.zo, entity.getZ()) + d2;
        poseStack.translate(d1, vec31.y, d2);
        float f = (float) (vec3.x - d3);
        float f1 = (float) (vec3.y - d4);
        float f2 = (float) (vec3.z - d5);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.leash());
        Matrix4f matrix4f = poseStack.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(entity.getEyePosition(partialTick));
        BlockPos blockpos1 = BlockPos.containing(leashHolder.getEyePosition(partialTick));
        int i = this.getBlockLightLevel(entity, blockpos);
        int j = 15; //TODO FIX THE LIGHT LEVEL CHECK
        int k = entity.level().getBrightness(LightLayer.SKY, blockpos);
        int l = entity.level().getBrightness(LightLayer.SKY, blockpos1);

        for (int i1 = 0; i1 <= 24; ++i1)
        {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for (int j1 = 24; j1 >= 0; --j1)
        {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        poseStack.popPose();
    }


    private static void addVertexPair(VertexConsumer buffer, Matrix4f pose, float startX, float startY, float startZ, int entityBlockLight, int holderBlockLight, int entitySkyLight, int holderSkyLight, float yOffset, float dy, float dx, float dz, int index, boolean reverse)
    {
        float f = (float) index / 24.0F;
        int i = (int) Mth.lerp(f, (float) entityBlockLight, (float) holderBlockLight);
        int j = (int) Mth.lerp(f, (float) entitySkyLight, (float) holderSkyLight);
        int k = LightTexture.pack(i, j);
        float f1 = index % 2 == (reverse ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = startX * f;
        float f6 = startY > 0.0F ? startY * f * f : startY - startY * (1.0F - f) * (1.0F - f);
        float f7 = startZ * f;
        buffer.addVertex(pose, f5 - dx, f6 + dy, f7 + dz).setColor(f2, f3, f4, 1.0F).setLight(k);
        buffer.addVertex(pose, f5 + dx, f6 + yOffset - dy, f7 - dz).setColor(f2, f3, f4, 1.0F).setLight(k);
    }

    public void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, int z, float u, float v, int normalX, int normalY, int normalZ, int packedLight)
    {
        consumer.addVertex(pose, (float) x, (float) y, (float) z).setColor(-1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, (float) normalX, (float) normalZ, (float) normalY);
    }

}
