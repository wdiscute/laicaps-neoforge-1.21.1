package com.wdiscute.laicaps.block.receiversender;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ReceiverBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private int counter = 0;
    private int tickOffset = 0;
    private final UUID[] arrayuuid = new UUID[15];
    private boolean droppingItems;
    private ObjectArrayList<ItemStack> arrayOfItemStacks = new ObjectArrayList<ItemStack>(new ItemStack[]{});
    private Player lastPlayerToClick;

    public void CanPlayerObtainDrops(Player player)
    {
        if (this.droppingItems) return;  //skips if its currently dropping items for another player

        for (int i = 0; i < this.arrayuuid.length; i++)
        {
            UUID uuid = player.getUUID();
            if (Objects.equals(this.arrayuuid[0], uuid))
            {
                level.playSound(null, this.getBlockPos(), SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 2f, 0.5f);
                return;
            }
            if (Objects.equals(this.arrayuuid[0], null))
            {
                this.lastPlayerToClick = player;
                this.arrayuuid[i] = uuid;
                this.droppingItems = true;

                LootParams.Builder builder = new LootParams.Builder((ServerLevel) this.level);
                LootParams params = builder.create(LootContextParamSets.EMPTY);

                arrayOfItemStacks = this.level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.ABANDONED_MINESHAFT).getRandomItems(params);

                return;
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
                return;

            tag.putUUID("user" + i, this.arrayuuid[i]);
        }


    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);

        for (int i = 0; i < 16; i++)
        {

            if (tag.contains("user" + i))
                this.arrayuuid[i] = tag.getUUID("user" + i);


        }

    }


    @Override
    public void tick()
    {

        //return if there's no level for whatever reason or if its client side
        if (this.level == null || this.level.isClientSide()) return;

        //runs if array has something
        if (arrayOfItemStacks != null)
        {
            if (arrayOfItemStacks.isEmpty()) droppingItems = false;

            if (droppingItems && this.counter % 4 == 0)
            {
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

                System.out.println(lastPlayerToClick);
                System.out.println(this.level);
                System.out.println(this.getBlockPos());

                level.playSound(null, pos, SoundEvents.CRAFTER_CRAFT, SoundSource.BLOCKS, 1f, r.nextFloat(0.1f) + 0.95f);

            }

        }




        if (this.tickOffset == 0)
        {
            this.tickOffset = (int) (Math.random() * 20 + 1);
        }
        counter++;

        if ((counter + this.tickOffset) % 20 == 0)
        {
            this.level.scheduleTick(this.getBlockPos(), ModBlocks.RECEIVER_BLOCK.get(), 1);
        }

        if (counter > 10000000) counter = 0;

    }


    public ReceiverBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.RECEIVER_BLOCK.get(), pPos, pBlockState);
    }
}
