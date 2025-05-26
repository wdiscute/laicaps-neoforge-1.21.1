package com.wdiscute.laicaps.mixin;

import com.wdiscute.laicaps.Laicaps;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingHook.class)
public abstract class FishingMixin extends Projectile{

    protected FishingMixin(EntityType<? extends Projectile> entityType, Level level) {super(entityType, level);}

    @ModifyArg(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/ReloadableServerRegistries$Holder;getLootTable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/storage/loot/LootTable;"),
            method = "retrieve")
    private ResourceKey<LootTable> resourceKeyFix(ResourceKey<LootTable> lootTableKey)
    {
        final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("asha"));
        final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("lunamar"));
        final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
        final ResourceKey<LootTable> lootTableAsha = ResourceKey.create(Registries.LOOT_TABLE, Laicaps.rl("fishing/asha_fish"));
        final ResourceKey<LootTable> lootTableLunamar = ResourceKey.create(Registries.LOOT_TABLE, Laicaps.rl("fishing/lunamar_fish"));
        final ResourceKey<LootTable> lootTableEmber = ResourceKey.create(Registries.LOOT_TABLE, Laicaps.rl("fishing/Ember_fish"));

        if(this.level().dimension() == ASHA_KEY) return lootTableAsha;
        if(this.level().dimension() == LUNAMAR_KEY) return lootTableLunamar;
        if(this.level().dimension() == EMBER_KEY) return lootTableEmber;
        return lootTableKey;
    }

    @Shadow
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}
}
