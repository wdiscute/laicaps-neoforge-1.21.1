package com.wdiscute.laicaps.block.notes;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class NotesControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private int counter = -1;
    private int currentWave = -1;

    //0 - nothing
    //1 - displaying notes
    //2 - listening
    //3 - you did it! sounds and stuff
    //4 - WRONG! resetting
    //5 - complete, waiting before resetting
    private int state = 0;

    String[] wavesPerm = {"012", "012", "012", "", ""};
    String[] waves = {"", "", "", "", ""};
    BlockPos zero = new BlockPos(0, 0, 0);
    BlockPos[] links = {zero, zero, zero, zero, zero, zero, zero, zero, zero, zero};

    private String waveHelper = "";
    private String waveListener = "";

    private final UUID[] arrayuuid = new UUID[15];
    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});

    public void resetPlayersSaved()
    {
        for (int i = 0; i < 15; i++)
        {
            arrayuuid[i] = UUID.randomUUID();
        }
    }

    public void CanPlayerObtainDrops(Player player)
    {
        if(this.level.isClientSide) return;
        if (state == 6)
        {
            player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.treasure_chest_busy"), true);
            return ;
        }

        if(state != 5) return;

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            UUID uuid = player.getUUID();
            if (Objects.equals(this.arrayuuid[i], uuid))
            {
                level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 2f, 0.5f);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
                player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.treasure_chest_looted"), true);
                return;
            }
            if (Objects.equals(this.arrayuuid[i], null))
            {
                this.arrayuuid[i] = uuid;
                state = 6;

                LootParams.Builder builder = new LootParams.Builder((ServerLevel) this.level);
                LootParams params = builder.create(LootContextParamSets.EMPTY);

                ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha_puzzle"));

                arrayOfItemStacks = this.level.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);
                return;
            }

        }
    }


    public Integer getState()
    {
        return state;
    }

    public void setNextLinkedBlock(BlockPos blockPos, Player player)
    {
        BlockPos blockPosZero = new BlockPos(0, 0, 0);
        for (int i = 0; i < 11; i++)
        {
            if (Objects.equals(links[i], blockPosZero))
            {
                links[i] = blockPos;
                setChanged();
                player.displayClientMessage(Component.literal("Set " + blockPos + " to slot " + i), true);
                return;
            }
        }
    }

    //aux method to get how many waves have values to set state WAVES
    private int getTotalWaves()
    {
        if (wavesPerm[0].isEmpty()) return 1; //should never happen!
        if (wavesPerm[1].isEmpty()) return 1;
        if (wavesPerm[2].isEmpty()) return 2;
        if (wavesPerm[3].isEmpty()) return 3;
        if (wavesPerm[4].isEmpty()) return 4;
        return 5;
    }

    public void start()
    {
        if (state != 0)
        {
            return;
        }

        waveHelper = "";
        waveListener = "";
        counter = 0;
        currentWave = 0;
        state = 1;

        setWaveValues();

        BlockState bs = level.getBlockState(getBlockPos());
        bs = bs.setValue(NotesControllerBlock.WAVES, getTotalWaves());
        bs = bs.setValue(NotesControllerBlock.WAVES_COMPLETE, 0);
        bs = bs.setValue(NotesControllerBlock.WAVE_IN_PROGRESS, false);
        level.setBlockAndUpdate(getBlockPos(), bs);


    }

    public void reset()
    {
        waveHelper = "";
        waveListener = "";
        counter = -1;
        currentWave = 0;
        state = 0;

        setWaveValues();

        BlockState bs = level.getBlockState(getBlockPos());
        bs = bs.setValue(NotesControllerBlock.WAVES, getTotalWaves());
        bs = bs.setValue(NotesControllerBlock.WAVES_COMPLETE, 0);
        bs = bs.setValue(NotesControllerBlock.WAVE_IN_PROGRESS, false);
        level.setBlockAndUpdate(getBlockPos(), bs);


    }

    private BlockPos DecodeBlockPosWithOffset(Level plevel, BlockPos pos, BlockPos posOffset)
    {
        //returns the world coords of the linked block based on the offset stored
        BlockState pState = plevel.getBlockState(pos);


        if (pState.getValue(NotesControllerBlock.FACING) == Direction.SOUTH)
            return new BlockPos(pos.getX() + (posOffset.getZ() * -1), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getX());

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.WEST)
            return new BlockPos(pos.getX() + (posOffset.getX() * -1), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getZ() * -1));

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.NORTH)
            return new BlockPos(pos.getX() + posOffset.getZ(), pos.getY() + posOffset.getY(), pos.getZ() + (posOffset.getX() * -1));

        if (pState.getValue(NotesControllerBlock.FACING) == Direction.EAST)
            return new BlockPos(pos.getX() + posOffset.getX(), pos.getY() + posOffset.getY(), pos.getZ() + posOffset.getZ());

        return new BlockPos(0, 0, 0);

    }

    private int checkBlocksLinked()
    {
        BlockPos zero = new BlockPos(0, 0, 0);
        int ret = 10;
        if (Objects.equals(links[9], zero)) ret = 9;
        if (Objects.equals(links[8], zero)) ret = 8;
        if (Objects.equals(links[7], zero)) ret = 7;
        if (Objects.equals(links[6], zero)) ret = 6;
        if (Objects.equals(links[5], zero)) ret = 5;
        if (Objects.equals(links[4], zero)) ret = 4;
        if (Objects.equals(links[3], zero)) ret = 3;
        if (Objects.equals(links[2], zero)) ret = 2;
        if (Objects.equals(links[1], zero)) ret = 1;
        if (Objects.equals(links[0], zero)) ret = 0;

        return ret;

    }

    private void setWaveValues()
    {
        setChanged();
        Random r = new Random();
        int blocksLinked = checkBlocksLinked();

        for (int k = 0; k < 5; k++)
        {
            if (wavesPerm[k].contains("random") && blocksLinked > 0)
            {
                waves[k] = "";
                for (int i = 0; i < Integer.parseInt(wavesPerm[k].substring(6)); i++)
                    waves[k] += r.nextInt(blocksLinked);
            } else
            {
                if (blocksLinked == 0)
                {
                    waves[k] = "";
                } else
                {
                    waves[k] = wavesPerm[k];
                }

            }
        }


    }

    private int getSlotFromBlockOffset(BlockPos bp)
    {
        for (int i = 0; i < 10; i++)
        {
            if (links[i].equals(bp))
            {
                return i;
            }
        }
        return -1;
    }

    public void receiveClicked(BlockPos bp)
    {
        if (state == 2)
        {
            waveListener += "" + getSlotFromBlockOffset(bp);
        }

    }

    @Override
    public void tick()
    {

        //counter goes up only while state is not 0
        if (state != 0) counter++;

        if(state == 0 && getBlockState().getValue(NotesControllerBlock.WAVES_COMPLETE) != 0) reset();
        if(state == 0 && getBlockState().getValue(NotesControllerBlock.WAVE_IN_PROGRESS)) reset();

        //displaying sequence
        if (state == 1)
        {
            //set waveHelper to currentwave if empty
            if (Objects.equals(waveHelper, "")) waveHelper = waves[currentWave];

            //every 20 ticks activates the noteblock in pos waveHelper.substring(0, 1) and removed it from wavehelper
            if (counter == 20)
            {
                int currentLinkedPos = Integer.parseInt(waveHelper.substring(0, 1));
                BlockPos bpDecoded = DecodeBlockPosWithOffset(level, getBlockPos(), links[currentLinkedPos]);

                if (level.getBlockEntity(bpDecoded) instanceof NotesPuzzleBlockEntity npbe)
                {
                    npbe.playNote(10);
                }
                counter = 0;
                waveHelper = waveHelper.substring(1);
            }

            //if there are no more numbers in waveHelper
            if (Objects.equals(waveHelper, ""))
            {
                state = 2;
                waveListener = "";
                BlockState bs = level.getBlockState(getBlockPos()).setValue(NotesControllerBlock.WAVE_IN_PROGRESS, true);
                level.setBlockAndUpdate(getBlockPos(), bs);
            }
        }

        //listening to sequence input
        if (state == 2)
        {
            counter = 0;
            if (waveListener.length() >= waves[currentWave].length())
            {
                if (Objects.equals(waveListener, waves[currentWave]))
                {
                    state = 3;
                    BlockState bs = level.getBlockState(getBlockPos()).setValue(NotesControllerBlock.WAVE_IN_PROGRESS, false);
                    bs = bs.setValue(NotesControllerBlock.WAVES_COMPLETE, currentWave + 1);
                    level.setBlockAndUpdate(getBlockPos(), bs);
                } else
                {
                    state = 4;
                    BlockState bs = level.getBlockState(getBlockPos()).setValue(NotesControllerBlock.WAVE_IN_PROGRESS, false);
                    bs = bs.setValue(NotesControllerBlock.WAVES_COMPLETE, 0);
                    level.setBlockAndUpdate(getBlockPos(), bs);
                }
            }
        }

        //correct sequence
        if (state == 3)
        {
            if (counter == 10)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1.5f, 1f);
            if (counter == 20)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1.5f, 0.9f);
            if (counter == 25)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1.5f, 1.1f);
            if (counter == 30)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1.5f, 1.3f);
            if (counter == 35)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1.5f, 1.5f);
            if (counter == 50)
            {
                //if its wave 4 sets state to complete since theres no more after
                if (currentWave == 4)
                {
                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            getBlockPos().getX() + 0.5f,
                            getBlockPos().getY() + 0.5f,
                            getBlockPos().getZ() + 0.5f,
                            30,
                            0.5f, 0.5f, 0.5f, 0f
                    );
                    state = 5;
                    return;
                }

                //if next wave is empty sets state to complete
                if (!Objects.equals(waves[currentWave + 1], ""))
                {
                    currentWave += 1;
                    state = 1;
                    counter = 0;
                } else
                {
                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            getBlockPos().getX() + 0.5f,
                            getBlockPos().getY() + 0.5f,
                            getBlockPos().getZ() + 0.5f,
                            30,
                            0.5f, 0.5f, 0.5f, 0f
                    );
                    state = 5;
                }

            }
        }

        //wong sequence
        if (state == 4)
        {
            if (counter == 10)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1f, 1f);
            if (counter == 20)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1f, 0.9f);
            if (counter == 30)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1f, 0.8f);
            if (counter == 40)
                level.playSound(null, getBlockPos(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.BLOCKS, 1f, 0.7f);
            if (counter == 60)
            {
                state = 0;
                counter = -1;
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
                state = 0;
                BlockState bs = level.getBlockState(getBlockPos());
                bs = bs.setValue(NotesControllerBlock.WAVES, getTotalWaves());
                bs = bs.setValue(NotesControllerBlock.WAVES_COMPLETE, 0);
                bs = bs.setValue(NotesControllerBlock.WAVE_IN_PROGRESS, false);
                level.setBlockAndUpdate(getBlockPos(), bs);

                //play closing sound
                level.playSound(null, getBlockPos(), SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.BLOCKS);
                ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() - 0.5f, getBlockPos().getY(), getBlockPos().getZ() - 0.5f, 50, 1f, 1f, 1f, 0f);

            }
        }

        if (state == 6)
        {
            //grabs loot table and stores in arrayOfItemStacks
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

    public NotesControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.NOTES_CONTROLLER_BLOCK.get(), pPos, pBlockState);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.loadAdditional(pTag, pRegistries);

        for (int i = 0; i < arrayuuid.length; i++)
        {
            if (pTag.contains("user" + i))
                this.arrayuuid[i] = pTag.getUUID("user" + i);
        }

        wavesPerm[0] = pTag.getString("wave0");
        wavesPerm[1] = pTag.getString("wave1");
        wavesPerm[2] = pTag.getString("wave2");
        wavesPerm[3] = pTag.getString("wave3");
        wavesPerm[4] = pTag.getString("wave4");

        links[0] = new BlockPos(pTag.getIntArray("link0")[0], pTag.getIntArray("link0")[1], pTag.getIntArray("link0")[2]);
        links[1] = new BlockPos(pTag.getIntArray("link1")[0], pTag.getIntArray("link1")[1], pTag.getIntArray("link1")[2]);
        links[2] = new BlockPos(pTag.getIntArray("link2")[0], pTag.getIntArray("link2")[1], pTag.getIntArray("link2")[2]);
        links[3] = new BlockPos(pTag.getIntArray("link3")[0], pTag.getIntArray("link3")[1], pTag.getIntArray("link3")[2]);
        links[4] = new BlockPos(pTag.getIntArray("link4")[0], pTag.getIntArray("link4")[1], pTag.getIntArray("link4")[2]);
        links[5] = new BlockPos(pTag.getIntArray("link5")[0], pTag.getIntArray("link5")[1], pTag.getIntArray("link5")[2]);
        links[6] = new BlockPos(pTag.getIntArray("link6")[0], pTag.getIntArray("link6")[1], pTag.getIntArray("link6")[2]);
        links[7] = new BlockPos(pTag.getIntArray("link7")[0], pTag.getIntArray("link7")[1], pTag.getIntArray("link7")[2]);
        links[8] = new BlockPos(pTag.getIntArray("link8")[0], pTag.getIntArray("link8")[1], pTag.getIntArray("link8")[2]);
        links[9] = new BlockPos(pTag.getIntArray("link9")[0], pTag.getIntArray("link9")[1], pTag.getIntArray("link9")[2]);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.saveAdditional(pTag, pRegistries);

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            if (this.arrayuuid[i] == null)
                break;
            pTag.putUUID("user" + i, this.arrayuuid[i]);
        }

        pTag.putString("wave0", wavesPerm[0]);
        pTag.putString("wave1", wavesPerm[1]);
        pTag.putString("wave2", wavesPerm[2]);
        pTag.putString("wave3", wavesPerm[3]);
        pTag.putString("wave4", wavesPerm[4]);

        pTag.putIntArray("link0", new int[]{links[0].getX(), links[0].getY(), links[0].getZ()});
        pTag.putIntArray("link1", new int[]{links[1].getX(), links[1].getY(), links[1].getZ()});
        pTag.putIntArray("link2", new int[]{links[2].getX(), links[2].getY(), links[2].getZ()});
        pTag.putIntArray("link3", new int[]{links[3].getX(), links[3].getY(), links[3].getZ()});
        pTag.putIntArray("link4", new int[]{links[4].getX(), links[4].getY(), links[4].getZ()});
        pTag.putIntArray("link5", new int[]{links[5].getX(), links[5].getY(), links[5].getZ()});
        pTag.putIntArray("link6", new int[]{links[6].getX(), links[6].getY(), links[6].getZ()});
        pTag.putIntArray("link7", new int[]{links[7].getX(), links[7].getY(), links[7].getZ()});
        pTag.putIntArray("link8", new int[]{links[8].getX(), links[8].getY(), links[8].getZ()});
        pTag.putIntArray("link9", new int[]{links[9].getX(), links[9].getY(), links[9].getZ()});
    }
}
