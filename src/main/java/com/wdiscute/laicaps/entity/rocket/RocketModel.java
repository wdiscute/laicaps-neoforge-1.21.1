package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.gecko.GeckoAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class RocketModel<T extends RocketEntity> extends HierarchicalModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("rocket"), "main");
    private final ModelPart Root;

    public RocketModel(ModelPart root)
    {
        this.Root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(4, 2).addBox(1.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rocket = main.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -16.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 39).addBox(-8.0F, -11.0F, -8.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(5.0F, -11.0F, -8.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 42).addBox(5.0F, -11.0F, 5.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 39).addBox(-8.0F, -11.0F, 5.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 24).addBox(-2.0F, -37.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(RocketEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.shakeAnimationState, RocketAnimation.ANIM_ROCKET_SHAKE, ageInTicks, 1f);

    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color)
    {
        Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root()
    {
        return Root;
    }

}
