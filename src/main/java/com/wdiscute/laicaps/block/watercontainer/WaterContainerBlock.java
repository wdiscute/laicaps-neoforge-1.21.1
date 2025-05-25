package com.wdiscute.laicaps.block.watercontainer;

import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class WaterContainerBlock extends Block
{
    public WaterContainerBlock(Properties properties)
    {
        super(properties);
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(Minecraft.getInstance().player.getMainHandItem().is(ModBlocks.WATER_CONTAINER.asItem()))
        {

            level.addParticle(ParticleTypes.CLOUD,
                    pos.getX() + 0.5f,
                    pos.getY() + 0.5f,
                    pos.getZ() + 0.5f,
                    0,0,0);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        if (context instanceof EntityCollisionContext ecc && ecc.getEntity() instanceof Player player)
        {
            if (context.isHoldingItem(ModBlocks.WATER_CONTAINER.asItem()))
                return Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F);

            if(player.isCreative())
                return Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

            return Block.box(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }

        return Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

}
