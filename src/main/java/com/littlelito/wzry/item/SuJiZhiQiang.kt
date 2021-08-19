package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.RangedWeaponItem
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World

class SuJiZhiQiang: WzrySwordItem(
    WzryWeaponMaterials().SUJIZHIQIANG, Settings().rarity(Rarity.UNCOMMON).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0,
    -3.8F,
    0.25F,
    0F
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.sujizhiqiang.tooltip.properties"))
        tooltip.add(TranslatableText("item.wzry.sujizhiqiang.tooltip.description").formatted(Formatting.ITALIC, Formatting.GRAY))
    }

    fun passiveSkill(f: Float, playerEntity: PlayerEntity): Float {
        return if (playerEntity.activeItem.item is RangedWeaponItem) {
            f + 6
        } else f + 3
    }
}