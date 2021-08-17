package com.littlelito.wzry.item

import net.minecraft.util.Rarity

class FengBaoJuJian: WzrySwordItem(
    WzryWeaponMaterials().FENGBAOJUJIAN, Settings().rarity(Rarity.UNCOMMON).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0,
    -3.9F,
    0F,
    0F
) {
}