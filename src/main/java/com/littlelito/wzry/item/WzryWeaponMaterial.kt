package com.littlelito.wzry.item

import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient

class WzryWeaponMaterial(
    private val itemDurability: Int,
    private val miningSpeedMultiplier: Float,
    private val attackDamage: Float,
    private val miningLevel: Int,
    private val enchantAbility: Int,
    private val repairIngredient: Item,
): ToolMaterial {

    override fun getDurability(): Int = itemDurability

    override fun getMiningSpeedMultiplier(): Float = miningSpeedMultiplier

    override fun getAttackDamage(): Float = attackDamage

    override fun getMiningLevel(): Int = miningLevel

    override fun getEnchantability(): Int = enchantAbility

    override fun getRepairIngredient(): Ingredient = Ingredient.ofItems(repairIngredient)
}