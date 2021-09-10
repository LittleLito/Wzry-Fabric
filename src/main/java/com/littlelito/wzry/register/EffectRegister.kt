package com.littlelito.wzry.register

import com.littlelito.wzry.entity.effect.WzryEffects
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class EffectRegister {
    fun register() {
        Registry.register(Registry.STATUS_EFFECT, Identifier("wzry", "dizziness"), WzryEffects.DIZZINESS)
    }
}