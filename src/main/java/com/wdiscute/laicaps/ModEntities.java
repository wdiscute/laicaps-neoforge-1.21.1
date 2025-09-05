package com.wdiscute.laicaps;

import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.bluetale.RedtaleEntity;
import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.entity.boat.ModChestBoatEntity;
import com.wdiscute.laicaps.entity.bubblemouth.BubblemouthEntity;
import com.wdiscute.laicaps.entity.glimpuff.GlimpuffEntity;
import com.wdiscute.laicaps.entity.magmaboss.magma.MagmaEntity;
import com.wdiscute.laicaps.entity.magmaboss.rock.RockEntity;
import com.wdiscute.laicaps.entity.magmaboss.shield.ShieldEntity;
import com.wdiscute.laicaps.entity.moonray.MoonrayEntity;
import com.wdiscute.laicaps.entity.nimble.NimbleEntity;
import com.wdiscute.laicaps.entity.rocket.RE;
import com.wdiscute.laicaps.entity.snuffler.SnufflerEntity;
import com.wdiscute.laicaps.entity.swibble.SwibbleEntity;
import com.wdiscute.laicaps.entity.fishing.FishingBobEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
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


    public static final Supplier<EntityType<RE>> ROCKET =
            ENTITY_TYPES.register("rocket", () -> EntityType.Builder.of(RE::new, MobCategory.MISC)
                    .sized(1f, 1f).build("rocket"));

    public static final Supplier<EntityType<FishingBobEntity>> FISHING_BOB =
            registerKapiten("fishing_bob", FishingBobEntity::new, MobCategory.MISC,
                    b -> b.noSummon().noSave().sized(0.3f, 0.3f));

    public static final Supplier<EntityType<MagmaEntity>> MAGMA =
            ENTITY_TYPES.register("magma", () -> EntityType.Builder.of(MagmaEntity::new, MobCategory.MONSTER)
                    .sized(3f, 5f).build("magma"));

    public static final Supplier<EntityType<ShieldEntity>> SHIELD =
            registerKapiten("shield", ShieldEntity::new, MobCategory.MISC,
                    b -> b.noSummon().noSave().sized(1f, 1));

    public static final Supplier<EntityType<RockEntity>> ROCK =
            registerKapiten("rock", RockEntity::new, MobCategory.MISC,
                    b -> b.noSave().canSpawnFarFromPlayer().sized(1.5f, 0.5f));


    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

    static <T extends Entity> Supplier<EntityType<T>> registerKapiten(String name, EntityType.EntityFactory<T> factory, MobCategory category, UnaryOperator<EntityType.Builder<T>> provider) {
        return ENTITY_TYPES.register(name, () -> provider.apply(EntityType.Builder.of(factory, category)).build(name));
    }

}
