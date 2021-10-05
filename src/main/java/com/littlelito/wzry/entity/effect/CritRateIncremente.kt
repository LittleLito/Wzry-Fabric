package com.littlelito.wzry.entity.effect

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class CritRateIncremente: StatusEffect(StatusEffectType.BENEFICIAL, 0xff6f00) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
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
            attributeModifiers[attribute] = entityAttributeModifier
            this
        } else {
            super.addAttributeModifier(attribute, uuid, amount, operation)
        }
    }
}