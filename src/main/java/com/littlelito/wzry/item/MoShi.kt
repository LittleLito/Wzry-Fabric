package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World

class MoShi: WzryAxeItem(
    WzryWeaponMaterials().MOSHI, Settings().rarity(Rarity.RARE).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0F,
    0,
    -3.8F,
    0.3F,
    0.1F
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.moshi.tooltip.properties1"))
        tooltip.add(TranslatableText("item.wzry.moshi.tooltip.properties2"))
        tooltip.add(TranslatableText("item.wzry.moshi.tooltip.description").formatted(Formatting.ITALIC, Formatting.GRAY))
    }

    fun passiveSkill(float: Float, target: Entity): Float {
        return if (target is LivingEntity) {
            float + (target.health * 0.08).toFloat()
        } else float
    }
}