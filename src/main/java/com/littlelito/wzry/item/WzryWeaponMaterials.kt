package com.littlelito.wzry.item

import net.minecraft.item.Item
import net.minecraft.item.Items

class WzryWeaponMaterials {

    // COMMON
    val TIEJIAN = WzryWeaponMaterial(
        50,
        2F,
        2F,
        1,
        3,
        Items.OAK_PLANKS
    )

    // UNCOMMON
    val FENGBAOJUJIAN = WzryWeaponMaterial(
        100,
        3F,
        8F,
        1,
        8,
        Items.OAK_PLANKS
    )

    // RARE
    val WUJINZHANREN = WzryWeaponMaterial(
        150,
        5F,
        13F,
        3,
        15,
        Items.OAK_PLANKS
    )
    val POJUN = WzryWeaponMaterial(
        250,
        2F,
        18F,
        3,
        10,
        Items.OAK_PLANKS
    )
}