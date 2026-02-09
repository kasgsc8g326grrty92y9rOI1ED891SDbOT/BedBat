package com.example.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
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
        return 1.0f; // always fully ready to attack
    }
    
    // Modify weapon damage to Bedrock values
    @ModifyVariable(
        method = "attack",
        at = @At(value = "STORE", ordinal = 0),
        ordinal = 0
    )
    private float modifyDamage(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack weapon = player.getMainHandStack();
        
        if (!weapon.isEmpty()) {
            Identifier itemId = Registries.ITEM.getId(weapon.getItem());
            String path = itemId.getPath();
            
            // Check for swords
            if (path.endsWith("_sword")) {
                return getSwordDamage(path);
            }
            
            // Check for axes
            if (path.endsWith("_axe")) {
                return getAxeDamage(path);
            }
        }
        return damage;
    }
    
    private float getSwordDamage(String itemPath) {
        return switch (itemPath) {
            case "wooden_sword" -> 4.0f;
            case "golden_sword" -> 4.0f;
            case "stone_sword" -> 5.0f;
            case "iron_sword" -> 6.0f;
            case "diamond_sword" -> 7.0f;
            case "netherite_sword" -> 8.0f;
            default -> 1.0f; // custom swords
        };
    }
    
    private float getAxeDamage(String itemPath) {
        return switch (itemPath) {
            case "wooden_axe" -> 5.0f;
            case "golden_axe" -> 5.0f;
            case "stone_axe" -> 6.0f;
            case "iron_axe" -> 7.0f;
            case "diamond_axe" -> 8.0f;
            case "netherite_axe" -> 9.0f;
            default -> 1.0f; // custom axes
        };
    }
}
