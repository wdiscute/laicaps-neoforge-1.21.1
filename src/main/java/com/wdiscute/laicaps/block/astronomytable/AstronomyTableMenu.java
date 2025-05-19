package com.wdiscute.laicaps.block.astronomytable;

import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.types.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AstronomyTableMenu extends AbstractContainerMenu {
    public final AstronomyTableBlockEntity blockEntity;
    public final Level level;


    @Override
    public boolean clickMenuButton(Player player, int id) {

        if (player instanceof ServerPlayer sp) {
            if (id == 1) {

                AdvHelper.awardAdvancementCriteria(sp, "bookmarks", "asha_entry1");

            }
        }

        return super.clickMenuButton(player, id);
    }

    public AstronomyTableMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public AstronomyTableMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.ASTRONOMY_TABLE_MENU.get(), containerId);
        this.blockEntity = ((AstronomyTableBlockEntity) blockEntity);
        this.level = inv.player.level();
    }


    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ASTRONOMY_RESEARCH_TABLE.get());
    }
}
