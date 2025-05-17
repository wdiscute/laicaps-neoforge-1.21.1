package com.wdiscute.laicaps;

import com.google.common.collect.Iterables;
import com.wdiscute.laicaps.mixin.AdvancementProgressAcessor;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class AdvHelper
{
    public static boolean hasAdvancement(ClientAdvancements clientAdvancements, String achievementName)
    {
        return hasAdvancement(clientAdvancements, Laicaps.MOD_ID, achievementName);
    }

    public static boolean hasAdvancement(ClientAdvancements clientAdvancements, String namespace, String advancementName)
    {
        AdvancementHolder adv = clientAdvancements.get(ResourceLocation.fromNamespaceAndPath(namespace, advancementName));

        if (clientAdvancements instanceof AdvancementProgressAcessor acessor && adv != null)
            return Iterables.size(acessor.getProgress().get(adv).getCompletedCriteria()) > 0;
        else
            return false;
    }





    public static Iterable<String> getEntriesCompletedAsIterable(ClientAdvancements clientAdvancements, String achievementName)
    {
        return getEntriesCompletedAsIterable(clientAdvancements, Laicaps.MOD_ID, achievementName);
    }

    public static Iterable<String> getEntriesCompletedAsIterable(ClientAdvancements clientAdvancements, String namespace, String advancementName)
    {
        AdvancementHolder adv = clientAdvancements.get(ResourceLocation.fromNamespaceAndPath(namespace, advancementName));

        if (clientAdvancements instanceof AdvancementProgressAcessor acessor && adv != null)
        {
            return acessor.getProgress().get(adv).getCompletedCriteria();
        } else
        {
            List<String> list = new ArrayList<>();
            list.add("none");
            return list;
        }
    }


    public static int getEntriesCompletedFromAdvancement(ClientAdvancements clientAdvancements, String achievementName)
    {
        return getEntriesCompletedFromAdvancement(clientAdvancements, Laicaps.MOD_ID, achievementName);
    }

    public static int getEntriesCompletedFromAdvancement(ClientAdvancements clientAdvancements, String namespace, String advancementName)
    {
        AdvancementHolder adv = clientAdvancements.get(ResourceLocation.fromNamespaceAndPath(namespace, advancementName));

        if (clientAdvancements instanceof AdvancementProgressAcessor acessor && adv != null)
        {
            return Iterables.size(acessor.getProgress().get(adv).getCompletedCriteria());
        } else
        {
            return 0;
        }
    }


    public static void awardAdvancementCriteria(ServerPlayer player, String achievementName, String criteria)
    {
        awardAdvancementCriteria(player, Laicaps.MOD_ID, achievementName, criteria);
    }


    public static void awardAdvancementCriteria(ServerPlayer player, String namespace, String achievementName, String criteria)
    {
        AdvancementHolder advHolder = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(namespace, achievementName));
        if (advHolder != null)
        {
            player.getAdvancements().award(advHolder, criteria);
        }

    }

    public static void awardAdvancement(ServerPlayer player, String achievementName)
    {
        awardAdvancement(player, Laicaps.MOD_ID, achievementName);
    }

    public static void awardAdvancement(ServerPlayer player, String namespace, String achievementName)
    {
        AdvancementHolder advHolder = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(namespace, achievementName));

        if (advHolder != null)
        {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advHolder);

            if (!progress.isDone())
            {
                for (String criteria : progress.getRemainingCriteria()) player.getAdvancements().award(advHolder, criteria);
            }
        }
    }


    public static Iterable<String> getEntriesRemainingAsIterable(ServerPlayer player, String achievementName)
    {
        return getEntriesRemainingAsIterable(player, Laicaps.MOD_ID, achievementName);
    }

    public static Iterable<String> getEntriesRemainingAsIterable(ServerPlayer player, String namespace, String achievementName)
    {
        AdvancementHolder advHolder = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(namespace, achievementName));

        if (advHolder != null)
        {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advHolder);

            if (!progress.isDone())
            {
                return progress.getRemainingCriteria();
            }
        }

        List<String> list = new ArrayList<>();
        list.add("none");
        return list;
    }
}
