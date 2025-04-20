package com.wdiscute.laicaps.block.hidden;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesControllerBlock;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
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

public class HiddenControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{

    BlockPos ze = new BlockPos(0, 0, 0);
    BlockPos[] linksBS = {ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze, ze};
    String[] linksString = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    int counter;
    int randomOffset = new Random().nextInt(19);
    int state = 1;

    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private final UUID[] arrayuuid = new UUID[15];

    public void setNextLinkedBlock(BlockPos blockPos, Player player)
    {
        setChanged();
        BlockPos blockPosZero = new BlockPos(0, 0, 0);
        for (int i = 0; i < linksBS.length; i++)
        {
            if (Objects.equals(linksBS[i], blockPosZero))
            {
                linksBS[i] = blockPos;
                BlockPos decoded = DecodeBlockPosWithOffset(level, getBlockPos(), blockPos);

                linksString[i] = BuiltInRegistries.BLOCK.getKey(level.getBlockState(decoded).getBlock()).toString();

                player.displayClientMessage(Component.literal(
                        "Linked " +
                                "X: §a" + decoded.getX() +
                                "§r, Y: §a" + decoded.getY() +
                                "§r, Z: §a" + decoded.getZ() +
                                "§r to slot §c" + i + "§r with block §3" + linksString[i]), false);
                return;
            }
        }

        player.displayClientMessage(Component.literal("§cThere are no more free slots in this controller"), true);
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

    //aux method to get how many waves have values to set state WAVES
    private int getTotalLinks()
    {
        for (int i = 0; i < linksBS.length; i++)
        {
            if (Objects.equals(linksBS[i], ze)) return i;
        }
        return linksBS.length;
    }


    public void CanPlayerObtainDrops(Player player)
    {
        setChanged();
        if (this.level.isClientSide) return;
        if (state == 6)
        {
            player.displayClientMessage(Component.translatable("tooltip.laicaps.generic.treasure_chest_busy"), true);
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

                LootParams.Builder builder = new LootParams.Builder((ServerLevel) this.level);
                LootParams params = builder.create(LootContextParamSets.EMPTY);

                ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, "asha_puzzle"));

                arrayOfItemStacks = this.level.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);
                return;
            }
        }
    }

    @Override
    public void tick()
    {
        counter++;

        if (state == 1)
        {
            if ((counter + randomOffset) % (20 + randomOffset) != 0) return;
            counter = 0;

            BlockState bs = level.getBlockState(getBlockPos());
            BlockState bs2 = bs;
            bs = bs.setValue(HiddenControllerBlock.ACTIVE, true);
            for (int i = 0; i < getTotalLinks(); i++)
            {
                BlockPos decoded = DecodeBlockPosWithOffset(level, getBlockPos(), linksBS[i]);
                if (!BuiltInRegistries.BLOCK.getKey(level.getBlockState(decoded).getBlock()).toString().equals(linksString[i]))
                {
                    bs = bs.setValue(HiddenControllerBlock.ACTIVE, false);
                    level.setBlockAndUpdate(getBlockPos(), bs);
                    break;
                }
            }

            //if active is false and becomes true, you did it!
            if (bs.getValue(HiddenControllerBlock.ACTIVE) && !bs2.getValue(HiddenControllerBlock.ACTIVE))
            {
                level.setBlockAndUpdate(getBlockPos(), bs);

                level.playSound(null, getBlockPos(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1f, 2f);

                Random r = new Random();
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        getBlockPos().getX() + 0.5f,
                        getBlockPos().getY() + 1f,
                        getBlockPos().getZ() + 0.5f,
                        30,
                        2f, 1f, 2f, 0f
                );
            }

        }

        //drop items
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
                        state = 1;
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
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries)
    {
        super.loadAdditional(tag, pRegistries);

        for (int i = 0; i < linksBS.length; i++)
        {
            if (tag.contains("linkbs" + i))
            {
                linksBS[i] = new BlockPos(tag.getIntArray("linkbs" + i)[0], tag.getIntArray("linkbs" + i)[1], tag.getIntArray("linkbs" + i)[2]);
                linksString[i] = tag.getString("linkstring" + i);
            }
        }

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries)
    {
        super.saveAdditional(tag, pRegistries);

        for (int i = 0; i < linksBS.length; i++)
        {
            //breaks if linkBS[i] hasn't been changed
            if (Objects.equals(linksBS[i], ze)) break;

            tag.putIntArray("linkbs" + i, new int[]{linksBS[i].getX(), linksBS[i].getY(), linksBS[i].getZ()});
        }

        for (int i = 0; i < linksString.length; i++)
        {
            //breaks if linkBS[i] hasn't been changed
            if (Objects.equals(linksString[i], "")) break;
            tag.putString("linkstring" + i, linksString[i]);
        }

    }

    public HiddenControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.HIDDEN_CONTROLLER_BLOCK.get(), pPos, pBlockState);
    }
}
