package com.wdiscute.laicaps.block.chase;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.generics.TickableBlockEntity;
import com.wdiscute.laicaps.block.notes.NotesControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.Objects;

public class ChaseControllerBlockEntity extends BlockEntity implements TickableBlockEntity
{
    private Player player = null;
    private int state = 0;
    private int current_waypoint = 0;

    BlockPos bszero = new BlockPos(0, 0, 0);
    private BlockPos[] waypoints = {bszero, bszero, bszero, bszero, bszero, bszero, bszero};

    public void assignPlayer(Player playeruuid)
    {
        if (player == null)
        {
            player = playeruuid;
            state = 1;
        }
    }

    public void UpdateBlockState()
    {
        setChanged();
        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ChaseControllerBlock.WAYPOINTS, getTotalWaypointsSet()));
    }

    private int getTotalWaypointsSet()
    {
        if(Objects.equals(waypoints[0], bszero)) return 0;
        if(Objects.equals(waypoints[1], bszero)) return 1;
        if(Objects.equals(waypoints[2], bszero)) return 2;
        if(Objects.equals(waypoints[3], bszero)) return 3;
        if(Objects.equals(waypoints[4], bszero)) return 4;
        if(Objects.equals(waypoints[5], bszero)) return 5;
        if(Objects.equals(waypoints[6], bszero)) return 6;
        return 7;
    }

    public Boolean SetNextLinkedBlock(Direction facing, BlockPos pos, BlockPos posOffset)
    {
        for (int i = 0; i < 7; i++)
        {
            if(Objects.equals(waypoints[i], bszero))
            {
                setChanged();
                waypoints[i] = DecodeBlockPosWithOffset(facing, pos, posOffset);
                System.out.println("set waypoint " + i);
                return true;
            }
        }
        return false;
    }

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



    @Override
    public void tick()
    {


    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);

        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(waypoints[i], bszero))
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

        for (int i = 0; i < 7; i++)
        {
            if (tag.contains("waypoint" + i))
            {
                waypoints[i] = new BlockPos(tag.getIntArray("waypoint" + i)[0], tag.getIntArray("waypoint" + i)[1], tag.getIntArray("waypoint" + i)[2]);
            }
            else
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
