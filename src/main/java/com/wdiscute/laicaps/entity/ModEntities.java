package com.wdiscute.laicaps.entity;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.RedtaleEntity;
import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.entity.boat.ModChestBoatEntity;
import com.wdiscute.laicaps.entity.gecko.GeckoEntity;
import com.wdiscute.laicaps.entity.nimble.NimbleEntity;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import com.wdiscute.laicaps.entity.snuffler.SnufflerEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Laicaps.MOD_ID);


    public static final Supplier<EntityType<ModBoatEntity>> MOD_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_boat"));

    public static final Supplier<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_chest_boat"));


    public static final Supplier<EntityType<GeckoEntity>> GECKO =
            ENTITY_TYPES.register("gecko", () -> EntityType.Builder.of(GeckoEntity::new, MobCategory.CREATURE)
                    .sized(0.75f, 0.35f).build("gecko"));



    public static final Supplier<EntityType<BluetaleEntity>> BLUETALE =
            ENTITY_TYPES.register("bluetale", () -> EntityType.Builder.of(BluetaleEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.35f).build("bluetale"));

    public static final Supplier<EntityType<RedtaleEntity>> REDTALE =
            ENTITY_TYPES.register("redtale", () -> EntityType.Builder.of(RedtaleEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.35f).build("redtale"));


    public static final Supplier<EntityType<NimbleEntity>> NIMBLE =
            ENTITY_TYPES.register("nimble", () -> EntityType.Builder.of(NimbleEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.35f).build("nimble"));

    public static final Supplier<EntityType<SnufflerEntity>> SNUFFLER =
            ENTITY_TYPES.register("snuffler", () -> EntityType.Builder.of(SnufflerEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 0.9f).build("nimble"));


    public static final Supplier<EntityType<RocketEntity>> ROCKET =
            ENTITY_TYPES.register("rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC)
                    .sized(2.0f, 2.0f).build("rocket"));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
