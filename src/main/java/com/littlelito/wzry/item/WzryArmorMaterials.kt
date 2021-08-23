package com.littlelito.wzry.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents

class WzryArmorMaterials {
    // COMMON
    // UNCOMMON
    // RARE
    val FANJIA = WzryArmorMaterial(
        "fan_jia",
        EquipmentSlot.CHEST,
        3500,
        350,
        20,
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        5F,
        Items.REDSTONE_BLOCK,
        0F
    )
}