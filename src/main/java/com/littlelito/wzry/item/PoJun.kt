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

class PoJun: WzrySwordItem(
    WzryWeaponMaterials().POJUN, Settings().rarity(Rarity.RARE).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0,
    0F
) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.pojun.tooltip.description").formatted(Formatting.GRAY, Formatting.ITALIC))
    }

    fun passiveSkill(target: Entity, f: Float): Float {
        return if (target is LivingEntity) {
            if (target.health < target.maxHealth * 0.5) {
                (f * 1.3).toFloat()
            } else f
        } else f
    }
}