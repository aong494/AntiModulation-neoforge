package com.aong.antimodulation;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = "antimodulation", value = Dist.CLIENT)
public class SecurityClientHandler {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        String name = screen.getClass().getSimpleName();

        // 서버 관련 창이 열릴 때
        if (name.contains("EditServer") || name.contains("Direct")) {
            int count = 0;
            for (var child : screen.children()) {
                if (child instanceof EditBox editBox) {
                    count++;
                    // 주소창(2번째 박스 또는 직접연결 1번째)을 찾으면
                    if ((name.contains("Direct") && count == 1) || count == 2) {
                    }
                }
            }
        }
    }
}