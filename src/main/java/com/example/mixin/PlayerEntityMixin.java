package com.example.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
    
    // Modify sword damage to Bedrock values
    @ModifyVariable(
        method = "attack",
        at = @At(value = "STORE", ordinal = 0),
        ordinal = 0
    )
    private float modifyDamage(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack weapon = player.getMainHandStack();
        
        if (weapon.getItem() instanceof SwordItem sword) {
            String material = sword.getMaterial().toString().toLowerCase();
            return switch (material) {
                case "wood" -> 4.0f;
                case "stone" -> 5.0f;
                case "iron" -> 6.0f;
                case "gold" -> 4.0f;  // gold same as wood in Bedrock
                case "diamond" -> 7.0f;
                case "netherite" -> 8.0f;
                default -> damage;
            };
        }
        return damage;
    }
}
