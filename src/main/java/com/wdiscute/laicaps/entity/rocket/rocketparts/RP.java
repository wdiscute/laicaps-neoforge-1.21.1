package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;

public class RP extends PartEntity<RocketEntity>
{
    public final RocketEntity parentRocket;
    public final InteractionsEnum interaction;
    private final Vec3 offset;
    private final AABB aabb;
    private final boolean canRiderInteract;
    private final boolean canCollide;

    public RP(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RocketEntity parentRocket, InteractionsEnum interaction)
    {
        super(parentRocket);
        this.offset = offsetFromCenter;
        this.canRiderInteract = canRiderInteract;
        this.canCollide = canCollide;
        this.refreshDimensions();
        this.parentRocket = parentRocket;
        this.interaction = interaction;
        this.aabb = hitboxSize;
    }

    @Override
    protected AABB makeBoundingBox()
    {
        if (aabb == null) return super.makeBoundingBox();

        return aabb.move(position());
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if(parentRocket.getFirstPassenger() == player && interaction == InteractionsEnum.RIDE) return InteractionResult.PASS;
        if(hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;

        return parentRocket.interactWithPart(player, interaction);
    }


    public void updatePos(Vec3 vec3)
    {
        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;

        this.xo = position().x;
        this.yo = position().y;
        this.zo = position().z;
        this.xOld = position().x;
        this.yOld = position().y;
        this.zOld = position().z;

        super.setPos(x + offset.x, y + offset.y + parentRocket.getAcc(), z + offset.z);

    }


    @Override
    public boolean canRiderInteract()
    {
        return canRiderInteract;
    }

    @Override
    public boolean canBeCollidedWith()
    {


        return canCollide;
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
    }



    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getPickResult()
    {
        return this.parentRocket.getPickResult();
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        return this.isInvulnerableTo(source) ? false : this.parentRocket.hurt(source, amount);
    }

    @Override
    public boolean is(Entity entity)
    {
        return this == entity || this.parentRocket == entity;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean shouldBeSaved()
    {
        return false;
    }
}
