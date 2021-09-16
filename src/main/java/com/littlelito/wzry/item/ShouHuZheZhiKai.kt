package com.littlelito.wzry.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World

class ShouHuZheZhiKai(
    private val material: WzryArmorMaterial = WzryArmorMaterials().SHOUHUZHEZHIKAI,
    private val slot: EquipmentSlot = EquipmentSlot.CHEST,
    val settings: Settings = Settings().rarity(Rarity.UNCOMMON).group(WzryItems.DEFEND_GROUP)
): ArmorItem(
    material, slot, settings
) {

    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.shouhuzhezhikai.tooltip.description").formatted(Formatting.ITALIC, Formatting.GRAY))
    }

}