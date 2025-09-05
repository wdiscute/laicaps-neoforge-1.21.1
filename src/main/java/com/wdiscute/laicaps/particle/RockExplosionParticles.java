package com.wdiscute.laicaps.particle;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RockExplosionParticles extends TextureSheetParticle
{
    protected RockExplosionParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet)
    {
        super(level, x, y, z);

        Random r = new Random();

        float xOffset = r.nextFloat() * 2 -1;
        float zOffset = r.nextFloat() * 2 -1;

        move(xOffset, 0, zOffset);

        this.xd = xOffset / 2;
        this.yd = 0.2f + r.nextFloat() / 2;
        this.zd = zOffset / 2;


        this.lifetime = r.nextInt(10) + 10;

        if(r.nextInt(10) > 7) this.lifetime += r.nextInt(20);

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) this.remove();

        float f = (float)this.age / (float)this.lifetime;
        if (this.random.nextFloat() > f) {
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd/2, this.yd / 2, this.zd/2);
        }

        if(random.nextFloat() > 0.9)
        {
            this.level.addParticle(ParticleTypes.WHITE_ASH, this.x + random.nextFloat() * 2 - 1, this.y, this.z  + random.nextFloat() * 2 - 1, this.xd/2, this.yd /2, this.zd/2);
            this.level.addParticle(ParticleTypes.WHITE_SMOKE, this.x + random.nextFloat() * 2 - 1, this.y, this.z  + random.nextFloat() * 2 - 1, this.xd/2, this.yd /2, this.zd/2);
        }

        this.yd -= 0.1;

        this.xd *= 0.8;
        this.zd *= 0.8;
        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new RockExplosionParticles(clientLevel, x, y, z, this.spriteSet);
        }
    }

}
