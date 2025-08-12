package com.wdiscute.laicaps.block.rotating;

import com.wdiscute.laicaps.ModBlockEntity;
import com.wdiscute.laicaps.block.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Random;

public class RotatingPuzzleBlockentity extends BlockEntity implements TickableBlockEntity
{
    private final Random r = new Random();

    BlockPos bsZero = new BlockPos(0, 0, 0);
    private BlockPos[] blocksLinked = {bsZero, bsZero, bsZero, bsZero, bsZero, bsZero, bsZero};

    public void rotateBlocks()
    {
        rotateSelf();

        level.playSound(null, getBlockPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1f, r.nextFloat(0.1f) + 0.95f);
        level.playSound(null, getBlockPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, r.nextFloat(0.1f) + 0.95f);
        level.playSound(null, getBlockPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, r.nextFloat(0.1f) + 0.95f);

        for (int i = 0; i < getTotalWaypoints(); i++)
        {
            Direction dir = level.getBlockState(getBlockPos()).getValue(RotatingPuzzleBlock.ORIENTATION);

            if (level.getBlockEntity(decodeBlockPosWithOffset(dir, blocksLinked[i])) instanceof RotatingPuzzleBlockentity rpbe)
            {
                rpbe.rotateSelf();
            }

        }
    }


    public void rotateSelf()
    {

        BlockState state = level.getBlockState(getBlockPos());

        level.setBlockAndUpdate(getBlockPos(),
                state.setValue(HorizontalDirectionalBlock.FACING,
                        rotateDirection(state.getValue(HorizontalDirectionalBlock.FACING))
                ));
    }

    private static Direction rotateDirection(Direction dir)
    {
        return switch (dir)
        {
            case Direction.WEST -> Direction.SOUTH;
            case Direction.SOUTH -> Direction.EAST;
            case Direction.EAST -> Direction.NORTH;
            default -> Direction.WEST;
        };
    }


    private int getTotalWaypoints()
    {
        if (Objects.equals(blocksLinked[0], bsZero)) return 0;
        if (Objects.equals(blocksLinked[1], bsZero)) return 1;
        if (Objects.equals(blocksLinked[2], bsZero)) return 2;
        if (Objects.equals(blocksLinked[3], bsZero)) return 3;
        if (Objects.equals(blocksLinked[4], bsZero)) return 4;
        if (Objects.equals(blocksLinked[5], bsZero)) return 5;
        if (Objects.equals(blocksLinked[6], bsZero)) return 6;
        return 7;
    }

    public Boolean SetNextLinkedBlock(BlockPos posOffset, Player player)
    {
        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(blocksLinked[i], bsZero))
            {
                setChanged();
                blocksLinked[i] = posOffset;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);

        for (int i = 0; i < 7; i++)
        {
            if (Objects.equals(blocksLinked[i], bsZero))
            {
                break;
            }
            tag.putIntArray("blocks" + i, new int[]{blocksLinked[i].getX(), blocksLinked[i].getY(), blocksLinked[i].getZ()});
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);

        for (int i = 0; i < 7; i++)
        {
            if (tag.contains("blocks" + i))
            {
                blocksLinked[i] = new BlockPos(tag.getIntArray("blocks" + i)[0], tag.getIntArray("blocks" + i)[1], tag.getIntArray("blocks" + i)[2]);
            }
            else
            {
                break;
            }
        }
    }

    public RotatingPuzzleBlockentity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntity.ROTATING_PUZZLE.get(), pPos, pBlockState);
    }

    @Override
    public void tick()
    {

    }
}
