package com.example.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // Completely remove attack cooldown
    @Redirect(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"
        )
    )
    private float removeCooldown(PlayerEntity player, float baseTime) {
        return 1.0f; // always fully ready to attack
    }
}
