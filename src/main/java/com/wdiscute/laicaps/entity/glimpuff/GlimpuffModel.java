package com.wdiscute.laicaps.entity.glimpuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthAnimations;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GlimpuffModel<T extends GlimpuffEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("glimpuff"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart belly;
    private final ModelPart tail;
    private final ModelPart bone;

    public GlimpuffModel(ModelPart root) {
    this.root = root.getChild("root");
    this.body = this.root.getChild("body");
    this.belly = this.body.getChild("belly");
    this.tail = this.body.getChild("tail");
    this.bone = this.tail.getChild("bone");
}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -6.0F, -6.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(0.5F, -7.75F, -3.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 10).addBox(1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.0F, -4.5F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 0).addBox(1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.0F, -4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -4.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, -2.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 20).addBox(-0.5F, -6.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-0.5F, -3.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone = tail.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(10, 20).addBox(0.0F, -2.5F, 0.75F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.5F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(GlimpuffEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        AnimationDefinition animation = entity.getEntityData().get(GlimpuffEntity.FULL) ? GlimpuffAnimations.FULL_SWIM : GlimpuffAnimations.SWIM;

        //System.out.println("played animation with " + animation);
        this.animateWalk(animation, limbSwing, limbSwingAmount, 1f, 1f);
        this.animate(entity.idleAnimationState, animation, ageInTicks, 1f);

        //wobble when out of water
        if (!entity.isInWater()) {
            float f = 1.3F;
            float f1 = 1.7F;
            this.body.yRot = -f * 0.25F * Mth.sin(f1 * 0.6F * ageInTicks);
        }

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color)
    {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root()
    {
        return root;
    }
}
