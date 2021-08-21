package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Rarity
import net.minecraft.world.World

class HongMaNao: Item(
    Settings().rarity(Rarity.COMMON).group(WzryItems.DEFEND_GROUP)
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.hongmanao.tooltip.properties"))
    }
}