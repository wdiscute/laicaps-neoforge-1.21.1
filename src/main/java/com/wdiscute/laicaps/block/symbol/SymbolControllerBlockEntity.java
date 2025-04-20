package com.wdiscute.laicaps.block.symbol;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.chase.ChaseControllerBlock;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.particle.ModParticles;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class SymbolControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private int counter = 0;
    private boolean ticking = true;
    private int counterOffset = new Random().nextInt(20);
    private int state = 0;

    private BlockPos zero = new BlockPos(0, 0, 0);
    private BlockPos[] links = {zero, zero, zero, zero, zero, zero, zero, zero, zero, zero};

    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private final UUID[] arrayuuid = new UUID[15];


    public BlockPos DecodeBlockPosWithOffset(Direction facing, BlockPos pos, BlockPos posOffset)
    {
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

    public void setNextLinkedBlock(BlockPos blockPos)
    {
        setChanged();
        for (int i = 0; i < 10; i++)
        {
            if (Objects.equals(links[i], zero))
            {
                links[i] = blockPos;
                break;
            }
        }

    }

    public Boolean setTicking()
    {
        setChanged();
        this.ticking = !this.ticking;
        return this.ticking;
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

        //setup of random symbols for blocks, only runs on load
        if (state == 0)
        {
            state = 1;
            for (int i = 0; i < 10; i++)
            {
                //get blockPos and blockState of linked block
                BlockPos linkedBP = DecodeBlockPosWithOffset(getBlockState().getValue(SymbolControllerBlock.FACING), getBlockPos(), links[i]);
                BlockState linkedBS = level.getBlockState(linkedBP);

                if (level.getBlockEntity(linkedBP) instanceof SymbolPuzzleBlockEntity spbe)
                {
                    String symbols = spbe.getSymbols();

                    //randomize SymbolPuzzleBlock.SYMBOLS based on symbols allowed
                    if (linkedBS.getValue(SymbolPuzzleBlock.SYMBOLS) == SymbolsEnum.RANDOM)
                    {
                        if (symbols.equals("all"))
                        {
                            level.setBlockAndUpdate(linkedBP, linkedBS.setValue(SymbolPuzzleBlock.SYMBOLS, SymbolsEnum.getRandom()));
                        } else
                        {
                            for (int j = 0; j < 100; j++)
                            {
                                SymbolsEnum symbolRandom = SymbolsEnum.getRandom();
                                if (symbols.contains(symbolRandom.getSerializedName()))
                                {
                                    level.setBlockAndUpdate(linkedBP, linkedBS.setValue(SymbolPuzzleBlock.SYMBOLS, symbolRandom));
                                    break;
                                }
                            }
                        }
                    }

                    //get blockPos and blockState of block linked in SymbolPuzzleBlock
                    BlockPos linkedInactiveBP = DecodeBlockPosWithOffset(linkedBS.getValue(SymbolPuzzleBlock.FACING), linkedBP, spbe.getLinkedBLock());
                    BlockState linkedInactiveBS = level.getBlockState(linkedInactiveBP);

                    //if block linked in SymbolPuzzleBlock is SYMBOL_PUZZLE_BLOCK_INACTIVE and its symbol is random, randomize
                    if (linkedInactiveBS.is(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE) && linkedInactiveBS.getValue(SymbolPuzzleBlockInactive.SYMBOLS) == SymbolsEnum.RANDOM)
                    {
                        for (int j = 0; j < 100; j++)
                        {
                            SymbolsEnum symbolRandom = SymbolsEnum.getRandom();
                            if ((symbols.equals("all") || (symbols.contains(symbolRandom.getSerializedName()))) && symbolRandom != level.getBlockState(linkedBP).getValue(SymbolPuzzleBlock.SYMBOLS))
                            {
                                level.setBlockAndUpdate(linkedInactiveBP, linkedInactiveBS.setValue(SymbolPuzzleBlockInactive.SYMBOLS, symbolRandom));
                                break;
                            }
                        }
                    }


                }


            }
        }

        if (state == 1)
        {
            if (!ticking) return;
            if ((counterOffset + counter) % 20 != 0) return;
            boolean flag = true;

            for (int i = 0; i < 10; i++)
            {
                if (links[i].equals(zero))
                {
                    break;
                }

                //get blockPos and blockState of linked block
                BlockPos linkedBP = DecodeBlockPosWithOffset(getBlockState().getValue(SymbolControllerBlock.FACING), getBlockPos(), links[i]);
                BlockState linkedBS = level.getBlockState(linkedBP);

                if (level.getBlockEntity(linkedBP) instanceof SymbolPuzzleBlockEntity spbe)
                {
                    BlockPos linkedInactiveBP = DecodeBlockPosWithOffset(linkedBS.getValue(SymbolPuzzleBlock.FACING), linkedBP, spbe.getLinkedBLock());
                    BlockState linkedInactiveBS = level.getBlockState(linkedInactiveBP);

                    if (linkedInactiveBS.is(ModBlocks.SYMBOL_PUZZLE_BLOCK_INACTIVE))
                    {
                        if (linkedInactiveBS.getValue(SymbolPuzzleBlockInactive.SYMBOLS) != linkedBS.getValue(SymbolPuzzleBlock.SYMBOLS))
                        {
                            flag = false;
                        }
                    }
                }
            }


            if (flag != getBlockState().getValue(SymbolControllerBlock.ACTIVE))
            {
                if (flag)
                {
                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            getBlockPos().getX() + 0.5f,
                            getBlockPos().getY() + 0.5f,
                            getBlockPos().getZ() + 0.5f,
                            30,
                            0.5f, 0.5f, 0.5f, 0f
                    );
                }
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(SymbolControllerBlock.ACTIVE, flag));
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

    public SymbolControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.SYMBOL_CONTROLLER_BLOCK.get(), pPos, pBlockState);
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
            if (tag.contains("link" + i))
            {
                links[i] = new BlockPos(tag.getIntArray("link" + i)[0], tag.getIntArray("link" + i)[1], tag.getIntArray("link" + i)[2]);
            } else
            {
                break;
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
                break;
            tag.putUUID("user" + i, this.arrayuuid[i]);
        }

        for (int i = 0; i < 10; i++)
        {
            if (Objects.equals(links[i], zero))
            {
                break;
            }
            tag.putIntArray("link" + i, new int[]{links[i].getX(), links[i].getY(), links[i].getZ()});
        }
    }
}
