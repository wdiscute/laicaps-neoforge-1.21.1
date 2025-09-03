package com.wdiscute.laicaps.entity.magmaboss.rock;

import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RockEntity extends Projectile
{
    public RockEntity(EntityType<? extends RockEntity> entityType, Level level)
    {
        super(entityType, level);
    }


    public RockEntity(Level level, MagmaEntity magma)
    {
        super(ModEntities.SHIELD.get(), level);

        this.moveTo(magma.position());
        this.setOwner(magma);
    }


    @Override
    public void tick()
    {
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
        return false;
        //return super.hurt(source, amount);
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
        AABB box = new AABB(-2, -2, -2, 2, 2, 2);
        return box.move(position());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {

    }

}