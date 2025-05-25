package com.wdiscute.laicaps.entity.snuffler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.nimble.NimbleAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SnufflerModel<T extends SnufflerEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("snuffler"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart earL;
    private final ModelPart earR;
    private final ModelPart frontL;
    private final ModelPart frontR;
    private final ModelPart backR;
    private final ModelPart backL;

    public SnufflerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.head = this.body.getChild("head");
        this.nose = this.head.getChild("nose");
        this.earL = this.head.getChild("earL");
        this.earR = this.head.getChild("earR");
        this.frontL = this.root.getChild("frontL");
        this.frontR = this.root.getChild("frontR");
        this.backR = this.root.getChild("backR");
        this.backL = this.root.getChild("backL");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 44.0F, -5.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -7.0F, -11.0F, 16.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.0F, 5.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(46, 34).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 12.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 34).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -11.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 55).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -7.0F));

        PartDefinition earL = head.addOrReplaceChild("earL", CubeListBuilder.create(), PartPose.offset(5.0F, -6.0F, -1.0F));

        PartDefinition cube_r1 = earL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 56).addBox(-2.0F, -4.2679F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.5F, -1.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition earR = head.addOrReplaceChild("earR", CubeListBuilder.create(), PartPose.offset(-5.0F, -6.0F, -1.0F));

        PartDefinition cube_r2 = earR.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 56).addBox(-2.0F, -4.2679F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 3.75F, -1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition frontL = root.addOrReplaceChild("frontL", CubeListBuilder.create().texOffs(12, 55).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -24.0F, -3.0F));

        PartDefinition frontR = root.addOrReplaceChild("frontR", CubeListBuilder.create().texOffs(46, 48).addBox(-2.0F, 0.1241F, -1.0477F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -25.0F, -4.0F));

        PartDefinition backR = root.addOrReplaceChild("backR", CubeListBuilder.create().texOffs(46, 40).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -23.0F, 13.0F));

        PartDefinition backL = root.addOrReplaceChild("backL", CubeListBuilder.create().texOffs(0, 55).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -23.0F, 13.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(SnufflerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animateWalk(SnufflerAnimations.WALK, limbSwing, limbSwingAmount, 2f, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch)
    {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 25f);

        this.head.yRot = headYaw * ((float) Math.PI / 180f);
        this.head.xRot = headPitch * ((float) Math.PI / 180f);

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
