package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketEntity extends LivingEntity
{
    private static final Logger log = LoggerFactory.getLogger(RocketEntity.class);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    public final AnimationState shakeAnimationState = new AnimationState();

    public RocketEntity(EntityType<? extends LivingEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        this.shakeAnimationState.start(this.tickCount);
        System.out.println(this.shakeAnimationState.isStarted());

//        if(hand == InteractionHand.OFF_HAND) return  InteractionResult.FAIL;
//        player.startRiding(this);

        return super.interactAt(player, vec, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (this.isRemoved()) return false;
        if (this.level().isClientSide) return false;

        if(source.isCreativePlayer()) this.kill();

        return true;
    }

    @Override
    public boolean isPushedByFluid(FluidType type)
    {
        return false;
    }

    @Override
    public boolean isPushable()
    {
        return false;
    }

    @Override
    public void animateHurt(float yaw)
    {

    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50d)
                .add(Attributes.GRAVITY, 0);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots()
    {
        return this.armorItems;
    }

    @Override
    public boolean isNoGravity()
    {
        return true;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot)
    {
        switch (slot.getType())
        {
            case HAND:
                return this.handItems.get(slot.getIndex());
            case HUMANOID_ARMOR:
                return this.armorItems.get(slot.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack)
    {

    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    public void knockback(double strength, double x, double z)
    {
        //empty so it doesnt take knockback
    }

    @Override
    public HumanoidArm getMainArm()
    {
        return HumanoidArm.RIGHT;
    }
}
