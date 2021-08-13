package com.littlelito.wzry.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity

class PoJun() : WzrySwordItem(
    WzryWeaponMaterials().POJUN, Settings(),
    0F,
    0F,
    0,
    0F
) {
    fun passiveSkill(target: Entity, f: Float): Float {
        if (target is LivingEntity) {
            if (target.health < target.maxHealth * 0.5) {
                return (f * 1.3).toFloat()
            } else return f
        } else return f
    }
}