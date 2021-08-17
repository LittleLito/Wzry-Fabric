package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Rarity
import net.minecraft.world.World

class XiXueZhiLian: WzryAxeItem(
    WzryWeaponMaterials().XIXUEZHILIAN, Settings().rarity(Rarity.COMMON).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0,
    -3.9F,
    0F,
    0.08F
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.xixuezhilian.tooltip.properties"))
    }
}