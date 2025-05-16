package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.mixin.JumpingAcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketEntity extends Entity implements PlayerRideable, MenuProvider, ContainerEntity
{
    private static final Logger log = LoggerFactory.getLogger(RocketEntity.class);

    public final AnimationState shakeAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> JUMPING = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);

    private static final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
    private static final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));
    private static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    private static final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));


    public RocketEntity(EntityType<? extends Entity> entityType, Level level)
    {
        super(entityType, level);
    }


    public static int getFuelRemainingForSelectedDestination(NonNullList<ItemStack> itemStacks)
    {

        if (itemStacks.get(4).isEmpty()) return -1;
        if (itemStacks.get(2).isEmpty()) return -1;

        if (Minecraft.getInstance().player == null) return -1;

        float fuelRequired = 0;
        int fuelAvailable = itemStacks.get(2).get(ModDataComponents.FUEL);
        boolean flag = false;

        if (Minecraft.getInstance().player.level().dimension() == EMBER_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 700;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 1240;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == ASHA_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 870;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == OVERWORLD_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 790;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 660;
            flag = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == LUNAMAR_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 1240;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 870;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 660;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
            flag = true;
        }

        if (!flag)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
        }

        return ((int) (fuelAvailable - fuelRequired));
    }

    private void handleClientEntityMovement()
    {
        int state = entityData.get(STATE);
        Vec3 vec3 = position();


        if (state == 1)
        {
            lerpTo(vec3.x, vec3.y + 0.2f, vec3.z, 0, 0, 0);
        }

        //landing
        if (state == 3)
        {
            if (onGround())
            {
                return;
            }

            lerpTo(vec3.x, vec3.y - 0.5f, vec3.z, 0, 0, 0);
            return;
        }

    }



    public void tick()
    {
        super.tick();

        if (level().isClientSide)
        {
            handleClientEntityMovement();
            return;
        }

        if (Minecraft.getInstance().player == null) return;

        this.shakeAnimationState.start(this.tickCount);

        int state = entityData.get(STATE);
        int jumping = entityData.get(JUMPING);

        ItemStack rocketState = this.itemStacks.get(3);
        if (state == 0 && !rocketState.is(Items.DIRT)) this.itemStacks.set(3, new ItemStack(Items.DIRT, 1));
        if (state == 1 && !rocketState.is(Items.STONE)) this.itemStacks.set(3, new ItemStack(Items.STONE, 1));
        if (state == 2 && !rocketState.is(Items.ANDESITE)) this.itemStacks.set(3, new ItemStack(Items.ANDESITE, 1));
        if (state == 3 && !rocketState.is(Items.GRANITE)) this.itemStacks.set(3, new ItemStack(Items.GRANITE, 1));
        if (state == 4 && !rocketState.is(Items.DIORITE)) this.itemStacks.set(3, new ItemStack(Items.DIORITE, 1));

        //landed aka on-ground
        if (state == 0)
        {
            //JUMPING handler & countdown display
            if (getFirstPassenger() instanceof JumpingAcessor jumpingAcessor)
            {
                if (jumpingAcessor.isJumping())
                {
                    if (getFuelRemainingForSelectedDestination(itemStacks) > 0)
                        entityData.set(JUMPING, entityData.get(JUMPING) + 1);
                    else
                    {
                        if (getFirstPassenger() instanceof Player player)
                        {
                            player.displayClientMessage(Component.translatable("gui.laicaps.rocket.missing_something"), true);
                        }
                        entityData.set(JUMPING, -1);
                    }
                } else
                {
                    entityData.set(JUMPING, -1);
                }


            }

            if (getFirstPassenger() instanceof ServerPlayer player)
            {
                if (jumping % 20 == 0)
                    player.displayClientMessage(Component.translatable("gui.laicaps.rocket.takeoff." + jumping / 20), true);
            }


            //takeoff at 220 jumping ticks
            if (jumping == 220)
            //if (jumping == 20)
            {
                entityData.set(STATE, 1);
                entityData.set(JUMPING, -1);
                ItemStack itemStack = new ItemStack(itemStacks.get(2).getItem());
                itemStack.set(ModDataComponents.FUEL, getFuelRemainingForSelectedDestination(itemStacks));
                itemStacks.set(2, itemStack);
            }

            return;
        }

        //takeoff
        if (state == 1)
        {
            //TODO SMOOTH MOVEMENT

            //move(MoverType.SELF, new Vec3(0, 0.2f, 0));

            Vec3 vec3 = position();
            lerpTo(vec3.x, vec3.y + 0.2f, vec3.z, 0, 0, 0);

            if (position().y > 200)
                entityData.set(STATE, 2);

            //TODO SPAWN PARTICLES


            return;
        }

        //ORBIT / TELEPORT TO DIMENSION / TODO MINIGAME
        if (state == 2)
        {
            //go up to y == 400
            //if (delta.y < 0.3 && position().y < 400) setDeltaMovement(0, 0.35, 0);
            //if (position().y > 400) setDeltaMovement(0, 0, 0);


            entityData.set(STATE, 3);

            ResourceKey<Level> key = null;

            if (itemStacks.get(4).is(ModItems.EMBER.get()))
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));

            if (itemStacks.get(4).is(ModItems.ASHA.get()))
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));

            if (itemStacks.get(4).is(ModItems.OVERWORLD.get()))
                key = ResourceKey.create(
                        Registries.DIMENSION,
                        ResourceLocation.withDefaultNamespace("overworld"));

            if (itemStacks.get(4).is(ModItems.LUNAMAR.get()))
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));

            DimensionTransition dt = new DimensionTransition(
                    level().getServer().getLevel(key),
                    new Vec3(0, 269, 0),
                    new Vec3(0, 0, 0),
                    0.0f,
                    0.0f,
                    entity ->
                    {
                    }
            );

            this.changeDimension(dt);
            return;
        }


        //landing
        if (state == 3)
        {
            if (level().getBlockState(blockPosition().below()).isAir())
            {
                Vec3 vec3 = position();
                lerpTo(vec3.x, vec3.y - 0.5f, vec3.z, 0, 0, 0);

            } else
            {
                Vec3 vec3 = position();
                lerpTo(vec3.x, blockPosition().getY(), vec3.z, 0, 0, 0);
                entityData.set(STATE, 0);
                return;
            }
            return;
        }

        //crashing if minigame failed
        if (state == 4)
        {
        }


    }


    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        this.shakeAnimationState.start(this.tickCount);

        if (player.isShiftKeyDown())
        {
            if (itemStacks.get(4).isEmpty())
            {
                ResourceKey<Level> dimension = Minecraft.getInstance().player.level().dimension();
                itemStacks.set(4, new ItemStack(ModItems.OVERWORLD.get()));
                if (dimension == EMBER_KEY) itemStacks.set(4, new ItemStack(ModItems.EMBER.get()));
                if (dimension == ASHA_KEY) itemStacks.set(4, new ItemStack(ModItems.ASHA.get()));
                if (dimension == LUNAMAR_KEY) itemStacks.set(4, new ItemStack(ModItems.LUNAMAR.get()));
            }

            if (!player.level().isClientSide)
                player.openMenu(this);
        } else
            player.startRiding(this);

        return super.interactAt(player, vec, hand);
    }


    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        this.kill();
        return super.hurt(source, amount);
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
    public boolean isPickable()
    {
        return !this.isRemoved();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(STATE, 0);
        builder.define(JUMPING, 0);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new RocketSpaceMenu(i, inventory, this);
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
        ItemStack itemstack = (ItemStack) this.getItemStacks().get(slot);
        if (itemstack.isEmpty())
        {
            return ItemStack.EMPTY;
        } else
        {
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
