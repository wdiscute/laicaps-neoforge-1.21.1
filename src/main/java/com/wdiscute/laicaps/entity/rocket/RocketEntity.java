package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.mixin.JumpingAcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketEntity extends VehicleEntity implements PlayerRideable, MenuProvider, ContainerEntity
{
    private static final Logger log = LoggerFactory.getLogger(RocketEntity.class);

    public final AnimationState shakeAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> JUMPING = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private boolean started = false;

    public RocketEntity(EntityType<? extends VehicleEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    public void tick()
    {
        super.tick();


        if(!started)
        {
            this.shakeAnimationState.start(this.tickCount);
            started = true;
        }

        if(true) return;

        int state = entityData.get(STATE);
        int jumping = entityData.get(JUMPING);
        Vec3 delta = getDeltaMovement();

        if(state == 0) this.itemStacks.set(3, new ItemStack(Items.DIRT, 1));
        if(state == 1) this.itemStacks.set(3, new ItemStack(Items.STONE, 1));
        if(state == 2) this.itemStacks.set(3, new ItemStack(Items.ANDESITE, 1));
        if(state == 3) this.itemStacks.set(3, new ItemStack(Items.GRANITE, 1));
        if(state == 4) this.itemStacks.set(3, new ItemStack(Items.DIORITE, 1));

        //landed aka on-ground
        if (state == 0)
        {
            //if state is on-ground but not on-ground, then state = landing
            //if(!onGround()) entityData.set(STATE, 4);

            //JUMPING handler & countdown display
            if (getFirstPassenger() instanceof JumpingAcessor player && player.isJumping())
                entityData.set(JUMPING, entityData.get(JUMPING) + 1);
            else
                entityData.set(JUMPING, -1);

            if (getFirstPassenger() instanceof ServerPlayer player)
            {
                if (jumping % 20 == 0)
                    player.displayClientMessage(Component.translatable("gui.laicaps.rocket.takeoff." + jumping / 20), true);
            }


            //takeoff at 220 jumping ticks
            if (jumping == 20)
            //TODO if (jumping == 220)
            {
                entityData.set(STATE, 2);
                entityData.set(JUMPING, -1);
            }

        }


        //takeoff
        if (state == 2)
        {
            //TODO SMOOTH MOVEMENT
            setDeltaMovement(0, 1, 0);

            if (position().y > 200)
                entityData.set(STATE, 3);

            //TODO SPAWN PARTICLES
        }


        //ORBIT
        if (state == 3)
        {
            //go up to y == 400
            if (delta.y < 0.3 && position().y < 400) setDeltaMovement(0, 0.35, 0);
            if (position().y > 400) setDeltaMovement(0, 0, 0);

            //open space menu
            if (getFirstPassenger() instanceof ServerPlayer player)
            {
                if (!player.hasContainerOpen())
                    player.openMenu(new SimpleMenuProvider(this, Component.literal("Space Menu")));
            }

        }


        //landing
        if (state == 4)
        {
            if (onGround())
            {
                entityData.set(STATE, 0);
                return;
            }

            setDeltaMovement(0, -0.35, 0);


        }


    }






    @Override
    public InteractionResult interactWithContainerVehicle(Player player)
    {
        System.out.println("test");
        return ContainerEntity.super.interactWithContainerVehicle(player);
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand)
    {
        System.out.println("test1");
        return super.interact(player, hand);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        System.out.println("test2");
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        this.shakeAnimationState.start(this.tickCount);

        if (player.isShiftKeyDown())
        {
            if(!player.level().isClientSide)
                player.openMenu(this);
        }
        else
            player.startRiding(this);

        return super.interactAt(player, vec, hand);
    }




    //
    //                       ,--.                           ,--.
    //,--,--,   ,---.      ,-'  '-.  ,---.  ,--.,--.  ,---. |  ,---.  ,--. ,--.
    //|      \ | .-. |     '-.  .-' | .-. | |  ||  | | .--' |  .-.  |  \  '  /
    //|  ||  | ' '-' '       |  |   ' '-' ' '  ''  ' \ `--. |  | |  |   \   '
    //`--''--'  `---'        `--'    `---'   `----'   `---' `--' `--' .-'  /
    //
    //                                                                `---'


    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(STATE, 0);
        builder.define(JUMPING, 0);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new RocketSpaceMenu(i, inventory, this);
    }

    @Override
    public boolean canRiderInteract()
    {
        return true;
    }

    @Override
    protected Item getDropItem()
    {
        return ModItems.ROCKET.get();
    }


    @Override
    protected void positionRider(Entity passenger, MoveFunction callback)
    {
        Vec3 vec3 = this.getPassengerRidingPosition(passenger);
        Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
        callback.accept(passenger, vec3.x - vec31.x, vec3.y - vec31.y + 0.2f, vec3.z - vec31.z);
    }

    @Override
    public @Nullable ResourceKey<LootTable> getLootTable()
    {
        return null;
    }

    @Override
    public void setLootTable(@Nullable ResourceKey<LootTable> resourceKey)
    {

    }

    @Override
    public long getLootTableSeed()
    {
        return 0;
    }

    @Override
    public void setLootTableSeed(long l)
    {

    }

    @Override
    public NonNullList<ItemStack> getItemStacks()
    {
        return this.itemStacks;
    }

    @Override
    public void clearItemStacks()
    {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize()
    {
        return 5;
    }

    @Override
    public ItemStack getItem(int index)
    {
        return this.itemStacks.get(index);
    }

    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        return ContainerHelper.removeItem(this.getItemStacks(), slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        ItemStack itemstack = (ItemStack)this.getItemStacks().get(slot);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.getItemStacks().set(slot, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        this.itemStacks.set(index, stack);
    }

    @Override
    public void setChanged()
    {

    }

    @Override
    public boolean stillValid(Player player)
    {
        return false;
    }

    @Override
    public void clearContent()
    {

    }



    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        compound.putInt("state", this.entityData.get(STATE));
        compound.putInt("jumping", this.entityData.get(JUMPING));

        ItemStackHandler inventory = new ItemStackHandler(5);
        inventory.setStackInSlot(0, itemStacks.get(0));
        inventory.setStackInSlot(1, itemStacks.get(1));
        inventory.setStackInSlot(2, itemStacks.get(2));
        inventory.setStackInSlot(3, itemStacks.get(3));
        inventory.setStackInSlot(4, itemStacks.get(4));
        compound.put("inventory", inventory.serializeNBT(registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        this.entityData.set(STATE, compound.getInt("state"));
        this.entityData.set(JUMPING, compound.getInt("jumping"));

        ItemStackHandler inventory = new ItemStackHandler(5);
        inventory.deserializeNBT(registryAccess(), compound.getCompound("inventory"));
        itemStacks.set(0, inventory.getStackInSlot(0));
        itemStacks.set(1, inventory.getStackInSlot(1));
        itemStacks.set(2, inventory.getStackInSlot(2));
        itemStacks.set(3, inventory.getStackInSlot(3));
        itemStacks.set(4, inventory.getStackInSlot(4));
        compound.put("inventory", inventory.serializeNBT(registryAccess()));

    }

}
