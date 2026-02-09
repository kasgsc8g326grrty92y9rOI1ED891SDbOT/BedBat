package com.example.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.AttackIndicatorStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class ExampleClientMixin {
    
    @Shadow @Final private Minecraft minecraft;
    
    @Unique
    @Nullable
    private static AttackIndicatorStatus attackIndicator = null;
    
    @Inject(method = "renderCrosshair", at = @At("HEAD"))
    public void beforeRenderCrossHair(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        // Temporarily disable attack indicator
        if (attackIndicator == null) {
            var option = minecraft.options.attackIndicator();
            attackIndicator = option.get();
            option.set(AttackIndicatorStatus.OFF);
        }
    }
    
    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    public void afterRenderCrossHair(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        // Restore attack indicator setting
        if (attackIndicator != null) {
            minecraft.options.attackIndicator().set(attackIndicator);
            attackIndicator = null;
        }
    }
    
    @Inject(method = "renderItemHotbar", at = @At("HEAD"))
    public void beforeRenderHotBar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        // Temporarily disable attack indicator
        if (attackIndicator == null) {
            var option = minecraft.options.attackIndicator();
            attackIndicator = option.get();
            option.set(AttackIndicatorStatus.OFF);
        }
    }
    
    @Inject(method = "renderItemHotbar", at = @At("TAIL"))
    public void afterRenderHotBar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        // Restore attack indicator setting
        if (attackIndicator != null) {
            minecraft.options.attackIndicator().set(attackIndicator);
            attackIndicator = null;
        }
    }
}
