package com.wdiscute.laicaps.entity.bubblemouth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.bluetale.BluetaleAnimations;
import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BubblemouthModel<T extends BubblemouthEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("bubblemouth"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart bone;

    public BubblemouthModel(ModelPart root) {
    this.root = root.getChild("root");
    this.body = this.root.getChild("body");
    this.tail = this.body.getChild("tail");
    this.bone = this.tail.getChild("bone");
}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -6.0F, -6.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(12, 12).addBox(0.5F, -7.75F, -3.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(0.5F, -3.25F, -4.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.0F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 18).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.0F, -4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 19).addBox(-0.5F, -6.0F, 1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone = tail.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, -2.5F, 0.75F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.5F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(BubblemouthEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(BubblemouthAnimations.SWIM, limbSwing, limbSwingAmount, 1f, 1f);
        this.animate(entity.idleAnimationState, BubblemouthAnimations.SWIM, ageInTicks, 1f);
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
