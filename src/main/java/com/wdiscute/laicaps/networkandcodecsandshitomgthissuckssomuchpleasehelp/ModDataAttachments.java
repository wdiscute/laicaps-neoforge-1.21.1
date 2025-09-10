package com.wdiscute.laicaps.networkandcodecsandshitomgthissuckssomuchpleasehelp;

import com.mojang.serialization.Codec;
import com.wdiscute.laicaps.Laicaps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ModDataAttachments
{
    // Create the DeferredRegister for attachment types
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, Laicaps.MOD_ID);


    public static final Supplier<AttachmentType<String>> FISHING = ATTACHMENT_TYPES.register(
            "laicaps_fishing", () -> AttachmentType.builder(() -> "").serialize(Codec.unit("")).build()
    );

    public static final Supplier<AttachmentType<List<FishCaughtCounter>>> FISHES_CAUGHT = ATTACHMENT_TYPES.register(
            "fishes_caught", () ->
                    AttachmentType.builder(() -> List.of(new FishCaughtCounter(Laicaps.rl("fishes_caught"), 0)))
                            .serialize(FishCaughtCounter.LIST_CODEC)
                            .sync(new FishCounterSyncHandler())
                            .copyOnDeath()
                            .build()
    );


    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }


    public static class FishCounterSyncHandler implements AttachmentSyncHandler<List<FishCaughtCounter>>
    {
        @Override
        public void write(@NotNull RegistryFriendlyByteBuf buf, @NotNull List<FishCaughtCounter> attachment, boolean initialSync)
        {
            FishCaughtCounter.STREAM_CODEC.encode(buf, attachment);
        }

        @Override
        @Nullable
        public List<FishCaughtCounter> read(@NotNull IAttachmentHolder holder, @NotNull RegistryFriendlyByteBuf buf, @Nullable List<FishCaughtCounter> previousValue)
        {
            return FishCaughtCounter.STREAM_CODEC.decode(buf);
        }

        @Override
        public boolean sendToPlayer(@NotNull IAttachmentHolder holder, @NotNull ServerPlayer to)
        {
            return holder == to;
        }
    }


}
