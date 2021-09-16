package com.littlelito.wzry.entity.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.entity.effect.StatusEffects

class Dizziness: StatusEffect(StatusEffectType.HARMFUL, 0x98D982) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        entity.addStatusEffect(StatusEffectInstance(StatusEffects.NAUSEA, 100, 4, true, false, true))


    }

    override fun addAttributeModifier(
        attribute: EntityAttribute,
        uuid: String?,
        amount: Double,
        operation: EntityAttributeModifier.Operation
    ): StatusEffect {
        return if (uuid == null) {
            val entityAttributeModifier = EntityAttributeModifier(
                this.translationKey, amount, operation
            )
            this.attributeModifiers[attribute] = entityAttributeModifier
            this
        } else {
            super.addAttributeModifier(attribute, uuid, amount, operation)
        }
    }



}