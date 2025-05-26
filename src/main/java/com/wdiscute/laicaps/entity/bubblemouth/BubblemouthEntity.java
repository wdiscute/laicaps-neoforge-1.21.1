package com.wdiscute.laicaps.entity.bubblemouth;

import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.particle.ModParticles;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector2d;

public class BubblemouthEntity extends AbstractFish
{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public BubblemouthEntity(EntityType<? extends BubblemouthEntity> entityType, Level level)
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
    public void tick()
    {
        super.tick();

        if (level().isClientSide)
        {
            this.setupAnimationStates();
            if (getRandom().nextFloat() > 0.95)
            {
                level().addParticle(
                        ModParticles.CHASE_PUZZLE_PARTICLES.get(),
                        getX() + (getLookAngle().x * 0.5),
                        getY() + 0.2,
                        getZ() + (getLookAngle().z * 0.5),
                        0,
                        0,
                        0);
            }
        }
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(ModItems.BUBBLEMOUTH_BUCKET.get());

    }
}
