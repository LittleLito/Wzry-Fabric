package com.littlelito.wzry.item

import net.minecraft.item.AxeItem
import net.minecraft.item.ToolMaterial

open class WzryAxeItem(material: ToolMaterial,
                       settings: Settings,
                       val critRate: Float = 0F,
                       val penetration: Float = 0F,
                       val penetrationPercentage: Float = 0F,
                       attackDamage: Int = 0,
                       val attackSpeed: Float,
                       val attackSpeedPercentage: Float = 0F,
                       val suckingHealthPercentage: Float = 0F
                  ) :
    AxeItem(material, attackDamage.toFloat(), attackSpeed, settings.maxCount(1)) {
}