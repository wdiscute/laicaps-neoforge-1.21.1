package com.wdiscute.laicaps;


import com.wdiscute.laicaps.entity.boat.ModBoatEntity;
import com.wdiscute.laicaps.item.*;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModItems
{

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Laicaps.MOD_ID);


    public static final DeferredItem<Item> WEAPON_POISON = ITEMS.register(
            "weapon_poison", () -> new Item(new Item.Properties().stacksTo(1)
            )
            {
                @Override
                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected)
                {
                    ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

                    mutable.set(level.holderLookup(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT).get(), 1);

                    stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable().withTooltip(false));
                    stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
                }
            });


    //
    //,--.   ,--. ,--.  ,---.    ,-----.
    //|   `.'   | |  | '   .-'  '  .--./
    //|  |'.'|  | |  | `.  `-.  |  |
    //|  |   |  | |  | .-'    | '  '--'\
    //`--'   `--' `--' `-----'   `-----'
    //

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> ASTRONOMY_NOTEBOOK = ITEMS.register("astronomy_notebook", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final DeferredItem<Item> EMBER_ENTRY = ITEMS.register(
            "ember_entry", () ->
                    new EntryItem(new Item.Properties().rarity(Rarity.RARE)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Ember Entry Page")
                    )
                    {
                        @Override
                        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
                        {

                            if (awardEntry(player, "ember"))
                            {
                                ItemStack is = player.getItemInHand(usedHand);
                                is.shrink(1);
                                return InteractionResultHolder.consume(is);
                            }

                            return super.use(level, player, usedHand);
                        }
                    });

    public static final DeferredItem<Item> ASHA_ENTRY = ITEMS.register(
            "asha_entry", () ->
                    new EntryItem(new Item.Properties().rarity(Rarity.RARE)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Asha Entry Page"))
                    {
                        @Override
                        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
                        {

                            if (awardEntry(player, "asha"))
                            {
                                ItemStack is = player.getItemInHand(usedHand);
                                is.shrink(1);
                                return InteractionResultHolder.consume(is);
                            }

                            return super.use(level, player, usedHand);
                        }
                    });

    public static final DeferredItem<Item> OVERWORLD_ENTRY = ITEMS.register(
            "overworld_entry", () ->
                    new EntryItem(new Item.Properties().rarity(Rarity.RARE)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Overworld Entry Page")
                    )
                    {
                        @Override
                        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
                        {

                            if (awardEntry(player, "overworld"))
                            {
                                ItemStack is = player.getItemInHand(usedHand);
                                is.shrink(1);
                                return InteractionResultHolder.consume(is);
                            }

                            return super.use(level, player, usedHand);
                        }
                    });

    public static final DeferredItem<Item> LUNAMAR_ENTRY = ITEMS.register(
            "lunamar_entry", () ->
                    new EntryItem(new Item.Properties().rarity(Rarity.RARE)
                            .stacksTo(1)
                            .component(ModDataComponents.ENTRY_NAME, "Lunamar Entry Page")
                    )
                    {
                        @Override
                        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
                        {

                            if (awardEntry(player, "lunamar"))
                            {
                                ItemStack is = player.getItemInHand(usedHand);
                                is.shrink(1);
                                return InteractionResultHolder.consume(is);
                            }

                            return super.use(level, player, usedHand);
                        }
                    });

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


    public static final DeferredItem<Item> TELESCOPE_UPGRADE_KIT = ITEMS.register("telescope_upgrade_kit", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final DeferredItem<Item> TELESCOPE_LENSES = ITEMS.register("telescope_lenses", () -> new Item(new Item.Properties().stacksTo(16)));


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
                    .component(ModDataComponents.FUEL, 0)
                    .durability(400)
                    .setNoRepair()
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
                    return (400 - stack.get(ModDataComponents.FUEL) == 0) ? 1 : 400 - stack.get(ModDataComponents.FUEL);
                }
            });

    public static final DeferredItem<Item> MEDIUM_TANK = ITEMS.register(
            "medium_tank", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .component(ModDataComponents.FUEL, 0)
                    .durability(800)
                    .setNoRepair())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {
                    setHoverTextForTanks(stack, tooltipComponents, "medium");
                }

                @Override
                public int getDamage(ItemStack stack)
                {
                    return (800 - stack.get(ModDataComponents.FUEL) == 0) ? 1 : 800 - stack.get(ModDataComponents.FUEL);
                }
            });


    public static final DeferredItem<Item> LARGE_TANK = ITEMS.register(
            "large_tank", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .durability(1500)
                    .component(ModDataComponents.FUEL, 0)
                    .setNoRepair())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
                {

                    setHoverTextForTanks(stack, tooltipComponents, "large");
                }

                @Override
                public int getDamage(ItemStack stack)
                {
                    return (1500 - stack.get(ModDataComponents.FUEL) == 0) ? 1 : 1500 - stack.get(ModDataComponents.FUEL);
                }
            });

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


    public static final DeferredItem<Item> STARFLIES_JAR = ITEMS.register(
            "starflies_jar",
            () -> new Item(new Item.Properties().stacksTo(1))
            {
                @Override
                public InteractionResult useOn(UseOnContext context)
                {
                    if (context.getLevel().getBlockState(context.getClickedPos().above()).isAir())
                    {
                        context.getLevel().setBlockAndUpdate(context.getClickedPos().above(), ModBlocks.STARFLIES_BLOCK.get().defaultBlockState());
                        context.getItemInHand().shrink(1);
                        context.getPlayer().addItem(new ItemStack(ModItems.JAR.get()));
                        return InteractionResult.SUCCESS;
                    }

                    return super.useOn(context);
                }
            });

    public static final DeferredItem<Item> JAR = ITEMS.register("jar", () -> new Item(new Item.Properties().stacksTo(16)));


    public static final DeferredItem<Item> SWEETLILY_SUGAR = ITEMS.register("sweetlily_sugar", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SNUFFLER_CHOP = ITEMS.register("snuffler_chop", () -> new Item(new Item.Properties().food(ModFoodProperties.SNUFFLER_CHOP)));
    public static final DeferredItem<Item> COOKED_SNUFFLER_CHOP = ITEMS.register("cooked_snuffler_chop", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_SNUFFLER_CHOP)));
    public static final DeferredItem<Item> SNUFFLER_SPAWN_EGG = ITEMS.register("snuffler_spawn_egg", () -> new SpawnEggItem(ModEntities.SNUFFLER.get(), 15971928, 13534776, new Item.Properties()));

    public static final DeferredItem<Item> SWIBBLE_SPAWN_EGG = ITEMS.register("swibble_spawn_egg", () -> new SpawnEggItem(ModEntities.SNUFFLER.get(), 4892577, 13534776, new Item.Properties()));


    public static final DeferredItem<Item> BLUETALE = ITEMS.register("bluetale", () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_BLUETALE)));
    public static final DeferredItem<Item> COOKED_BLUETALE = ITEMS.register("cooked_bluetale", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BLUETALE)));
    public static final DeferredItem<Item> BLUETALE_BUCKET = ITEMS.register("bluetale_bucket", () -> new MobBucketItem(ModEntities.BLUETALE.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> BLUETALE_SPAWN_EGG = ITEMS.register("bluetale_spawn_egg", () -> new SpawnEggItem(ModEntities.BLUETALE.get(), 9429956, 10858979, new Item.Properties()));


    public static final DeferredItem<Item> REDTALE = ITEMS.register("redtale", () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_BLUETALE)));
    public static final DeferredItem<Item> COOKED_REDTALE = ITEMS.register("cooked_redtale", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BLUETALE)));
    public static final DeferredItem<Item> REDTALE_BUCKET = ITEMS.register("redtale_bucket", () -> new MobBucketItem(ModEntities.BUBBLEMOUTH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> REDTALE_SPAWN_EGG = ITEMS.register("redtale_spawn_egg", () -> new SpawnEggItem(ModEntities.BUBBLEMOUTH.get(), 9429956, 14919099, new Item.Properties()));

    public static final DeferredItem<Item> BUBBLEMOUTH = ITEMS.register("bubblemouth", () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_BLUETALE)));
    public static final DeferredItem<Item> COOKED_BUBBLEMOUTH = ITEMS.register("cooked_bubblemouth", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BLUETALE)));
    public static final DeferredItem<Item> BUBBLEMOUTH_BUCKET = ITEMS.register("bubblemouth_bucket", () -> new MobBucketItem(ModEntities.BUBBLEMOUTH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> BUBBLEMOUTH_SPAWN_EGG = ITEMS.register("bubblemouth_spawn_egg", () -> new SpawnEggItem(ModEntities.BUBBLEMOUTH.get(), 9429956, 14919099, new Item.Properties()));

    public static final DeferredItem<Item> GLIMPUFF = ITEMS.register("glimpuff", () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_BLUETALE)));
    public static final DeferredItem<Item> COOKED_GLIMPUFF = ITEMS.register("cooked_glimpuff", () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_BLUETALE)));
    public static final DeferredItem<Item> GLIMPUFF_BUCKET = ITEMS.register("glimpuff_bucket", () -> new MobBucketItem(ModEntities.GLIMPUFF.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> GLIMPUFF_SPAWN_EGG = ITEMS.register("glimpuff_spawn_egg", () -> new SpawnEggItem(ModEntities.GLIMPUFF.get(), 9429956, 14919099, new Item.Properties()));


    public static final DeferredItem<Item> NIMBLE_SPAWN_EGG = ITEMS.register("nimble_spawn_egg", () -> new SpawnEggItem(ModEntities.NIMBLE.get(), 14531970, 14714721, new Item.Properties()));
    public static final DeferredItem<Item> NIMBLE_SWEET_TREAT = ITEMS.register("nimble_sweet_treat", () -> new Item(new Item.Properties()));


    public static final DeferredItem<Item> OAKHEART_BERRIES = ITEMS.register(
            "oakheart_berries", () ->
                    new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES)));

    public static final DeferredItem<Item> OAKHEART_BERRIES_JAM = ITEMS.register(
            "oakheart_berries_jam", () ->
                    new Item(new Item.Properties().food(ModFoodProperties.OAKHEART_BERRIES_JAM).stacksTo(16)));


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


    //
    //,--.    ,--. ,--. ,--.  ,--.   ,---.   ,--.   ,--.   ,---.   ,------.
    //|  |    |  | |  | |  ,'.|  |  /  O  \  |   `.'   |  /  O  \  |  .--. '
    //|  |    |  | |  | |  |' '  | |  .-.  | |  |'.'|  | |  .-.  | |  '--'.'
    //|  '--. '  '-'  ' |  | `   | |  | |  | |  |   |  | |  | |  | |  |\  \
    //`-----'  `-----'  `--'  `--' `--' `--' `--'   `--' `--' `--' `--' '--'
    //


    public static final DeferredItem<Item> MOONSHADE_FRUIT = ITEMS.register("moonshade_fruit", () -> new Item(new Item.Properties().food(ModFoodProperties.MOONSHADE_FRUIT)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


    private static boolean awardEntry(Player player, String planet)
    {
        if (player instanceof ServerPlayer sp)
        {
            Random r = new Random();

            List<String> result = new ArrayList<>();
            AdvHelper.getEntriesRemainingAsIterable(sp, planet + "_entries").forEach(result::add);
            String criteria = result.get(r.nextInt(result.size()));

            if (criteria.equals("none"))
            {
                sp.displayClientMessage(Component.literal("There are no entries left to unlock"), true);
                return false;
            }
            sp.displayClientMessage(Component.translatable("tooltip.laicaps.entry_page.unlock.before").append(Component.translatable("gui.astronomy_research_table." + planet + "." + criteria + ".name")).append(Component.translatable("tooltip.laicaps.entry_page.unlock.before")), true);
            AdvHelper.awardAdvancementCriteria(sp, planet + "_entries", criteria);
            return true;
        }

        return false;
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

            if (stack.get(ModDataComponents.FUEL) > maxFuel / 2) color = "§6";
            if (stack.get(ModDataComponents.FUEL) > maxFuel / 1.5) color = "§a";

            tooltipComponents.add(Component.literal(color + I18n.get("tooltip.laicaps.tank.fuel") + ": §l[" + stack.get(ModDataComponents.FUEL) + " / " + maxFuel + "]"));
        }
        else tooltipComponents.add(Component.translatable("tooltip.laicaps.generic.shift_up"));
    }


}
