package com.wdiscute.laicaps.types;

import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.block.astrologytable.AstrologyTableMenu;
import com.wdiscute.laicaps.block.telescope.TelescopeMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Laicaps.MOD_ID);



    public static final DeferredHolder<MenuType<?>, MenuType<TelescopeMenu>> TELESCOPE_MENU =
            registerMenuType("telescope_menu", TelescopeMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AstrologyTableMenu>> ASTROLOGY_TABLE_MENU =
            registerMenuType("astrology_table_menu", AstrologyTableMenu::new);







    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
