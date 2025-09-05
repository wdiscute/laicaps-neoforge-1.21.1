package com.wdiscute.laicaps.particle;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RockParticles extends TextureSheetParticle
{
    protected RockParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet)
    {
        super(level, x, y, z);

        Random r = new Random();

        this.quadSize *= 2;

        this.xd = r.nextFloat(0.2f) - 0.1f;
        this.yd = 0.5f;
        this.zd = r.nextFloat(0.2f) - 0.1f;

        //offset position by 0.9 blocks
        move(r.nextFloat(1.8f) - 0.9f, 0, r.nextFloat(1.8f) - 0.9f);

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
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        }

        if(random.nextFloat() > 0.6)
        {
            this.level.addParticle(ParticleTypes.WHITE_ASH, this.x + random.nextFloat() * 2 - 1, this.y, this.z  + random.nextFloat() * 2 - 1, this.xd, this.yd, this.zd);
            this.level.addParticle(ParticleTypes.WHITE_SMOKE, this.x + random.nextFloat() * 2 - 1, this.y, this.z  + random.nextFloat() * 2 - 1, this.xd, this.yd, this.zd);
        }

        this.yd *= 0.9;
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
            return new RockParticles(clientLevel, x, y, z, this.spriteSet);
        }
    }

}
