package com.wdiscute.laicaps.entity.nimble;

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

public class NimbleModel<T extends NimbleEntity> extends HierarchicalModel<T>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("nimble"), "main");
    private final ModelPart root;
    private final ModelPart body;

    public NimbleModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, 0.0F));

        PartDefinition feet = body.addOrReplaceChild("feet", CubeListBuilder.create().texOffs(12, 13).addBox(0.75F, -1.25F, -2.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(0.75F, -1.25F, 1.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 16).addBox(-2.75F, -1.25F, 1.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-2.75F, -1.25F, -2.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition EarL = body.addOrReplaceChild("EarL", CubeListBuilder.create(), PartPose.offset(2.0F, -4.0F, -2.0F));

        PartDefinition cube_r1 = EarL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.75F, 0.5F, -0.1687F, 0.045F, 0.258F));

        PartDefinition EarR = body.addOrReplaceChild("EarR", CubeListBuilder.create(), PartPose.offset(-2.0F, -4.0F, -2.0F));

        PartDefinition cube_r2 = EarR.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 0.5F, -0.2084F, -0.0651F, -0.2986F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 2.0F));

        PartDefinition tail_r1 = Tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(10, 10).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.75F, 1.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition snout = body.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -1.25F, -3.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition furright = body.addOrReplaceChild("furright", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition four_r1 = furright.addOrReplaceChild("four_r1", CubeListBuilder.create().texOffs(22, 30).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 2.0F, -0.2182F, 0.0F, 1.5708F));

        PartDefinition three_r1 = furright.addOrReplaceChild("three_r1", CubeListBuilder.create().texOffs(22, 28).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 1.0F, -0.2182F, 0.0F, 1.5708F));

        PartDefinition two_r1 = furright.addOrReplaceChild("two_r1", CubeListBuilder.create().texOffs(22, 26).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 0.0F, -0.2182F, 0.0F, 1.5708F));

        PartDefinition one_r1 = furright.addOrReplaceChild("one_r1", CubeListBuilder.create().texOffs(22, 24).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 4.0F, -1.0F, -0.2182F, 0.0F, 1.5708F));

        PartDefinition furleft = body.addOrReplaceChild("furleft", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition four_r2 = furleft.addOrReplaceChild("four_r2", CubeListBuilder.create().texOffs(4, 30).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 4.0F, 2.0F, 0.2182F, 0.0F, 1.5708F));

        PartDefinition three_r2 = furleft.addOrReplaceChild("three_r2", CubeListBuilder.create().texOffs(4, 26).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 4.0F, 1.0F, 0.2182F, 0.0F, 1.5708F));

        PartDefinition two_r2 = furleft.addOrReplaceChild("two_r2", CubeListBuilder.create().texOffs(4, 28).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 4.0F, 0.0F, 0.2182F, 0.0F, 1.5708F));

        PartDefinition one_r2 = furleft.addOrReplaceChild("one_r2", CubeListBuilder.create().texOffs(4, 24).addBox(-3.9696F, -0.3473F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 4.0F, -1.0F, 0.2182F, 0.0F, 1.5708F));

        PartDefinition furback = body.addOrReplaceChild("furback", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 3.0F));

        PartDefinition four_r3 = furback.addOrReplaceChild("four_r3", CubeListBuilder.create().texOffs(12, 30).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition three_r3 = furback.addOrReplaceChild("three_r3", CubeListBuilder.create().texOffs(12, 28).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition two_r3 = furback.addOrReplaceChild("two_r3", CubeListBuilder.create().texOffs(12, 26).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition one_r3 = furback.addOrReplaceChild("one_r3", CubeListBuilder.create().texOffs(13, 25).addBox(-3.0F, 0.0F, 0.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition furtop = body.addOrReplaceChild("furtop", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition four_r4 = furtop.addOrReplaceChild("four_r4", CubeListBuilder.create().texOffs(12, 22).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition three_r4 = furtop.addOrReplaceChild("three_r4", CubeListBuilder.create().texOffs(12, 20).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition two_r4 = furtop.addOrReplaceChild("two_r4", CubeListBuilder.create().texOffs(12, 18).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition one_r4 = furtop.addOrReplaceChild("one_r4", CubeListBuilder.create().texOffs(12, 16).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(NimbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(NimbleAnimations.shake, limbSwing, limbSwingAmount, 3f, 1f);
        this.animate(entity.idleAnimationState, NimbleAnimations.idle, ageInTicks, 1f);

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
