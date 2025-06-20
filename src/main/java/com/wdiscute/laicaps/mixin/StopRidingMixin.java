package com.wdiscute.laicaps.mixin;

import com.mojang.authlib.GameProfile;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class StopRidingMixin extends Player
{

    @Shadow @Final private static Logger LOGGER;

    public StopRidingMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile)
    {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "stopRiding", cancellable = true)
    private void stopRidingFix(CallbackInfo ci)
    {
        if(this.getVehicle() instanceof RocketEntity re)
        {
            System.out.println(re);
            if(re.getEntityData().get(RocketEntity.STATE) != 0)
            {
                ci.cancel();
            }
        }
    }

    @Override
    public boolean isSpectator()
    {
        return false;
    }

    @Override
    public boolean isCreative()
    {
        return false;
    }
}
