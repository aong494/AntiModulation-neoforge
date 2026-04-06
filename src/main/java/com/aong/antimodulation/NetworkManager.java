package com.aong.antimodulation;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkManager {
    public record VerifyPayload() implements CustomPacketPayload {
        // 서버 플러그인과 동일한 채널 이름 "antimodulation:verify"
        public static final CustomPacketPayload.Type<VerifyPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("antimodulation", "verify"));
        public static final StreamCodec<FriendlyByteBuf, VerifyPayload> CODEC = StreamCodec.unit(new VerifyPayload());

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    @EventBusSubscriber(modid = "antimodulation")
    public static class ModBusEvents {
        @SubscribeEvent
        public static void register(RegisterPayloadHandlersEvent event) {
            final PayloadRegistrar registrar = event.registrar("antimodulation").optional();

            registrar.playToServer(
                    VerifyPayload.TYPE,
                    VerifyPayload.CODEC,
                    (payload, context) -> {
                    }
            );
        }
    }
    @EventBusSubscriber(modid = "antimodulation", value = Dist.CLIENT)
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
            if (net.minecraft.client.Minecraft.getInstance().getConnection() != null) {
                net.minecraft.client.Minecraft.getInstance().getConnection().send(new VerifyPayload());
                System.out.println("보안 안내: 서버로 인증 신호를 전송했습니다!");
            }
        }
    }
}