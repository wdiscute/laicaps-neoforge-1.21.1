package com.wdiscute.laicaps.block.custom;

import com.wdiscute.laicaps.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f,1f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {

        if(pEntity instanceof ItemEntity itemEntity)
        {
            if(itemEntity.getItem().getItem() == Items.LAVA_BUCKET)
            {
                itemEntity.setItem(new ItemStack(Items.WATER_BUCKET, itemEntity.getItem().getCount()));
            }

            if(itemEntity.getItem().getItem() == Items.IRON_INGOT)
            {
                itemEntity.setItem(new ItemStack(Items.DIAMOND, itemEntity.getItem().getCount()));
            }

            // .is(tagname)
            // needs to be applied on an itemStack
            if(itemEntity.getItem().is(ModTags.Items.MAGIC_BLOCK_EGGS))
            {
                Chicken chicken = new Chicken(EntityType.CHICKEN, pLevel);
                chicken.moveTo(itemEntity.getX(), pPos.getY() + 1, itemEntity.getZ(), 1, 1);
                pLevel.addFreshEntity(chicken);
                itemEntity.getItem().setCount(itemEntity.getItem().getCount() - 1);
            }

        }

        if(pEntity instanceof LivingEntity)
        {
            ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 7, true, false), pEntity);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {

        pTooltipComponents.addLast(Component.translatable("tooltip.laicaps.magic_block.tooltip"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}




