package com.example.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderTickCounter;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class ExampleClientMixin {
    
    @Shadow @Final private MinecraftClient client;
    
    @Unique
    @Nullable
    private static AttackIndicator attackIndicator = null;
    
    @Inject(method = "renderCrosshair", at = @At("HEAD"))
    public void beforeRenderCrossHair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        // Temporarily disable attack indicator
        if (attackIndicator == null) {
            var option = client.options.getAttackIndicator();
            attackIndicator = option.getValue();
            option.setValue(AttackIndicator.OFF);
        }
    }
    
    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    public void afterRenderCrossHair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        // Restore attack indicator setting
        if (attackIndicator != null) {
            client.options.getAttackIndicator().setValue(attackIndicator);
            attackIndicator = null;
        }
    }
    
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    public void beforeRenderHotBar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        // Temporarily disable attack indicator
        if (attackIndicator == null) {
            var option = client.options.getAttackIndicator();
            attackIndicator = option.getValue();
            option.setValue(AttackIndicator.OFF);
        }
    }
    
    @Inject(method = "renderHotbar", at = @At("TAIL"))
    public void afterRenderHotBar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        // Restore attack indicator setting
        if (attackIndicator != null) {
            client.options.getAttackIndicator().setValue(attackIndicator);
            attackIndicator = null;
        }
    }
}
