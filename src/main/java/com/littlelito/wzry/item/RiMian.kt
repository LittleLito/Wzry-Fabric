package com.littlelito.wzry.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.util.Rarity

class RiMian: WzryAxeItem(
    WzryWeaponMaterials().RIMIAN, Settings().rarity(Rarity.UNCOMMON).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0F,
    0,
    -3.9F,
    0F,
    0F,
) {
    private val attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier>
    init {
        val builder = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        builder.put(
            EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(
                ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier",
                this.attackDamage.toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )
        builder.put(
            EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(
                ATTACK_SPEED_MODIFIER_ID, "Tool modifier",
                attackSpeed.toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )
        builder.put(
            EntityAttributes.GENERIC_MAX_HEALTH, EntityAttributeModifier(
                MAX, "Tool modifier",
                (3).toDouble(), EntityAttributeModifier.Operation.ADDITION
            )
        )
        attributeModifiers = builder.build()
    }
    override fun getAttributeModifiers(slot: EquipmentSlot?): Multimap<EntityAttribute, EntityAttributeModifier> {
        return this.attributeModifiers
    }
}