package com.wdiscute.laicaps.block.combat;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.TickableBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class CombatControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{

    private int state = 0;
    private int counter = -1;
    private final Random r = new Random();

    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private final UUID[] arrayuuid = new UUID[15];
    private List<UUID> enemiesList = new ArrayList<>();
    private int mobsToSpawn = 5;
    private int mobsRemaining = 0;
    private float rangeVertical = 0f;
    private float rangeHorizontal = 9.0f;


    public void start()
    {
        if(state == 1)
        {
            ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.5f);
        }

        if(state == 2)
        {
            ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.5f);
        }

        if (state == 0)
        {
            level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(CombatControllerBlock.MOBS_TO_SPAWN, Math.clamp(mobsToSpawn, 0, 7)));
            state = 1;
            counter = 0;
            enemiesList.clear();
        }
    }

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

                ResourceKey<LootTable> lootTable = ResourceKey.create(
                        Registries.LOOT_TABLE,
                        Laicaps.rl("chests/asha_puzzle"));

                arrayOfItemStacks = level.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);

                return;
            }
        }
    }


    @Override
    public void tick()
    {
        if (counter > -1) counter++;
        if (counter > 199099) counter = 20000;

        //idle
        if (state == 0)
        {
        }

        //spawning mobs one by one
        if (state == 1 && !level.isClientSide)
        {
            if (counter == 1)
                level.playSound(null, getBlockPos(), SoundEvents.TRIAL_SPAWNER_CLOSE_SHUTTER, SoundSource.BLOCKS);

            //run every 35 ticks
            if ((counter + 5) % 35 == 0)
            {
                Vec3 vec3 = new Vec3(
                        getBlockPos().getX() + 0.5 + r.nextFloat(rangeHorizontal + 0.1f) - (double) rangeHorizontal / 2,
                        getBlockPos().getY() + 0.5 + r.nextFloat(rangeVertical + 0.1f) - (double) rangeVertical / 2,
                        getBlockPos().getZ() + 0.5 + r.nextFloat(rangeHorizontal + 0.1f) - (double) rangeHorizontal / 2);

                //check if pos inside block, if it is, retry another pos
                for (int i = 0; i < 100; i++)
                {
                    if(level.getBlockState(new BlockPos((int) vec3.x, ((int) vec3.y), ((int) vec3.z))).isAir())
                    {
                        break;
                    }else
                    {
                        vec3 = new Vec3(
                                getBlockPos().getX() + 0.5 + r.nextFloat(rangeHorizontal + 0.1f) - (double) rangeHorizontal / 2,
                                getBlockPos().getY() + 0.5 + r.nextFloat(rangeVertical + 0.1f) - (double) rangeVertical / 2,
                                getBlockPos().getZ() + 0.5 + r.nextFloat(rangeHorizontal + 0.1f) - (double) rangeHorizontal / 2);
                    }
                }

                Drowned drowned = new Drowned(EntityType.DROWNED, getLevel());
                drowned.moveTo(vec3.x, vec3.y, vec3.z, 1, 1);
                getLevel().addFreshEntity(drowned);

                ((ServerLevel) level).sendParticles(ParticleTypes.LAVA,vec3.x, vec3.y + 0.5, vec3.z, 7, 0, 0.5, 0, 0);
                level.playSound(null, vec3.x, vec3.y, vec3.z, SoundEvents.TRIAL_SPAWNER_SPAWN_MOB, SoundSource.BLOCKS);
                enemiesList.add(drowned.getUUID());
                mobsRemaining++;
            }

            if (enemiesList.size() >= mobsToSpawn)
            {
                state = 2;
            }


        }


        if (state == 2)
        {
            if (level instanceof ServerLevel sv)
            {
                mobsRemaining = 0;
                for (int i = 0; i < enemiesList.size(); i++)
                {
                    if (sv.getEntities().get(enemiesList.get(i)) != null)
                    {
                        mobsRemaining++;
                    }
                }

                if(level.getBlockState(getBlockPos()).getValue(CombatControllerBlock.MOBS_KILLED) != mobsToSpawn - Math.clamp(mobsRemaining, 0, 7))
                {
                    level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(CombatControllerBlock.MOBS_KILLED, mobsToSpawn - Math.clamp(mobsRemaining, 0, 7)));
                }

                if (mobsRemaining == 0)
                {
                    counter = 0;
                    state = 5;
                }

            }
        }

        //puzzle complete - waiting for a valid player to claim loot
        if (state == 5)
        {
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
                state = 0;
                mobsRemaining = 0;
                counter = -1;
                enemiesList.clear();
                //play closing sound
                level.playSound(null, getBlockPos(), SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.BLOCKS);
                //reset mobs killed BS
                level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(CombatControllerBlock.MOBS_KILLED, 0));
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() - 0.5f, getBlockPos().getY(), getBlockPos().getZ() - 0.5f, 50, 1f, 1f, 1f, 0f);

            }

        }

        //drop items stored in arrayOfItemStacks
        if (state == 6)
        {
            if (counter % 4 == 0)
            {
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

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);

        tag.putInt("mobstospawn", mobsToSpawn);
        tag.putFloat("rangehorizontal", rangeHorizontal);
        tag.putFloat("rangevertical", rangeVertical);

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            if (this.arrayuuid[i] == null)
            {
                if (tag.contains("user" + i)) tag.remove("user" + i);
            }
            else
            {
                tag.putUUID("user" + i, this.arrayuuid[i]);
            }
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);

        mobsToSpawn = tag.getInt("mobstospawn");

        rangeHorizontal = tag.getFloat("rangehorizontal") + 0.1f;
        rangeVertical = tag.getFloat("rangevertical") + 0.1f;

        for (int i = 0; i < arrayuuid.length; i++)
        {
            if (tag.contains("user" + i))
                this.arrayuuid[i] = tag.getUUID("user" + i);
        }

    }

    public CombatControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.COMBAT_CONTROLLER_BLOCK.get(), pPos, pBlockState);
    }

}
