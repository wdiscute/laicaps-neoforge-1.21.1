package com.wdiscute.laicaps.entity.glimpuff;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.block.single.MoonshadeKelpBlock;
import com.wdiscute.laicaps.ModParticles;
import com.wdiscute.laicaps.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GlimpuffEntity extends AbstractFish
{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int fullTime = 0;
    public static final EntityDataAccessor<Boolean> FULL = SynchedEntityData.defineId(GlimpuffEntity.class, EntityDataSerializers.BOOLEAN);

    public GlimpuffEntity(EntityType<? extends GlimpuffEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(FULL, false);
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimeout <= 0)
        {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        }
        else
        {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if (player.getItemInHand(hand).is(Items.CARROT) && !entityData.get(FULL))
        {
            this.entityData.set(FULL, true);
            player.getItemInHand(hand).shrink(1);
            if (!level().isClientSide)
            {
                fullTime = 2400;
                level().playSound(null, blockPosition(), ModSounds.GLIMPUFF_CARROT.get(), SoundSource.AMBIENT);
            }
            return InteractionResult.SUCCESS;
        }

        if (player.getItemInHand(hand).is(ModItems.MOONSHADE_FRUIT) && !entityData.get(FULL))
        {
            this.entityData.set(FULL, true);
            player.getItemInHand(hand).shrink(1);
            if (!level().isClientSide)
            {
                fullTime = 2400;

                //TODO need a good sound
                //level().playSound(null, blockPosition(), ModSounds.GLIMPUFF_CARROT.get(), SoundSource.AMBIENT);
                level().playSound(null, blockPosition(), SoundEvents.FOX_EAT, SoundSource.AMBIENT);
            }
            return InteractionResult.SUCCESS;
        }
        return super.interactAt(player, vec, hand);
    }


    @Override
    public void tick()
    {
        super.tick();

        if (level().isClientSide)
        {
            this.setupAnimationStates();
            if (random.nextFloat() > 0.8 && this.entityData.get(FULL))
            {
                level().addParticle(
                        ModParticles.LUNARVEIL_PARTICLES.get(),
                        getX() + random.nextFloat() * 2 - 1f,
                        getY() + random.nextFloat() * 2 - 0.5f,
                        getZ() + random.nextFloat() * 2 - 1f,
                        0, 0, 0);
            }
        }


        if (!level().isClientSide)
        {
            fullTime--;
            if (fullTime <= 0 && entityData.get(FULL))
            {
                entityData.set(FULL, false);
                return;
            }
            BlockState bs = level().getBlockState(blockPosition());
            if (bs.is(ModBlocks.MOONSHADE_KELP) && !entityData.get(FULL))
            {
                if (bs.getValue(MoonshadeKelpBlock.GROWN))
                {
                    level().playSound(null, blockPosition(), SoundEvents.FOX_EAT, SoundSource.AMBIENT);
                    fullTime = 2400;
                    this.entityData.set(FULL, true);
                    bs = bs.setValue(MoonshadeKelpBlock.GROWN, false);
                    level().setBlockAndUpdate(blockPosition(), bs);
                }
            }
        }

    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(ModItems.GLIMPUFF_BUCKET.get());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        fullTime = compound.getInt("fulltime");
        this.entityData.set(FULL, compound.getBoolean("full"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("fulltime", fullTime);
        compound.putBoolean("full", this.entityData.get(FULL));
    }
}
