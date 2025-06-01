package com.wdiscute.laicaps;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Laicaps.MOD_ID);






    public static final Supplier<SimpleParticleType> CHASE_PUZZLE_PARTICLES =
            PARTICLE_TYPES.register("chase_puzzle_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> WATER_FLOWER_PARTICLES =
            PARTICLE_TYPES.register("water_flower_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> LUNARVEIL_PARTICLES =
            PARTICLE_TYPES.register("lunarveil_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> ROCKET_FIRE_PARTICLES =
            PARTICLE_TYPES.register("rocket_fire_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> ROCKET_FIRE_SIMPLE_PARTICLES =
            PARTICLE_TYPES.register("rocket_fire_simple_particles", () -> new SimpleParticleType(true));






    public static void register(IEventBus eventBus)
    {
        PARTICLE_TYPES.register(eventBus);
    }

}
