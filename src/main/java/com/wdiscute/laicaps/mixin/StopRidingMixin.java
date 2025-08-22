package com.wdiscute.laicaps.mixin;

import com.mojang.authlib.GameProfile;
import com.wdiscute.laicaps.entity.rocket.RE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class StopRidingMixin extends Player
{

    @Shadow public abstract void displayClientMessage(Component chatComponent, boolean actionBar);

    public StopRidingMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile)
    {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "stopRiding", cancellable = true)
    private void stopRidingFix(CallbackInfo ci)
    {
        if(this.getVehicle() instanceof RE re)
        {
            if(re.getEntityData().get(RE.STATE) != 0)
            {
                displayClientMessage(Component.translatable("gui.laicaps.spaceship.unsafe"), true);
                ci.cancel();
            }
        }


    }
}
