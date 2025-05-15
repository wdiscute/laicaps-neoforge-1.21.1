package com.wdiscute.laicaps.entity.snuffler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SnufflerModel<T extends SnufflerEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("snuffler"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;

    public SnufflerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 44.0F, -5.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 34).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(24, 55).addBox(-3.0F, -2.0F, -7.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -33.0F, -6.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 56).addBox(-2.0F, -4.2679F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, -2.25F, -2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 56).addBox(-2.0F, -4.2679F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -3.5F, -2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 55).addBox(4.0F, -2.0F, -5.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 34).addBox(-3.0F, -10.0F, 16.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 48).addBox(-7.0F, -2.0F, -5.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 40).addBox(-7.0F, -2.0F, 12.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55).addBox(4.0F, -2.0F, 12.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -12.0F, -6.0F, 16.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(SnufflerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

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
