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
    val BUXUE = WzryArmorMaterial(
        "bu_xue",
        EquipmentSlot.FEET,
        300,
        0,
        6,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        3F,
        Items.LEATHER,
        0F
    )
    // UNCOMMON
    val DIKANGZHIXUE = WzryArmorMaterial(
        "di_kang_zhi_xue",
        EquipmentSlot.FEET,
        1000,
        110,
        12,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        4F,
        Items.LEATHER,
        0F
    )
    val BAOLIZHIXUE = WzryArmorMaterial(
        "bao_li_zhi_xue",
        EquipmentSlot.FEET,
        1000,
        0,
        12,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        4F,
        Items.LEATHER,
        0F
    )
    val LILIANGYAODAI = WzryArmorMaterial(
        "li_liang_yao_dai",
        EquipmentSlot.LEGS,
        1000,
        0,
        12,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        4F,
        Items.IRON_BLOCK,
        0F
    )
    val JINJINDELILIANG = WzryArmorMaterial(
        "jin_jin_de_li_liang",
        EquipmentSlot.HEAD,
        1200,
        120,
        12,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        4F,
        Items.REDSTONE_BLOCK,
        0F
    )
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