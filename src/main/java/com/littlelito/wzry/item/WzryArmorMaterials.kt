package com.littlelito.wzry.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents

class WzryArmorMaterials {
    // COMMON
    val BUJIA = WzryArmorMaterial(
        "bu_jia",
        EquipmentSlot.CHEST,
        500,
        90,
        8,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        3F,
        Items.LEATHER,
        0F
    )
    val BUHUTUI = WzryArmorMaterial(
        "bu_hu_tui",
        EquipmentSlot.LEGS,
        500,
        90,
        8,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        3F,
        Items.LEATHER,
        0F
    )
    val BUTOUKUI = WzryArmorMaterial(
        "bu_tou_kui",
        EquipmentSlot.HEAD,
        500,
        90,
        8,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        3F,
        Items.LEATHER,
        0F
    )
    // UNCOMMON
    // RARE
    val FANJIA = WzryArmorMaterial(
        "fan_jia",
        EquipmentSlot.CHEST,
        3500,
        360,
        20,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        5F,
        Items.REDSTONE_BLOCK,
        0F
    )
}