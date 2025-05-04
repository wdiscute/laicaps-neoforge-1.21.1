package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.mixin.JumpingAcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketEntity extends LivingEntity implements PlayerRideable, MenuProvider
{
    private static final Logger log = LoggerFactory.getLogger(RocketEntity.class);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    public final AnimationState shakeAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);

    public RocketEntity(EntityType<? extends LivingEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    public final ItemStackHandler inventory = new ItemStackHandler(3)
    {
        @Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            if (slot == 0)
                if (stack.is(ModItems.ASTRONOMY_NOTEBOOK))
                    return 1;
                else
                    return 0;

            if (slot == 1)
                if (stack.is(ModItems.ENDERBLAZE_FUEL))
                    return 64;
                else
                    return 0;

            if (slot == 2)
                if (stack.is(ModItems.TANK) || stack.is(ModItems.MEDIUM_TANK) || stack.is(ModItems.LARGE_TANK))
                    return 1;
                else
                    return 0;




            return 64;
        }

    };

    @Override
    public void tick()
    {
        super.tick();

        if (position().y > 200 && !level().isClientSide && getFirstPassenger() instanceof Player player)
        {
            if (!player.hasContainerOpen())
                player.openMenu(new SimpleMenuProvider(this, Component.literal("Space")));
        }

        if (getFirstPassenger() instanceof JumpingAcessor player)
        {
            setDeltaMovement(new Vec3(0, 0, 0));
            if (player.isJumping())
            {
                setDeltaMovement(new Vec3(0, 0.6f, 0));
            } else
            {
                if (!this.onGround())
                {
                    setDeltaMovement(new Vec3(0, -0.1f, 0));
                }
            }
        }
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new RocketSpaceMenu(i, inventory, this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(STATE, 0);
    }


    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        this.shakeAnimationState.start(this.tickCount);
        //player.openMenu(new SimpleMenuProvider(this, Component.literal("Space")));

        if (hand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        player.startRiding(this);
        return super.interactAt(player, vec, hand);
    }


    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (this.isRemoved()) return false;
        if (this.level().isClientSide) return false;

        if (source.isCreativePlayer()) this.kill();

        //TODO MAKE BETTER SYSTEM TO REMOVE ROCKET FROM WORLD
        this.kill();
        return true;
    }


    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50d)
                .add(Attributes.GRAVITY, 0);
    }


    //
    //                       ,--.                           ,--.
    //,--,--,   ,---.      ,-'  '-.  ,---.  ,--.,--.  ,---. |  ,---.  ,--. ,--.
    //|      \ | .-. |     '-.  .-' | .-. | |  ||  | | .--' |  .-.  |  \  '  /
    //|  ||  | ' '-' '       |  |   ' '-' ' '  ''  ' \ `--. |  | |  |   \   '
    //`--''--'  `---'        `--'    `---'   `----'   `---' `--' `--' .-'  /
    //                                                                `---'


    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("state", this.entityData.get(STATE));
        compound.put("inventory", inventory.serializeNBT(registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.entityData.set(STATE, compound.getInt("state"));
        inventory.deserializeNBT(registryAccess(), compound.getCompound("inventory"));
    }

    @Override
    public void kill()
    {
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
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

    @Override
    public Iterable<ItemStack> getArmorSlots()
    {
        return this.armorItems;
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

    //empty so it doesnt take knockback
    @Override
    public void knockback(double strength, double x, double z)
    {
    }

    @Override
    public HumanoidArm getMainArm()
    {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean isNoGravity()
    {
        return true;
    }

    @Override
    public boolean shouldRiderSit()
    {
        return true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback)
    {
        Vec3 vec3 = this.getPassengerRidingPosition(passenger);
        Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
        callback.accept(passenger, vec3.x - vec31.x, vec3.y - vec31.y + 0.2f, vec3.z - vec31.z);
    }

}
