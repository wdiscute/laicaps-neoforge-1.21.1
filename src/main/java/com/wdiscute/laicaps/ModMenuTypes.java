package com.wdiscute.laicaps;

import com.wdiscute.laicaps.block.astronomytable.AstronomyTableMenu;
import com.wdiscute.laicaps.block.refuelstation.RefuelStationMenu;
import com.wdiscute.laicaps.block.researchstation.ResearchStationMenu;
import com.wdiscute.laicaps.block.telescope.TelescopeMenu;
import com.wdiscute.laicaps.entity.rocket.RocketSpaceMenu;
import com.wdiscute.laicaps.fishing.FishingRodMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Laicaps.MOD_ID);



    public static final DeferredHolder<MenuType<?>, MenuType<TelescopeMenu>> TELESCOPE_MENU =
            registerMenuType("telescope_menu", TelescopeMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AstronomyTableMenu>> ASTRONOMY_TABLE_MENU =
            registerMenuType("astronomy_table_menu", AstronomyTableMenu::new);

    public static final Supplier<MenuType<RocketSpaceMenu>> ROCKET_SPACE_MENU =
            registerMenuType("rocket_space_menu", RocketSpaceMenu::new);

    public static final Supplier<MenuType<RefuelStationMenu>> REFUEL_STATION_MENU =
            registerMenuType("refuel_station_menu", RefuelStationMenu::new);

    public static final Supplier<MenuType<ResearchStationMenu>> RESEARCH_STATION_MENU =
            registerMenuType("research_station_menu", ResearchStationMenu::new);

    public static final Supplier<MenuType<FishingRodMenu>> FISHING_ROD_MENU =
            registerMenuType("fishing_rod_menu", FishingRodMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
