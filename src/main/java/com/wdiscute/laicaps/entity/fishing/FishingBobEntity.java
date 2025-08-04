package com.wdiscute.laicaps.entity.fishing;

import com.wdiscute.laicaps.*;
import com.wdiscute.laicaps.network.Payloads;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class FishingBobEntity extends Projectile
{
    public final Player player;
    private FishHookState currentState;
    public ItemStack stack;

    int minTicksToFish;
    int maxTicksToFish;
    int chanceToFishEachTick;

    int ticksInWater;

    enum FishHookState
    {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING,
        FISHING
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
        {
            this.setOwner(player);

            minTicksToFish = 100;
            maxTicksToFish = 300;
            chanceToFishEachTick = 100;


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
            Vec3 vec3 = new Vec3(-f3, Mth.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
            double d3 = vec3.length();
            vec3 = vec3.multiply(0.6 / d3 + this.random.triangle(0.5F, 0.0103365), 0.6 / d3 + this.random.triangle(0.5F, 0.0103365), 0.6 / d3 + this.random.triangle(0.5F, 0.0103365));
            this.setDeltaMovement(vec3);
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) 180.0F / (double) (float) Math.PI));
            this.setXRot((float) (Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double) 180.0F / (double) (float) Math.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        if (!level.isClientSide) player.setData(ModDataAttachments.FISHING.get(), this.uuid.toString());

        currentState = FishHookState.FLYING;
    }


    private void sendPacket()
    {
        LootParams.Builder builder = new LootParams.Builder((ServerLevel) level());
        LootParams params = builder.create(LootContextParamSets.EMPTY);

        ResourceKey<LootTable> lootTable = ResourceKey.create(
                Registries.LOOT_TABLE,
                Laicaps.rl("fishing/asha_1"));

        ObjectArrayList<ItemStack> arrayOfItemStacks = level().getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);

        int randomInt = getRandom().nextInt(arrayOfItemStacks.size());
        ItemStack randomItemStack = arrayOfItemStacks.get(randomInt);

        stack = randomItemStack;

        if (stack.is(ModTags.Items.FISH))
        {
            PacketDistributor.sendToPlayer(((ServerPlayer) player), new Payloads.FishingPayload(randomItemStack, 3));
        }
        else
        {
            level().addFreshEntity(new ItemEntity(
                    level(), player.position().x + 0.5f, player.position().y + 1.2f, player.position().z,
                    stack));
            player.setData(ModDataAttachments.FISHING.get(), "");
            kill();
        }


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
            player.setData(ModDataAttachments.FISHING.get(), "");
            this.discard();
            return true;
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        Player player = ((Player) this.getOwner());
        if (player == null)
        {
            this.discard();
            player.setData(ModDataAttachments.FISHING.get(), "");
        }

        if (this.level().isClientSide || !this.shouldStopFishing(player))
        {

            BlockPos blockpos = this.blockPosition();
            FluidState fluidstate = this.level().getFluidState(blockpos);
            FluidState fluidstate1 = this.level().getFluidState(blockpos.below());

            if (this.currentState == FishHookState.FLYING)
            {
                if(getDeltaMovement().y < 1.2f)
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.02, 0));

                if (fluidstate.is(FluidTags.WATER))
                {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.3, 0.3, 0.3));
                    this.currentState = FishHookState.BOBBING;
                    return;
                }
            }

            if (!fluidstate.is(FluidTags.WATER) && !fluidstate1.is(FluidTags.WATER))
            {
                currentState = FishHookState.FLYING;
            }


            //TODO check for water level instead of just blockstate to make the entity sit better in water
            if (this.currentState == FishHookState.BOBBING || this.currentState == FishHookState.FISHING)
            {


                checkForFish();

                if (fluidstate.is(FluidTags.WATER))
                {
                    setDeltaMovement(this.getDeltaMovement().add(0.0F, 0.01, 0.0F));
                }
                else
                {
                    if (random.nextFloat() > 0.02)
                    {
                        setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.03, 0.0F));
                    }
                    else
                    {
                        setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.01, 0.0F));
                    }
                }
            }


            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();

            if (this.onGround() || this.horizontalCollision)
            {
                this.setDeltaMovement(Vec3.ZERO);
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.92));
            this.reapplyPosition();
        }

    }


    private void checkForFish()
    {
        if (!level().isClientSide && currentState == FishHookState.BOBBING)
        {
            ticksInWater++;
            int i = random.nextInt(chanceToFishEachTick);
            if((i == 1 || ticksInWater > maxTicksToFish) && ticksInWater > minTicksToFish)
            {
                currentState = FishHookState.FISHING;
                sendPacket();
            }
        }

    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {

    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> key)
    {
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
