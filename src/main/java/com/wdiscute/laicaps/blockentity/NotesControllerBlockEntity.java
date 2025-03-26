package com.wdiscute.laicaps.blockentity;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.block.custom.notes.NotesControllerBlock;
import com.wdiscute.laicaps.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Random;

public class NotesControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private int counter = -1;
    private boolean start = false;
    private int currentWave = -1;

    //0 - nothing
    //1 - displaying notes
    //2 - listening
    //3 - you did it! sounds and stuff
    //4 - complete, waiting before resetting
    private int state = 0;

    String[] wavesPerm = {"random5", "1221", "random3", "random8", "random4"};
    String[] waves = {"", "", "", "", ""};
    BlockPos zero = new BlockPos(0, 0, 0);
    BlockPos[] links = {zero, zero, zero, zero, zero, zero, zero, zero, zero, zero};

    private String wavehelper = "";


    public BlockPos getLinkedBlock(int blockposlink)
    {
        setChanged();
        return links[blockposlink];
    }

    public void resetLinks()
    {
        setChanged();
        BlockPos zero = new BlockPos(0, 0, 0);
        links[0] = zero;
        links[1] = zero;
        links[2] = zero;
        links[3] = zero;
        links[4] = zero;
        links[5] = zero;
        links[6] = zero;
        links[7] = zero;
        links[8] = zero;
        links[9] = zero;
        links[10] = zero;
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

    public void start()
    {
        start = true;
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

    private void setRandomLinksValues()
    {

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

    @Override
    public void tick()
    {

        //start displaying the notes
        if (counter == -1 && start)
        {
            setRandomLinksValues();
            setWaveValues();
            currentWave = 0;
            state = 1;

            System.out.println("waves 0: " + waves[0]);
            System.out.println("waves 1: " + waves[1]);
            System.out.println("waves 2: " + waves[2]);
            System.out.println("waves 3: " + waves[3]);
            System.out.println("waves 4: " + waves[4]);
            counter++;
            return;
        }

        //System.out.println("counter" + counter);
        if (counter > -1) counter++;
        if (state == 1)
        {
            if (Objects.equals(wavehelper, "")) wavehelper = waves[currentWave];
            if (counter == 20)
            {
                int currentLinkedPos = Integer.parseInt(wavehelper.substring(0, 1));
                BlockPos bpDecoded = DecodeBlockPosWithOffset(level, getBlockPos(), links[currentLinkedPos]);

                if (level.getBlockEntity(bpDecoded) instanceof NotesPuzzleBlockEntity npbe)
                {
                    npbe.playNote(10);
                }
                counter = 0;
                wavehelper = wavehelper.substring(1);
            }

            if (Objects.equals(wavehelper, ""))
            {
                System.out.println("finished wave " + currentWave);
                currentWave += 1;
                state = 2;
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
