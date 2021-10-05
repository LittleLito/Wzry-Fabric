package com.littlelito.wzry.register

import com.littlelito.wzry.entity.effect.WzryEffects
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class EffectRegister {
    fun register() {
        Registry.register(
            Registry.STATUS_EFFECT,
            Identifier("wzry", "dizziness"),
            WzryEffects.DIZZINESS)

        Registry.register(
            Registry.STATUS_EFFECT,
            Identifier("wzry", "crystal_resistance"),
            WzryEffects.CRYSTAL_RESISTANCE)
        Registry.register(
            Registry.STATUS_EFFECT,
            Identifier("wzry", "crystal_target_blue"),
            WzryEffects.CRYSTAL_TARGET_BLUE)
        Registry.register(
            Registry.STATUS_EFFECT,
            Identifier("wzry", "crystal_target_red"),
            WzryEffects.CRYSTAL_TARGET_RED)

    }
}