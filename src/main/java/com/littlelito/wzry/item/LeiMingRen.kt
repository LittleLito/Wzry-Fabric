package com.littlelito.wzry.item

import net.minecraft.util.Rarity

class LeiMingRen:WzrySwordItem(
    WzryWeaponMaterials().LEIMINGREN, Settings().rarity(Rarity.COMMON).group(WzryItems.ATTACK_GROUP),
    0F,
    0F,
    0F,
    0,
    -3.9F,
    0F,
    0F)