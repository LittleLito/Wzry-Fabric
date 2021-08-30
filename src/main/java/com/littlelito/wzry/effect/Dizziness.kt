package com.littlelito.wzry.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.entity.effect.StatusEffects

class Dizziness() : StatusEffect(StatusEffectType.HARMFUL, 0x98D982) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        entity.addStatusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 1, 99999))
    }
}