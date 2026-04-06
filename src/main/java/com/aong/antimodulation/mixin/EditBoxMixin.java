package com.aong.antimodulation.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EditBox.class)
public class EditBoxMixin {

    @ModifyVariable(method = "renderWidget", at = @At("STORE"), ordinal = 0)
    private String maskTextDisplay(String value) {
        Screen screen = Minecraft.getInstance().screen;

        if (screen != null) {
            String name = screen.getClass().getSimpleName();
            if (name.contains("Server") || name.contains("Direct") || name.contains("Edit")) {
                EditBox self = (EditBox) (Object) this;
                if (self.getY() > 85 && value != null && !value.isEmpty()) {
                    return value.replaceAll("[^.]", "*");
                }
            }
        }
        return value;
    }
}