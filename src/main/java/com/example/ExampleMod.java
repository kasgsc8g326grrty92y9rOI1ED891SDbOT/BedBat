package com.example;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class ExampleMod implements ModInitializer {

    private static final UUID DAMAGE_MODIFIER = UUID.fromString("9f3a88a8-5f3e-4a4a-9a1d-1f2fda0e4b0c");

    @Override
    public void onInitialize() {
        System.out.println("[BedrockCombat] Mod initialized!");

        // Apply Bedrock sword damage to all players every tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                applyBedrockDamage(player);
            }
        });
    }

    private void applyBedrockDamage(PlayerEntity player) {
        // Set base attack damage and attack speed for main-hand item
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof SwordItem si) {
            double damage = getBedrockDamage(si);
            si.getAttributeModifiers(EquipmentSlot.MAINHAND).put(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    new EntityAttributeModifier(DAMAGE_MODIFIER, "bedrock_damage", damage, EntityAttributeModifier.Operation.ADDITION)
            );
            // Set high attack speed for instant hits
            player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED)
                  .setBaseValue(1024.0);
        }
    }

    private double getBedrockDamage(SwordItem sword) {
        switch (sword.getMaterial().getName()) {
            case "wood" -> { return 4; }
            case "stone" -> { return 5; }
            case "iron" -> { return 6; }
            case "diamond" -> { return 7; }
            case "netherite" -> { return 8; }
        }
        return 1;
    }
}
