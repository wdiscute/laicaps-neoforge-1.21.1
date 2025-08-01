package com.wdiscute.laicaps;

import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.RedtaleEntity;
import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.entity.boat.ModChestBoatEntity;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthEntity;
import com.wdiscute.laicaps.entity.gecko.GeckoEntity;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffEntity;
import com.wdiscute.laicaps.entity.moonray.MoonrayEntity;
import com.wdiscute.laicaps.entity.nimble.NimbleEntity;
import com.wdiscute.laicaps.entity.rocket.RocketEntity;
import com.wdiscute.laicaps.entity.snuffler.SnufflerEntity;
import com.wdiscute.laicaps.entity.swibble.SwibbleEntity;
import com.wdiscute.laicaps.fishing.FishingBobEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

    public static final Supplier<EntityType<BubblemouthEntity>> BUBBLEMOUTH =
            ENTITY_TYPES.register("bubblemouth", () -> EntityType.Builder.of(BubblemouthEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.55f).build("bubblemouth"));

    public static final Supplier<EntityType<MoonrayEntity>> MOONRAY =
            ENTITY_TYPES.register("moonray", () -> EntityType.Builder.of(MoonrayEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.55f).build("moonray"));

    public static final Supplier<EntityType<GlimpuffEntity>> GLIMPUFF =
            ENTITY_TYPES.register("glimpuff", () -> EntityType.Builder.of(GlimpuffEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.55f).build("glimpuff"));

    public static final Supplier<EntityType<SwibbleEntity>> SWIBBLE =
            ENTITY_TYPES.register("swibble", () -> EntityType.Builder.of(SwibbleEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.9f, 1.5f).build("swibble"));

    public static final Supplier<EntityType<NimbleEntity>> NIMBLE =
            ENTITY_TYPES.register("nimble", () -> EntityType.Builder.of(NimbleEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.35f).build("nimble"));

    public static final Supplier<EntityType<SnufflerEntity>> SNUFFLER =
            ENTITY_TYPES.register("snuffler", () -> EntityType.Builder.of(SnufflerEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 0.9f).build("nimble"));


    public static final Supplier<EntityType<RocketEntity>> ROCKET =
            ENTITY_TYPES.register("rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC)
                    .sized(1f, 1f).build("rocket"));

    public static final Supplier<EntityType<FishingBobEntity>> FISHING_BOB =
            ENTITY_TYPES.register("fishing_bob", () -> EntityType.Builder.<FishingBobEntity>of(FishingBobEntity::new, MobCategory.MISC)
                    .sized(1f, 1f).build("fishing_bob"));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

    static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, UnaryOperator<EntityType.Builder<T>> provider) {
        return ENTITY_TYPES.register(name, () -> provider.apply(EntityType.Builder.of(factory, category)).build(name));
    }

}
