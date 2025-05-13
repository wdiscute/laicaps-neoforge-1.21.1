package com.wdiscute.laicaps.block.single;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SimpleTeleporterBlock extends Block
{

    public SimpleTeleporterBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        //player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0, true, false));

        ServerLevel dimensionLevel = ((ServerLevel) level).getServer().getLevel(getDimensionKey());
        player.teleportTo(dimensionLevel, 0, 200, 0, null, 0,0 );

        return ItemInteractionResult.SUCCESS;
    }

    public ResourceKey<Level> getDimensionKey()
    {
        return null;
    }



}
