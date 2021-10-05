package com.littlelito.wzry.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.block.DispenserBlock
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity
import net.minecraft.world.World
import java.util.*

class FanShangCiJia(
    private val material: WzryArmorMaterial = WzryArmorMaterials().FANJIA,
    private val slot: EquipmentSlot = EquipmentSlot.CHEST,
    val settings: Settings = Settings().rarity(Rarity.RARE).group(WzryItems.DEFEND_GROUP)
): ArmorItem(
    material, slot, settings
) {
    private val MODIFIERS = arrayOf(
        UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
        UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
        UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
    )
    private val attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier>

    init {
        val protection = material.getProtectionAmount(slot)
        val toughness = material.toughness
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR)
        val builder = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        val uUID = MODIFIERS[slot.entitySlotId]
        builder.put(
            EntityAttributes.GENERIC_ARMOR, EntityAttributeModifier(
                uUID, "Armor modifier",
                protection.toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )
        builder.put(
            EntityAttributes.GENERIC_ARMOR_TOUGHNESS, EntityAttributeModifier(
                uUID, "Armor toughness",
                toughness.toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )
        builder.put(
            EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(
                uUID, "Armor attack",
                (3).toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )

        attributeModifiers = builder.build()
    }

    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        tooltip.add(TranslatableText("item.wzry.fanjia.tooltip.description1").formatted(Formatting.ITALIC, Formatting.GRAY))
        tooltip.add(TranslatableText("item.wzry.fanjia.tooltip.description2").formatted(Formatting.ITALIC, Formatting.GRAY))
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return if (slot == this.slot) this.attributeModifiers else super.getAttributeModifiers(slot)
    }
}