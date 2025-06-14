package com.wdiscute.laicaps.block.researchstation;

import com.wdiscute.laicaps.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModBlocks;
import com.wdiscute.laicaps.ModMenuTypes;
import com.wdiscute.laicaps.block.astronomytable.AstronomyTableBlockEntity;
import com.wdiscute.laicaps.block.refuelstation.RefuelStationBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ResearchStationMenu extends AbstractContainerMenu
{
    public final ResearchStationBlockEntity blockEntity;
    public final Level level;


    @Override
    public boolean clickMenuButton(Player player, int id)
    {

        return super.clickMenuButton(player, id);
    }

    public ResearchStationMenu(int containerId, Inventory inv, FriendlyByteBuf extraData)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ResearchStationMenu(int containerId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModMenuTypes.RESEARCH_STATION_MENU.get(), containerId);
        this.blockEntity = ((ResearchStationBlockEntity) blockEntity);
        this.level = inv.player.level();
    }


    @Override
    public ItemStack quickMoveStack(Player player, int i)
    {
        return null;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(
                ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.RESEARCH_STATION.get());
    }
}
