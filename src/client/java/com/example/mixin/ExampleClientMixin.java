package com.example.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ExampleClientMixin {
    
    // Make the cooldown indicator always show as ready
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void hideAttackCooldown(float baseTime, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0f); // Always show as fully charged
    }
}
