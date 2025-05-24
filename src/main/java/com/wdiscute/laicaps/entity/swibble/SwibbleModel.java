package com.wdiscute.laicaps.entity.swibble;

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

public class SwibbleModel<T extends SwibbleEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("swibble"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart topbone;
    private final ModelPart bottombone;
    private final ModelPart bottombone2;
    private final ModelPart topbone2;
    private final ModelPart bottombone3;
    private final ModelPart bottombone4;
    private final ModelPart topbone3;
    private final ModelPart bottombone5;
    private final ModelPart bottombone6;
    private final ModelPart topbone5;
    private final ModelPart bottombone9;
    private final ModelPart bottombone10;
    private final ModelPart topbone6;
    private final ModelPart bottombone11;
    private final ModelPart bottombone12;
    private final ModelPart topbone7;
    private final ModelPart bottombone13;
    private final ModelPart bottombone14;
    private final ModelPart topbone4;
    private final ModelPart bottombone7;
    private final ModelPart bottombone8;
    private final ModelPart topbone9;
    private final ModelPart bottombone17;
    private final ModelPart bottombone18;
    private final ModelPart topbone8;
    private final ModelPart bottombone15;
    private final ModelPart bottombone16;
    private final ModelPart topbone10;
    private final ModelPart bottombone19;
    private final ModelPart bottombone20;

    public SwibbleModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.topbone = this.body.getChild("topbone");
        this.bottombone = this.topbone.getChild("bottombone");
        this.bottombone2 = this.bottombone.getChild("bottombone2");
        this.topbone2 = this.body.getChild("topbone2");
        this.bottombone3 = this.topbone2.getChild("bottombone3");
        this.bottombone4 = this.bottombone3.getChild("bottombone4");
        this.topbone3 = this.body.getChild("topbone3");
        this.bottombone5 = this.topbone3.getChild("bottombone5");
        this.bottombone6 = this.bottombone5.getChild("bottombone6");
        this.topbone5 = this.body.getChild("topbone5");
        this.bottombone9 = this.topbone5.getChild("bottombone9");
        this.bottombone10 = this.bottombone9.getChild("bottombone10");
        this.topbone6 = this.body.getChild("topbone6");
        this.bottombone11 = this.topbone6.getChild("bottombone11");
        this.bottombone12 = this.bottombone11.getChild("bottombone12");
        this.topbone7 = this.body.getChild("topbone7");
        this.bottombone13 = this.topbone7.getChild("bottombone13");
        this.bottombone14 = this.bottombone13.getChild("bottombone14");
        this.topbone4 = this.body.getChild("topbone4");
        this.bottombone7 = this.topbone4.getChild("bottombone7");
        this.bottombone8 = this.bottombone7.getChild("bottombone8");
        this.topbone9 = this.body.getChild("topbone9");
        this.bottombone17 = this.topbone9.getChild("bottombone17");
        this.bottombone18 = this.bottombone17.getChild("bottombone18");
        this.topbone8 = this.body.getChild("topbone8");
        this.bottombone15 = this.topbone8.getChild("bottombone15");
        this.bottombone16 = this.bottombone15.getChild("bottombone16");
        this.topbone10 = this.body.getChild("topbone10");
        this.bottombone19 = this.topbone10.getChild("bottombone19");
        this.bottombone20 = this.bottombone19.getChild("bottombone20");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -20.0F, -9.0F, 18.0F, 20.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-6.0F, -17.0F, -6.0F, 12.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition topbone = body.addOrReplaceChild("topbone", CubeListBuilder.create().texOffs(48, 38).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.75F, 0.0F, 6.0F));

        PartDefinition bottombone = topbone.addOrReplaceChild("bottombone", CubeListBuilder.create().texOffs(48, 43).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone2 = bottombone.addOrReplaceChild("bottombone2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone2 = body.addOrReplaceChild("topbone2", CubeListBuilder.create().texOffs(48, 48).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 0.0F, 6.0F));

        PartDefinition bottombone3 = topbone2.addOrReplaceChild("bottombone3", CubeListBuilder.create().texOffs(48, 53).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone4 = bottombone3.addOrReplaceChild("bottombone4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone3 = body.addOrReplaceChild("topbone3", CubeListBuilder.create().texOffs(48, 58).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.25F, 0.0F, 6.0F));

        PartDefinition bottombone5 = topbone3.addOrReplaceChild("bottombone5", CubeListBuilder.create().texOffs(60, 38).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone6 = bottombone5.addOrReplaceChild("bottombone6", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone5 = body.addOrReplaceChild("topbone5", CubeListBuilder.create().texOffs(60, 53).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.25F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bottombone9 = topbone5.addOrReplaceChild("bottombone9", CubeListBuilder.create().texOffs(60, 58).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone10 = bottombone9.addOrReplaceChild("bottombone10", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone6 = body.addOrReplaceChild("topbone6", CubeListBuilder.create().texOffs(48, 63).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bottombone11 = topbone6.addOrReplaceChild("bottombone11", CubeListBuilder.create().texOffs(60, 63).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone12 = bottombone11.addOrReplaceChild("bottombone12", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone7 = body.addOrReplaceChild("topbone7", CubeListBuilder.create().texOffs(0, 64).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bottombone13 = topbone7.addOrReplaceChild("bottombone13", CubeListBuilder.create().texOffs(12, 64).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone14 = bottombone13.addOrReplaceChild("bottombone14", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone4 = body.addOrReplaceChild("topbone4", CubeListBuilder.create().texOffs(60, 43).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.25F, 0.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bottombone7 = topbone4.addOrReplaceChild("bottombone7", CubeListBuilder.create().texOffs(60, 48).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone8 = bottombone7.addOrReplaceChild("bottombone8", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone9 = body.addOrReplaceChild("topbone9", CubeListBuilder.create().texOffs(48, 68).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.25F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bottombone17 = topbone9.addOrReplaceChild("bottombone17", CubeListBuilder.create().texOffs(60, 68).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone18 = bottombone17.addOrReplaceChild("bottombone18", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone8 = body.addOrReplaceChild("topbone8", CubeListBuilder.create().texOffs(24, 64).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, 0.0F, -3.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottombone15 = topbone8.addOrReplaceChild("bottombone15", CubeListBuilder.create().texOffs(36, 64).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone16 = bottombone15.addOrReplaceChild("bottombone16", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition topbone10 = body.addOrReplaceChild("topbone10", CubeListBuilder.create().texOffs(0, 69).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottombone19 = topbone10.addOrReplaceChild("bottombone19", CubeListBuilder.create().texOffs(12, 69).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition bottombone20 = bottombone19.addOrReplaceChild("bottombone20", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(SwibbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(SwibbleAnimations.attack, limbSwing, limbSwingAmount, 1f, 1f);
        this.animate(entity.idleAnimationState, SwibbleAnimations.attack, ageInTicks, 1f);

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
