package com.wdiscute.laicaps.block.chase;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.ModParticles;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ChaseControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private Player player = null;
    private int state = 0;
    private int counter = -1;
    private int current_waypoint = 0;
    public BlockPos particlePosition = new BlockPos(0, 0, 0);
    private Vec3 particlePositionTemp;
    private Vec3 particlePositionOffset;
    private double timeToReachNextCheckpoint;
    private final Random r = new Random();

    BlockPos bsZero = new BlockPos(0, 0, 0);
    private BlockPos[] waypoints = {bsZero, bsZero, bsZero, bsZero, bsZero, bsZero, bsZero};
    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private final UUID[] arrayuuid = new UUID[15];


    public void resetPlayersSaved()
    {
        for (int i = 0; i < 15; i++)
        {
            arrayuuid[i] = null;
        }
    }


    public void CanPlayerObtainDrops(Player player)
    {
        setChanged();
        if (this.level.isClientSide) return;
        if (state != 5) return;

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            UUID uuid = player.getUUID();
            if (Objects.equals(this.arrayuuid[i], uuid))
            {
                level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.5f);
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.treasure_chest_looted"), true);

                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);

                return;
            }
            if (Objects.equals(this.arrayuuid[i], null))
            {
                this.arrayuuid[i] = uuid;
                state = 6;

                LootParams.Builder builder = new LootParams.Builder((ServerLevel) this.level);
                LootParams params = builder.create(LootContextParamSets.EMPTY);

                ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE,
                        Laicaps.rl("chests/asha_puzzle"));

                arrayOfItemStacks = level.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);

                return;
            }
        }
    }

    private int getTotalWaypoints()
    {
        if (Objects.equals(waypoints[0], bsZero)) return 0;
        if (Objects.equals(waypoints[1], bsZero)) return 1;
        if (Objects.equals(waypoints[2], bsZero)) return 2;
        if (Objects.equals(waypoints[3], bsZero)) return 3;
        if (Objects.equals(waypoints[4], bsZero)) return 4;
        if (Objects.equals(waypoints[5], bsZero)) return 5;
        if (Objects.equals(waypoints[6], bsZero)) return 6;
        return 7;
    }

    public Boolean SetNextLinkedBlock(BlockPos posOffset, Player player)
    {
        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(waypoints[i], bsZero))
            {
                setChanged();
                waypoints[i] = posOffset;
                player.displayClientMessage(Component.translatable("Set Waypoint " + i + "/6"), true);
                return true;
            }
        }
        return false;
    }

    public BlockPos DecodeBlockPosWithOffset(Direction facing, BlockPos posOffset)
    {
        BlockPos pos = getBlockPos();

        if (facing == Direction.SOUTH)
            return new BlockPos(pos.getX() + (posOffset.getZ() * -1), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getX());

        if (facing == Direction.WEST)
            return new BlockPos(pos.getX() + (posOffset.getX() * -1), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getZ() * -1));

        if (facing == Direction.NORTH)
            return new BlockPos(pos.getX() + posOffset.getZ(), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getX() * -1));

        if (facing == Direction.EAST)
            return new BlockPos(pos.getX() + posOffset.getX(), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getZ());

        return new BlockPos(0, 0, 0);
    }

    public void assignPlayer(Player playeruuid)
    {
        if (player == null)
        {
            player = playeruuid;
            state = 2;
            current_waypoint = 0;
            counter = 0;


            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS, getTotalWaypoints()));
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS_COMPLETED, 0));

            particlePosition = DecodeBlockPosWithOffset(getBlockState().getValue(ChaseControllerBlock.FACING), waypoints[current_waypoint]);

            particlePositionOffset = new Vec3(particlePosition.getX() - getBlockPos().getX(), particlePosition.getY() - getBlockPos().getY(), particlePosition.getZ() - getBlockPos().getZ());
            particlePositionTemp = new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
            timeToReachNextCheckpoint = (Math.abs(particlePositionOffset.x) + Math.abs(particlePositionOffset.y) + Math.abs(particlePositionOffset.z)) * 2;

        }
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS_COMPLETED, 0));
    }

    @Override
    public void tick()
    {
        if (counter > -1) counter++;
        if (counter > 199099) counter = 20000;

        //idle
        if (state == 0)
        {
            Random r = new Random();
            if (r.nextInt(10) > 6)
            {
                ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(), getBlockPos().getX() + 0.5f, getBlockPos().getY() + 1.2f, getBlockPos().getZ() + 0.5f, 1, 0, 0, 0, 0);
            }
        }

        //return if player is null, hopefully this also means it's null if player logs off
        if (player == null)
        {
            state = 0;
            return;
        }

        //spawning particles and checking if player is close
        if (state == 1)
        {
            Random r = new Random();
            if (r.nextInt(10) > 6)
            {
                ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(), particlePosition.getX() + 0.5f, particlePosition.getY() + 1.2f, particlePosition.getZ() + 0.5f, 1, 0, 0, 0, 0);
            }

            if (player.position().distanceTo(new Vec3(particlePosition.getX(), particlePosition.getY() + 1.2f, particlePosition.getZ())) < 1.5f)
            {
                counter = 0;
                state = 2;
                current_waypoint += 1;
                if (current_waypoint == 7)
                {
                    state = 2;
                    particlePositionOffset = new Vec3(getBlockPos().getX() - particlePosition.getX(), getBlockPos().getY() - particlePosition.getY(), getBlockPos().getZ() - particlePosition.getZ());
                    particlePositionTemp = new Vec3(particlePosition.getX(), particlePosition.getY(), particlePosition.getZ());
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS_COMPLETED, current_waypoint));
                    return;
                }
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS_COMPLETED, current_waypoint));

                BlockPos particlePositionOld = particlePosition;

                //gets position of next waypoint
                particlePosition = DecodeBlockPosWithOffset(getBlockState().getValue(ChaseControllerBlock.FACING), waypoints[current_waypoint]);

                //stores offset from old waypoint to new waypoint
                particlePositionOffset = new Vec3(particlePosition.getX() - particlePositionOld.getX(), particlePosition.getY() - particlePositionOld.getY(), particlePosition.getZ() - particlePositionOld.getZ());

                //sets time to reach the next checkpoint based on the distance (should prob change to another distance check method tho)
                timeToReachNextCheckpoint = (Math.abs(particlePositionOffset.x) + Math.abs(particlePositionOffset.y) + Math.abs(particlePositionOffset.z)) * 2;

                //particlePositionTemp is used to spawn particles in an animation from one waypoint to the next
                particlePositionTemp = new Vec3(particlePositionOld.getX(), particlePositionOld.getY(), particlePositionOld.getZ());

                ((ServerLevel) level).sendParticles(ParticleTypes.HAPPY_VILLAGER,
                        getBlockPos().getX() + 0.5f,
                        getBlockPos().getY() + 0.5f,
                        getBlockPos().getZ() + 0.5f,
                        20,
                        0.5f, 0.3f, 0.5f, 0);
            }
        }

        //animation from old waypoint to new waypoint
        if (state == 2)
        {
            //temp goes slightly towards next waypoint every tick
            particlePositionTemp = new Vec3(particlePositionTemp.x + particlePositionOffset.x / timeToReachNextCheckpoint, particlePositionTemp.y + particlePositionOffset.y / timeToReachNextCheckpoint, particlePositionTemp.z + particlePositionOffset.z / timeToReachNextCheckpoint);

            //spawn particles at temp
            ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(), particlePositionTemp.x() + 0.5f, particlePositionTemp.y() + 1.2f, particlePositionTemp.z() + 0.5f, 1, 0, 0, 0, 0);

            //play sound upon leaving checkpoint
            if (counter == 1)
                level.playSound(null, particlePositionTemp.x, particlePositionTemp.y, particlePositionTemp.z, SoundEvents.BOAT_PADDLE_WATER, SoundSource.BLOCKS, 3f, 1f);

            //play sound 0.25s before reaching next checkpoint
            if (counter == timeToReachNextCheckpoint - 5)
                level.playSound(null, particlePositionTemp.x, particlePositionTemp.y, particlePositionTemp.z, SoundEvents.BOAT_PADDLE_WATER, SoundSource.BLOCKS, 3f, r.nextFloat(0.49F));


            //if particles at next way point then goes back to state 1 or finishes
            if (counter == timeToReachNextCheckpoint)
            {
                if (current_waypoint >= getTotalWaypoints())
                {
                    counter = 0;
                    state = 5;
                    level.playSound(null, getBlockPos(), SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 1f, 0.4f);

                } else
                {
                    state = 1;
                }
            }
        }

        //puzzle complete - waiting for a valid player to claim loot
        if (state == 5)
        {
            //spawn big burst of bubbles at start
            if (counter == 1)
            {
                ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(),
                        getBlockPos().getX() + 0.5f,
                        getBlockPos().getY() + 0.5f,
                        getBlockPos().getZ() + 0.5f,
                        50,
                        0.5f, 0.7f, 0.5f, 0);
            }

            //particles + sound 5 seconds before closing
            if (counter == 1100)
            {
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 1.2f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            }

            //particles + sound 4 seconds before closing
            if (counter == 1120)
            {
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 1.1f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            }

            //particles + sound 3 seconds before closing
            if (counter == 1140)
            {
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 1f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 1f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 1f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            }

            //particles + sound 2 seconds before closing
            if (counter == 1160)
            {
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.9f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.9f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.9f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            }

            //particles + sound 1 second before closing
            if (counter == 1180)
            {
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.8f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.8f);
                level.playSound(null, getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.8f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            }

            //resets if 1 minute goes by without players claiming loot
            if (counter == 1200)
            {
                //reset stuff
                player = null;
                state = 0;
                BlockState bs = level.getBlockState(getBlockPos());
                bs = bs.setValue(ChaseControllerBlock.WAYPOINTS, getTotalWaypoints());
                bs = bs.setValue(ChaseControllerBlock.WAYPOINTS_COMPLETED, 0);
                level.setBlockAndUpdate(getBlockPos(), bs);

                //play closing sound
                level.playSound(null, getBlockPos(), SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.BLOCKS);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() - 0.5f, getBlockPos().getY(), getBlockPos().getZ() - 0.5f, 50, 1f, 1f, 1f, 0f);

            }

            //play ambient bubble particles around block
            ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(),
                    getBlockPos().getX() + 0.5f,
                    getBlockPos().getY() + 0.5f,
                    getBlockPos().getZ() + 0.5f,
                    1,
                    0.5f, 0.3f, 0.5f, 0);


        }

        //drop items stored in arrayOfItemStacks
        if (state == 6)
        {
            if (counter % 4 == 0)
            {
                ((ServerLevel) level).sendParticles(ModParticles.CHASE_PUZZLE_PARTICLES.get(), getBlockPos().getX() + 0.5f, getBlockPos().getY() + 1.2f, getBlockPos().getZ() + 0.5f, 1, 0, 0, 0, 0);

                //runs if array has something
                if (arrayOfItemStacks != null)
                {
                    if (arrayOfItemStacks.isEmpty())
                    {
                        state = 5;
                        return;
                    }

                    Random r = new Random();

                    BlockPos pos = this.getBlockPos();
                    int randomInt = r.nextInt(arrayOfItemStacks.size());
                    ItemStack randomItemStack = arrayOfItemStacks.get(randomInt);

                    if (randomItemStack.getCount() == 1)
                    {
                        arrayOfItemStacks.remove(randomInt);
                        Item randomItem = randomItemStack.getItem();
                        this.level.addFreshEntity(new ItemEntity(this.level, pos.getX() + 0.5f, pos.getY() + 1.2f, pos.getZ() + 0.5f, new ItemStack(randomItem)));
                    }

                    if (randomItemStack.getCount() > 1)
                    {
                        randomItemStack.shrink(1);

                        arrayOfItemStacks.set(randomInt, randomItemStack);

                        Item randomItem = randomItemStack.getItem();
                        this.level.addFreshEntity(new ItemEntity(this.level, pos.getX() + 0.5f, pos.getY() + 1.2f, pos.getZ() + 0.5f, new ItemStack(randomItem)));
                    }

                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            pos.getX() - 0.5f + r.nextFloat(2f),
                            pos.getY() + r.nextFloat(1.2f),
                            pos.getZ() - 0.5f + r.nextFloat(2f),
                            1,
                            0f, 0f, 0f, 0f
                    );
                    level.playSound(null, pos, SoundEvents.CRAFTER_CRAFT, SoundSource.BLOCKS, 1f, r.nextFloat(0.1f) + 0.95f);
                }
            }
        }

    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            if (this.arrayuuid[i] == null)
            {
                if (tag.contains("user" + i)) tag.remove("user" + i);

            } else
            {
                tag.putUUID("user" + i, this.arrayuuid[i]);
            }
        }

        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(waypoints[i], bsZero))
            {
                break;
            }
            tag.putIntArray("waypoint" + i, new int[]{waypoints[i].getX(), waypoints[i].getY(), waypoints[i].getZ()});
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);

        for (int i = 0; i < arrayuuid.length; i++)
        {
            if (tag.contains("user" + i))
                this.arrayuuid[i] = tag.getUUID("user" + i);
        }

        for (int i = 0; i < 7; i++)
        {
            if (tag.contains("waypoint" + i))
            {
                waypoints[i] = new BlockPos(tag.getIntArray("waypoint" + i)[0], tag.getIntArray("waypoint" + i)[1], tag.getIntArray("waypoint" + i)[2]);
            } else
            {
                break;
            }
        }
    }

    public ChaseControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.CHASE_CONTROLLER_BLOCK.get(), pPos, pBlockState);
    }

}
