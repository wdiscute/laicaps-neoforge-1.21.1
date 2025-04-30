package com.wdiscute.laicaps.entity.rocket;

import com.wdiscute.laicaps.entity.ModEntities;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RocketEntity extends LivingEntity
{
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    public final AnimationState shakeAnimationState = new AnimationState();

    public RocketEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots()
    {
        return this.armorItems;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot)
    {
        switch (slot.getType()) {
            case HAND:
                return this.handItems.get(slot.getIndex());
            case HUMANOID_ARMOR:
                return this.armorItems.get(slot.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack)
    {

    }

    @Override
    public void tick() {
        super.tick();

        System.out.println("rocket ticked");
    }

    @Override
    public HumanoidArm getMainArm()
    {
        return HumanoidArm.RIGHT;
    }
}
