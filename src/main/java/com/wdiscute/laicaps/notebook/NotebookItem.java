package com.wdiscute.laicaps.notebook;

import com.wdiscute.laicaps.item.EntryUnlockableItem;
import com.wdiscute.laicaps.util.AdvHelper;
import com.wdiscute.laicaps.block.astronomytable.NotebookMenu;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class NotebookItem extends EntryUnlockableItem implements MenuProvider
{
    public NotebookItem(Properties properties, String adv, String criteria)
    {
        super(properties, adv, criteria);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {
        player.openMenu(this);
        if(player instanceof ServerPlayer sp && !AdvHelper.hasAdvancementCriteria(sp, "menu_entries", "entry2"))
        {
            AdvHelper.awardAdvancementCriteria(sp, "menu_entries", "entry2");
            PacketDistributor.sendToPlayer(sp, new Payloads.EntryUnlockedPayload("menu_entries", "entry2"));
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("notebook");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new NotebookMenu(i, inventory);
    }
}
