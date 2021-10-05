package com.littlelito.wzry.entity.effect

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class CrystalResistance: StatusEffect(StatusEffectType.NEUTRAL, 0xaed581) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }
}