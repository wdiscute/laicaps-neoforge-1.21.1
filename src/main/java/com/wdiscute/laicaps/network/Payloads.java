package com.wdiscute.laicaps.network;

import com.wdiscute.laicaps.Laicaps;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;


public class Payloads
{

    public record FishingPayload(ItemStack stack, ItemStack bobber, ItemStack bait, int difficulty) implements CustomPacketPayload
    {

        public static final CustomPacketPayload.Type<FishingPayload> TYPE = new CustomPacketPayload.Type<>(Laicaps.rl("fishing"));

        public static final StreamCodec<ByteBuf, FishingPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.fromCodec(ItemStack.CODEC),
                FishingPayload::stack,
                ByteBufCodecs.fromCodec(ItemStack.CODEC),
                FishingPayload::bobber,
                ByteBufCodecs.fromCodec(ItemStack.CODEC),
                FishingPayload::bait,
                ByteBufCodecs.INT,
                FishingPayload::difficulty,
                FishingPayload::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }


    public record FishingCompletedPayload(int time) implements CustomPacketPayload
    {

        public static final CustomPacketPayload.Type<FishingCompletedPayload> TYPE = new CustomPacketPayload.Type<>(Laicaps.rl("fishing_completed"));

        public static final StreamCodec<ByteBuf, FishingCompletedPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                FishingCompletedPayload::time,
                FishingCompletedPayload::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }


    public record ToastPayload(String menuName, String entryName) implements CustomPacketPayload
    {

        public static final CustomPacketPayload.Type<ToastPayload> TYPE = new CustomPacketPayload.Type<>(Laicaps.rl("sendToast"));

        public static final StreamCodec<ByteBuf, ToastPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                ToastPayload::menuName,
                ByteBufCodecs.STRING_UTF8,
                ToastPayload::entryName,
                ToastPayload::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }


}
