package com.wdiscute.laicaps.fishing;

import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;

public class FishingBobEntity extends Projectile
{
    public final Player player;
    private int life;
    private FishHookState currentState;
    private Entity hookedIn;
    private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(FishingBobEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_BITING = SynchedEntityData.defineId(FishingBobEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean biting;
    private int nibble;
    private int timeUntilLured;
    private int timeUntilHooked;
    private float fishAngle;
    private boolean openWater;
    private int outOfWaterTime;

    enum FishHookState
    {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING;

        FishHookState()
        {
        }
    }

    public FishingBobEntity(EntityType<? extends FishingBobEntity> entityType, Level level)
    {
        super(entityType, level);
        player = null;
    }

    public FishingBobEntity(Level level, Player player)
    {
        super(ModEntities.FISHING_BOB.get(), level);
        this.player = player;
        this.setOwner(player);
        float f = player.getXRot();
        float f1 = player.getYRot();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        double d0 = player.getX() - (double) f3 * 0.3;
        double d1 = player.getEyeY();
        double d2 = player.getZ() - (double) f2 * 0.3;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3((double) (-f3), (double) Mth.clamp(-(f5 / f4), -5.0F, 5.0F), (double) (-f2));
        double d3 = vec3.length();
        vec3 = vec3.multiply(0.6 / d3 + this.random.triangle((double) 0.5F, 0.0103365), 0.6 / d3 + this.random.triangle((double) 0.5F, 0.0103365), 0.6 / d3 + this.random.triangle((double) 0.5F, 0.0103365));
        this.setDeltaMovement(vec3);
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) 180.0F / (double) (float) Math.PI));
        this.setXRot((float) (Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double) 180.0F / (double) (float) Math.PI));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    private boolean shouldStopFishing(Player player)
    {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        boolean flag = main.is(ModItems.CUSTOM_FISHING_ROD);
        boolean flag1 = off.is(ModItems.CUSTOM_FISHING_ROD);
        if (!player.isRemoved() && player.isAlive() && (flag || flag1) && !(this.distanceToSqr(player) > (double) 1024.0F))
        {
            return false;
        }
        else
        {
            System.out.println("discarding");
            System.out.println(flag);
            System.out.println(flag1);
            System.out.println(!player.isRemoved() && player.isAlive() && (flag || flag1) && !(this.distanceToSqr(player) > (double) 1024.0F));
            this.discard();
            return true;
        }
    }

    public void tick()
    {
        super.tick();

        System.out.println("state " + currentState);

        Player player = ((Player) this.getOwner());
        if (player == null)
        {
            this.discard();
        }
        else if (this.level().isClientSide || !this.shouldStopFishing(player))
        {
            if (this.onGround())
            {
                ++this.life;
                if (this.life >= 1200)
                {
                    System.out.println("discarded on " + level());
                    this.discard();
                    return;
                }
            }
            else
            {
                this.life = 0;
            }

            float f = 0.0F;
            BlockPos blockpos = this.blockPosition();
            FluidState fluidstate = this.level().getFluidState(blockpos);
            if (fluidstate.is(FluidTags.WATER))
            {
                f = fluidstate.getHeight(this.level(), blockpos);
            }

            boolean flag = f > 0.0F;
            if (this.currentState == FishHookState.FLYING)
            {
                if (this.hookedIn != null)
                {
                    this.setDeltaMovement(Vec3.ZERO);
                    this.currentState = FishHookState.HOOKED_IN_ENTITY;
                    return;
                }

                if (flag)
                {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.3, 0.2, 0.3));
                    this.currentState = FishHookState.BOBBING;
                    return;
                }

                this.checkCollision();
            }
            else
            {
                if (this.currentState == FishHookState.HOOKED_IN_ENTITY)
                {
                    if (this.hookedIn != null)
                    {
                        if (!this.hookedIn.isRemoved() && this.hookedIn.level().dimension() == this.level().dimension())
                        {
                            this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8), this.hookedIn.getZ());
                        }
                        else
                        {
                            this.setHookedEntity(null);
                            this.currentState = FishHookState.FLYING;
                        }
                    }

                    return;
                }

                if (this.currentState == FishHookState.BOBBING)
                {
                    Vec3 vec3 = this.getDeltaMovement();
                    double d0 = this.getY() + vec3.y - (double) blockpos.getY() - (double) f;
                    if (Math.abs(d0) < 0.01)
                    {
                        d0 += Math.signum(d0) * 0.1;
                    }

                    this.setDeltaMovement(vec3.x * 0.9, vec3.y - d0 * (double) this.random.nextFloat() * 0.2, vec3.z * 0.9);
                    if (this.nibble <= 0 && this.timeUntilHooked <= 0)
                    {
                        this.openWater = true;
                    }
                    else
                    {
                        this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater();
                    }

                    if (flag)
                    {
                        this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                        if (this.biting)
                        {
                            //this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.1 * this.syncronizedRandom.nextFloat() * (double) this.syncronizedRandom.nextFloat(), 0.0F));
                        }

                        if (!this.level().isClientSide)
                        {
                            //this.catchingFish(blockpos);
                        }
                    }
                    else
                    {
                        this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
                    }
                }
            }

            if (!fluidstate.is(FluidTags.WATER))
            {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.03, 0.0F));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            if (this.currentState == FishHookState.FLYING && (this.onGround() || this.horizontalCollision))
            {
                this.setDeltaMovement(Vec3.ZERO);
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.92));
            this.reapplyPosition();
        }

    }


    private boolean calculateOpenWater()
    {
        return true;
    }

    private void checkCollision()
    {
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() == HitResult.Type.MISS || !EventHooks.onProjectileImpact(this, hitresult))
        {
            this.onHit(hitresult);
        }

    }


    private void setHookedEntity(@Nullable Entity hookedEntity)
    {
        this.hookedIn = hookedEntity;
        this.getEntityData().set(DATA_HOOKED_ENTITY, hookedEntity == null ? 0 : hookedEntity.getId() + 1);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(DATA_HOOKED_ENTITY, 0);
        builder.define(DATA_BITING, false);
    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> key)
    {
        if (DATA_HOOKED_ENTITY.equals(key))
        {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            this.hookedIn = i > 0 ? this.level().getEntity(i - 1) : null;
        }

        if (DATA_BITING.equals(key))
        {
            this.biting = this.getEntityData().get(DATA_BITING);
            if (this.biting)
            {
                //this.setDeltaMovement(this.getDeltaMovement().x, -0.4F * Mth.nextFloat(this.syncronizedRandom, 0.6F, 1.0F), this.getDeltaMovement().z);
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag)
    {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag)
    {

    }

}
