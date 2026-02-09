package com.example.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ExampleClientMixin {
    
    @Shadow
    public abstract void resetLastAttackedTicks();
    
    // Reset attack indicator every tick so it never shows
    @Inject(method = "tick", at = @At("HEAD"))
    private void resetAttackIndicator(CallbackInfo ci) {
        this.resetLastAttackedTicks();
    }
}
