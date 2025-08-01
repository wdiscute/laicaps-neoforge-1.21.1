package com.wdiscute.laicaps.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FishingBobModel<T extends FishingBobEntity> extends HierarchicalModel<T>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("bluetalemodel"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;

    public FishingBobModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(8, 0).addBox(-0.5F, -6.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).mirror().addBox(0.5F, -7.75F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 11).addBox(0.5F, -3.25F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 14).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 11).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 6).addBox(-0.5F, -6.0F, 1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.5F, -6.75F, 2.75F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(FishingBobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.root().getAllParts().forEach(ModelPart::resetPose);
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
