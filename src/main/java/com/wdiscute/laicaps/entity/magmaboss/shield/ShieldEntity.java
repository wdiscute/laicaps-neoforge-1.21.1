package com.wdiscute.laicaps.entity.magmaboss.shield;

import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShieldEntity extends Projectile
{
    public static final EntityDataAccessor<Integer> ORDER = SynchedEntityData.defineId(ShieldEntity.class, EntityDataSerializers.INT);

    public int order;

    public ShieldEntity(EntityType<? extends ShieldEntity> entityType, Level level)
    {
        super(entityType, level);
    }


    public ShieldEntity(Level level, MagmaEntity magma, int order)
    {
        super(ModEntities.SHIELD.get(), level);

        this.moveTo(magma.position());
        this.setOwner(magma);
        this.entityData.set(ORDER, order);
    }


    @Override
    public void tick()
    {
        this.order = entityData.get(ORDER);

        if(this.getOwner() == null) return;

        MagmaEntity magma = ((MagmaEntity) this.getOwner());

        float counter = level().getGameTime();
        if(level().isClientSide) counter += 1;

        double distanceOffset = Math.sin(counter / 20d);

        Vec3 vec3 = new Vec3(4 + distanceOffset, 0, 0);
        float angle = 45 * order + (counter % 360);

        double x = (vec3.x * Math.cos(Math.toRadians(angle)) - (vec3.z * Math.sin(Math.toRadians(angle))));
        double z = (vec3.x * Math.sin(Math.toRadians(angle)) + (vec3.z * Math.cos(Math.toRadians(angle))));

        Vec3 fina1 = magma.position().add(new Vec3(x, 2, z));
        setPos(fina1);

        this.xo = fina1.x;
        this.yo = fina1.y;
        this.zo = fina1.z;
        this.xOld = fina1.x;
        this.yOld = fina1.y;
        this.zOld = fina1.z;


        super.tick();
    }

    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        System.out.println(level());
        ((MagmaEntity) this.getOwner()).destroyShield(order);
        this.kill();
        return super.hurt(source, amount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag)
    {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag)
    {

    }

    @Override
    public AABB getBoundingBoxForCulling()
    {
        AABB box = new AABB(-10, -10, -10, 10, 10, 10);
        return box.move(position());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(ORDER, this.order);
    }

}