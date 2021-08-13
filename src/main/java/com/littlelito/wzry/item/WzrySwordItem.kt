package com.littlelito.wzry.item

import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial

open class WzrySwordItem(material: ToolMaterial,
                         settings: Settings,
                         val critRate: Float = 0F,
                         val penetration: Float = 0F,
                         val attackDamage: Int = 0,
                         val attackSpeed: Float = 0F,
):
    SwordItem(material, attackDamage, attackSpeed, settings.maxCount(1)) {
}
