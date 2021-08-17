package com.littlelito.wzry.item

import net.minecraft.item.AxeItem
import net.minecraft.item.ToolMaterial

open class WzryAxeItem(material: ToolMaterial,
                       settings: Settings,
                       val critRate: Float = 0F,
                       val penetration: Float = 0F,
                       val attackDamage: Int = 0,
                       val attackSpeed: Float,
                       val attackSpeedPercentage: Float,
                       val suckingHealthPercentage: Float = 0F
                  ) :
    AxeItem(material, attackDamage.toFloat(), attackSpeed, settings.maxCount(1)) {
}