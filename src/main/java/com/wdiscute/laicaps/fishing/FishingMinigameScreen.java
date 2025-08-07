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

    private static final ResourceLocation TEXTURE = Laicaps.rl("textures/gui/fishing/fishing.png");

    final ItemStack itemBeingFished;

    int completion = 20;
    int completionSmooth = 20;
    int tickCount = 0;

    Random r = new Random();

    int sweetSpot1Pos;
    int sweetSpotThinPos;

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

        sweetSpot1Pos = r.nextInt(360);
        sweetSpotThinPos = 60 + r.nextInt(240) + sweetSpot1Pos;

        if (sweetSpotThinPos > 360) sweetSpotThinPos -= 360;

        if (dif == 1)
        {
            hitReward = 10;
            hitRewardThin = 20;
            missPenalty = 10;

            currentSpeed = 6;

            shouldFlipRotation = false;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }


        if (dif == 2)
        {
            hitReward = 10;
            hitRewardThin = 15;
            missPenalty = 15;

            currentSpeed = 8;

            shouldFlipRotation = false;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }


        if (dif == 3)
        {
            hitReward = 10;
            hitRewardThin = 15;
            missPenalty = 10;

            currentSpeed = 10;

            shouldFlipRotation = true;
            shouldChangeSpeedEveryHit = false;

            shouldHaveThinSweetSpot = true;
        }

        if (dif == 4)
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
        RenderSystem.setShaderTexture(0, TEXTURE);


        //tank background
        guiGraphics.blit(
                TEXTURE, width / 2 - 42 - 100, height / 2 - 48,
                85, 97, 0, 0, 85, 97, 256, 256);

        //wheel background
        guiGraphics.blit(
                TEXTURE, width / 2 - 32, height / 2 - 32,
                64, 64, 0, 192, 64, 64, 256, 256);


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
                    TEXTURE, width / 2 - 8, height / 2 - 8 - 25,
                    16, 16, 16, 160, 16, 16, 256, 256);

            poseStack.popPose();
        }

        //SWEET_SPOT_2
        if (shouldHaveThinSweetSpot)
        {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            float centerX = width / 2f;
            float centerY = height / 2f;

            poseStack.translate(centerX, centerY, 0);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(sweetSpotThinPos)));
            poseStack.translate(-centerX, -centerY, 0);

            guiGraphics.blit(
                    TEXTURE, width / 2 - 8, height / 2 - 8 - 25,
                    16, 16, 48, 160, 16, 16, 256, 256);

            poseStack.popPose();
        }


        //wheel second layer
        guiGraphics.blit(
                TEXTURE, width / 2 - 32, height / 2 - 32,
                64, 64, 64, 192, 64, 64, 256, 256);

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

            //16 offset on y for texture centering
            guiGraphics.blit(
                    TEXTURE, width / 2 - 32, height / 2 - 32 - 16,
                    64, 64, 128, 192, 64, 64, 256, 256);

            poseStack.popPose();
        }

        //
        guiGraphics.blit(
                TEXTURE, width / 2 - 16, height / 2 - 16,
                32, 32, 208, 208, 32, 32, 256, 256);

        //fishing rod
        guiGraphics.blit(
                TEXTURE, width / 2 - 32 - 70, height / 2 - 24 - 57,
                64, 48, 192, 0, 64, 48, 256, 256);

        //fishing line
        guiGraphics.blit(
                TEXTURE, width / 2 - 6 - 102, height / 2 - 56 - 18,
                16, 112 - completionSmooth,
                176, 0 + completionSmooth,
                16, 112 - completionSmooth,
                256, 256);

        //FISH
        {
            guiGraphics.renderItem(itemBeingFished, width / 2 - 8 - 100, height / 2 - 8 + 35 - completionSmooth);
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

            float pointerPrecise = (pointerPos + ((currentSpeed * partial) * currentRotation));


            //if hit sweet spot
            if (Math.abs(sweetSpot1Pos - pointerPrecise) < 12 || Math.abs(sweetSpot1Pos - pointerPrecise) > 348)
            {
                level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1, false);

                //reposition sweet spot without overlapping old position or the other sweet spot
                int attempted;
                do
                {
                    attempted = (60 + r.nextInt(240) + sweetSpotThinPos) % 360;
                }
                while (Math.abs(attempted - sweetSpotThinPos) < 30);

                sweetSpot1Pos = attempted;

                //difficulty checks
                if (shouldFlipRotation) currentRotation *= -1;
                if (shouldChangeSpeedEveryHit) currentSpeed = minSpeed + r.nextInt(maxSpeed - minSpeed);

                completion += hitReward;
                safe = true;
            }

            //if hit sweet spot thin
            if (Math.abs(sweetSpotThinPos - pointerPrecise) < 5 || Math.abs(sweetSpotThinPos - pointerPrecise) > 355)
            {
                if (!shouldHaveThinSweetSpot) return false;

                level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1, false);

                //reposition sweet spot without overlapping old position or the other sweet spot
                int attempted;
                do
                {
                    attempted = (60 + r.nextInt(240) + sweetSpotThinPos) % 360;
                }
                while (Math.abs(attempted - sweetSpot1Pos) < 30);

                sweetSpotThinPos = attempted;

                //difficulty checks
                if (shouldFlipRotation) currentRotation *= -1;
                if (shouldChangeSpeedEveryHit) currentSpeed = minSpeed + r.nextInt(maxSpeed - minSpeed);

                completion += hitRewardThin;
                safe = true;
            }


            if (!safe)
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

        if (pointerPos > 360) pointerPos -= 360;
        if (pointerPos < 0) pointerPos += 360;

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

        //if (completionSmooth > 10)
        if (completionSmooth > 75)
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
