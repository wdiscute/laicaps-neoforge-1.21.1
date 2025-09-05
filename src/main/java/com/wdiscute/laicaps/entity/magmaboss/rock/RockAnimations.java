package com.wdiscute.laicaps.entity.magmaboss.rock;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class RockAnimations
{

    public static final AnimationDefinition SPAWN = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation(
                    "cube", new AnimationChannel(
                            AnimationChannel.Targets.SCALE,
                            new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
            .build();
}