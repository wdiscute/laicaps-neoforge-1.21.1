package com.wdiscute.laicaps.entity.rocket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;

public class RocketPart extends PartEntity<RocketEntity> {
    public RocketEntity parentMob;
    public String name;
    private EntityDimensions size;

    public RocketPart(RocketEntity parentMob, String name, float width, float height) {
        super(parentMob);
        this.size = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
        this.parentMob = parentMob;
        this.name = name;
        System.out.println("constructor! hello i am " + name + " and my parent is " + parentMob);
        System.out.println("constructor! hello i am " + name + " and my parent is " + parentMob);

    }

    @Override
    public void setPos(double x, double y, double z) {
        System.out.println("hello i am " + name + " and my parent is " + parentMob);
        super.setPos(x, y, z);

        if(parentMob != null)
        {
            this.xo = parentMob.xo;
            this.yo =  parentMob.yo;
            this.zo =  parentMob.zo;
            this.xOld =  parentMob.xOld;
            this.yOld =  parentMob.yOld;
            this.zOld =  parentMob.zOld;
        }

    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
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
