package com.wdiscute.laicaps.entity.rocket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;

public class RocketPart extends PartEntity<RocketEntity> {
    public RocketEntity parentMob;
    private EntityDimensions size;
    private Vec3 offset = new Vec3(1,1,1);

    public RocketPart(RocketEntity parentMob, float width, float height, Vec3 offset) {
        super(parentMob);
        this.size = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
        this.offset = offset;
        this.parentMob = parentMob;
        this.setPos(parentMob.getX(),parentMob.getY(), parentMob.getZ());
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        System.out.println("level is " + level());
        return parentMob.interactAt(player, vec, hand);
    }

    public void updatePos(Vec3 vec3)
    {
        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;

        super.setPos(x + offset.x, y + offset.y, z + offset.z);
        this.xo = x + offset.x;
        this.yo = y + offset.y;
        this.zo = z + offset.z;
        this.xOld = x + offset.x;
        this.yOld = y + offset.y;
        this.zOld = z + offset.z;
    }


    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    public boolean isPickable() {
        return true;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    public boolean hurt(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : this.parentMob.hurt(source, amount);
    }

    public boolean is(Entity entity) {
        return this == entity || this.parentMob == entity;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        throw new UnsupportedOperationException();
    }

    public EntityDimensions getDimensions(Pose pose) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }
}
