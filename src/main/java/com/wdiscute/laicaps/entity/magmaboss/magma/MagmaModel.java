package com.wdiscute.laicaps.entity.magmaboss.magma;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MagmaModel<T extends MagmaEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("magma"), "main");
    private final ModelPart root;


    public MagmaModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 113).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-24.0F, -47.0F, -17.0F, 48.0F, 31.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-13.0F, -71.0F, -12.0F, 26.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(100, 112).addBox(-7.234F, -30.3572F, -8.0F, 16.0F, 31.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-44.0F, -20.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(100, 65).addBox(-7.234F, -30.3572F, -8.0F, 16.0F, 31.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(43.0F, -20.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(MagmaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
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