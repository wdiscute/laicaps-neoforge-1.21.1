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
    private final ModelPart root;
    private final ModelPart shipmain;
    private final ModelPart rocketright;
    private final ModelPart rocketleft;
    private final ModelPart feetright;
    private final ModelPart feetleft;
    private final ModelPart feetfront;
    private final ModelPart cockpit;
    private final ModelPart window;
    private final ModelPart cockpitdecorations;

    public RocketModel(ModelPart root) {
        this.root = root.getChild("root");
        this.shipmain = this.root.getChild("shipmain");
        this.rocketright = this.shipmain.getChild("rocketright");
        this.rocketleft = this.shipmain.getChild("rocketleft");
        this.feetright = this.shipmain.getChild("feetright");
        this.feetleft = this.shipmain.getChild("feetleft");
        this.feetfront = this.shipmain.getChild("feetfront");
        this.cockpit = this.root.getChild("cockpit");
        this.window = this.cockpit.getChild("window");
        this.cockpitdecorations = this.cockpit.getChild("cockpitdecorations");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, -6.0F));

        PartDefinition shipmain = root.addOrReplaceChild("shipmain", CubeListBuilder.create().texOffs(0, 0).addBox(-32.0F, -55.0F, -10.0F, 63.0F, 55.0F, 49.0F, new CubeDeformation(0.0F))
                .texOffs(0, 104).addBox(-20.5F, -62.0F, -1.0F, 43.0F, 7.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(126, 141).addBox(-41.5F, -62.0F, 12.0F, 80.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 141).addBox(-23.0F, -49.0F, 32.0F, 46.0F, 40.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rocketright = shipmain.addOrReplaceChild("rocketright", CubeListBuilder.create().texOffs(126, 176).addBox(19.0F, -92.0F, -10.0F, 17.0F, 32.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(224, 44).addBox(22.0F, -60.0F, -7.0F, 11.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(146, 131).addBox(9.0F, -78.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(190, 131).addBox(9.0F, -87.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(32, 245).addBox(24.0F, -99.0F, -5.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(72, 198).addBox(9.0F, -69.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(224, 9).addBox(-54.0F, -87.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(224, 0).addBox(-54.0F, -78.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(72, 207).addBox(-54.0F, -69.0F, -5.0F, 17.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 49.0F, 17.0F));

        PartDefinition cube_r1 = rocketright.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(72, 250).addBox(-1.3132F, -6.9254F, -1.0F, 5.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -103.0F, -3.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition rocketleft = shipmain.addOrReplaceChild("rocketleft", CubeListBuilder.create().texOffs(190, 176).addBox(14.0F, -92.0F, -10.0F, 17.0F, 32.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(224, 55).addBox(17.0F, -60.0F, -7.0F, 11.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(52, 245).addBox(18.0F, -99.0F, -5.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-67.0F, 49.0F, 17.0F));

        PartDefinition cube_r2 = rocketleft.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 250).addBox(-1.3132F, -6.9254F, -1.0F, 5.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, -103.0F, -3.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition feetright = shipmain.addOrReplaceChild("feetright", CubeListBuilder.create().texOffs(30, 225).addBox(-8.0F, 10.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(244, 243).addBox(-22.0F, 10.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(244, 223).addBox(-14.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 2.0F, 5.5F));

        PartDefinition cube_r3 = feetright.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(128, 250).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 10.25F, 9.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r4 = feetright.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(104, 250).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.25F, 9.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition feetleft = shipmain.addOrReplaceChild("feetleft", CubeListBuilder.create().texOffs(0, 245).addBox(-1.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(30, 235).addBox(-8.0F, -14.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(234, 131).addBox(-14.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(29.0F, 14.0F, 5.5F));

        PartDefinition cube_r5 = feetleft.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(116, 250).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.75F, 9.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r6 = feetleft.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(252, 103).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -10.75F, 9.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition feetfront = shipmain.addOrReplaceChild("feetfront", CubeListBuilder.create().texOffs(224, 85).addBox(-14.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(244, 233).addBox(-14.0F, -23.0F, 8.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 14.0F, -35.0F));

        PartDefinition cube_r7 = feetfront.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(250, 18).addBox(-13.0F, -4.0F, 8.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, -13.0F, -4.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r8 = feetfront.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(244, 253).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4F, -20.75F, 15.0F, 2.4876F, -0.0266F, -0.0346F));

        PartDefinition cube_r9 = feetfront.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(244, 253).addBox(-10.1504F, -11.5F, 1.1471F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.75F, 11.0F, 0.5303F, -0.151F, -0.0879F));

        PartDefinition cockpit = root.addOrReplaceChild("cockpit", CubeListBuilder.create().texOffs(126, 151).addBox(-14.0F, 13.0F, -10.0F, 24.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -24.0F, -23.0F));

        PartDefinition cube_r10 = cockpit.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(224, 18).addBox(-15.0F, -14.0F, -12.0F, 11.0F, 24.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r11 = cockpit.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(72, 223).addBox(-13.0F, -2.0F, -12.0F, 11.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 2.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r12 = cockpit.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 198).addBox(-13.0F, -2.0F, -12.0F, 11.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 2.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition window = cockpit.addOrReplaceChild("window", CubeListBuilder.create().texOffs(220, 151).addBox(-40.0F, -22.0F, -10.0F, 28.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 223).addBox(-40.0F, -22.0F, -8.0F, 2.0F, 22.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(194, 223).addBox(-14.0F, -22.0F, -8.0F, 2.0F, 22.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(146, 104).addBox(-40.0F, -22.0F, -10.0F, 28.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 4.0F, -2.0F));

        PartDefinition cockpitdecorations = cockpit.addOrReplaceChild("cockpitdecorations", CubeListBuilder.create().texOffs(224, 66).addBox(-25.0F, -2.0F, -9.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(250, 29).addBox(-14.0F, -2.0F, -8.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(72, 216).addBox(-24.0F, -5.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 6.0F, -1.0F));

        PartDefinition cube_r13 = cockpitdecorations.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(116, 198).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition cube_r14 = cockpitdecorations.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(84, 216).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.0F, -3.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r15 = cockpitdecorations.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(224, 95).addBox(-6.0F, -3.605F, -1.0092F, 13.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -4.0F, 2.0F, 0.0F, -1.5708F, 0.4363F));

        PartDefinition cube_r16 = cockpitdecorations.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 225).addBox(-6.0F, -11.5307F, -4.6955F, 13.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, 2.0F, 1.75F, 0.6545F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
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
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root()
    {
        return root;
    }

}
