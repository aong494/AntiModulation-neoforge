package com.aong.antimodulation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.minecraft.client.gui.screens.Screen;

@EventBusSubscriber(modid = "antimodulation", value = Dist.CLIENT)
public class ShaderScreenBlocker {

    // 차단할 쉐이더 설정창의 클래스 경로들
    private static final String[] BLOCKED_SCREENS = {
            "net.optifine.gui.GuiShaders", // 옵티파인(OptiFine) 쉐이더 창
            "net.irisshaders.iris.gui.screen.ShaderPackScreen", // 아이리스(Iris) / 오큘러스(Oculus) 쉐이더 창
            "net.irisshaders.iris.gui.screen.ShaderPackScreen$1" // 내부 클래스가 열릴 경우를 대비한 추가 경로
    };

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        Screen screen = event.getNewScreen();
        if (screen == null) return;
        String screenClassName = screen.getClass().getName();
        for (String blockedScreen : BLOCKED_SCREENS) {
            if (screenClassName.equals(blockedScreen)) {
                event.setCanceled(true);
                System.out.println("보안 안내: 쉐이더 설정창 접근이 차단되었습니다.");
                break;
            }
        }
    }
}