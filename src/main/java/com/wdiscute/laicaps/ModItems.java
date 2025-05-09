package com.wdiscute.laicaps;


import com.wdiscute.laicaps.entity.ModEntities;
import com.wdiscute.laicaps.entity.bluetale.BluetaleEntity;
import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.item.*;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);


    //
    //,--.   ,--. ,--.  ,---.    ,-----.
    //|   `.'   | |  | '   .-'  '  .--./
    //|  |'.'|  | |  | `.  `-.  |  |
    //|  |   |  | |  | .-'    | '  '--'\
    //`--'   `--' `--' `-----'   `-----'
    //

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> ASTRONOMY_NOTEBOOK = ITEMS.register("astronomy_notebook", () -> new AstronomyNotebookItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));


    //
    // ,-----. ,--.  ,--. ,--. ,------.   ,---.
    //'  .--./ |  '--'  | |  | |  .--. ' '   .-'
    //|  |     |  .--.  | |  | |  '--' | `.  `-.
    //'  '--'\ |  |  |  | |  | |  | --'  .-'    |
    // `-----' `--'  `--' `--' `--'      `-----'
    //

    public static final DeferredItem<Item> BASIC_MICROCHIP = ITEMS.register("basic_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> REFINED_MICROCHIP = ITEMS.register("refined_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ADVANCED_MICROCHIP = ITEMS.register("advanced_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ELITE_MICROCHIP = ITEMS.register("elite_microchip", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    //
    //,--------. ,------. ,--.    ,------.  ,---.    ,-----.  ,-----.  ,------.  ,------.
    //'--.  .--' |  .---' |  |    |  .---' '   .-'  '  .--./ '  .-.  ' |  .--. ' |  .---'
    //   |  |    |  `--,  |  |    |  `--,  `.  `-.  |  |     |  | |  | |  '--' | |  `--,
    //   |  |    |  `---. |  '--. |  `---. .-'    | '  '--'\ '  '-'  ' |  | --'  |  `---.
    //   `--'    `------' `-----' `------' `-----'   `-----'  `-----'  `--'      `------'
    //


    public static final DeferredItem<Item> TELESCOPE_UPGRADE_KIT = ITEMS.register("telescope_upgrade_kit", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));


    //
    //,------.   ,-----.   ,-----. ,--. ,--. ,------. ,--------.
    //|  .--. ' '  .-.  ' '  .--./ |  .'   / |  .---' '--.  .--'
    //|  '--'.' |  | |  | |  |     |  .   '  |  `--,     |  |
    //|  |\  \  '  '-'  ' '  '--'\ |  |\   \ |  `---.    |  |
    //`--' '--'  `-----'   `-----' `--' '--' `------'    `--'
    //


    public static final DeferredItem<Item> ROCKET = ITEMS.register("rocket", () -> new RocketItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final DeferredItem<Item> TANK = ITEMS.register(
            "tank", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .component(ModDataComponentTypes.FUEL, 0).durability(400)
            )
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {
                    setHoverTextForTanks(stack, tooltipComponents, "small");
                }

                @Override
                public int getDamage(ItemStack stack)
                {
                    return (400 - stack.get(ModDataComponentTypes.FUEL) == 0) ? 1 : 400 - stack.get(ModDataComponentTypes.FUEL);
                }
            });

    public static final DeferredItem<Item> MEDIUM_TANK = ITEMS.register(
            "medium_tank", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).component(ModDataComponentTypes.FUEL, 0).durability(800))
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {
                    setHoverTextForTanks(stack, tooltipComponents, "medium");
                }

                @Override
                public int getDamage(ItemStack stack)
                {
                    return (800 - stack.get(ModDataComponentTypes.FUEL) == 0) ? 1 : 800 - stack.get(ModDataComponentTypes.FUEL);
                }
            });

    public static final DeferredItem<Item> LARGE_TANK = ITEMS.register(
            "large_tank", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(1500).component(ModDataComponentTypes.FUEL, 0))
            {

                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {

                    setHoverTextForTanks(stack, tooltipComponents, "large");
                }

                @Override
                public int getDamage(ItemStack stack)
                {
                    return (1500 - stack.get(ModDataComponentTypes.FUEL) == 0) ? 1 : 1500 - stack.get(ModDataComponentTypes.FUEL);
                }
            });

    public static final DeferredItem<Item> CANISTER = ITEMS.register("canister", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));


    public static final DeferredItem<Item> ENDERBLAZE_FUEL = ITEMS.register("enderblaze_fuel", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> PRISTINE_ENDERPEARL_DUST = ITEMS.register("pristine_enderpearl_dust", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));


    public static final DeferredItem<Item> EMBER = ITEMS.register("ember", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> ASHA = ITEMS.register("asha", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> OVERWORLD = ITEMS.register("overworld", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> LUNAMAR = ITEMS.register("lunamar", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> UNKNOWN = ITEMS.register("unknown", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));








    //
    //  ,---.    ,---.   ,--.  ,--.   ,---.
    // /  O  \  '   .-'  |  '--'  |  /  O  \
    //|  .-.  | `.  `-.  |  .--.  | |  .-.  |
    //|  | |  | .-'    | |  |  |  | |  | |  |
    //`--' `--' `-----'  `--'  `--' `--' `--'
    //

    public static final DeferredItem<Item> BLUETALE_BUCKET = ITEMS.register("bluetale_bucket",
            () -> new MobBucketItem(
                    ModEntities.BLUETALE.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
            )

    );

    public static final DeferredItem<Item> REDTALE_BUCKET = ITEMS.register("redtale_bucket",
            () -> new MobBucketItem(
                    ModEntities.REDTALE.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
            )

    );


    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register(
            "oakheart_berries", () -> new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES))
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {
                    Laicaps.appendHoverText(stack, tooltipComponents);
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> OAKHEART_DOOR = ITEMS.register("oakheart_door", () -> new DoubleHighBlockItem(ModBlocks.OAKHEART_DOOR.get(), new Item.Properties()));

    public static final DeferredItem<Item> OAKHEART_SIGN = ITEMS.register("oakheart_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKHEART_SIGN.get(), ModBlocks.OAKHEART_WALL_SIGN.get()));

    public static final DeferredItem<Item> OAKHEART_HANGING_SIGN = ITEMS.register("oakheart_hanging_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKHEART_HANGING_SIGN.get(), ModBlocks.OAKHEART_WALL_HANGING_SIGN.get()));

    public static final DeferredItem<Item> OAKHEART_BOAT = ITEMS.register("oakheart_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.OAKHEART, new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> OAKHEART_CHEST_BOAT = ITEMS.register("oakheart_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.OAKHEART, new Item.Properties().stacksTo(16)));


    public static final DeferredItem<Item> OAKROOT_DOOR = ITEMS.register("oakroot_door", () -> new DoubleHighBlockItem(ModBlocks.OAKROOT_DOOR.get(), new Item.Properties()));

    public static final DeferredItem<Item> OAKROOT_HANGING_SIGN = ITEMS.register("oakroot_hanging_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKROOT_HANGING_SIGN.get(), ModBlocks.OAKROOT_WALL_HANGING_SIGN.get()));

    public static final DeferredItem<Item> OAKROOT_SIGN = ITEMS.register("oakroot_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.OAKROOT_SIGN.get(), ModBlocks.OAKROOT_WALL_SIGN.get()));

    public static final DeferredItem<Item> OAKROOT_CHEST_BOAT = ITEMS.register("oakroot_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.OAKROOT, new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> OAKROOT_BOAT = ITEMS.register("oakroot_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.OAKROOT, new Item.Properties().stacksTo(16)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    private static void setHoverTextForTanks(ItemStack stack, List<Component> tooltipComponents, String size)
    {
        int maxFuel = stack.getMaxDamage();
        if (Screen.hasShiftDown())
        {
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_down"));
            tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.empty"));
            for (int i = 0; i < 100; i++)
            {
                if (!I18n.exists("tooltip.laicaps.tank." + size + "." + i)) break;
                tooltipComponents.add(Component.translatable("tooltip.laicaps.tank." + size + "." + i));
            }


            String color = "§4";

            if (stack.get(ModDataComponentTypes.FUEL) > maxFuel / 2) color = "§6";
            if (stack.get(ModDataComponentTypes.FUEL) > maxFuel / 1.5) color = "§a";

            tooltipComponents.add(Component.literal(color + I18n.get("tooltip.laicaps.tank.fuel") + ": §l[" + stack.get(ModDataComponentTypes.FUEL) + " / " + maxFuel + "]"));
        } else tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
    }


}
