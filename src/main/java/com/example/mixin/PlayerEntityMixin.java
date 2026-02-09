package com.example.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
        return 1.0f;
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
        
        if (!weapon.isEmpty()) {
            String itemId = weapon.getItem().toString().toLowerCase();
            
            if (itemId.contains("sword")) {
                return getSwordDamage(itemId);
            }
        }
        return damage;
    }
    
    private float getSwordDamage(String itemId) {
        if (itemId.contains("wooden") || itemId.contains("wood")) {
            return 4.0f;
        } else if (itemId.contains("stone")) {
            return 5.0f;
        } else if (itemId.contains("iron")) {
            return 6.0f;
        } else if (itemId.contains("golden") || itemId.contains("gold")) {
            return 4.0f;
        } else if (itemId.contains("diamond")) {
            return 7.0f;
        } else if (itemId.contains("netherite")) {
            return 8.0f;
        }
        return 1.0f; // fallback for custom swords
    }
}
