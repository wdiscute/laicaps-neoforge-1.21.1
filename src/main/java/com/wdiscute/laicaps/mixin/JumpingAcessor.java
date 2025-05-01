package com.wdiscute.laicaps.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface JumpingAcessor {

    @Accessor
    boolean isJumping();
}
