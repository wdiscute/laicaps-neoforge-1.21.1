package com.wdiscute.laicaps.entity.magmaboss.magma;

import com.wdiscute.laicaps.ModDataSerializers;
import com.wdiscute.laicaps.entity.magmaboss.MagmaState;
import com.wdiscute.laicaps.entity.magmaboss.rock.RockEntity;
import com.wdiscute.laicaps.entity.magmaboss.shield.ShieldEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.xml.transform.stax.StAXSource;
import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;

public class MagmaEntity extends Monster
{

    private static final EntityDataAccessor<MagmaState> STATE = SynchedEntityData.defineId(
            MagmaEntity.class, ModDataSerializers.MAGMA_STATE.get());


    private final Random r = new Random();
    private final int random = r.nextInt(20);

    private final ShieldEntity[] shields;

    public int counter;
    private final ServerBossEvent daLordeHealth;

    private int shieldRegenCooldown;

    public MagmaEntity(EntityType<? extends Monster> entityType, Level level)
    {
        super(entityType, level);

        this.shields = new ShieldEntity[]{null, null, null, null, null, null, null, null,};

        this.daLordeHealth = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

        this.setHealth(200);
    }

    @Override
    public int getTeamColor()
    {
        return 16740912;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer)
    {
        daLordeHealth.addPlayer(serverPlayer);
        super.startSeenByPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer)
    {
        daLordeHealth.removePlayer(serverPlayer);
        super.stopSeenByPlayer(serverPlayer);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200d);
    }

    @Override
    public void knockback(double strength, double x, double z)
    {

    }


    @Override
    public void tick()
    {
        MagmaState state = entityData.get(STATE);

        daLordeHealth.setProgress(getHealth() / getMaxHealth());

        //idle
        if (state == MagmaState.IDLE && !level().isClientSide)
        {
            if (getHealth() != getMaxHealth())
            {
                this.setHealth(getMaxHealth());
            }

            if (random + tickCount % 20 == random)
            {
                if (level().hasNearbyAlivePlayer(position().x, position().y, position().z, 10))
                {
                    entityData.set(STATE, MagmaState.WAKING);
                }
            }
        }


        //waking up
        if (state == MagmaState.WAKING && !level().isClientSide)
        {
            shieldRegenCooldown--;
            counter++;

            boolean awake = false;

            if (shieldRegenCooldown < 1)
            {
                awake = true;
                for (int i = 0; i < 8; i++)
                {
                    if (shields[i] == null)
                    {
                        ShieldEntity re = new ShieldEntity(level(), this, i);
                        re.moveTo(position());
                        shields[i] = re;
                        level().addFreshEntity(re);
                        shieldRegenCooldown = 20;
                        awake = false;
                        break;
                    }
                }
            }
            if (awake)
            {
                entityData.set(STATE, MagmaState.FIRST_PHASE);
            }
        }


        if (state == MagmaState.FIRST_PHASE  && !level().isClientSide)
        {
            if (getHealth() < getMaxHealth() / 2)
            {
                this.setHealth(getMaxHealth() / 2);
                entityData.set(STATE, MagmaState.INVULNERABLE);
                counter = 0;
            }
        }


        if (state == MagmaState.INVULNERABLE  && !level().isClientSide)
        {
            counter++;

            if (getHealth() != getMaxHealth() / 2)
            {
                this.setHealth(getMaxHealth() / 2);
            }

            if(counter % 8 == 0)
            {
                Vec3 pos = position().add(r.nextFloat(30f) - 15, 15 + r.nextFloat(2f) - 1, r.nextFloat(30f) - 15);
                RockEntity rock = new RockEntity(level(), pos);
                level().addFreshEntity(rock);
            }

            if (counter % 20 == 0)
            {
                List<Entity> entities = level().getEntities(null, new AABB(-20, -10, -20, 20, 10, 20).move(position()));

                for (Entity e : entities)
                {
                    if(e instanceof ServerPlayer sp)
                    {
                        Vec3 pos = sp.position().add(r.nextFloat(4f) - 2, 15 + r.nextFloat(2f) - 1, r.nextFloat(4f) - 2);

                        RockEntity rock = new RockEntity(level(), pos);

                        level().addFreshEntity(rock);
                    }
                }
            }

            if (counter > 300)
            {
                entityData.set(STATE, MagmaState.SECOND_PHASE);
            }


            //blablabla
        }

        super.tick();
    }


    @Override
    public boolean isCurrentlyGlowing()
    {
        return entityData.get(STATE) == MagmaState.INVULNERABLE || entityData.get(STATE) == MagmaState.WAKING;
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (amount > getMaxHealth() * 2)
        {
            this.setRemoved(RemovalReason.KILLED);
        }

        if (entityData.get(STATE) == MagmaState.FIRST_PHASE || entityData.get(STATE) == MagmaState.SECOND_PHASE)
        {
            return super.hurt(source, amount);
        }
        else
        {
            return false;
        }
    }


    public boolean destroyShield(int order)
    {
        if (entityData.get(STATE) != MagmaState.WAKING)
        {
            shields[order] = null;
            return true;
        }
        return false;
    }


    @Override
    public AABB getBoundingBoxForCulling()
    {
        AABB box = new AABB(-10, -10, -10, 10, 10, 10);
        return box.move(position());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(STATE, MagmaState.IDLE);
    }

    @Override
    public boolean canCollideWith(Entity entity)
    {
        return false;
    }

    @Override
    public boolean isPushable()
    {
        return false;
    }

}
