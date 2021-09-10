package com.littlelito.wzry.entity.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class Dizziness: StatusEffect(StatusEffectType.HARMFUL, 0x98D982) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        /*val moveSpeedModifier = EntityAttributeModifier("Speed Modifier", (0).toDouble(), EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
        speedUUID = moveSpeedModifier.id

        val moveSpeed = entity.attributes.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
        try {
            moveSpeed!!.removeModifier(speedUUID)
        } catch (e: Exception) {

        }
        moveSpeed!!.addTemporaryModifier(moveSpeedModifier)*/
        /*entity.addStatusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 2, 99999))
        entity.addStatusEffect(StatusEffectInstance(StatusEffects.WEAKNESS, 2, 99999))*/


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