package com.littlelito.wzry.item

import net.minecraft.item.AxeItem
import net.minecraft.item.ToolMaterial

class WzryAxeItem(material: ToolMaterial,
                  settings: Settings,
                  attackDamage: Float,
                  val attackSpeed: Float,
                  ) :
    AxeItem(material, attackDamage, attackSpeed, settings.maxCount(1)) {
}