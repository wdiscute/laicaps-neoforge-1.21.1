package com.wdiscute.laicaps.entity.magma;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class MagmaEntity extends Entity
{
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(MagmaEntity.class, EntityDataSerializers.INT);

    private final Random r = new Random();
    private final int random = r.nextInt(20);

    private final RockEntity[] shields;

    public int counter;

    private int shieldRegenCooldown;

    public MagmaEntity(EntityType<?> entityType, Level level)
    {
        super(entityType, level);

        this.shields = new RockEntity[]{null, null, null, null, null, null, null, null, };
    }

    @Override
    public void tick()
    {
        int state = entityData.get(STATE);

        //idle
        if (state == 0 && !level().isClientSide)
        {
            if (random + tickCount % 20 == random)
            {
                if (level().hasNearbyAlivePlayer(position().x, position().y, position().z, 10))
                {
                    entityData.set(STATE, 1);
                }
            }
        }


        //starting up
        if (state == 1)
        {
            shieldRegenCooldown--;
            counter++;

            if(shieldRegenCooldown < 1)
            {
                for (int i = 0; i < 8; i++)
                {
                    if(shields[i] == null && !level().isClientSide)
                    {
                        RockEntity re = new RockEntity(level(), this, i);
                        re.moveTo(position());
                        shields[i] = re;
                        level().addFreshEntity(re);
                        shieldRegenCooldown = 100;
                        break;
                    }
                }
            }

        }

        //first phase
        if (state == 2)
        {
            counter++;
        }

        //invuln
        if (state == 3)
        {

        }

        //second phase
        if (state == 4)
        {

        }

        //death anim
        if (state == 5)
        {

        }

        super.tick();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(STATE, 0);
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

    public void destroyShield(int order)
    {
        shields[order] = null;
    }
}
