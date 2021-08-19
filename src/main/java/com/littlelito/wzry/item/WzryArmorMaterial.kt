package com.littlelito.wzry.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent

class WzryArmorMaterial(
    val materialName: String,
    val equipmentSlot: EquipmentSlot,
    val durability: Int,
    val protection: Int,
    val enchantAbility: Int,
    val armorEquipSound: SoundEvent,
    val armorToughness: Float,
    val repairIngredient: Item,
    val armorKnockbackResistance: Float
): ArmorMaterial {
    override fun getDurability(slot: EquipmentSlot?): Int {
        return if (slot == equipmentSlot) {
            durability
        } else 0
    }

    override fun getProtectionAmount(slot: EquipmentSlot?): Int {
        return if (slot == equipmentSlot) {
            protection
        } else 0
    }

    override fun getEnchantability(): Int {
        return enchantAbility
    }

    override fun getEquipSound(): SoundEvent {
        return armorEquipSound
    }

    override fun getRepairIngredient(): Ingredient {
        return Ingredient.ofItems(repairIngredient)
    }

    @Environment(EnvType.CLIENT)
    override fun getName(): String {
        return materialName
    }

    override fun getToughness(): Float {
        return armorToughness
    }

    override fun getKnockbackResistance(): Float {
        return armorKnockbackResistance
    }
}