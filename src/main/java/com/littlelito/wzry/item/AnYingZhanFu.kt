package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World

class AnYingZhanFu: WzryAxeItem(
    WzryWeaponMaterials().ANYINGZHANFU, Settings().rarity(Rarity.RARE).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0F,
    0,
    -3.8F,
    0.15F,
    0F
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.anyingzhanfu.tooltip.properties"))
        tooltip.add(TranslatableText("item.wzry.anyingzhanfu.tooltip.description1").formatted(Formatting.ITALIC, Formatting.GRAY))
        tooltip.add(TranslatableText("item.wzry.anyingzhanfu.tooltip.description2").formatted(Formatting.ITALIC, Formatting.GRAY))
    }

    fun passiveSkill(playerEntity: PlayerEntity): Int {
        val level: Int = playerEntity.experienceLevel
        if (level == 0) { return 0 }
        return if (level <= 15) {
            60 + (level - 1) * 10
        } else {
            200
        }
    }
}