package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.*;
import com.wdiscute.laicaps.entity.rocket.rocketparts.*;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.mixin.JumpingAcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class RocketEntity extends Entity implements PlayerRideable, MenuProvider, ContainerEntity
{
    public final AnimationState globeSpinAnimationState = new AnimationState();
    public final AnimationState doorOpenAnimationState = new AnimationState();
    public final AnimationState doorCloseAnimationState = new AnimationState();
    private boolean doorOpen;

    public float globeSpinCounter;

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMPING = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> MISSING_FUEL = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DOOR = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> MISSING_KNOWLEDGE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<ItemStack> CARPET = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> GLOBE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.ITEM_STACK);

    public NonNullList<ItemStack> itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);

    private static final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
    private static final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));
    private static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    private static final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));

    private final RP[] subEntities;

    public int landingCounter;
    public int takeoffCounter;

    public RocketEntity(EntityType<? extends Entity> entityType, Level level)
    {
        super(entityType, level);

        //cockpit
        RP cockpitTop = new RP(new AABB(0, 0, 0, 1.8, 0.08, 1.6), new Vec3(-0.9, 3.45, -2.6), false, true, this, InteractionsEnum.NONE);
        RP cockpitBottom = new RP(new AABB(0, 0, 0, 1.8, 0.08, 1.6), new Vec3(-0.9, 1.45, -2.6), false, true, this, InteractionsEnum.NONE);
        RPCarpet cockpitCarpet = new RPCarpet(new AABB(-0.5, 0, -0.5, 0.5, 0.1, 0.5), new Vec3(0, 1.50, -1.5), false, false, this, InteractionsEnum.RIDE);
        RP cockpitWindowRight = new RP(new AABB(0, 0, 0, 0.08, 2.05, 1.6), new Vec3(0.85, 1.45, -2.6), false, true, this, InteractionsEnum.NONE);
        RP cockpitWindowLeft = new RP(new AABB(0, 0, 0, 0.08, 2.05, 1.6), new Vec3(-0.9, 1.45, -2.6), false, true, this, InteractionsEnum.NONE);
        RP cockpitWindowFront = new RP(new AABB(0, 0, 0, 1.75, 2.05, 0.08), new Vec3(-0.9, 1.45, -2.6), false, true, this, InteractionsEnum.NONE);
        RP cockpitStairs = new RP(new AABB(0, 0, 0, 1, 0.5, 0.5), new Vec3(-0.5, 0.8, -1), false, true, this, InteractionsEnum.NONE);

        RP mainScreen = new RP(new AABB(0, 0, 0, 0.8, 0.6, 0.3), new Vec3(-0.43, 1.8, -2.3), true, false, this, InteractionsEnum.OPEN_MAIN_SCREEN);
        RPGlobe globe = new RPGlobe(new AABB(0, 0, 0, 0.2, 0.2, 0.2), new Vec3(0.5, 2.2, -2.4), true, false, this, InteractionsEnum.GLOBE_SPIN);


        //main body
        RP mainFloor = new RP(new AABB(-2, 0, -1.05, 2, 0.08, 2.2), new Vec3(0, 0.8, 0), false, true, this, InteractionsEnum.NONE);
        RP mainCeiling = new RP(new AABB(-2, 0, -1.05, 2, 0.08, 2.2), new Vec3(0, 4.25, 0), false, true, this, InteractionsEnum.NONE);
        RP extraCeiling = new RP(new AABB(-1.45, 0, -0.45, 1.45, 0.45, 1.65), new Vec3(0, 4.35, 0), false, true, this, InteractionsEnum.NONE);
        RP leftWall = new RP(new AABB(0, 0, -1, 0.08, 3.52, 2.3), new Vec3(-2.04, 0.8, 0), false, true, this, InteractionsEnum.NONE);
        RP rightWall = new RP(new AABB(0, 0, -1, 0.08, 3.52, 2.3), new Vec3(1.92, 0.8, 0), false, true, this, InteractionsEnum.NONE);


        //door
        RPDoorStairs doorStairs = new RPDoorStairs(new AABB(-0.8, 0, -0.25, 0.8, 0.5, 0.25), new Vec3(0, 0, 3), false, this, InteractionsEnum.TOGGLE_DOOR);
        RP doorFloor = new RP(new AABB(-1.5, 0, 0, 1.5, 0.08, 0.6), new Vec3(0, 0.8, 2.2), false, true, this, InteractionsEnum.NONE);

        RP doorLeft       = new RP(new AABB(-0.3, 0, 0, 0.3, 3.3, 0.08), new Vec3(-1.15, 0.8, 2.75), false, true, this, InteractionsEnum.NONE);
        RPDoor door   = new RPDoor(new AABB(-0.9, 0, 0, 0.9, 3.3, 0.08), new Vec3( 0.00, 0.8, 2.75), false, this, InteractionsEnum.NONE);
        RP doorRight      = new RP(new AABB(-0.3, 0, 0, 0.3, 3.3, 0.08), new Vec3( 1.15, 0.8, 2.75), false, true, this, InteractionsEnum.NONE);


        this.subEntities = new RP[]{doorRight, doorLeft, door, cockpitCarpet, doorFloor, doorStairs, cockpitStairs, cockpitTop, mainScreen, mainFloor, mainCeiling, extraCeiling, leftWall, rightWall, cockpitBottom, cockpitWindowRight, cockpitWindowLeft, cockpitWindowFront, globe};
        this.setId(ENTITY_COUNTER.getAndAdd(subEntities.length + 1) + 1);
    }


    public void handleClientEntityMovement()
    {
        int state = entityData.get(STATE);
        Vec3 vec3 = position();

        if (state == 1)
        {
            takeoffCounter++;

            lerpTo(vec3.x, vec3.y + getAcc(), vec3.z, 0, 0, 0);

            if (position().y > 200)
            {
                takeoffCounter = 0;
            }
            return;
        }

        //landing
        if (state == 3)
        {
            if (onGround())
            {
                return;
            }

            lerpTo(vec3.x, vec3.y + getAcc(), vec3.z, 0, 0, 0);
            return;
        }


        return;
    }


    private void handleClientAnimations()
    {

        //globe
        if (tickCount == 20) globeSpinAnimationState.start(tickCount);
        if (level().isClientSide && globeSpinCounter > 0) globeSpinCounter--;

        //door open/close
        boolean door = entityData.get(DOOR);
        if (door != doorOpen)
        {
            doorOpen = door;
            if (doorOpen)
            {
                doorOpenAnimationState.start(tickCount);
                doorCloseAnimationState.stop();
            }
            else
            {
                doorCloseAnimationState.start(tickCount);
                doorOpenAnimationState.stop();
            }
        }
    }

    public void tick()
    {
        super.tick();

        int state = entityData.get(STATE);
        int jumping = entityData.get(JUMPING);

        for (int i = 0; i < subEntities.length; i++)
        {
            subEntities[i].updatePos(position());
            subEntities[i].tick();
        }

        if (level().isClientSide)
        {

            //TODO MAKE PARTICLES SPAWN FROM THE FUEL TANK AND NOT FIXED COORDS
            if (random.nextFloat() < (float) jumping / 200 || state != 0)
            {
                Vec3 left = position().add(2.8, 1.5, 0.5);
                Vec3 right = position().add(-2.8, 1.5, 0.5);

                level().addParticle(
                        ModParticles.ROCKET_FIRE_PARTICLES.get(),
                        left.x,
                        left.y,
                        left.z,
                        0, 0, 0);
                level().addParticle(
                        ModParticles.ROCKET_FIRE_SIMPLE_PARTICLES.get(),
                        left.x + random.nextFloat() * 1 - 0.5,
                        left.y,
                        left.z + random.nextFloat() * 1 - 0.5,
                        0, 0, 0);

                level().addParticle(
                        ModParticles.ROCKET_FIRE_PARTICLES.get(),
                        right.x,
                        right.y,
                        right.z,
                        0, 0, 0);
                level().addParticle(
                        ModParticles.ROCKET_FIRE_SIMPLE_PARTICLES.get(),
                        right.x + random.nextFloat() * 1 - 0.5,
                        right.y,
                        right.z + random.nextFloat() * 1 - 0.5,
                        0, 0, 0);


            }

            handleClientEntityMovement();
            handleClientAnimations();
            return;
        }

        if (Minecraft.getInstance().player == null) return;

        ItemStack rocketState = this.itemStacks.get(3);
        if (state == 0 && !rocketState.is(Items.DIRT)) this.itemStacks.set(3, new ItemStack(Items.DIRT, 1));
        if (state == 1 && !rocketState.is(Items.STONE)) this.itemStacks.set(3, new ItemStack(Items.STONE, 1));
        if (state == 2 && !rocketState.is(Items.ANDESITE)) this.itemStacks.set(3, new ItemStack(Items.ANDESITE, 1));
        if (state == 3 && !rocketState.is(Items.GRANITE)) this.itemStacks.set(3, new ItemStack(Items.GRANITE, 1));
        if (state == 4 && !rocketState.is(Items.DIORITE)) this.itemStacks.set(3, new ItemStack(Items.DIORITE, 1));

        if (!level().isClientSide)
            entityData.set(MISSING_FUEL, !(getFuelRemainingForSelectedDestination(itemStacks) > 0));
        if (!level().isClientSide && getFirstPassenger() != null)
            entityData.set(MISSING_KNOWLEDGE, !checkPlanetUnlocked(getFirstPassenger(), itemStacks));
        if (!level().isClientSide && itemStacks.get(4).isEmpty())
            itemStacks.set(4, new ItemStack(ModItems.OVERWORLD.get()));

        //landed aka on-ground
        if (state == 0)
        {

            //JUMPING handler & countdown display
            if (getFirstPassenger() instanceof JumpingAcessor jumpingAcessor)
            {
                if (jumpingAcessor.isJumping())
                {
                    if (getFuelRemainingForSelectedDestination(itemStacks) > 0 && checkPlanetUnlocked(getFirstPassenger(), itemStacks))
                        entityData.set(JUMPING, entityData.get(JUMPING) + 1);
                    else
                    {
                        if (getFirstPassenger() instanceof Player player)
                        {
                            player.displayClientMessage(Component.translatable("gui.laicaps.rocket.missing_something"), true);
                        }
                        entityData.set(JUMPING, -1);
                    }
                }
                else
                {
                    entityData.set(JUMPING, -1);
                }


            }

            //takeoff at 220 jumping ticks
            if (jumping == 220)
            //if (jumping == 20)
            {
                entityData.set(STATE, 1);
                entityData.set(JUMPING, -1);
            }

            return;
        }

        //takeoff
        if (state == 1)
        {
            takeoffCounter++;

            Vec3 vec3 = position();

            double acc = Math.min((double) (takeoffCounter * takeoffCounter) / 150000, 0.4);

            System.out.println(level() + " - " + acc);

            lerpTo(vec3.x, vec3.y + acc, vec3.z, 0, 0, 0);

            if (position().y > 200)
            {
                ItemStack itemStack = new ItemStack(itemStacks.get(2).getItem());
                itemStack.set(ModDataComponents.FUEL, getFuelRemainingForSelectedDestination(itemStacks));
                itemStacks.set(2, itemStack);
                entityData.set(STATE, 2);
                takeoffCounter = 0;
            }
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

            landingCounter = 0;
            this.changeDimension(dt);
            return;
        }


        //landing
        if (state == 3)
        {
            landingCounter++;


            Component comp = null;
            Component compTitle = Component.literal("WIP");

            comp = switch (landingCounter)
            {
                case 1 -> Component.literal("Landing on... <insert planet name>");
                case 100 -> Component.literal("temperature: idk probably not too bad");
                case 200 -> Component.literal("Atmosphere: breathable (steve doesnt have lungs)");
                default -> comp;
            };

            if (getFirstPassenger() instanceof ServerPlayer sp && comp != null)
            {
                sp.connection.send(new ClientboundSetTitleTextPacket(compTitle));
                sp.connection.send(new ClientboundSetSubtitleTextPacket(comp));
            }


            if (level().getBlockState(blockPosition().below()).isAir())
            {
                Vec3 vec3 = position();
                lerpTo(vec3.x, vec3.y - 0.5f, vec3.z, 0, 0, 0);

            }
            else
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

    public InteractionResult interactWithPart(Player player, InteractionsEnum interaction)
    {
        //ride
        if (interaction.equals(InteractionsEnum.RIDE) && !level().isClientSide)
        {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        //open main menu
        if (interaction.equals(InteractionsEnum.OPEN_MAIN_SCREEN) && !level().isClientSide)
        {
            player.openMenu(this);
            return InteractionResult.SUCCESS;
        }

        //make globe go weeeeee and spin
        if (interaction.equals(InteractionsEnum.GLOBE_SPIN) && level().isClientSide)
        {

        }

        //open door
        if (interaction.equals(InteractionsEnum.TOGGLE_DOOR))
        {
            entityData.set(DOOR, !entityData.get(DOOR));
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.PASS;
    }

    public static boolean checkPlanetUnlocked(Entity firstPassenger, NonNullList<ItemStack> itemStacks)
    {

        boolean canTravel = false;

        if (firstPassenger instanceof ServerPlayer sp)
        {
            boolean emberDiscovered = AdvHelper.hasAdvancement(sp, "ember_discovered");
            boolean ashaDiscovered = AdvHelper.hasAdvancement(sp, "asha_discovered");
            boolean lunamarDiscovered = AdvHelper.hasAdvancement(sp, "lunamar_discovered");

            if (itemStacks.get(4).is(ModItems.EMBER) && emberDiscovered) canTravel = true;
            if (itemStacks.get(4).is(ModItems.ASHA) && ashaDiscovered) canTravel = true;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) canTravel = true;
            if (itemStacks.get(4).is(ModItems.LUNAMAR) && lunamarDiscovered) canTravel = true;

            return canTravel;
        }


        return false;
    }

    public static int getFuelRemainingForSelectedDestination(NonNullList<ItemStack> itemStacks)
    {

        if (itemStacks.get(4).isEmpty()) return -1;
        if (itemStacks.get(2).isEmpty()) return -1;

        if (Minecraft.getInstance().player == null) return -1;

        float fuelRequired = 0;
        int fuelAvailable = itemStacks.get(2).get(ModDataComponents.FUEL);


        boolean isCurrentDimensionUnknown = true;
        ResourceKey<Level> dimension = Minecraft.getInstance().player.level().dimension();

        if (dimension == EMBER_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 700;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 1240;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == ASHA_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 870;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == OVERWORLD_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 790;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 660;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == LUNAMAR_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 1240;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 870;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 660;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
            isCurrentDimensionUnknown = false;
        }

        if (isCurrentDimensionUnknown)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 120;
        }

        return ((int) (fuelAvailable - fuelRequired));
    }

    public double getAcc()
    {
        int state = entityData.get(STATE);

        //takeoff
        if (state == 1)
        {
            return Math.min((double) (takeoffCounter * takeoffCounter) / 150000, 0.4);
        }


        //landing
        if (state == 3)
        {
            return -0.5;
        }

        return 0;
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
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(STATE, 0);
        builder.define(DOOR, false);
        builder.define(JUMPING, 0);
        builder.define(MISSING_FUEL, false);
        builder.define(MISSING_KNOWLEDGE, false);
        builder.define(CARPET, new ItemStack(Items.CYAN_CARPET));
        builder.define(GLOBE, new ItemStack(ModBlocks.OVERWORLD_GLOBE));
    }

    @Override
    public AABB getBoundingBoxForCulling()
    {
        AABB box = new AABB(-3.5, 0, -3.5, 3.5, 5, 3.5);
        return box.move(position());
    }

    @Override
    public void setId(int id)
    {
        super.setId(id);
        for (int i = 0; i < this.subEntities.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.subEntities[i].setId(id + i + 1);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger)
    {

        return position().add(new Vec3(0, 1.55, -1.5));
    }

    @Override
    public @Nullable ItemStack getPickResult()
    {
        return new ItemStack(ModItems.ROCKET.get());
    }

    @Override
    public boolean isMultipartEntity()
    {
        return true;
    }

    @Override
    public PartEntity<RocketEntity>[] getParts()
    {
        return this.subEntities;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity)
    {
        return new Vec3(position().x, position().y + 1.7, position().z - 1.5);
    }

    @Override
    public boolean isPickable()
    {
        return !this.isRemoved();
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
        }
        else
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
        compound.putBoolean("door", this.entityData.get(DOOR));
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
        this.entityData.set(DOOR, compound.getBoolean("door"));
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
