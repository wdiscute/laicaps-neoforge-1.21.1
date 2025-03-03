package com.wdiscute.laicaps.event;


import com.wdiscute.laicaps.Laicaps;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = Laicaps.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents
{

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event)
    {
        if (event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player)
        {
            if (player.isCrouching() && player.getMainHandItem().getItem() == Items.SLIME_BALL)
            {
                sheep.moveTo(sheep.position().x, sheep.position().y + 4f, sheep.position().z);
            }
        }
    }

}
