package com.wdiscute.laicaps.entity.rocket.rocketparts;

import com.wdiscute.laicaps.block.astronomytable.NotebookMenu;
import com.wdiscute.laicaps.entity.rocket.InteractionsEnum;
import com.wdiscute.laicaps.entity.rocket.RE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RPTable extends RP implements MenuProvider
{

    public RPTable(AABB hitboxSize, Vec3 offsetFromCenter, boolean canRiderInteract, boolean canCollide, RE parentRocket, InteractionsEnum interaction)
    {
        super(hitboxSize, offsetFromCenter, canRiderInteract, canCollide, parentRocket, interaction);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand)
    {

        player.openMenu(new SimpleMenuProvider(this, Component.literal("Astronomy Table")));


        return InteractionResult.SUCCESS;
    }


    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new NotebookMenu(i, inventory);
    }
}
