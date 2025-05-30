package com.wdiscute.laicaps.entity.nimble;

import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NimbleEntity extends Animal
{
    public final AnimationState idleAS = new AnimationState();
    private int idleCounter = 0;

    public final AnimationState eatingAS = new AnimationState();
    private int eatingCounter = 0;

    public final AnimationState rollLeftAS = new AnimationState();
    public final AnimationState rollRightAS = new AnimationState();
    private int rollCounter = 0;

    private int friendship;

    public NimbleEntity(EntityType<? extends NimbleEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new NimbleEatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25, p_335386_ -> p_335386_.is(ModItems.OAKHEART_BERRIES), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return SoundEvents.ANVIL_PLACE;
    }


    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        //breed
        if (this.isFood(stack))
        {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove())
            {
                this.usePlayerItem(player, hand, stack);
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby())
            {
                this.usePlayerItem(player, hand, stack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (this.level().isClientSide)
            {
                return InteractionResult.CONSUME;
            }
        }

        //if fed a treat
        if (stack.is(ModItems.NIMBLE_SWEET_TREAT))
        {
            friendship += 5;
            stack.shrink(1);
            this.eatingAS.start(this.tickCount);
            this.eatingCounter = 85;
            return InteractionResult.SUCCESS;
        }


        //clicked with the hand
        if (rollCounter < 1 && eatingCounter < 1)
        {
            rollCounter = 70;
            if (getRandom().nextIntBetweenInclusive(0, 1) == 1) rollRightAS.start(this.tickCount);
            else rollLeftAS.start(this.tickCount);

        } else
        {
            return InteractionResult.PASS;
        }

        return InteractionResult.SUCCESS;

    }


    @Override
    public void tick()
    {


        --eatingCounter;

        //play sounds
        if (eatingCounter > 0 && eatingCounter % 10 == 0 && !level().isClientSide()) playSound(SoundEvents.FOX_EAT);
        if (eatingCounter == 1 && !level().isClientSide()) playSound(SoundEvents.PLAYER_BURP, 1, 1.5f);

        if (eatingCounter > 0)
        {
            goalSelector.setControlFlag(Goal.Flag.MOVE, false);
            goalSelector.setControlFlag(Goal.Flag.JUMP, false);
        } else
        {
            eatingAS.stop();
        }

        --rollCounter;
        if (rollCounter > 0)
        {
            goalSelector.setControlFlag(Goal.Flag.MOVE, false);
            goalSelector.setControlFlag(Goal.Flag.JUMP, false);
            goalSelector.setControlFlag(Goal.Flag.LOOK, false);
            goalSelector.setControlFlag(Goal.Flag.TARGET, false);
        } else
        {
            rollRightAS.stop();
            rollLeftAS.stop();
        }


        if (level().isClientSide())
        {
            if (idleCounter <= 0)
            {
                idleCounter = 80;
                idleAS.start(tickCount);
            } else
            {
                --idleCounter;
            }
        }


        super.tick();
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return itemStack.is(ModItems.OAKHEART_BERRIES) || itemStack.is(ModItems.SWEETLILY_SUGAR);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob)
    {
        return ModEntities.NIMBLE.get().create(serverLevel);

    }

}
