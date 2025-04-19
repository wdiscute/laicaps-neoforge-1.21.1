package com.wdiscute.laicaps.particle;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WaterFlowerParticles extends TextureSheetParticle
{
    protected WaterFlowerParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet)
    {
        super(level, x, y, z);

        Random r = new Random();
        this.xd = 0f;
        this.yd = 0f;
        this.zd = 0f;

        this.quadSize = 0.1f + r.nextFloat(0.05f);

        this.lifetime = 40;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) this.remove();

        if (this.yd < 0.03)
        {
            this.yd += 0.001;
        }

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
            return new WaterFlowerParticles(clientLevel, x, y, z, this.spriteSet);
        }
    }

}
