package com.littlelito.wzry.entity.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText

class CrystalTarget(val crystalColor: Boolean): StatusEffect(StatusEffectType.NEUTRAL, 0xbf360c) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        super.applyUpdateEffect(entity, amplifier)

        val color = if (crystalColor) "蓝色" else "红色"
        if (entity is PlayerEntity && !entity.hasStatusEffect(WzryEffects.CRYSTAL_RESISTANCE)) {
            for (player in entity.world.players) {
                player.sendMessage(LiteralText("玩家 ${entity.name.string} 已进入${color}方水晶的攻击范围"), false)
            }
        }
    }
}