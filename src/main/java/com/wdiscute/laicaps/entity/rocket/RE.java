package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.*;
import com.wdiscute.laicaps.entity.rocket.rocketparts.*;
import com.wdiscute.laicaps.item.ModDataComponents;
import com.wdiscute.laicaps.mixin.JumpingAcessor;
import com.wdiscute.laicaps.network.Payloads;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RE extends Entity implements PlayerRideable, MenuProvider
{
    public final AnimationState globeSpinAnimationState = new AnimationState();
    public final AnimationState doorOpenAnimationState = new AnimationState();
    public final AnimationState doorCloseAnimationState = new AnimationState();
    private boolean doorOpen;

    public float globeSpinCounter;

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RE.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<ItemStack> PLANET_SELECTED = SynchedEntityData.defineId(RE.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(RE.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMPING = SynchedEntityData.defineId(RE.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> MISSING_FUEL = SynchedEntityData.defineId(RE.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DOOR = SynchedEntityData.defineId(RE.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> MISSING_KNOWLEDGE = SynchedEntityData.defineId(RE.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<ItemStack> CARPET_FIRST_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> CARPET_SECOND_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> CARPET_THIRD_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> GLOBE = SynchedEntityData.defineId(RE.class, EntityDataSerializers.ITEM_STACK);


    public static final EntityDataAccessor<Optional<UUID>> FIRST_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Optional<UUID>> SECOND_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Optional<UUID>> THIRD_SEAT = SynchedEntityData.defineId(RE.class, EntityDataSerializers.OPTIONAL_UUID);


    public NonNullList<ItemStack> itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);

    private final RP[] subEntities;

    public int landingCounter;
    public int takeoffCounter;

    private final Random r = new Random();

    public RE(EntityType<? extends Entity> entityType, Level level)
    {
        super(entityType, level);

        //cockpit
        RP cockpitTop = new RP(new AABB(0, 0, 0, 1.8, 0.08, 1.6), new Vec3(-0.9, 3.45, -2.6), false, true, this, interact.NONE);
        RP cockpitBottom = new RP(new AABB(0, 0, 0, 1.8, 0.08, 1.6), new Vec3(-0.9, 1.45, -2.6), false, true, this, interact.NONE);
        RPCarpet cockpitCarpet = new RPCarpet(new AABB(-0.5, 0, -0.5, 0.5, 0.1, 0.5), new Vec3(0, 1.50, -1.5), false, false, this, interact.RIDE, CARPET_FIRST_SEAT, FIRST_SEAT);
        RP cockpitWindowRight = new RP(new AABB(0, 0, 0, 0.08, 2.05, 1.6), new Vec3(0.85, 1.45, -2.6), false, true, this, interact.NONE);
        RP cockpitWindowLeft = new RP(new AABB(0, 0, 0, 0.08, 2.05, 1.6), new Vec3(-0.9, 1.45, -2.6), false, true, this, interact.NONE);
        RP cockpitWindowFront = new RP(new AABB(0, 0, 0, 1.75, 2.05, 0.08), new Vec3(-0.9, 1.45, -2.6), false, true, this, interact.NONE);
        RP cockpitStairs = new RP(new AABB(0, 0, 0, 1, 0.5, 0.5), new Vec3(-0.5, 0.8, -1), false, true, this, interact.NONE);

        //interactables cockpit
        RP mainScreen = new RP(new AABB(0, 0, 0, 0.8, 0.6, 0.3), new Vec3(-0.43, 1.8, -2.3), true, false, this, interact.OPEN_MAIN_SCREEN);
        RP refuelScreen = new RP(new AABB(-0.1, -0.2, -0.45, 0.1, 0.2, 0.45), new Vec3(-0.65, 2.3, -1.7), true, false, this, interact.OPEN_REFUEL_SCREEN);
        RPGlobe globe = new RPGlobe(new AABB(0, 0, 0, 0.2, 0.2, 0.2), new Vec3(0.5, 2.2, -2.4), true, false, this, interact.GLOBE_SPIN);

        //extra seats
        RPCarpet secondSeatCarpet = new RPCarpet(new AABB(-0.5, 0, -0.5, 0.5, 0.1, 0.5), new Vec3(-1.3, 1, -0.3), false, false, this, interact.RIDE, CARPET_SECOND_SEAT, SECOND_SEAT);
        RPCarpet thirdSeatCarpet = new RPCarpet(new AABB(-0.5, 0, -0.5, 0.5, 0.1, 0.5), new Vec3(-1.3, 1, 1.5), false, false, this, interact.RIDE, CARPET_THIRD_SEAT, THIRD_SEAT);

        //main body
        RP mainFloor = new RP(new AABB(-2, 0, -1.05, 2, 0.08, 2.2), new Vec3(0, 0.8, 0), false, true, this, interact.NONE);
        RP mainCeiling = new RP(new AABB(-2, 0, -1.05, 2, 0.08, 2.2), new Vec3(0, 4.25, 0), false, true, this, interact.NONE);
        RP extraCeiling = new RP(new AABB(-1.45, 0, -0.45, 1.45, 0.45, 1.65), new Vec3(0, 4.35, 0), false, true, this, interact.NONE);
        RP leftWall = new RP(new AABB(0, 0, -1, 0.08, 3.52, 2.27), new Vec3(-2.04, 0.8, 0), false, true, this, interact.NONE);
        RP rightWall = new RP(new AABB(0, 0, -1, -0.08, 3.52, 2.27), new Vec3(2.04, 0.8, 0), false, true, this, interact.NONE);

        RP rightCockpitWall = new RP(new AABB(-0.6, 0, -0.08, 0.6, 3.52, 0), new Vec3(1.4, 0.8, -1), false, true, this, interact.NONE);
        RP leftCockpitWall = new RP(new AABB(-0.6, 0, -0.08, 0.6, 3.52, 0), new Vec3(-1.4, 0.8, -1), false, true, this, interact.NONE);

        //door inner
        RPDoorStairs doorStairs = new RPDoorStairs(new AABB(-0.8, 0, -0.25, 0.8, 0.5, 0.25), new Vec3(0, 0, 3), false, this, interact.TOGGLE_DOOR);
        RP doorFloor = new RP(new AABB(-1.5, 0, 0, 1.5, 0.08, 0.6), new Vec3(0, 0.8, 2.2), false, true, this, interact.NONE);
        RP doorInnerLeft = new RP(new AABB(-0.3, 0, 0, 0.3, 3.52, 0.08), new Vec3(-1.70, 0.8, 2.2), false, true, this, interact.NONE);
        RP doorInnerRight = new RP(new AABB(-0.3, 0, 0, 0.3, 3.52, 0.08), new Vec3(1.70, 0.8, 2.2), false, true, this, interact.NONE);

        //door outer
        RP doorLeft = new RP(new AABB(-0.3, 0, 0, 0.3, 3.15, 0.08), new Vec3(-1.15, 0.8, 2.75), false, true, this, interact.NONE);
        RPDoor door = new RPDoor(new AABB(-0.9, 0, 0, 0.9, 3.15, 0.08), new Vec3(0.00, 0.8, 2.75), false, this, interact.NONE);
        RP doorRight = new RP(new AABB(-0.3, 0, 0, 0.3, 3.15, 0.08), new Vec3(1.15, 0.8, 2.75), false, true, this, interact.NONE);

        RP doorCeiling = new RP(new AABB(-1.45, 0, 0, 1.45, 0.08, 0.6), new Vec3(0, 3.95, 2.25), false, true, this, interact.NONE);


        RPTable table = new RPTable(new AABB(0, 0, 0, 1, 1, 1), new Vec3(1, 1, 0), false, false, this, interact.OPEN_RESEARCH_SCREEN);


        //tanks
        RP tankLeft = new RP(new AABB(0, 0, 0, -1.05, 2.2, 0.95), new Vec3(-2.25, 1.4, 0.25), false, true, this, interact.NONE);
        RP tankRight = new RP(new AABB(0, 0, 0, 1.05, 2.2, 0.95), new Vec3(2.25, 1.4, 0.25), false, true, this, interact.NONE);


        this.subEntities = new RP[]{refuelScreen, rightCockpitWall, leftCockpitWall, secondSeatCarpet, thirdSeatCarpet, doorCeiling, tankRight, tankLeft, doorInnerLeft, doorInnerRight, doorRight, doorLeft, door, cockpitCarpet, doorFloor, doorStairs, cockpitStairs, cockpitTop, mainScreen, mainFloor, mainCeiling, extraCeiling, leftWall, rightWall, cockpitBottom, cockpitWindowRight, cockpitWindowLeft, cockpitWindowFront, globe};
        //this.subEntities = new RP[]{refuelScreen};

        this.setId(ENTITY_COUNTER.getAndAdd(subEntities.length + 1) + 1);

        if (!level().isClientSide) itemStacks.set(4, new ItemStack(ModItems.OVERWORLD.get()));

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


    public void checkPassengers(EntityDataAccessor<Optional<UUID>> seat)
    {

        boolean firstsafe = false;
        for (Entity entity : getPassengers())
        {
            if (entityData.get(seat).equals(Optional.of(entity.getUUID())))
            {
                firstsafe = true;
                break;
            }

        }

        if (!firstsafe) entityData.set(seat, Optional.of(UUID.randomUUID()));


    }


    public void tick()
    {
        super.tick();

        int state = entityData.get(STATE);
        int jumping = entityData.get(JUMPING);

        for (RP subEntity : subEntities)
        {
            subEntity.updatePos(position());
            subEntity.tick();
        }

        if (level().isClientSide)
        {
            if (Minecraft.getInstance().player == null) return;

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


        ItemStack rocketState = this.itemStacks.get(3);
        if (state == 0 && !rocketState.is(Items.DIRT)) this.itemStacks.set(3, new ItemStack(Items.DIRT, 1));
        if (state == 1 && !rocketState.is(Items.STONE)) this.itemStacks.set(3, new ItemStack(Items.STONE, 1));
        if (state == 2 && !rocketState.is(Items.ANDESITE)) this.itemStacks.set(3, new ItemStack(Items.ANDESITE, 1));
        if (state == 3 && !rocketState.is(Items.GRANITE)) this.itemStacks.set(3, new ItemStack(Items.GRANITE, 1));
        if (state == 4 && !rocketState.is(Items.DIORITE)) this.itemStacks.set(3, new ItemStack(Items.DIORITE, 1));


        if (!level().isClientSide) entityData.set(MISSING_FUEL, !(getFuelRemainingForSelectedDestination() > 0));
        if (!level().isClientSide) entityData.set(MISSING_KNOWLEDGE, !isPlanetSelectedUnlocked());

        //landed aka on-ground
        if (state == 0)
        {

            //JUMPING handler & countdown display
            if (getFirstPassenger() instanceof JumpingAcessor jumpingAcessor)
            {
                if (jumpingAcessor.isJumping())
                {
                    if (getFuelRemainingForSelectedDestination() > 0 && isPlanetSelectedUnlocked())
                        entityData.set(JUMPING, entityData.get(JUMPING) + 1);
                    else
                    {
                        if (getFirstPassenger() instanceof Player player)
                        {
                            player.displayClientMessage(Component.translatable("gui.laicaps.main_screen.missing_something"), true);
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

            lerpTo(vec3.x, vec3.y + acc, vec3.z, 0, 0, 0);

            if (position().y > 200)
            {
                ItemStack itemStack = new ItemStack(itemStacks.get(2).getItem());
                itemStack.set(ModDataComponents.FUEL, getFuelRemainingForSelectedDestination());
                itemStacks.set(2, itemStack);
                entityData.set(STATE, 2);
                takeoffCounter = 0;
            }
            return;
        }

        //ORBIT / TELEPORT TO DIMENSION / TODO MINIGAME
        if (state == 2)
        {
            entityData.set(STATE, 3);

            ResourceKey<Level> key = null;

            if (itemStacks.get(4).is(ModItems.EMBER.get()))
            {
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));

                for (Entity entity : getPassengers())
                {
                    if (entity instanceof ServerPlayer sp)
                    {
                        AdvHelper.awardAdvancementCriteria(sp, "ember_entries", "entry2");
                        PacketDistributor.sendToPlayer(sp, new Payloads.ToastPayload("ember", "entry2"));
                    }
                }
            }

            if (itemStacks.get(4).is(ModItems.ASHA.get()))
            {
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));

                for (Entity entity : getPassengers())
                {
                    if (entity instanceof ServerPlayer sp)
                    {
                        AdvHelper.awardAdvancementCriteria(sp, "asha_entries", "entry2");
                        PacketDistributor.sendToPlayer(sp, new Payloads.ToastPayload("asha", "entry2"));
                    }
                }
            }

            if (itemStacks.get(4).is(ModItems.OVERWORLD.get()))
            {
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));
            }

            if (itemStacks.get(4).is(ModItems.LUNAMAR.get()))
            {
                key = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));

                for (Entity entity : getPassengers())
                {
                    if (entity instanceof ServerPlayer sp)
                    {
                        AdvHelper.awardAdvancementCriteria(sp, "lunamar_entries", "entry2");
                        PacketDistributor.sendToPlayer(sp, new Payloads.ToastPayload("lunamar", "entry2"));
                    }
                }
            }

            DimensionTransition dt = new DimensionTransition(
                    level().getServer().getLevel(key),
                    new Vec3(0, 469, 0), new Vec3(0, 0, 0), 0.0f, 0.0f, entity ->
            {
            });

            landingCounter = 0;
            this.changeDimension(dt);
            return;
        }


        //landing
        if (state == 3)
        {
            landingCounter++;

            Component compTitle = Component.literal("");
            Component comp = null;

            AABB aabb = new AABB(-5, -5, -5, 5, 5, 5).move(position());
            List<Entity> entites = level().getEntities(null, aabb);

            List<ServerPlayer> sps = new ArrayList<>();

            for (Entity entity : entites)
            {
                if (entity instanceof ServerPlayer sp)
                {
                    sps.add(sp);
                }
            }


            //landing on
            if (landingCounter == 50)
            {
                Component planet = Component.translatable("gui.laicaps.landing.landing.overworld");

                if (level().dimension().equals(Laicaps.EMBER_KEY))
                    planet = Component.translatable("gui.laicaps.landing.landing.ember");
                if (level().dimension().equals(Laicaps.ASHA_KEY))
                    planet = Component.translatable("gui.laicaps.landing.landing.asha");
                if (level().dimension().equals(Laicaps.OVERWORLD_KEY))
                    planet = Component.translatable("gui.laicaps.landing.landing.overworld");
                if (level().dimension().equals(Laicaps.LUNAMAR_KEY))
                    planet = Component.translatable("gui.laicaps.landing.landing.lunamar");

                comp = Component.translatable("gui.laicaps.landing.landing.base").append(planet);

                for (ServerPlayer sp : sps)
                {
                    sp.connection.send(new ClientboundSetTitleTextPacket(compTitle));
                    sp.connection.send(new ClientboundSetSubtitleTextPacket(comp));
                }
            }


            //temperature
            if (landingCounter == 200)
            {

                int temp = 0;

                if (level().dimension().equals(Laicaps.EMBER_KEY)) temp = r.nextInt(100) - 50 + 523;
                if (level().dimension().equals(Laicaps.ASHA_KEY)) temp = r.nextInt(10) - 5 + 22;
                if (level().dimension().equals(Laicaps.OVERWORLD_KEY)) temp = r.nextInt(100) - 50 + 523;
                if (level().dimension().equals(Laicaps.LUNAMAR_KEY)) temp = r.nextInt(100) - 50 + 523;

                if (level().rainLevel > 20) temp -= 10;

                String tempString = temp + "";

                if (temp > 50)
                {
                    tempString = "§c" + tempString;
                }
                else
                {
                    tempString = "§2" + tempString;
                }

                comp = Component.translatable("gui.laicaps.landing.temperature.base").append(Component.literal(tempString + "°c"));
            }


            //weather
            if (landingCounter == 350)
            {

                Component weather = Component.translatable("gui.laicaps.landing.weather.clear");
                ;

                int random = r.nextInt(3);

                if (level().rainLevel > 20)
                {
                    if (random == 0) weather = Component.translatable("gui.laicaps.landing.weather.light_rain");
                    if (random == 1) weather = Component.translatable("gui.laicaps.landing.weather.moderate_rain");
                    if (random == 2) weather = Component.translatable("gui.laicaps.landing.weather.strong_rain");
                }

                if (level().thunderLevel > 20)
                {
                    if (random == 0) weather = Component.translatable("gui.laicaps.landing.weather.light_thunder");
                    if (random == 1) weather = Component.translatable("gui.laicaps.landing.weather.moderate_thunder");
                    if (random == 2) weather = Component.translatable("gui.laicaps.landing.weather.strong_thunder");
                }


                comp = Component.translatable("gui.laicaps.landing.weather.base").append(weather);
            }


            //atmosphere
            if (landingCounter == 500)
            {

                Component atmosphere = Component.translatable("gui.laicaps.landing.atmosphere.breathable");

                if (level().dimension().equals(Laicaps.EMBER_KEY))
                    atmosphere = Component.translatable("gui.laicaps.landing.atmosphere.unbreathable");

                comp = Component.translatable("gui.laicaps.landing.atmosphere.base").append(atmosphere);
            }


            //send packet
            if (comp != null)
            {
                for (ServerPlayer sp : sps)
                {
                    sp.connection.send(new ClientboundSetTitleTextPacket(compTitle));
                    sp.connection.send(new ClientboundSetSubtitleTextPacket(comp));
                }
            }


            //check if landed
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

    @OnlyIn(Dist.CLIENT)
    public void screen()
    {
        Minecraft.getInstance().setScreen(new MainScreen(this));
    }


    public InteractionResult interactWithPart(Player player, interact interaction)
    {
        //ride
        if (interaction.equals(interact.RIDE) && !level().isClientSide)
        {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        //open main menu
        if (interaction.equals(interact.OPEN_MAIN_SCREEN) && level().isClientSide)
        {
            screen();
            return InteractionResult.SUCCESS;
        }

        //open refuel screen
        if (interaction.equals(interact.OPEN_REFUEL_SCREEN) && !level().isClientSide)
        {

            player.openMenu(this);
            return InteractionResult.SUCCESS;
        }

        //make globe go weeeeee and spin
        if (interaction.equals(interact.GLOBE_SPIN) && level().isClientSide)
        {

        }

        //open door
        if (interaction.equals(interact.TOGGLE_DOOR))
        {
            entityData.set(DOOR, !entityData.get(DOOR));
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.PASS;
    }

    public boolean isPlanetSelectedUnlocked()
    {
        ServerPlayer spToCheck = null;


        if (getFirstPassenger() instanceof ServerPlayer serverPlayer)
        {
            spToCheck = serverPlayer;
        }
        else
        {
            //if theres no first passenger, gets closest player in a 10x10x10
            AABB aabb = new AABB(-5, -5, -5, 5, 5, 5).move(position());

            List<Entity> entityList = level().getEntities(null, aabb);

            //TODO CHECK WHICH PLAYER IS CLOSEST TO SCREEN INSTEAD OF JUST THE FIRST FOUND
            for (Entity entities : entityList)
            {
                if (entities instanceof ServerPlayer serverPlayer)
                {
                    spToCheck = serverPlayer;
                    break;
                }
            }
        }

        //if no player was found, keeps whatever was already there
        if (spToCheck == null) return !entityData.get(MISSING_KNOWLEDGE);

        //return if player has discovered the planet selected
        if (itemStacks.get(4).is(ModItems.EMBER)) return AdvHelper.hasAdvancement(spToCheck, "ember_discovered");
        if (itemStacks.get(4).is(ModItems.ASHA)) return AdvHelper.hasAdvancement(spToCheck, "asha_discovered");
        if (itemStacks.get(4).is(ModItems.OVERWORLD)) return true;
        if (itemStacks.get(4).is(ModItems.LUNAMAR)) return AdvHelper.hasAdvancement(spToCheck, "lunamar_discovered");

        return false;
    }

    public int getFuelRemainingForSelectedDestination()
    {

        if (itemStacks.get(4).isEmpty()) return -1;
        if (itemStacks.get(2).isEmpty()) return -1;

        float fuelRequired = 0;
        int fuelAvailable = itemStacks.get(2).get(ModDataComponents.FUEL);


        boolean isCurrentDimensionUnknown = true;

        ResourceKey<Level> dimension = level().dimension();

        if (dimension == Laicaps.EMBER_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 700;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 1240;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == Laicaps.ASHA_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 490;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 870;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == Laicaps.OVERWORLD_KEY)
        {
            if (itemStacks.get(4).is(ModItems.EMBER)) fuelRequired = 790;
            if (itemStacks.get(4).is(ModItems.ASHA)) fuelRequired = 330;
            if (itemStacks.get(4).is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (itemStacks.get(4).is(ModItems.LUNAMAR)) fuelRequired = 660;
            isCurrentDimensionUnknown = false;
        }

        if (dimension == Laicaps.LUNAMAR_KEY)
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
        //this.kill();
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
    protected boolean canAddPassenger(Entity passenger)
    {
        return this.getPassengers().size() <= 3;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick)
    {

        int i = Math.max(this.getPassengers().indexOf(entity), 0);


        if (entityData.get(FIRST_SEAT).equals(Optional.of(entity.getUUID())))
        {
            return new Vec3(0, 1.7, -1.5);
        }

        if (entityData.get(SECOND_SEAT).equals(Optional.of(entity.getUUID())))
        {
            return new Vec3(-1.3, 0.8, -0.3);
        }

        if (entityData.get(THIRD_SEAT).equals(Optional.of(entity.getUUID())))
        {
            return new Vec3(-1.3, 0.8, 1.5);
        }


        return new Vec3(0, 10, 0);

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(STATE, 0);
        builder.define(PLANET_SELECTED, new ItemStack(ModItems.OVERWORLD.get()));
        builder.define(FUEL, 0);
        builder.define(DOOR, false);
        builder.define(JUMPING, 0);
        builder.define(MISSING_FUEL, false);
        builder.define(MISSING_KNOWLEDGE, false);
        builder.define(CARPET_FIRST_SEAT, new ItemStack(Items.CYAN_CARPET));
        builder.define(CARPET_SECOND_SEAT, new ItemStack(Items.RED_CARPET));
        builder.define(CARPET_THIRD_SEAT, new ItemStack(Items.BLUE_CARPET));
        builder.define(GLOBE, new ItemStack(ModBlocks.OVERWORLD_GLOBE));
        builder.define(FIRST_SEAT, Optional.of(UUID.randomUUID()));
        builder.define(SECOND_SEAT, Optional.of(UUID.randomUUID()));
        builder.define(THIRD_SEAT, Optional.of(UUID.randomUUID()));
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
        return new ItemStack(ModItems.SPACESHIP_BLUEPRINT.get());
    }

    @Override
    public boolean isMultipartEntity()
    {
        return true;
    }

    @Override
    public PartEntity<RE>[] getParts()
    {
        return this.subEntities;
    }


//    @Override
//    public Vec3 getPassengerRidingPosition(Entity entity)
//    {
//        return new Vec3(position().x, position().y + 1.7, position().z - 1.5);
//    }

    @Override
    public boolean isPickable()
    {
        return !this.isRemoved();
    }


    @Override
    protected void positionRider(Entity passenger, MoveFunction callback)
    {
        Vec3 vec3 = this.getPassengerRidingPosition(passenger);
        Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
        callback.accept(passenger, vec3.x - vec31.x, vec3.y - vec31.y + 0.2f, vec3.z - vec31.z);
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

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new RefuelMenu(inventory, i, this);
    }

    public enum interact
    {
        RIDE,
        OPEN_MAIN_SCREEN,
        OPEN_RESEARCH_SCREEN,
        OPEN_REFUEL_SCREEN,
        GLOBE_SPIN,
        TOGGLE_DOOR,
        NONE
    }

}
