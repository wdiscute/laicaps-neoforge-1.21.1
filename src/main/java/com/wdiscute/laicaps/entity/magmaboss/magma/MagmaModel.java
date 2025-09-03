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
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Laicaps.rl("rocket"), "main");
    private final ModelPart root;
    private final ModelPart shipmain;
    private final ModelPart door;
    private final ModelPart doorbottom;
    private final ModelPart doortop;
    private final ModelPart rocketright;
    private final ModelPart rocketleft;
    private final ModelPart feetright;
    private final ModelPart feetleft;
    private final ModelPart feetfront;
    private final ModelPart cockpit;
    private final ModelPart window;
    private final ModelPart cockpitdecorations;
    private final ModelPart globe;

    public MagmaModel(ModelPart root) {
        this.root = root.getChild("root");
        this.shipmain = this.root.getChild("shipmain");
        this.door = this.shipmain.getChild("door");
        this.doorbottom = this.door.getChild("doorbottom");
        this.doortop = this.doorbottom.getChild("doortop");
        this.rocketright = this.shipmain.getChild("rocketright");
        this.rocketleft = this.shipmain.getChild("rocketleft");
        this.feetright = this.shipmain.getChild("feetright");
        this.feetleft = this.shipmain.getChild("feetleft");
        this.feetfront = this.shipmain.getChild("feetfront");
        this.cockpit = this.root.getChild("cockpit");
        this.window = this.cockpit.getChild("window");
        this.cockpitdecorations = this.cockpit.getChild("cockpitdecorations");
        this.globe = this.cockpitdecorations.getChild("globe");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, -6.0F));

        PartDefinition shipmain = root.addOrReplaceChild("shipmain", CubeListBuilder.create().texOffs(0, 0).addBox(-32.0F, -55.0F, -10.0F, 64.0F, 55.0F, 52.0F, new CubeDeformation(0.0F))
                .texOffs(0, 146).addBox(-39.0F, -62.0F, 15.0F, 78.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 107).addBox(-22.0F, -62.0F, 0.0F, 44.0F, 7.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(246, 208).addBox(-9.0F, -6.0F, -10.0F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition door = shipmain.addOrReplaceChild("door", CubeListBuilder.create().texOffs(0, 156).addBox(-23.0F, -50.0F, 42.0F, 46.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(232, 53).addBox(-23.0F, -48.0F, 49.0F, 9.0F, 46.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(14.0F, -48.0F, 49.0F, 9.0F, 46.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(228, 208).addBox(21.0F, -48.0F, 42.0F, 2.0F, 46.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(232, 0).addBox(-23.0F, -48.0F, 42.0F, 2.0F, 46.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(152, 134).addBox(-23.0F, -2.0F, 42.0F, 46.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition doorbottom = door.addOrReplaceChild("doorbottom", CubeListBuilder.create().texOffs(204, 145).addBox(-14.0F, -23.0F, 0.0F, 28.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 49.0F));

        PartDefinition doortop = doorbottom.addOrReplaceChild("doortop", CubeListBuilder.create().texOffs(0, 214).addBox(-14.0F, -23.0F, -1.0F, 28.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 1.0F));

        PartDefinition rocketright = shipmain.addOrReplaceChild("rocketright", CubeListBuilder.create().texOffs(0, 167).addBox(19.0F, -92.0F, -7.0F, 17.0F, 32.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(64, 167).addBox(22.0F, -60.0F, -4.0F, 11.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(78, 252).addBox(25.0F, -99.0F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 49.0F, 17.0F));

        PartDefinition tankconnector_r1 = rocketright.addOrReplaceChild("tankconnector_r1", CubeListBuilder.create().texOffs(158, 253).addBox(-1.3132F, -6.9254F, -1.0F, 5.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -103.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition rocketleft = shipmain.addOrReplaceChild("rocketleft", CubeListBuilder.create().texOffs(64, 181).addBox(14.0F, -92.0F, -7.0F, 17.0F, 32.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(204, 170).addBox(17.0F, -60.0F, -4.0F, 11.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(98, 252).addBox(20.0F, -99.0F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-67.0F, 49.0F, 17.0F));

        PartDefinition topconnector_r1 = rocketleft.addOrReplaceChild("topconnector_r1", CubeListBuilder.create().texOffs(142, 253).addBox(-1.3132F, -7.9254F, -1.0F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.0F, -103.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition feetright = shipmain.addOrReplaceChild("feetright", CubeListBuilder.create().texOffs(244, 170).addBox(-7.25F, 10.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(250, 40).addBox(-21.25F, 10.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(250, 20).addBox(-14.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 2.0F, 5.5F));

        PartDefinition cube_r1 = feetright.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(198, 253).addBox(-10.0F, -8.0F, 2.0F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, 10.25F, 9.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r2 = feetright.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(210, 253).addBox(1.4282F, -10.7942F, 2.0F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, 10.25F, 9.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition feetleft = shipmain.addOrReplaceChild("feetleft", CubeListBuilder.create().texOffs(246, 251).addBox(-1.25F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(250, 10).addBox(-8.0F, -14.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(250, 0).addBox(-14.25F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(29.0F, 14.0F, 5.5F));

        PartDefinition cube_r3 = feetleft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(186, 253).addBox(-10.0F, -8.0F, 2.0F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -1.75F, 9.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r4 = feetleft.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(254, 50).addBox(-10.0F, -7.0F, 2.0F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.25F, -10.75F, 9.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition feetfront = shipmain.addOrReplaceChild("feetfront", CubeListBuilder.create().texOffs(166, 145).addBox(-14.0F, -2.0F, 9.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(118, 253).addBox(-13.1F, -17.0F, 4.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(250, 30).addBox(-14.0F, -23.0F, 8.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 14.0F, -35.0F));

        PartDefinition cube_r5 = feetfront.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(254, 66).addBox(-10.0F, -11.0F, 2.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4F, -20.75F, 15.0F, 2.4876F, 0.0F, 0.0F));

        PartDefinition cube_r6 = feetfront.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(174, 253).addBox(-1.6504F, -15.25F, 1.1471F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cockpit = root.addOrReplaceChild("cockpit", CubeListBuilder.create().texOffs(110, 156).addBox(-14.0F, 13.0F, -10.0F, 24.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -24.0F, -23.0F));

        PartDefinition front_r1 = cockpit.addOrReplaceChild("front_r1", CubeListBuilder.create().texOffs(22, 239).addBox(-15.0F, -14.0F, -12.0F, 11.0F, 24.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition left_r1 = cockpit.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(200, 181).addBox(-13.0F, -2.0F, -12.0F, 11.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 2.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition right_r1 = cockpit.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(128, 181).addBox(-13.0F, -2.0F, -12.0F, 11.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 2.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition window = cockpit.addOrReplaceChild("window", CubeListBuilder.create().texOffs(60, 228).addBox(-40.0F, -22.0F, -10.0F, 28.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 208).addBox(-40.0F, -22.0F, -8.0F, 2.0F, 22.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(178, 208).addBox(-14.0F, -22.0F, -8.0F, 2.0F, 22.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(152, 107).addBox(-40.0F, -22.0F, -10.0F, 28.0F, 2.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 4.0F, -2.0F));

        PartDefinition cockpitdecorations = cockpit.addOrReplaceChild("cockpitdecorations", CubeListBuilder.create().texOffs(246, 220).addBox(-25.0F, -2.0F, -9.0F, 5.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(48, 239).addBox(-14.0F, -2.0F, -8.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 167).addBox(-23.0F, -5.0F, -7.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 6.0F, -1.0F));

        PartDefinition leftscreenholder_r1 = cockpitdecorations.addOrReplaceChild("leftscreenholder_r1", CubeListBuilder.create().texOffs(120, 228).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition book_r1 = cockpitdecorations.addOrReplaceChild("book_r1", CubeListBuilder.create().texOffs(244, 101).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.0F, -3.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition leftscreen_r1 = cockpitdecorations.addOrReplaceChild("leftscreen_r1", CubeListBuilder.create().texOffs(48, 252).addBox(-6.0F, -3.605F, -1.0092F, 13.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -4.0F, 2.0F, 0.0F, -1.5708F, 0.4363F));

        PartDefinition mainscreen_r1 = cockpitdecorations.addOrReplaceChild("mainscreen_r1", CubeListBuilder.create().texOffs(246, 239).addBox(-6.0F, -11.5307F, -4.6955F, 13.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, 2.0F, 1.75F, 0.6545F, 0.0F, 0.0F));

        PartDefinition globe = cockpitdecorations.addOrReplaceChild("globe", CubeListBuilder.create(), PartPose.offset(-22.5F, -5.5F, -6.5F));

        PartDefinition cube_r7 = globe.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(232, 101).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6643F, -0.625F, 0.4296F));

        return LayerDefinition.create(meshdefinition, 512, 512);
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