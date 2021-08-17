package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World

class WuJinZhanRen: WzrySwordItem(
    WzryWeaponMaterials().WUJINZHANREN, Settings().rarity(Rarity.RARE).group(WzryItems.ATTACK_GROUP),
    0.2F,
    0F,
    0,
    -3.8F,
    0F,
    0F
) {

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.wujinzhanren.tooltip.properties"))
        tooltip.add(TranslatableText("item.wzry.wujinzhanren.tooltip.description").formatted(Formatting.GRAY, Formatting.ITALIC))
    }

    fun passiveSkill(critEffect: Float): Float {
        return critEffect + 0.4F
    }
}

