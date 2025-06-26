package com.wdiscute.laicaps.entity.moonray;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MoonrayModel<T extends MoonrayEntity> extends HierarchicalModel<T>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("moonraymodel"), "main");
    private final ModelPart root;
    private final ModelPart front;
    private final ModelPart middle;
    private final ModelPart back;

    public MoonrayModel(ModelPart root) {
        this.root = root.getChild("root");
        this.front = this.root.getChild("front");
        this.middle = this.root.getChild("middle");
        this.back = this.root.getChild("back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 11.0F));

        PartDefinition front = main.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -3.0F, -9.0046F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-5.0F, -1.0F, -5.0046F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 10).addBox(-2.0F, -1.0F, -9.0046F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 23).addBox(-4.0F, -1.0F, -7.0046F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-7.0F, 0.0F, -5.0046F, 14.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, -9.0F));

        PartDefinition middle = main.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(18, 27).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, 0.0F, 0.5F, 14.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, -9.5F));

        PartDefinition back = main.addOrReplaceChild("back", CubeListBuilder.create().texOffs(28, 17).addBox(-2.0F, -1.25F, -0.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-4.0F, -0.25F, -0.5F, 8.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.75F, -3.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MoonrayEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(MoonrayAnimations.SWIM, limbSwing, limbSwingAmount, 4f, 4f);

        //wobble when out of water
        if (!entity.isInWater()) {
            float f = 1.3F;
            float f1 = 1.7F;
            this.root.yRot = -f * 0.25F * Mth.sin(f1 * 0.6F * ageInTicks);
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
