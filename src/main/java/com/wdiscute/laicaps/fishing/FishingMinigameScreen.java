package com.wdiscute.laicaps.fishing;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Random;

public class
FishingMinigameScreen extends Screen implements GuiEventListener
{

    private static final ResourceLocation CIRCLE = Laicaps.rl("textures/gui/fishing/circle.png");
    private static final ResourceLocation METER = Laicaps.rl("textures/gui/fishing/meter.png");
    private static final ResourceLocation CIRCLE_SWEET_SPOT = Laicaps.rl("textures/gui/fishing/circle_sweet_spot.png");
    private static final ResourceLocation CIRCLE_SWEET_SPOT_THIN = Laicaps.rl("textures/gui/fishing/circle_sweet_spot_thin.png");
    private static final ResourceLocation POINTER = Laicaps.rl("textures/gui/fishing/pointer.png");

    final ItemStack itemBeingFished;

    int completion = 20;
    int completionSmooth = 20;
    int tickCount = 0;

    Random r = new Random();

    int sweetSpot1Pos = r.nextInt(360);
    int sweetSpot2Pos = r.nextInt(360);

    int hitReward = 10;
    int hitRewardThin = 15;
    int missPenalty = 15;

    int pointerPos = 0;

    boolean shouldFlipRotation;
    boolean shouldChangeSpeedEveryHit;
    boolean shouldHaveThinSweetSpot;

    int currentRotation = 1;

    int currentSpeed;
    int minSpeed;
    int maxSpeed;

    float partial;

    public FishingMinigameScreen(Component title, ItemStack stack, int dif)
    {
        super(title);
        this.itemBeingFished = stack;

        if(dif == 1)
        {
            hitReward = 10;
            hitRewardThin = 20;
            missPenalty = 10;

            currentSpeed = 6;

            shouldFlipRotation = false;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }


        if(dif == 2)
        {
            hitReward = 10;
            hitRewardThin = 15;
            missPenalty = 15;

            currentSpeed = 8;

            shouldFlipRotation = false;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }


        if(dif == 3)
        {
            hitReward = 10;
            hitRewardThin = 15;
            missPenalty = 10;

            currentSpeed = 10;

            shouldFlipRotation = true;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }

        if(dif == 4)
        {
            hitReward = 5;
            hitRewardThin = 15;
            missPenalty = 16;

            currentSpeed = 7;

            shouldFlipRotation = true;
            shouldChangeSpeedEveryHit = true;
            minSpeed = 7;
            maxSpeed = 20;

            shouldHaveThinSweetSpot = true;
        }



    }


    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        partial = partialTick;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, CIRCLE);

        guiGraphics.blit(
                CIRCLE, width / 2 - 32, height / 2 - 32,
                0, 0, 64, 64, 64, 64);


        //SWEET_SPOT_1
        {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            float centerX = width / 2f;
            float centerY = height / 2f;

            poseStack.translate(centerX, centerY, 0);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(sweetSpot1Pos)));
            poseStack.translate(-centerX, -centerY, 0);

            guiGraphics.blit(
                    CIRCLE_SWEET_SPOT, width / 2 - 32, height / 2 - 32,
                    0, 0, 64, 64, 64, 64);

            poseStack.popPose();
        }

        //SWEET_SPOT_2
        if(shouldHaveThinSweetSpot){
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            float centerX = width / 2f;
            float centerY = height / 2f;

            poseStack.translate(centerX, centerY, 0);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(sweetSpot2Pos)));
            poseStack.translate(-centerX, -centerY, 0);

            guiGraphics.blit(
                    CIRCLE_SWEET_SPOT_THIN, width / 2 - 32, height / 2 - 32,
                    0, 0, 64, 64, 64, 64);

            poseStack.popPose();
        }


        //POINTER
        {
            //TODO make it not use the partial ticks from rendering thread of whatever honestly its just nerd stuff that no one will care about
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            float centerX = width / 2f;
            float centerY = height / 2f;

            poseStack.translate(centerX, centerY, 0);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(pointerPos + ((currentSpeed * partialTick) * currentRotation))));
            poseStack.translate(-centerX, -centerY, 0);

            guiGraphics.blit(
                    POINTER, width / 2 - 32, height / 2 - 32,
                    0, 0, 64, 64, 64, 64);

            poseStack.popPose();
        }


        //METER
        {
            guiGraphics.blit(
                    METER, width / 2 - 128 - 60, height / 2 - 128 - 16,
                    0, 0, 256, 256, 256, 256);
        }


        //FISH
        {
            guiGraphics.renderItem(itemBeingFished, width / 2 - 8 - 60, height / 2 - 8 + 20 - completionSmooth);
        }
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == Minecraft.getInstance().options.keyJump.getKey().getValue())
        {
            Minecraft.getInstance().player.swing(InteractionHand.MAIN_HAND, true);

            boolean safe = false;

            Vec3 pos = Minecraft.getInstance().player.position();
            ClientLevel level = Minecraft.getInstance().level;

            if(Math.abs(sweetSpot1Pos - (pointerPos + ((currentSpeed * partial) * currentRotation))) < 20 || Math.abs(sweetSpot1Pos - (pointerPos + ((currentSpeed * partial) * currentRotation))) > 340)
            {
                level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1, false);

                sweetSpot1Pos = 60 + r.nextInt(240) + sweetSpot1Pos;
                if(sweetSpot1Pos > 360) sweetSpot1Pos -= 360;

                if(shouldFlipRotation)
                {
                    currentRotation *= -1;
                }

                if(shouldChangeSpeedEveryHit)
                {
                    currentSpeed = minSpeed + r.nextInt(maxSpeed - minSpeed);
                }


                completion += hitReward;
                safe = true;
            }

            if(Math.abs(sweetSpot2Pos - (pointerPos + ((currentSpeed * partial) * currentRotation))) < 5 || Math.abs(sweetSpot2Pos - (pointerPos + ((currentSpeed * partial) * currentRotation))) > 355)
            {
                if(!shouldHaveThinSweetSpot) return false;

                level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1, false);

                sweetSpot2Pos = 60 + r.nextInt(240) + sweetSpot2Pos;

                if(shouldFlipRotation)
                {
                    currentRotation *= -1;
                }

                if(shouldChangeSpeedEveryHit)
                {
                    currentSpeed = minSpeed + r.nextInt(maxSpeed - minSpeed);
                }

                if(sweetSpot2Pos > 360) sweetSpot2Pos -= 360;

                completion += hitRewardThin;
                safe = true;
            }


            if(!safe)
            {
                level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1, 1, false);
                completion -= 5;
            }

        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void tick()
    {
        pointerPos += currentSpeed * currentRotation;

        if(pointerPos > 360) pointerPos -= 360;
        if(pointerPos < 0) pointerPos += 360;

        tickCount++;


        completionSmooth += (int) Math.signum(completion - completionSmooth);

        if (tickCount % 5 == 0)
        {
            completion--;
        }

        if (completionSmooth < 0)
        {
            PacketDistributor.sendToServer(new Payloads.FishingCompletedPayload(-1));

            this.onClose();
        }

        if (completionSmooth > 70)
        {
            PacketDistributor.sendToServer(new Payloads.FishingCompletedPayload(tickCount));

            this.onClose();
        }

    }

    @Override
    public void onClose()
    {
        PacketDistributor.sendToServer(new Payloads.FishingCompletedPayload(-1));
        this.minecraft.popGuiLayer();
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

}
