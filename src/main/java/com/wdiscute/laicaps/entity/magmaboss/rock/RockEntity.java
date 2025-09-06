package com.wdiscute.laicaps.entity.magmaboss.rock;

import com.wdiscute.laicaps.ModEntities;
import com.wdiscute.laicaps.ModParticles;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthModel;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.List;

public class RockEntity extends ThrowableProjectile
{
    float xRot = 0;
    float yRot = 0;
    float zRot = 0;

    public final AnimationState spawnAnimationState = new AnimationState();

    public RockEntity(EntityType<? extends RockEntity> entityType, Level level)
    {
        super(ModEntities.ROCK.get(), level);
        spawnAnimationState.start(0);
    }

    public RockEntity(Level level, Vec3 pos)
    {
        super(ModEntities.ROCK.get(), level);
        spawnAnimationState.start(0);
        moveTo(pos);
    }



    @Override
    protected void onHitEntity(EntityHitResult result)
    {
        if (result.getEntity() instanceof Player player)
        {
            player.hurt(this.damageSources().thrown(this, this.getOwner()), 7);
        }
    }

    @Override
    public void tick()
    {
        if (level().isClientSide)
        {
            level().addParticle(
                    ModParticles.ROCK_FALLING.get(), true,
                    position().x, position().y, position().z, 0, 0, 0);

            level().addParticle(
                    ModParticles.ROCK_FALLING.get(), true,
                    position().x, position().y, position().z, 0, 0, 0);
        }

        super.tick();
    }

    @Override
    public boolean isCurrentlyGlowing()
    {
        return true;
    }

    @Override
    public int getTeamColor()
    {
        return 16740912;
    }

    @Override
    protected void onHit(HitResult result)
    {
        super.onHit(result);
        if (level() instanceof ServerLevel sl)
        {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();

            List<Entity> entities = sl.getEntities(null, new AABB(-2.5F, -2, -2.5F, 2.5F, 2, 2.5F).move(position()));

            for (Entity e : entities)
            {
                if (e instanceof LivingEntity) e.hurt(this.damageSources().thrown(this, this.getOwner()), 7);
            }


            sl.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MAGMA_BLOCK.defaultBlockState()),
                    position().x, position().y, position().z, 250, 1.3F, 0.3F, 1.3F, 0.15F);


            sl.sendParticles(
                    ModParticles.ROCK_EXPLOSION.get(), position().x, position().y, position().z,
                    60, 0d, 0d, 0d, 0);


            sl.playSound(
                    null, position().x, position().y, position().z,
                    SoundEvents.STONE_BREAK, SoundSource.BLOCKS);

            sl.playSound(
                    null, position().x, position().y, position().z,
                    SoundEvents.DEEPSLATE_BREAK, SoundSource.BLOCKS);

            sl.playSound(
                    null, position().x, position().y, position().z,
                    SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);

            sl.playSound(
                    null, position().x, position().y, position().z,
                    SoundEvents.LAVA_POP, SoundSource.BLOCKS);

            sl.playSound(
                    null, position().x, position().y, position().z,
                    SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS);

        }
    }


    @Override
    protected double getDefaultGravity()
    {
        return 0.015;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
    }

}