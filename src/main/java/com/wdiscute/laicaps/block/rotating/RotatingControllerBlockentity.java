package com.wdiscute.laicaps.block.rotating;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.TickableBlockEntity;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class RotatingControllerBlockentity extends BlockEntity implements TickableBlockEntity
{
    private int state = 0;
    private int counter = -1;
    private final Random r = new Random();

    BlockPos bsZero = new BlockPos(0, 0, 0);
    private BlockPos[] blocksLinkedBP = {bsZero, bsZero, bsZero, bsZero, bsZero, bsZero, bsZero};
    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private final UUID[] arrayuuid = new UUID[15];

    public void resetPlayersSaved()
    {
        for (int i = 0; i < 15; i++)
        {
            arrayuuid[i] = null;
        }
    }


    public void obtainDrops(Player player)
    {
        setChanged();
        if (this.level.isClientSide) return;

        BlockState bs = level.getBlockState(getBlockPos());
        if (!bs.getValue(RotatingControllerBlock.CORRECT_BLOCKS).equals(bs.getValue(RotatingControllerBlock.BLOCKS)))
        {
            level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 3f, 0.5f);
            ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 50, 0.5f, 0.5f, 0.5f, 0f);
            return;
        }

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
                counter = 0;

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

    private int getTotalBlocksLinked()
    {
        if (blocksLinkedBP[0].equals(bsZero)) return 0;
        if (blocksLinkedBP[1].equals(bsZero)) return 1;
        if (blocksLinkedBP[2].equals(bsZero)) return 2;
        if (blocksLinkedBP[3].equals(bsZero)) return 3;
        if (blocksLinkedBP[4].equals(bsZero)) return 4;
        if (blocksLinkedBP[5].equals(bsZero)) return 5;
        if (blocksLinkedBP[6].equals(bsZero)) return 6;
        return 7;
    }

    public boolean setNextLinkedBlock(BlockPos posOffset, Player player)
    {
        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(blocksLinkedBP[i], bsZero))
            {

                setChanged();
                blocksLinkedBP[i] = posOffset;

                BlockState bs = level.getBlockState(getBlockPos());
                level.setBlockAndUpdate(
                        getBlockPos(), bs.setValue(RotatingControllerBlock.BLOCKS, getTotalBlocksLinked()));

                player.displayClientMessage(Component.translatable("Set Waypoint " + (i + 1) + "/7"), true);
                return true;
            }
        }
        return false;
    }

    public BlockPos decodeBlockPosWithOffset(Direction facing, BlockPos posOffset)
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

    @Override
    public void tick()
    {

        if (counter > -1) counter++;

        //idle
        if (state == 0)
        {
            BlockState bs = level.getBlockState(getBlockPos());
            Direction main = bs.getValue(HorizontalDirectionalBlock.FACING);

            int numberOfCorrectBlocks = 0;
            if (getTotalBlocksLinked() == 0) return;

            for (int i = 0; i < getTotalBlocksLinked(); i++)
            {
                BlockState r = level.getBlockState(decodeBlockPosWithOffset(main, blocksLinkedBP[i]));
                if (r.is(ModBlocks.ROTATING_PUZZLE_BLOCK))
                {
                    if(r.getValue(HorizontalDirectionalBlock.FACING) == bs.getValue(HorizontalDirectionalBlock.FACING))
                    {
                        numberOfCorrectBlocks++;
                    }
                }
                else
                {
                    return;
                }
            }

            if (level.getBlockState(getBlockPos()).getValue(RotatingControllerBlock.CORRECT_BLOCKS) != numberOfCorrectBlocks)
            {
                setChanged();
                level.setBlockAndUpdate(getBlockPos(), bs.setValue(RotatingControllerBlock.CORRECT_BLOCKS, numberOfCorrectBlocks));
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
                        state = 0;
                        counter = -1;
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

            }
            else
            {
                tag.putUUID("user" + i, this.arrayuuid[i]);
            }
        }

        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(blocksLinkedBP[i], bsZero))
            {
                break;
            }
            tag.putIntArray("blocks" + i, new int[]{blocksLinkedBP[i].getX(), blocksLinkedBP[i].getY(), blocksLinkedBP[i].getZ()});
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
            if (tag.contains("blocks" + i))
            {
                blocksLinkedBP[i] = new BlockPos(tag.getIntArray("blocks" + i)[0], tag.getIntArray("blocks" + i)[1], tag.getIntArray("blocks" + i)[2]);
            }
            else
            {
                break;
            }
        }
    }

    public RotatingControllerBlockentity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.ROTATING_CONTROLLER.get(), pPos, pBlockState);
    }

}
