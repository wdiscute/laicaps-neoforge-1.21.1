package com.wdiscute.laicaps.item;

import com.wdiscute.laicaps.entity.ModBoatEntity;
import com.wdiscute.laicaps.entity.ModChestBoatEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE;
    private final ModBoatEntity.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean hasChest, ModBoatEntity.Type type, Item.Properties properties) {
        super(properties);
        this.hasChest = hasChest;
        this.type = type;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
        ItemStack itemstack = player.getItemInHand(pHand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();

                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Boat boat = this.getBoat(level, hitresult, itemstack, player);
                if(boat instanceof ModChestBoatEntity chestBoat) {
                    chestBoat.setVariant(this.type);
                } else if(boat instanceof ModBoatEntity) {
                    ((ModBoatEntity)boat).setVariant(this.type);
                }
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private Boat getBoat(Level level, HitResult hitResult, ItemStack stack, Player player) {
        Vec3 vec3 = hitResult.getLocation();
        Boat boat = (Boat)(this.hasChest ? new ModChestBoatEntity(level, vec3.x, vec3.y, vec3.z) : new ModBoatEntity(level, vec3.x, vec3.y, vec3.z));
        if (level instanceof ServerLevel serverlevel) {
            EntityType.createDefaultStackConfig(serverlevel, stack, player).accept(boat);
        }

        return boat;
    }

    static {
        ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}


