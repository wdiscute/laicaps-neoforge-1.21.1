package com.wdiscute.laicaps.block.telescope;

import com.mojang.serialization.MapCodec;
import com.wdiscute.laicaps.*;
import com.wdiscute.laicaps.network.Payloads;
import com.wdiscute.laicaps.util.AdvHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class TelescopeBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public TelescopeBlock(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty ADVANCED = BooleanProperty.create("advanced");


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {

        if (stack.is(ModItems.TELESCOPE_UPGRADE_KIT) && state.is(ModBlocks.TELESCOPE) && level.getBlockEntity(pos) instanceof TelescopeBlockEntity tbe)
        {
            if (!state.getValue(ADVANCED))
            {
                level.setBlockAndUpdate(
                        pos, state
                                .setValue(ADVANCED, true)
                                .setValue(FACING, level.getBlockState(pos).getValue(FACING)));

                if (!level.isClientSide && player instanceof ServerPlayer sp)
                {

                    ((ServerLevel) level).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 20, 0.5f, 0.5f, 0.5f, 0f);

                    tbe.drops();

                    AdvHelper.awardAdvancement(sp, "telescope_advanced");

                }
            }

            return ItemInteractionResult.SUCCESS;
        }


        if (hand == InteractionHand.MAIN_HAND && !level.isClientSide && level.getBlockEntity(pos) instanceof TelescopeBlockEntity tbe)
        {
            int numberOfDays = (int) (level.getDayTime() / 24000f);

            if (level.getDayTime() - (numberOfDays * 24000L) > 14000 && level.getDayTime() - (numberOfDays * 24000L) < 23000)
            {
                player.openMenu(new SimpleMenuProvider(tbe, Component.literal("Telescope")), pos);

                //unlock "building a spaceship" entry
                if (player instanceof ServerPlayer sp && !AdvHelper.hasAdvancementCriteria(sp, "menu_entries", "entry3"))
                {
                    AdvHelper.awardAdvancementCriteria(sp, "menu_entries", "entry3");
                    PacketDistributor.sendToPlayer(sp, new Payloads.ToastPayload("menu_entries", "entry3"));
                }

            }
            else
            {
                player.displayClientMessage(Component.translatable("block.laicaps.telescope.daytime"), true);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston)
    {
        if (!level.getBlockState(pos.below()).is(ModBlocks.TELESCOPE_STAND)) level.destroyBlock(pos, true, null);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING);
        pBuilder.add(ADVANCED);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState bellow = context.getLevel().getBlockState(context.getClickedPos().below());
        if (bellow.is(ModBlocks.TELESCOPE_STAND))
        {
            return defaultBlockState().setValue(ADVANCED, false).setValue(FACING, bellow.getValue(FACING));
        }
        context.getPlayer().displayClientMessage(Component.translatable("block.laicaps.telescope.place"), true);
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return ModBlockEntity.TELESCOPE.get().create(pPos, pState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        if (state.getBlock() != newState.getBlock())
        {
            if (level.getBlockEntity(pos) instanceof TelescopeBlockEntity tbe)
            {
                tbe.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}




